package cse.moblie.ducks.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import cse.moblie.ducks.MainActivity;
import cse.moblie.ducks.R;
import cse.moblie.ducks.request.GetJson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class FragmentMyPage extends Fragment {

    final int REQUEST_GALLERY = 1;
    ImageButton ibMyPic;
    ImageView ivDuckPic;
    Bitmap bmImg;


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
                if (interestInfo[i].get("num") != null || interestInfo[i].get("num").equals("0"))
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

        ivDuckPic = view.findViewById(R.id.ivDuckPic);
        Log.d("",MainActivity.getDuckInfo().get("pic"));
        if(MainActivity.getDuckInfo().get("pic")!=null||!MainActivity.getDuckInfo().get("pic").equals("NULL"))
            new downloadPicTask().execute(MainActivity.getDuckInfo().get("pic"));

        ibMyPic = view.findViewById(R.id.ibMyPic);
        ibMyPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("프로필 사진 변경")
                        .setItems(R.array.select, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Intent intent = new Intent();
                                        intent.setType("image/*");
                                        intent.setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(intent, REQUEST_GALLERY);
                                        break;
                                    case 1:
                                        ibMyPic.setImageResource(R.drawable.my_pic);
                                        break;
                                }
                            }
                        })
                .show();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_GALLERY) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getActivity().getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    ibMyPic.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class downloadPicTask extends AsyncTask<String, Integer,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try{
                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();

                bmImg = BitmapFactory.decodeStream(is);

            }catch(IOException e){
                e.printStackTrace();
            }
            return bmImg;
        }

        protected void onPostExecute(Bitmap img){
            ivDuckPic.setImageBitmap(bmImg);
        }

    }

}
