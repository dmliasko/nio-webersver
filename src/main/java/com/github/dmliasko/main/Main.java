package com.github.dmliasko.main;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.github.dmliasko.main.model.Server;
import com.github.dmliasko.main.server.CacheHandlerImpl;
import com.github.dmliasko.main.server.ResponseWriterImpl;
import com.github.dmliasko.main.server.ServerImpl;
import com.github.dmliasko.main.util.RootFolder;

import static java.lang.System.exit;


public class Main {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		int port = 8080;

		if(args.length != 2){
			System.err.println("You need to set port and home folder first.");
			exit(1);
		} else {
			port = Integer.parseInt(args[0]);
			RootFolder.setFilePath(args[1]);
		}

		Server server = new ServerImpl(new InetSocketAddress("localhost", port));
		server.run(new CacheHandlerImpl(), new ResponseWriterImpl());
	}

}
