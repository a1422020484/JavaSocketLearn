package tomcatdome;

public class StudentServlet extends MyServlet {

	@Override
	protected void doGet(MyRequest request, MyResponse response) {
		try {
			response.write("i am a student");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(MyRequest request, MyResponse response) {
		try {
			response.write("i am a student");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
