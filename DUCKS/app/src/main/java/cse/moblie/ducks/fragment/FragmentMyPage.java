package cse.moblie.ducks.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import cse.moblie.ducks.MainActivity;
import cse.moblie.ducks.R;
import cse.moblie.ducks.request.GetJson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FragmentMyPage extends Fragment {

    public HashMap<String, String> userInfo = new HashMap<>();
    public HashMap<String, String> duckInfo = new HashMap<>();

    public HashMap<String, String>[] interestInfo = new HashMap[3];

    TextView tvName;
    TextView tvDuckName;
    TextView tvDuckFollowers;
    Button ibItrst1;
    Button ibItrst2;
    Button ibItrst3;

    public FragmentMyPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_page_fragment, container, false);

        final GetJson httpConn = GetJson.getInstance();
        new Thread() {
            public void run() {
                httpConn.requestWebServer(getInfoCallback, "getMyInfo.php", "ID=" + MainActivity.getLoginID());
            }
        }.start();

        tvName = view.findViewById(R.id.tvNickname);
        tvDuckName = view.findViewById(R.id.tvDuckName);
        tvDuckFollowers = view.findViewById(R.id.tvDuckFollowers);
        ibItrst1 = view.findViewById(R.id.ibInter1);
        ibItrst2 = view.findViewById(R.id.ibInter2);
        ibItrst3 = view.findViewById(R.id.ibInter3);

        return view;
    }

    private final Callback getInfoCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("MyInfo", "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("MyInfo", "성공:" + body);

            try {
                JSONArray jsonArray = new JSONArray(body);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                userInfo.put("num", jsonObject.getString("num"));
                userInfo.put("name", jsonObject.getString("name"));
                userInfo.put("duck", jsonObject.getString("duck"));
                userInfo.put("itrst1", jsonObject.getString("itrst1"));
                userInfo.put("itrst2", jsonObject.getString("itrst2"));
                userInfo.put("itrst3", jsonObject.getString("itrst3"));

                Handler handler = new Handler(Looper.getMainLooper());


                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        final GetJson httpConn = GetJson.getInstance();
                        httpConn.requestWebServer(getDuckCallback, "getDuckInfo.php", "NUM=" + userInfo.get("duck"));
                        String param = "";
                        for (int i = 1; i < 4; i++) {
                            param += "GENRE" + i + "=" + userInfo.get("itrst" + i) + "&";
                        }
                        httpConn.requestWebServer(getGenreCallback, "getGenre.php", param);
                        tvName.setText(userInfo.get("name"));

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private final Callback getDuckCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("Duck", "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("Duck", "성공:" + body);

            try {
                JSONArray jsonArray = new JSONArray(body);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                duckInfo.put("num", jsonObject.getString("num"));
                duckInfo.put("name", jsonObject.getString("name"));
                duckInfo.put("follower", jsonObject.getString("follower"));

                Handler handler = new Handler(Looper.getMainLooper());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvDuckName.setText(duckInfo.get("name"));
                        tvDuckFollowers.setText(duckInfo.get("follower") + "덕");
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private final Callback getGenreCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("Interest", "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("Interest", "성공:" + body);

            try {
                JSONArray jsonArray = new JSONArray(body);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    interestInfo[i] = new HashMap<>();
                    interestInfo[i].put("num", jsonObject.getString("num"));
                    interestInfo[i].put("genre", jsonObject.getString("genre"));
                }
                Handler handler = new Handler(Looper.getMainLooper());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < interestInfo.length; i++) {
                            if (interestInfo[i] != null)
                                if (interestInfo[i].get("genre") != null && !interestInfo[i].get("genre").equals(""))
                                    switch (i) {
                                        case 0:
                                            ibItrst1.setText(interestInfo[i].get("genre").toString());
                                            break;
                                        case 1:
                                            ibItrst2.setText(interestInfo[i].get("genre").toString());
                                            break;
                                        case 2:
                                            ibItrst3.setText(interestInfo[i].get("genre").toString());
                                            break;
                                    }
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}