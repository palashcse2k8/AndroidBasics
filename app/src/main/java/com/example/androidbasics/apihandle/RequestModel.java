package com.example.androidbasics.apihandle;

import java.util.List;

public class RequestModel {
    private MessageHeader messageHeader;
    private List<PayLoad> payLoad;

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public List<PayLoad> getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(List<PayLoad> payLoad) {
        this.payLoad = payLoad;
    }
}
