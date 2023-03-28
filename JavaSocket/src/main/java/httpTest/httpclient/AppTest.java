package httpTest.httpclient;

public class AppTest {

    public static void main(String[] args) throws Exception {
        HttpClient.ins().init();
        HttpClient.ins().post("http://localhost:8081", "");
    }
}
