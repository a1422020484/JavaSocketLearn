package reflect.proxyTest;

public class ProxyTest {
	public static void main(String[] args) {
		UserInactionHandler userInactionHandler = new UserInactionHandler();
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		UserService userService = (UserService) userInactionHandler.getProxyInstance(userServiceImpl);
		System.out.println(userService);
		userService.addUser(1L);
	}
}
