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

    public FragmentMyPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_page_fragment, container, false);

        TextView tvName = view.findViewById(R.id.tvNickname);
        TextView tvDuckName = view.findViewById(R.id.tvDuckName);
        TextView tvDuckFollowers = view.findViewById(R.id.tvDuckFollowers);
        Button ibItrst1 = view.findViewById(R.id.ibInter1);
        Button ibItrst2 = view.findViewById(R.id.ibInter2);
        Button ibItrst3 = view.findViewById(R.id.ibInter3);

        HashMap<String, String> userInfo = MainActivity.getUserInfo();
        HashMap<String, String> duckInfo = MainActivity.getDuckInfo();
        HashMap<String, String>[] interestInfo = MainActivity.getInterestInfo();

        tvName.setText(userInfo.get("name"));
        tvDuckName.setText(duckInfo.get("name"));
        tvDuckFollowers.setText(duckInfo.get("follower") + "덕");

        for (int i = 0; i < MainActivity.getInterestInfo().length; i++) {
            if (interestInfo[i] != null) {
                String genre = interestInfo[i].get("genre");
                if (genre != null && !genre.equals(""))
                    switch (i) {
                        case 0:
                            ibItrst1.setText(genre.toString());
                            break;
                        case 1:
                            ibItrst2.setText(genre.toString());
                            break;
                        case 2:
                            ibItrst3.setText(genre.toString());
                            break;
                    }
            }
        }
        return view;
    }
}