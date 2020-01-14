package mongodb;

import org.bson.Document;
import org.bson.types.ObjectId;

public class User implements BaseIdModel {
	private String id;
	private String name;
	private int age;
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Document toDoucment() {
		Document doc = new Document();
		if (this.id != null) {
			doc.put("_id", new ObjectId(this.id));
		}
		doc.put("name", name);
		doc.put("age", age);
		doc.put("address", address);
		return doc;
	}

	@Override
	public void toModel(Document result) {
		this.id = result.get("_id").toString();
		this.name = result.getString("name");
		this.age = result.getInteger("age");
		this.address = result.getString("address");
	}

	@Override
	public String toString() {
		return "User [_id=" + id + ", name=" + name + ", age=" + age + ", address=" + address + "]";
	}

}
