package protobuf;

import protobuf.userLogin.UserLogin1;

public class ProtobufToSeriaizlable {

	public static void main(String[] args) {
//		FirstProtobuf.testBuf.Builder builder = FirstProtobuf.testBuf.newBuilder();
//		builder.setID(10);
//		builder.setUrl("http://xxx.jpg");
//		FirstProtobuf.testBuf info = builder.build();
//
//		byte[] result = info.toByteArray();
		UserLogin1.TestInfo.Builder builder = UserLogin1.TestInfo.newBuilder();
		builder.setNum(114);
		builder.setTest("ok");
		UserLogin1.TestInfo info = builder.build();
		byte[] result = info.toByteArray();
		System.out.println(result);

		ProtobufFromSeriaizlable.toSeriaizlable(result);
	}

}
