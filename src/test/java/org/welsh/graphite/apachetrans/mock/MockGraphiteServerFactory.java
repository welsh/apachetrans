package org.welsh.graphite.apachetrans.mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.net.www.protocol.http.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class MockGraphiteServerFactory {

	private static final Logger log = LoggerFactory.getLogger(MockGraphiteServerFactory.class);

	public static HttpServer createHttpServer(int port) throws IOException {

		log.info("Starting Graphite Server");

		InetSocketAddress address = new InetSocketAddress(port);
		HttpServer httpServer = HttpServer.create(address, 0);

		HttpHandler handlerDefault = new HttpHandler() {
			public void handle(HttpExchange exchange) throws IOException {

				InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
				BufferedReader br = new BufferedReader(isr);
				
				int bytes;
				StringBuilder buf = new StringBuilder(512);
				while ((bytes = br.read()) != -1) {
				    buf.append((char) bytes);
				}
				
				log.info("Incoming Response: " + buf.toString());


				byte[] response = "".getBytes();
				
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,response.length);
				exchange.getResponseBody().write(response);
				exchange.close();
			}
		};

		httpServer.createContext("/", handlerDefault);
		log.info("Registered Path /");

		log.info("Graphite Server Started\n");

		return httpServer;
	}
}
