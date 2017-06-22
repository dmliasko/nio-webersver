package com.github.dmliasko.main;

import com.github.dmliasko.main.server.CacheHandlerImpl;
import com.github.dmliasko.main.server.ResponseWriterImpl;
import com.github.dmliasko.main.server.ServerImpl;
import com.github.dmliasko.main.model.Server;
import com.github.dmliasko.main.util.RootFolder;
import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by dmlia on 22.06.2017.
 */
public class ServerTest {

    private Server server;

    @BeforeClass
    public void setUp() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                server = new ServerImpl(new InetSocketAddress("localhost", 8080));
                server.run(new CacheHandlerImpl(), new ResponseWriterImpl());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        RootFolder.setFilePath("home");

        thread.start();

        Thread.sleep(1000);
    }

    private void testServer(String input, String exptected) throws IOException {
        Socket socket = new Socket("localhost", 8080);

        socket.getOutputStream().write(input.getBytes());

        InputStream inputStream = socket.getInputStream();
        String actual = IOUtils.toString(inputStream);
        Assert.assertEquals(actual, exptected);
    }

    @Test
    public void getIndexTest() throws IOException, InterruptedException {
        testServer("GET /index.html HTTP/1.1\n", "HTTP/1.1 200 OK\r\n" +
                "Server: github.com/dmliasko server\r\n" +
                "Connection: Closed\r\n" +
                "Content-Length: 21\r\n" +
                "\r\n" +
                "<h1>Hello world!</h1>");
    }

    @Test
    public void headTest() throws IOException, InterruptedException {
        testServer("HEAD /index.html HTTP/1.1\n", "HTTP/1.1 200 OK\r\n" +
                "Server: github.com/dmliasko server\r\n" +
                "Connection: Closed\r\n" +
                "\r\n" +
                "");
    }

    @Test
    public void deleteTest() throws IOException, InterruptedException {
        Path path = Paths.get(RootFolder.filePath + "\\deleteTest.txt");
        Files.createFile(path);

        junit.framework.Assert.assertTrue(Files.exists(path));

        testServer("DELETE /deleteTest.txt HTTP/1.1\r\n", "HTTP/1.1 200 OK\r\n" +
                "Server: github.com/dmliasko server\r\n" +
                "Connection: Closed\r\n" +
                "\r\n" +
                "");
    }

}
