package com.example.zgames;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zgames.model.SimonModel;
import com.example.zgames.tools.Toaster;
import com.example.zgames.types.Sequence;
import com.example.zgames.types.SimonPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SimonGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SimonGameFragment extends Fragment {
    Button redButton, yellowButton, greenButton, blueButton, submitButton;
    HashMap<Button, String> colorButtonsMap;
    int currentScore, highScore;
    List<String> piSequence, userSequence;
    SimonPlayer player;
    TextView highScoreView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SimonGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SimonGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SimonGameFragment newInstance(String param1, String param2) {
        SimonGameFragment fragment = new SimonGameFragment();
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
        View view = inflater.inflate(R.layout.fragment_simon_game, container, false);
        assert getArguments() != null;
        player = getArguments().getParcelable("player");
        highScore = player.highScore;

        highScoreView = view.findViewById(R.id.highScoreViewGame);
        highScoreView.setText(String.format("High Score: %d", highScore));

        submitButton = view.findViewById(R.id.simonGameSubmitButton);

        redButton = view.findViewById(R.id.redButton);
        yellowButton = view.findViewById(R.id.yellowButton);
        greenButton = view.findViewById(R.id.greenButton);
        blueButton = view.findViewById(R.id.blueButton);

        colorButtonsMap = new HashMap<Button, String>();
        colorButtonsMap.put(redButton, "red");
        colorButtonsMap.put(yellowButton, "yellow");
        colorButtonsMap.put(greenButton, "green");
        colorButtonsMap.put(blueButton, "blue");

        userSequence = new ArrayList<>();

        currentScore = 0;

        (new SimonModel()).startNewGame(getContext(), new SimonModel.SequenceResponseHandler() {
            @Override
            public void response(List<String> sequence) {
                piSequence = sequence;
                setColorButtonClickListeners();
                setSubmitButtonClickListener();
            }

            @Override
            public void error() {
                Toaster.showGeneralErrorToast(getContext());
            }
        });

        return view;
    }

    private boolean isCorrectSequence() {
        return userSequence.equals(piSequence);
    }

    private void setSubmitButtonClickListener() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCorrectSequence()) {
                    Toaster.showToast(getContext(), "Correct!");
                    removeColorButtonCLickListeners();
                    removeSubmitButtonClickListener();

                    userSequence.clear();

                    currentScore++;

                    Sequence sequence = new Sequence(piSequence);
                    (new SimonModel()).sendSequence(getContext(), sequence, new SimonModel.SequenceResponseHandler() {
                        @Override
                        public void response(List<String> sequence) {
                            piSequence = sequence;
                            setColorButtonClickListeners();
                            setSubmitButtonClickListener();
                        }

                        @Override
                        public void error() {
                            Toaster.showGeneralErrorToast(getContext());
                        }
                    });
                } else {
                    if (currentScore > highScore) {
                        (new SimonModel()).updateScore(getContext(), player.simonId, currentScore, new SimonModel.UpdateScoreResponseHandler() {
                            @Override
                            public void response() {
                                Toaster.showToast(getContext(), String.format("New High Score: %d", currentScore));
                            }

                            @Override
                            public void error() {
                                Toaster.showGeneralErrorToast(getContext());
                            }
                        });
                    } else {
                        Toaster.showToast(getContext(), String.format("Score: %d", currentScore));
                    }
                }
            }
        });
    }

    private void removeSubmitButtonClickListener() {
        submitButton.setOnClickListener(null);
    }

    private void setColorButtonClickListeners() {
        for (Map.Entry<Button, String> buttonStringEntry : colorButtonsMap.entrySet()) {
            Button button = buttonStringEntry.getKey();
            String color = buttonStringEntry.getValue();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userSequence.add(color);
                }
            });
        }
    }

    private void removeColorButtonCLickListeners() {
        for (Map.Entry<Button, String> buttonStringEntry : colorButtonsMap.entrySet()) {
            Button button = buttonStringEntry.getKey();
            button.setOnClickListener(null);
        }
    }
}