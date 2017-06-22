package com.github.dmliasko.main.model.impl;

import com.github.dmliasko.main.model.RequestHeader;
import com.github.dmliasko.main.model.Response;
import com.github.dmliasko.main.model.ResponseHeader;

import java.nio.channels.SocketChannel;

/**
 * Created by dmlia on 03.06.2017.
 */
public class HTTPResponse implements Response {

    protected SocketChannel client;
    protected byte[] responseData;

    protected RequestHeader requestHeader;

    private ResponseHeader responseHeader;

    public HTTPResponse(SocketChannel client, RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
        this.client = client;

        this.responseHeader = new ResponseHeaderImpl();
    }


    @Override
    public SocketChannel getClient() {
        return client;
    }

    @Override
    public byte[] getResponseData() {
        return responseData;
    }

    @Override
    public boolean isCacheable() {
        return true;
    }

    @Override
    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    @Override
    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    @Override
    public void setSocket(SocketChannel socketChannel) {
        this.client = socketChannel;
    }

    @Override
    public void setResponseData(byte[] responseData) {
        this.responseData = responseData;
    }


}
