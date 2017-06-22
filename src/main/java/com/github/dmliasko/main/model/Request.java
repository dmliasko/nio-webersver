package com.github.dmliasko.main.model;

import java.nio.channels.SocketChannel;

public interface Request {
    SocketChannel getClient();

    String getRequestData();

    RequestHeader getRequestHeader();
}
