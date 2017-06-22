package com.github.dmliasko.main.server;

import com.github.dmliasko.main.model.Response;
import com.github.dmliasko.main.model.ResponseWriter;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ResponseWriterImpl implements ResponseWriter {

    @Override
    public void accept(Response response) throws Exception {

        byte[] responseContent = response.getResponseData();

        SocketChannel socketChannel = response.getClient();
        if (socketChannel.isOpen()) {
            socketChannel.write(ByteBuffer.wrap(response.getResponseHeader().getHeaderAsByte()));
            if (responseContent != null) {
                socketChannel.write(ByteBuffer.wrap(responseContent));
            }
            socketChannel.close();
        }
    }

}
