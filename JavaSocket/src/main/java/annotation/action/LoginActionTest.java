package annotation.action;

@Action
public class LoginActionTest extends AbstractAction {
	@Action(id = 1)
	public void firstMethonTest(String username, int area) {
		System.out.println("firstMethonTest");
	}

}
