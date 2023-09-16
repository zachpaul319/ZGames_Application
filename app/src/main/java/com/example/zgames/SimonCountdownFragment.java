package com.example.zgames;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zgames.types.SimonPlayer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SimonCountdownFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SimonCountdownFragment extends Fragment {
    SimonPlayer player;
    TextView countdownValView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SimonCountdownFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SimonCountdownFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SimonCountdownFragment newInstance(String param1, String param2) {
        SimonCountdownFragment fragment = new SimonCountdownFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_simon_countdown, container, false);
        assert getArguments() != null;
        player = getArguments().getParcelable("player");

        countdownValView = view.findViewById(R.id.countdownValView);

        countdown(view);

        return view;
    }

    private void countdown(View view) {
        new CountDownTimer(5500, 1000) {
            @Override
            public void onTick(long l) {
                countdownValView.setText(String.valueOf(l/1000));
            }

            @Override
            public void onFinish() {
                Bundle bundle = new Bundle();
                bundle.putParcelable("player", player);
                Navigation.findNavController(view).navigate(R.id.action_simonCountdownFragment_to_simonGameFragment, bundle);
            }
        }.start();
    }
}