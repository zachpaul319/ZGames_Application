package com.example.zgames;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zgames.types.RPSPlayer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RPSHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RPSHomeFragment extends Fragment {
    Button rpsPlayButton;
    int longestStreak;
    RPSPlayer player;
    TextView longestStreakView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RPSHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RPSHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RPSHomeFragment newInstance(String param1, String param2) {
        RPSHomeFragment fragment = new RPSHomeFragment();
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

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rps_home, container, false);
        assert getArguments() != null;
        player = getArguments().getParcelable("player");
        longestStreak = player.longestStreak;

        longestStreakView = view.findViewById(R.id.longestStreakHomeView);
        longestStreakView.setText(String.format("Longest Streak: %d", longestStreak));

        rpsPlayButton = view.findViewById(R.id.rpsPlayButton);

        return view;
    }
}