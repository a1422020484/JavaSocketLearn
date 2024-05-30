package reflect.proxyTest;

public class ProxyTest {
	public static void main(String[] args) {
		UserInactionHandler userInactionHandler = new UserInactionHandler();
		UserService userService = (UserService) userInactionHandler.getProxyInstance(UserServiceImpl.class);
		System.out.println(userService);
		userService.addUser(1L);
	}
}
