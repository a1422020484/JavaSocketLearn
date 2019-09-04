package reflect.proxyTest;

public class UserServiceImpl implements UserService {

	@Override
	public void addUser(Long cardId) {
		System.out.println("cardId >>>>> " + cardId);
	}

}
