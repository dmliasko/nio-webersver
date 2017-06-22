package com.github.dmliasko.main.model.impl;

import com.github.dmliasko.main.model.Request;
import com.github.dmliasko.main.model.RequestHeader;

import java.nio.channels.SocketChannel;

/**
 * Created by dmlia on 03.06.2017.
 */
public class HTTPRequest implements Request {

    protected SocketChannel client;
    protected String requestData;

    private RequestHeader requestHeader;


    public HTTPRequest(SocketChannel client, String requestData) {
        this.requestData = requestData;
        this.client = client;

        this.requestHeader = new RequestHeaderImpl(requestData);
    }


    @Override
    public SocketChannel getClient() {
        return client;
    }

    @Override
    public String getRequestData() {
        return requestData;
    }

    @Override
    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

}
