package com.chanshiguan.quentincommon.http;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by jie.wang
 * on 2018/6/26
 */
public class HttpResponseModel implements Serializable {

    private static final long serialVersionUID = 2402302329688280808L;

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private boolean hasException = false;
    private int statusCode;
    private String reasonPhrase;
    private String contentType;
    private Charset charset;
    private Map<String, String> headers = new HashMap<>();
    private byte[] bodyBytes;
    private String bodyString = "";
    private String exception;
    private String exceptionStackTrace;

    public HttpResponseModel(String exception, String exceptionStackTrace) {
        this.hasException = true;
        this.exception = exception;
        this.exceptionStackTrace = exceptionStackTrace;
    }

    public HttpResponseModel(int statusCode, String reasonPhrase, String contentType, String charset,
                             Map<String, String> headers,
                             byte[] bodyBytes) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.contentType = contentType;
        String charsetString = StringUtils.trimToEmpty(charset);
        if (charsetString.isEmpty()) {
            this.charset = DEFAULT_CHARSET;
        } else {
            try {
                this.charset = Charset.forName(charsetString);
            } catch (Exception e) {
                this.charset = DEFAULT_CHARSET;
            }
        }
        if (headers != null) {
            this.headers.putAll(headers);
        }
        this.bodyBytes = bodyBytes;
        if (this.bodyBytes != null && this.bodyBytes.length > 0) {
            bodyString = new String(bodyBytes, this.charset);
        }
    }

    public boolean isOk() {
        return !hasException && (statusCode == HttpStatus.SC_OK);
    }

    public boolean isHasException() {
        return hasException;
    }

    public String getException() {
        return exception;
    }

    public String getExceptionStackTrace() {
        return exceptionStackTrace;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public String getContentType() {
        return contentType;
    }

    public Charset getCharset() {
        return charset;
    }

    public String getHeader(String headerName) {
        return StringUtils.trimToEmpty(headers.get(headerName));
    }

    public boolean hasHeader(String headerName) {
        return headers.containsKey(headerName);
    }

    public List<NameValuePair> getAllHeaders() {
        if (MapUtils.isEmpty(headers)) {
            return Collections.emptyList();
        }

        List<NameValuePair> paramNamePairs = new ArrayList<NameValuePair>(headers.size());
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            paramNamePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return paramNamePairs;
    }

    public byte[] getBodyBytes() {
        return bodyBytes;
    }

    public String getBodyString() {
        return bodyString;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
