package com.chanshiguan.quentincommon.http;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * Created by jie.wang
 * on 2018/6/26
 */
public class HttpClientManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientManager.class);

    private static final int CONNECT_TIMEOUT = 5000;
    private static final int REQUEST_TIMEOUT = 5000;
    private static final int SOCKET_TIMEOUT = 5000;

    private PoolingHttpClientConnectionManager connectionManager;
    private RequestConfig requestConfig;
    private ConnectionKeepAliveStrategy keepAliveStrategy;
    private IdleConnectionMonitorThread monitorThread;
    private CloseableHttpClient client;

    private PoolingHttpClientConnectionManager connectionTLSManager;
    private CloseableHttpClient TLSClient;

    private HttpClientManager() {
        connectionManager = new PoolingHttpClientConnectionManager();
        // Increase max total connection to 200
        connectionManager.setMaxTotal(1000);
        // Increase default max connection per route to 20
        connectionManager.setDefaultMaxPerRoute(200);

        requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(REQUEST_TIMEOUT).setCookieSpec(CookieSpecs.DEFAULT).build();

        keepAliveStrategy = new ConnectionKeepAliveStrategy() {
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                // Honor 'keep-alive' header
                HeaderElementIterator it = new BasicHeaderElementIterator(
                        response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            LOGGER.info("keep-alive header name:{};value:{}", param, value);
                            return Long.parseLong(value) * 1000;
                        } catch (NumberFormatException ignore) {
                        }
                    }
                }
                return 30 * 1000;
            }

        };

        client = HttpClients.custom().setConnectionManager(connectionManager).setKeepAliveStrategy(keepAliveStrategy)
                .setDefaultRequestConfig(requestConfig).setRetryHandler(new DefaultHttpRequestRetryHandler()).build();

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE).register("https", trustAllHttpsCertificates())
                .build();
        connectionTLSManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // Increase max total connection to 200
        connectionTLSManager.setMaxTotal(1000);
        // Increase default max connection per route to 20
        connectionTLSManager.setDefaultMaxPerRoute(200);

        TLSClient = HttpClients.custom().setConnectionManager(connectionTLSManager)
                .setKeepAliveStrategy(keepAliveStrategy).setDefaultRequestConfig(requestConfig)
                .setRetryHandler(new DefaultHttpRequestRetryHandler()).build();

        monitorThread = new IdleConnectionMonitorThread(connectionManager);
        monitorThread.start();

        monitorThread = new IdleConnectionMonitorThread(connectionTLSManager);
        monitorThread.start();
    }

    private static SSLConnectionSocketFactory trustAllHttpsCertificates() {
        SSLConnectionSocketFactory socketFactory = null;
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[] { new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
            } }, new java.security.SecureRandom());
            socketFactory = new SSLConnectionSocketFactory(sc, NoopHostnameVerifier.INSTANCE);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return socketFactory;
    }

    public static HttpClientManager getInstance() {
        return SingletonHolder.instance;
    }

    public void shutdown() {
        connectionManager.shutdown();
        connectionTLSManager.shutdown();
        monitorThread.shutdown();
    }

    public int getSize() {
        return connectionManager.getRoutes().size();
    }

    public CloseableHttpClient getClient() {
        return client;
    }

    public CloseableHttpClient getTLSClient() {
        return TLSClient;
    }

//	public CloseableHttpClient getNewClient() {
//		return HttpClients.custom().setConnectionManager(connectionManager).setKeepAliveStrategy(keepAliveStrategy)
//				.setDefaultRequestConfig(requestConfig).setRetryHandler(new DefaultHttpRequestRetryHandler()).build();
//	}

    public HttpClientContext getDefaultContext() {
        HttpClientContext context = HttpClientContext.create();
        context.setRequestConfig(requestConfig);
        return context;
    }

    /**
     * RequestConfig requestConfig =RequestConfig.custom()
     * .setConnectTimeout(connectTimeout)
     * .setConnectionRequestTimeout(connectionRequestTimeout)
     * .setSocketTimeout(socketTimeout).build();
     * <p>
     * socketTimeoutMillis means timeout value to setup a connection Connection
     * timeout is the timeout until a connection with the server is established.
     * ConnectionRequestTimeout used when requesting a connection from the
     * connection manager.
     *
     * @param connectionRequestTimeoutMillis
     *            the timeout when requesting a connection from the connection
     *            manager.
     * @param connectTimeoutMillis
     *            与服务器建立连接的超时时间(毫秒)
     * @param socketTimeoutMillis
     *            the timeout value to setup a connection
     * @return
     */
    public HttpClientContext getContext(int connectionRequestTimeoutMillis, int connectTimeoutMillis,
                                        int socketTimeoutMillis) {
        RequestConfig rc = RequestConfig.copy(requestConfig).setSocketTimeout(socketTimeoutMillis)
                .setConnectTimeout(connectTimeoutMillis).setConnectionRequestTimeout(connectionRequestTimeoutMillis)
                .build();
        HttpClientContext context = HttpClientContext.create();
        context.setRequestConfig(rc);
        return context;
    }

    static class SingletonHolder {
        static HttpClientManager instance = new HttpClientManager();
    }

    private static class IdleConnectionMonitorThread extends Thread {

        private final HttpClientConnectionManager connMgr;
        private volatile boolean shutdown;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(5000);
                        // Close expired connections
                        connMgr.closeExpiredConnections();
                        // Optionally, close connections
                        // that have been idle longer than 30 sec
                        connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException ex) {
                // terminate
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }

    }
}
