package org.welsh.graphite.apachetrans.mock;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.net.www.protocol.http.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class MockApacheServerFactory {
	
	private static final Logger log = LoggerFactory.getLogger(MockApacheServerFactory.class);

	public static HttpServer createHttpServer(int port) throws IOException {
		
		log.info("Starting Apache HTTP Server");
		
		InetSocketAddress address = new InetSocketAddress(port);
		HttpServer httpServer = HttpServer.create(address, 0);
		
		HttpHandler handlerSimple = new HttpHandler() {
			public void handle(HttpExchange exchange) throws IOException {
				StringBuilder message = new StringBuilder();
				message.append("BusyWorkers: 1\n");
				message.append("IdleWorkers: 7\n");
				message.append("Scoreboard: _______W......\n");
				
				byte[] response = message.toString().getBytes();
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);
				exchange.getResponseBody().write(response);
				exchange.close();
			}
		};
		
		HttpHandler handlerExtended = new HttpHandler() {
			public void handle(HttpExchange exchange) throws IOException {
				StringBuilder message = new StringBuilder();
				message.append("Total Accesses: 456\n");
				message.append("Total kBytes: 355\n");
				message.append("CPULoad: .00138545\n");
				message.append("Uptime: 163124\n");
				message.append("ReqPerSec: .00279542\n");
				message.append("BytesPerSec: 2.22849\n");
				message.append("BytesPerReq: 797.193\n");
				message.append("BusyWorkers: 1\n");
				message.append("IdleWorkers: 7\n");
				message.append("Scoreboard: _______W......\n");
				
				byte[] response = message.toString().getBytes();
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);
				exchange.getResponseBody().write(response);
				exchange.close();
			}
		};
		
		HttpHandler handlerNotFound = new HttpHandler() {
			public void handle(HttpExchange exchange) throws IOException {
				StringBuilder message = new StringBuilder();
				message.append("<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">");
				message.append("<html>");
				message.append("<head><title>404 Not Found</title></head>");
				message.append("<body><h1>Not Found</h1><p>The requested URL /server-status was not found on this server.</p></body>");
				message.append("</html>");
				
				byte[] response = message.toString().getBytes();
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, response.length);
				exchange.getResponseBody().write(response);
				exchange.close();
			}
		};

		httpServer.createContext("/server-status/simple", handlerSimple);
		log.info("Registered Path /server-status/simple");
		
		httpServer.createContext("/server-status/extended", handlerExtended);
		log.info("Registered Path /server-status/extended");
		
		httpServer.createContext("/404", handlerNotFound);
		log.info("Registered Path /404");
		
		log.info("Apache HTTP Server Started\n");
		
		return httpServer;
	}
}
