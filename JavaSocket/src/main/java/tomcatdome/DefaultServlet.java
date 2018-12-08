package tomcatdome;

public class DefaultServlet extends MyServlet {

	@Override
	protected void doGet(MyRequest request, MyResponse response) {
		try {
			response.write("i am a DefaultServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(MyRequest request, MyResponse response) {
		try {
			response.write("i am a DefaultServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
