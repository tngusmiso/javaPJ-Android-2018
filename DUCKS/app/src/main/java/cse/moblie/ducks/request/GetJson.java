package cse.moblie.ducks.request;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class GetJson {

    private OkHttpClient client;
    private static GetJson instance = new GetJson();
    public static GetJson getInstance() {
        return instance;
    }

    private GetJson(){ this.client = new OkHttpClient(); }


    /** 웹 서버로 요청을 한다. */
    public void requestWebServer(String parameter,Callback callback) {
        RequestBody body = new FormBody.Builder()

                .build();
        Request request = new Request.Builder()
                .url( "https://106.10.56.71/")
                .post(body)//받은 데이터
                .build();
        client.newCall(request).enqueue(callback); //통신후 콜백될 함수
    }


}
