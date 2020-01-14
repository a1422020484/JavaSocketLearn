package reflect.listener;

public enum EventEnum {

	PLAYER_LEVELUP(1, "角色升级"), 
	CREATE_ROLE(2, "创建角色");

	private final int id;
	private final String desc;

	EventEnum(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public EventEnum getEnumType(int id) {
		for (EventEnum type : EventEnum.values()) {
			if (id == type.id) {
				return type;
			}
		}
		return null;
	}
}
