package cn.saturn.web.controllers;

import net.paoding.rose.web.ControllerErrorHandler;
import net.paoding.rose.web.Invocation;

//错误接口
public class ErrorHandler implements ControllerErrorHandler {

	// 处理错误, 优先于所有controller
	@Override
	public Object onError(Invocation inv, Throwable ex) throws Throwable {

		// return "@is->err !!!!!:" + ex;

		// String url = Utils.url(inv, "login");
		// return JumpController.error(inv, ex.toString(), url);

//		HttpServletRequest request = inv.getRequest();
//		HttpServletResponse response = inv.getResponse();
//		ServletContext context = inv.getServletContext();
//		RequestDispatcher rd = context.getRequestDispatcher("/login");// 获取请求转发对象(RequestDispatcher)
//		rd.forward(request, response);// 调用forward方法实现请求转发

		ex.printStackTrace();
		return "@error";
	}
}
