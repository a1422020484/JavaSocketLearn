package tomcatdome;

public class TeacherServlet extends MyServlet {

	@Override
	protected void doGet(MyRequest request, MyResponse response) {
		try {
//			response.write("i am a teacher " + "id = " + request.getParamMap().get("id") + " name = " + request.getParamMap().get("name"));
			response.write("i am a teacher " + request.getParamMap().toString() + " GET ? ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(MyRequest request, MyResponse response) {
		try {
			response.write("i am a teacher" + request.getParamMap().toString() + " POST ? ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
