package protobuf;

public class ProtobufToSeriaizlable {

	public static void main(String[] args) {
		FirstProtobuf.testBuf.Builder builder = FirstProtobuf.testBuf.newBuilder();
		builder.setID(10);
		builder.setUrl("http://xxx.jpg");
		FirstProtobuf.testBuf info = builder.build();

		byte[] result = info.toByteArray();

		System.out.println(result);

		ProtobufFromSeriaizlable.toSeriaizlable(result);
	}

}
