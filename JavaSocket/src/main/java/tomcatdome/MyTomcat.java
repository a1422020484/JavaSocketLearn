package tomcatdome;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MyTomcat {

	private int port = 8080;
	private Map<String, String> urlServletMap = new HashMap<String, String>(16);

	public MyTomcat(int port) {
		this.port = port;
	}

	public static void main(String[] args) {
		try {
			new MyTomcat(8080).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		initServletMapping();
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(port);
			System.out.println("My Tomcat Begin");
			while (true) {
				Socket socket = serverSocket.accept();
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				MyRequest request = new MyRequest(inputStream);
				MyResponse response = new MyResponse(outputStream);
				
				dispatch(request, response);
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initServletMapping() {
		for (ServletMapping servletMapping : ServletMappConfig.servletMappings) {
			urlServletMap.put(servletMapping.getUrl(), servletMapping.getClazz());
		}
	}

	private void dispatch(MyRequest request, MyResponse response) {
		String clazz = urlServletMap.get(request.getUrl());

		try {
			if(clazz == null) {
				DefaultServlet defaultServlet = new DefaultServlet();
				defaultServlet.doGet(request, response);
				return;
			}
			Class<?> servletClass = Class.forName(clazz);
			MyServlet myServlet = (MyServlet) servletClass.newInstance();
			myServlet.service(request, response);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
