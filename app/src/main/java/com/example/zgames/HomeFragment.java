package com.example.zgames;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zgames.model.RPSModel;
import com.example.zgames.model.SimonModel;
import com.example.zgames.model.WordleModel;
import com.example.zgames.tools.Toaster;
import com.example.zgames.types.RPSPlayer;
import com.example.zgames.types.SimonPlayer;
import com.example.zgames.types.User;
import com.example.zgames.types.WordlePlayer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    Button updateAccountButton, deleteAccountButton;
    ImageButton simonButton, rpsButton, wordleButton, threeThirteenButton;
    int userId;
    String displayName;
    TextView welcomeNameView;
    User user;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        assert getArguments() != null;
        user = getArguments().getParcelable("user");
        displayName = user.displayName;
        userId = user.userId;

        welcomeNameView = view.findViewById(R.id.welcomeNameView);
        welcomeNameView.setText(String.format("Welcome, %s!", displayName));

        simonButton = view.findViewById(R.id.simonImageButton);
        simonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new SimonModel()).getPlayer(getContext(), userId, new SimonModel.GetPlayerResponseHandler() {
                    @Override
                    public void response(SimonPlayer player) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("player", player);
                        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_simonHomeFragment, bundle);
                    }

                    @Override
                    public void error() {
                        Toaster.showGeneralErrorToast(getContext());
                    }
                });
            }
        });

        rpsButton = view.findViewById(R.id.rpsImageButton);
        rpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new RPSModel()).getPlayer(getContext(), userId, new RPSModel.GetPlayerResponseHandler() {
                    @Override
                    public void response(RPSPlayer player) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("player", player);
                        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_RPSHomeFragment, bundle);
                    }

                    @Override
                    public void error() {
                        Toaster.showGeneralErrorToast(getContext());
                    }
                });
            }
        });

        wordleButton = view.findViewById(R.id.wordleImageButton);
        wordleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new WordleModel()).getPlayer(getContext(), userId, new WordleModel.GetPlayerResponseHandler() {
                    @Override
                    public void response(WordlePlayer player) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("player", player);
                        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_wordleHomeFragment, bundle);
                    }

                    @Override
                    public void error() {
                        Toaster.showGeneralErrorToast(getContext());
                    }
                });
            }
        });

        return view;
    }
}