package mongodb;

import org.bson.Document;

public interface BaseIdModel {
	String getId();

	void setId(String id);

	Document toDoucment();

	void toModel(Document result);

}
