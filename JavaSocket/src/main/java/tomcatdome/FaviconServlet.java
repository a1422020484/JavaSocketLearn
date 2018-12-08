package tomcatdome;

public class FaviconServlet extends MyServlet {

	@Override
	protected void doGet(MyRequest request, MyResponse response) {
		try {
			response.write("Favicon");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(MyRequest request, MyResponse response) {
		try {
			response.write("Favicon");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
