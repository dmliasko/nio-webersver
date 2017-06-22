package com.github.dmliasko.main.server;

import com.github.dmliasko.main.model.CacheHandler;
import com.github.dmliasko.main.model.ResponseWriter;
import com.github.dmliasko.main.model.Server;
import com.github.dmliasko.main.model.impl.HTTPRequest;
import com.github.dmliasko.main.util.ByteBufferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by dmlia on 21.06.2017.
 */
public class ServerImpl implements Server {

    private static final Logger logger = LoggerFactory.getLogger(ServerImpl.class);

    protected SocketAddress address;
    protected ByteBuffer byteBuffer = ByteBuffer.allocate(2048);

    public ServerImpl(SocketAddress address) {
        this.address = address;
    }

    @Override
    public void run(CacheHandler cacheHandler, ResponseWriter responseWriter) throws IOException {

        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(address);
        serverChannel.configureBlocking(false);

        logger.info("Server started on address:" + address);


        Selector sel = Selector.open();
        serverChannel.register(sel, serverChannel.validOps());

        while (true) {
            sel.select();
            Iterator<SelectionKey> selectionKeyIterator = sel.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()) {
                SelectionKey selectionKey = selectionKeyIterator.next();

                try {
                    if (selectionKey.isAcceptable()) {
                        SocketChannel clientChannel = serverChannel.accept();
                        clientChannel.configureBlocking(false);
                        clientChannel.register(sel, selectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {
                        SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
                        byteBuffer = ByteBuffer.allocate(2048);
                        if (clientChannel.isOpen()) {
                            String stringBuffer = ByteBufferUtil.readFromChannel(byteBuffer, clientChannel);

                            if (!stringBuffer.isEmpty()) {
                                HTTPRequest request = new HTTPRequest(clientChannel, stringBuffer);
                                try {
                                    responseWriter.accept(cacheHandler.apply(request));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }
                } catch (NullPointerException ignored){}
            }
        }
    }
}
