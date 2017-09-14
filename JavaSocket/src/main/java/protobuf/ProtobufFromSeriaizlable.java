package protobuf;

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
			FirstProtobuf.testBuf msg = FirstProtobuf.testBuf.parseFrom(result);
			System.out.println(msg);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
