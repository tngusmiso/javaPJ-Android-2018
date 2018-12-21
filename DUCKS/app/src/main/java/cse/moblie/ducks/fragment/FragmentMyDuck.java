package cse.moblie.ducks.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;

import cse.moblie.ducks.MainActivity;
import cse.moblie.ducks.R;

public class FragmentMyDuck extends Fragment {


    public FragmentMyDuck() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.my_duck_fragment, container, false);

        return view;
    }

}