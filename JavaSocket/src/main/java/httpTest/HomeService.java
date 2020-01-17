package httpTest;

public class HomeService {
	public static GameClient createHomeClient() {
		String host = "";

		int port = 0;
		host = host.replaceAll("http://", "");
		host += "?prot=";

		GameClient homeClient = new GameClient(host, port);
		return homeClient;
	}
	
	
}
