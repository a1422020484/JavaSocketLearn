package protobuf;

import protobuf.userLogin.UserLogin1;

public class ProtobufFromSeriaizlable {

	public static void main(String[] args) {
//		try {
//			FirstProtobuf.testBuf msg = FirstProtobuf.testBuf.parseFrom(result);
//			System.out.println(msg);
//		} catch (Exception ex) {
//			System.out.println(ex.getMessage());
//		}
	}

	public static void toSeriaizlable(byte[] result) {
		try {
			UserLogin1.TestInfo msg = UserLogin1.TestInfo.parseFrom(result);

			System.out.println("number == " + msg.getNum() + " test == " + msg.getTest());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
}
