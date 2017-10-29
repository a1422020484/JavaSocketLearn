package protobuf.userLogin;

public class UserLoginDoData {

	public static void doData(byte[] result) throws Exception{
		UserLogin.TestInfo msg = UserLogin.TestInfo.parseFrom(result);
		
		System.out.println("number == " + msg.getNum() + " test == " + msg.getTest());
	}
	
	public static byte[] sendData(){
		UserLogin.TestInfo.Builder builder = UserLogin.TestInfo.newBuilder();
		builder.setNum(114);
		builder.setTest("ok");
		UserLogin.TestInfo info = builder.build();
		byte[] result = info.toByteArray();
		return result;
	}
}
