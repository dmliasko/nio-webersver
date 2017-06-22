package com.github.dmliasko.main.model;

import java.nio.channels.SocketChannel;

public interface Response {
    SocketChannel getClient();

    byte[] getResponseData();

    /**
     * @return true if this response can be cached.
     * One might want to return false for all error responses so that they are not cached
     */
    boolean isCacheable();

    ResponseHeader getResponseHeader();

    RequestHeader getRequestHeader();

    void setSocket(SocketChannel socketChannel);

    void setResponseData(byte[] responseData);
}
