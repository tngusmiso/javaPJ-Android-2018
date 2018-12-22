package cse.moblie.ducks.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.zip.Inflater;

import cse.moblie.ducks.MainActivity;
import cse.moblie.ducks.R;

public class FragmentMyDuck extends Fragment {


    public FragmentMyDuck() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_duck_fragment, container, false);

        TextView tvDuckName = view.findViewById(R.id.tvDuckName);
        TextView tvFollower = view.findViewById(R.id.tvDuckFollowers);
        TextView tvMembers = view.findViewById(R.id.tvMembers);
        TextView tvLine2 = view.findViewById(R.id.tvLine2);
        TextView tvLink = view.findViewById(R.id.tvLink);


        HashMap<String, String> userInfo = MainActivity.getUserInfo();
        HashMap<String, String> duckInfo = MainActivity.getDuckInfo();
        HashMap<String, String>[] interestInfo = MainActivity.getInterestInfo();

        tvDuckName.setText(duckInfo.get("name"));
        tvFollower.setText(duckInfo.get("follower") + "덕");
        if(duckInfo.get("link")==null||duckInfo.get("link").equals("null"))
            tvLink.setText("");
        else tvLink.setText(duckInfo.get("link"));

        if(duckInfo.get("type").equals("0")){
            tvMembers.setVisibility(View.VISIBLE);
            tvLine2.setVisibility(View.VISIBLE);
        }else{
            tvMembers.setVisibility(View.GONE);
            tvLine2.setVisibility(View.GONE);
        }

        return view;
    }

}