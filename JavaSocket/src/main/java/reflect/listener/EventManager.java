package reflect.listener;

import java.util.HashMap;
import java.util.Map;

public class EventManager {

	private final static EventManager instance = new EventManager();

	public static EventManager getEventManager() {
		return instance;
	}

	private Map<Integer, ListenerEvent> listenerMap;

	public EventManager() {
		listenerMap = new HashMap<>();
	}

	public void addEvent(int eventId, ListenerEvent listenerEvent) {
		listenerMap.put(eventId, listenerEvent);
	}

	public void tiggerEvent(int eventId) {
		ListenerEvent listnerEvent = listenerMap.get(eventId);
		listnerEvent.event();
	}
}
