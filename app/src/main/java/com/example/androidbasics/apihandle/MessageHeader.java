package com.example.androidbasics.apihandle;

public class MessageHeader {
    private String actionType;
    private String apiKey;
    private String destination;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(String requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private String requestId;
    private String requestTimestamp;
    private String source;
    private String version;

    public MessageHeader(String actionType, String apiKey, String destination, String requestId, String requestTimestamp, String source, String version) {
        this.actionType = actionType;
        this.apiKey = apiKey;
        this.destination = destination;
        this.requestId = requestId;
        this.requestTimestamp = requestTimestamp;
        this.source = source;
        this.version = version;
    }
}
