package com.chanshiguan.quentincommon.http;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jie.wang
 * on 2018/6/26
 */
public class HttpUtil {


    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    public static String httpGetBodyString(String url) {
        return httpGetBodyString(url, "");
    }

    public static String httpGetBodyString(String url, String queryString) {
        HttpResponseModel response = httpGet(url, queryString);
        return response.getBodyString();
    }

    public static String httpGetBodyString(String url, String queryString, int connectionRequestTimeout,
                                           int connectTimeout, int socketTimeout) {
        HttpResponseModel response = httpGet(url, queryString, connectionRequestTimeout, connectTimeout, socketTimeout);
        return response.getBodyString();
    }

    public static String httpGetBodyString(String url, List<NameValuePair> params, String charset) {
        HttpResponseModel response = httpGet(url, params, charset);
        return response.getBodyString();
    }

    public static String httpGetBodyString(String url, List<NameValuePair> params, Charset charset) {
        HttpResponseModel response = httpGet(url, params, charset);
        return response.getBodyString();
    }

    public static String httpGetBodyString(String url, List<NameValuePair> params, String charset,
                                           int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        HttpResponseModel response = httpGet(url, params, charset, connectionRequestTimeout, connectTimeout,
                socketTimeout);
        return response.getBodyString();
    }

    public static String httpGetBodyString(String url, List<NameValuePair> params, List<NameValuePair> headers,
                                           String charset, int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        HttpResponseModel response = httpGet(url, params, headers, charset, connectionRequestTimeout, connectTimeout,
                socketTimeout);
        return response.getBodyString();
    }

    public static String httpGetBodyString(String url, List<NameValuePair> params, Charset charset,
                                           int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        HttpResponseModel response = httpGet(url, params, charset, connectionRequestTimeout, connectTimeout,
                socketTimeout);
        return response.getBodyString();
    }

    public static String httpPostBodyString(String url, List<NameValuePair> params, String charset) {
        HttpResponseModel response = httpPost(url, params, charset);
        return response.getBodyString();
    }

    public static String httpPostBodyString(String url, List<NameValuePair> params, Charset charset) {
        HttpResponseModel response = httpPost(url, params, charset);
        return response.getBodyString();
    }

    public static String httpPostBodyString(String url, List<NameValuePair> params, String charset,
                                            int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        HttpResponseModel response = httpPost(url, params, charset, connectionRequestTimeout, connectTimeout,
                socketTimeout);
        return response.getBodyString();
    }

    public static String httpPostBodyString(String url, List<NameValuePair> params, Charset charset,
                                            int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        HttpResponseModel response = httpPost(url, params, charset, connectionRequestTimeout, connectTimeout,
                socketTimeout);
        return response.getBodyString();
    }

    public static HttpResponseModel httpGet(String url) {
        return httpGet(url, "");
    }

    public static HttpResponseModel httpGet(String url, String queryString) {
        CloseableHttpClient httpClient = HttpClientManager.getInstance().getClient();
        HttpGet method = new HttpGet(appendQueryString(url, queryString));
        return getResponseModel(httpClient, method, HttpClientManager.getInstance().getDefaultContext());
    }

    public static HttpResponseModel httpGet(String url, List<NameValuePair> params, String charset) {
        return httpGet(url, params, parseCharset(charset));
    }

    public static HttpResponseModel httpGet(String url, List<NameValuePair> params, Charset charset) {
        String queryString = URLEncodedUtils.format(params, charset);
        return httpGet(url, queryString);
    }

    public static HttpResponseModel httpGet(String url, List<NameValuePair> params, String charset,
                                            int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        return httpGet(url, params, parseCharset(charset), connectionRequestTimeout, connectTimeout, socketTimeout);
    }

    public static HttpResponseModel httpGet(String url, List<NameValuePair> params, List<NameValuePair> headers,
                                            String charset, int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        return httpGet(url, params, headers, parseCharset(charset), connectionRequestTimeout, connectTimeout,
                socketTimeout);
    }

    public static HttpResponseModel httpGet(String url, List<NameValuePair> params, Charset charset,
                                            int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        String queryString = URLEncodedUtils.format(params, charset);
        return httpGet(url, queryString, connectionRequestTimeout, connectTimeout, socketTimeout);
    }

    public static HttpResponseModel httpGet(String url, List<NameValuePair> params, List<NameValuePair> headers,
                                            Charset charset, int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        String queryString = URLEncodedUtils.format(params, charset);
        return httpGet(url, queryString, headers, connectionRequestTimeout, connectTimeout, socketTimeout);
    }

    public static HttpResponseModel httpGet(String url, String queryString, int connectionRequestTimeout,
                                            int connectTimeout, int socketTimeout) {
        CloseableHttpClient httpClient = HttpClientManager.getInstance().getClient();
        HttpGet method = new HttpGet(appendQueryString(url, queryString));
        return getResponseModel(httpClient, method,
                HttpClientManager.getInstance().getContext(connectionRequestTimeout, connectTimeout, socketTimeout));
    }

    public static HttpResponseModel httpTLSGet(String url, String queryString, int connectionRequestTimeout,
                                               int connectTimeout, int socketTimeout) {
        CloseableHttpClient httpClient = HttpClientManager.getInstance().getTLSClient();
        HttpGet method = new HttpGet(appendQueryString(url, queryString));
        return getResponseModel(httpClient, method,
                HttpClientManager.getInstance().getContext(connectionRequestTimeout, connectTimeout, socketTimeout));
    }

    public static HttpResponseModel httpGet(String url, String queryString, List<NameValuePair> headers,
                                            int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        CloseableHttpClient httpClient = HttpClientManager.getInstance().getClient();
        HttpGet method = new HttpGet(appendQueryString(url, queryString));
        for (NameValuePair nameValuePair : headers) {
            method.addHeader(nameValuePair.getName(), nameValuePair.getValue());
        }
        return getResponseModel(httpClient, method,
                HttpClientManager.getInstance().getContext(connectionRequestTimeout, connectTimeout, socketTimeout));
    }

    public static HttpResponseModel httpPost(String url, List<NameValuePair> params, String charset) {
        return httpPost(url, params, parseCharset(charset));
    }

    public static HttpResponseModel httpPostForJson(String url, String jsonParam,
                                                    int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        CloseableHttpClient httpClient = HttpClientManager.getInstance().getClient();
        HttpPost method = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonParam, "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        method.setEntity(entity);
        return getResponseModel(httpClient, method,
                HttpClientManager.getInstance().getContext(connectionRequestTimeout, connectTimeout, socketTimeout));
    }

    public static HttpResponseModel httpPost(String url, Header[] headers, String jsonParam) {
        CloseableHttpClient httpClient = HttpClientManager.getInstance().getClient();
        HttpPost method = new HttpPost(url);
        method.setHeaders(headers);
        StringEntity entity = new StringEntity(jsonParam, "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        method.setEntity(entity);
        return getResponseModel(httpClient, method, HttpClientManager.getInstance().getDefaultContext());
    }

    public static HttpResponseModel httpPost(String url, List<NameValuePair> params, Charset charset) {
        CloseableHttpClient httpClient = HttpClientManager.getInstance().getClient();
        HttpPost method = new HttpPost(url);
        if (!CollectionUtils.isEmpty(params)) {
            UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(params, charset);
            method.setEntity(requestEntity);
        }
        return getResponseModel(httpClient, method, HttpClientManager.getInstance().getDefaultContext());
    }

    public static HttpResponseModel httpTLSPost(String url, List<NameValuePair> params, String charset) {
        return httpTLSPost(url, params, parseCharset(charset));
    }

    public static HttpResponseModel httpTLSPost(String url, List<NameValuePair> params, Charset charset) {
        CloseableHttpClient httpClient = HttpClientManager.getInstance().getTLSClient();
        HttpPost method = new HttpPost(url);
        if (!CollectionUtils.isEmpty(params)) {
            UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(params, charset);
            method.setEntity(requestEntity);
        }
        return getResponseModel(httpClient, method, HttpClientManager.getInstance().getDefaultContext());
    }

    public static HttpResponseModel httpPost(String url, List<NameValuePair> params, String charset,
                                             int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        return httpPost(url, params, parseCharset(charset), connectionRequestTimeout, connectTimeout, socketTimeout);
    }

    public static HttpResponseModel httpPost(String url, List<NameValuePair> params, Charset charset,
                                             int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        CloseableHttpClient httpClient = HttpClientManager.getInstance().getClient();
        HttpPost method = new HttpPost(url);
        if (!CollectionUtils.isEmpty(params)) {
            UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(params, charset);
            method.setEntity(requestEntity);
        }
        return getResponseModel(httpClient, method,
                HttpClientManager.getInstance().getContext(connectionRequestTimeout, connectTimeout, socketTimeout));
    }

    public static HttpResponseModel httpTLSPost(String url, List<NameValuePair> params, Charset charset,
                                                int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        CloseableHttpClient httpClient = HttpClientManager.getInstance().getTLSClient();
        HttpPost method = new HttpPost(url);
        if (!CollectionUtils.isEmpty(params)) {
            UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(params, charset);
            method.setEntity(requestEntity);
        }
        return getResponseModel(httpClient, method,
                HttpClientManager.getInstance().getContext(connectionRequestTimeout, connectTimeout, socketTimeout));
    }

    public static HttpResponseModel httpPost(String url, String rawData, int connectionRequestTimeout,
                                             int connectTimeout, int socketTimeout) {
        CloseableHttpClient httpClient = HttpClientManager.getInstance().getClient();
        HttpPost method = new HttpPost(url);
        if (!StringUtils.isEmpty(rawData)) {
            method.setEntity(EntityBuilder.create().setText(rawData).build());
        }
        return getResponseModel(httpClient, method,
                HttpClientManager.getInstance().getContext(connectionRequestTimeout, connectTimeout, socketTimeout));
    }

    public static HttpResponseModel httpPost(String url, Map<String, String> param, String fileName, File file)
            throws FileNotFoundException {
        return httpPost(url, param, fileName, file, 5000, 5000, 5000);
    }

    public static HttpResponseModel httpPost(String url, Map<String, String> param, String fileName, File file,
                                             int connectionRequestTimeout, int connectTimeout, int socketTimeout) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException();
        }
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        for (Map.Entry<String, String> entity : param.entrySet()) {
            multipartEntityBuilder.addTextBody(entity.getKey(), entity.getValue());
        }
        multipartEntityBuilder.addBinaryBody(fileName, file);
        HttpEntity httpEntity = multipartEntityBuilder.build();
        CloseableHttpClient httpClient = HttpClientManager.getInstance().getClient();
        HttpPost method = new HttpPost(url);
        method.setEntity(httpEntity);
        return getResponseModel(httpClient, method,
                HttpClientManager.getInstance().getContext(connectionRequestTimeout, connectTimeout, socketTimeout));
    }

    public static HttpResponseModel httpGet(String url, String queryString, Map<String, String> headMap) {
        CloseableHttpClient httpClient = HttpClientManager.getInstance().getClient();
        HttpGet method = new HttpGet(appendQueryString(url, queryString));
        if (MapUtils.isNotEmpty(headMap)) {
            for (Map.Entry<String, String> entry : headMap.entrySet()) {
                method.setHeader(entry.getKey(), entry.getValue());
            }
        }

        return getResponseModel(httpClient, method, HttpClientManager.getInstance().getDefaultContext());
    }

    private static HttpResponseModel getResponseModel(CloseableHttpClient httpClient, HttpRequestBase requestBase,
                                                      HttpClientContext context) {
        InputStream stream = null;
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(requestBase, context);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            String reasonPhrase = StringUtils.trimToEmpty(statusLine.getReasonPhrase());

            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new ClientProtocolException("Response contains no content");
            }
            Header[] headers = response.getAllHeaders();
            Map<String, String> headerMap = new HashMap<>();
            for (Header header : headers) {
                headerMap.put(header.getName(), header.getValue());
            }

            String contentType = StringUtils.trimToEmpty(headerMap.get("Content-Type"));
            String charset = StringUtils.trimToEmpty(headerMap.get("Content-Encoding"));
            String contentLengthString = StringUtils.trimToEmpty(headerMap.get("Content-Length"));

            if (!contentLengthString.isEmpty() && contentLengthString.equals("0")) {
                return new HttpResponseModel(statusCode, reasonPhrase, contentType, charset, headerMap, new byte[0]);
            }

            stream = entity.getContent();

            byte[] buffer = new byte[8192];
            ByteArrayOutputStream baos = new ByteArrayOutputStream(100 * 1024);
            int readSize;
            while ((readSize = stream.read(buffer)) != -1) {
                baos.write(buffer, 0, readSize);
            }
            return new HttpResponseModel(statusCode, reasonPhrase, contentType, charset, headerMap, baos.toByteArray());
        } catch (IOException e) {
            return new HttpResponseModel(e.getLocalizedMessage(), ExceptionUtils.getStackTrace(e));
        } finally {
            if (stream != null) {
                try {
                    // 如果正常关闭了 stream, 会自动 releaseConnection
                    stream.close();
                } catch (IOException ignore) {
                }
            }
            if (response != null) {
                try {
                    // 如果之前已经 releaseConnection 了, 则不会shutdown connection
                    response.close();
                } catch (IOException ignore) {
                }
            }
        }
    }

    private static Charset parseCharset(String charset) {
        try {
            return charset != null ? Charset.forName(charset) : Consts.UTF_8;
        } catch (Exception e) {
            return Consts.UTF_8;
        }
    }

    private static String appendQueryString(String url, String queryString) {
        String result = url;
        if (queryString != null && !queryString.equals("")) {
            if (result.contains("?")) {
                if (result.endsWith("?")) {
                    result += queryString;
                } else {
                    result = StringUtils.stripEnd(url, "&");
                    result += "&" + queryString;
                }
            } else {
                result += "?" + queryString;
            }
        }
        return result;
    }
}
