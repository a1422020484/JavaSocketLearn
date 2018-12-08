package tomcatdome;

import java.util.ArrayList;
import java.util.List;

public class ServletMappConfig {
	public static List<ServletMapping> servletMappings = new ArrayList<ServletMapping>();
	static {
		servletMappings.add(new ServletMapping("student", "/student", "tomcatdome.StudentServlet"));
		servletMappings.add(new ServletMapping("teacher", "/teacher", "tomcatdome.TeacherServlet"));
		servletMappings.add(new ServletMapping("favicon", "/favicon", "tomcatdome.FaviconServlet"));
	}
}
