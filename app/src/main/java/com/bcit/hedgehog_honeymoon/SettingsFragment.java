package com.bcit.hedgehog_honeymoon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    private SoundPlayer soundPlayer;

    private MainInterface mainListener;

    // TODO: Rename and change types of parameters
    private SettingsClass mParam1;
    //private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * //@param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(SettingsClass param1) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (SettingsClass) getArguments().getSerializable(ARG_PARAM1);
        } else {
            System.out.println("*ARGUMENTS NOTTTTTTTTTTTTTTTTTTFOUND*");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SwitchCompat switchView = getView().findViewById(R.id.switch_settings_music);
        SwitchCompat switchView2 = getView().findViewById(R.id.switch_settings_sfx);

        if(mParam1 != null) {
            boolean isMusicOn = mParam1.isMusicOn();
            boolean isSfxOn = mParam1.isSfxOn();
            // Set switches on or off depending on bool
            switchView.setChecked(isMusicOn);
            switchView2.setChecked(isSfxOn);


            switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Intent musicService = new Intent(getActivity(), BackgroundSoundService.class);
                    if (isChecked){
                        getActivity().startService(musicService);
                        ((MainActivity) getActivity()).setSettingsContextMusic(true);
                    } else {
                        getActivity().stopService(musicService);
                        ((MainActivity) getActivity()).setSettingsContextMusic(false);
                    }
                }
            });

            switchView2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        // add logic to turn on sfx
                        try {
                            ((MainActivity) getActivity()).setSettingsContextSfx(true);
                        } catch (NullPointerException e) {
                            System.out.println(e.getMessage());
                        }

                    } else {
                        // add logic to turn off sfx (maybe is done already in main activity
                        try {
                            ((MainActivity) getActivity()).setSettingsContextSfx(false);
                        } catch (NullPointerException e) {
                            System.out.println(e.getMessage());
                        }

                    }
                }
            });

            System.out.println("@@@@@@@@!!!!!!!!!!!!!!!!!!@@@@@@@@@@@");
            System.out.println("TESTING fragment = " + mParam1);

        } else {
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            System.out.println("mparam1 IS null");
        }

    }


}