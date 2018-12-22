package cse.moblie.ducks.request;

import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetJson {

    final String server = "http://106.10.56.71/";
    private OkHttpClient client;
    private static GetJson instance = new GetJson();

    public static GetJson getInstance() {
        return instance;
    }

    private GetJson() {
        this.client = new OkHttpClient();
    }


    /**
     * 웹 서버로 요청을 한다.
     */
    public void requestWebServer(Callback callback, String php, String... param) {
        String url = server + php;

        if (param.length > 0) url += "?";

        for (String s : param) {
            url += s + "&";
        }

        Log.d("URL", url);
        RequestBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)//받은 데이터
                .build();
        client.newCall(request).enqueue(callback); //통신후 콜백될 함수
    }

    public static JSONObject uploadImage(String imageUploadUrl, String sourceImageFile, String idx) {
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

        try {
            File sourceFile = new File(sourceImageFile);
            Log.d("TAG", "File...::::" + sourceFile + " : " + sourceFile.exists());
            String filename = sourceImageFile.substring(sourceImageFile.lastIndexOf("/")+1);

            // OKHTTP3
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("uploaded_file", filename, RequestBody.create(MEDIA_TYPE_PNG, sourceFile))
                    .addFormDataPart("idx", idx)
                    .build();

            Request request = new Request.Builder()
                    .url(imageUploadUrl)
                    .post(requestBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if (response != null) {
                if (response.isSuccessful()) {
                    String res = response.body().string();
                    Log.e("TAG", "Success : " + res);
                    return new JSONObject(res);
                }
            }
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("TAG", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("TAG", "Other Error: " + e.getLocalizedMessage());
        }
        return null;
    }

}

