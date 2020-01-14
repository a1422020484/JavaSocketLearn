package reflect.listener;

import org.springframework.stereotype.Component;

@Component
public class CreateRole implements ListenerEvent {

	@Override
	public void event() {
		System.out.println("CreateRole");
	}

	@Override
	public void putIntoListener() {
		EventManager.getEventManager().addEvent(EventEnum.CREATE_ROLE.getId(), this);
	}

}
