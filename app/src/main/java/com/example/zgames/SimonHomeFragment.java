package com.example.zgames;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zgames.types.SimonPlayer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SimonHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SimonHomeFragment extends Fragment {
    Button simonPlayButton;
    int highScore;
    SimonPlayer player;
    TextView highScoreView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SimonHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SimonHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SimonHomeFragment newInstance(String param1, String param2) {
        SimonHomeFragment fragment = new SimonHomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_simon_home, container, false);
        assert getArguments() != null;
        player = getArguments().getParcelable("player");
        highScore = player.highScore;

        highScoreView = view.findViewById(R.id.highScoreViewHome);
        highScoreView.setText(String.format("High Score: %d", highScore));

        simonPlayButton = view.findViewById(R.id.simonPlayButton);
        simonPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("player", player);
                Navigation.findNavController(view).navigate(R.id.action_simonHomeFragment_to_simonCountdownFragment, bundle);
            }
        });
        return view;
    }
}