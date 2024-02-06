package com.example.zgames.games;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.navigation.Navigation;

import com.example.zgames.R;
import com.example.zgames.model.SimonModel;
import com.example.zgames.tools.Toaster;
import com.example.zgames.types.Sequence;
import com.example.zgames.types.SimonPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simon {
    private final Button submitButton;
    private final HashMap<Button, String> colorButtonsMap;
    private final Context context;
    private final List<String> userSequence;
    private final SimonPlayer player;
    private final View view;

    private int currentScore;
    private List<String> piSequence;

    public Simon(Context context, View view, SimonPlayer player) {
        this.context = context;
        this.view = view;
        this.player = player;

        currentScore = 0;
        userSequence = new ArrayList<>();

        Button redButton = view.findViewById(R.id.redButton);
        Button yellowButton = view.findViewById(R.id.yellowButton);
        Button greenButton = view.findViewById(R.id.greenButton);
        Button blueButton = view.findViewById(R.id.blueButton);

        colorButtonsMap = new HashMap<>();
        colorButtonsMap.put(redButton, "red");
        colorButtonsMap.put(yellowButton, "yellow");
        colorButtonsMap.put(greenButton, "green");
        colorButtonsMap.put(blueButton, "blue");

        submitButton = view.findViewById(R.id.simonGameSubmitButton);
    }

    public void startGame() {
        (new SimonModel()).startNewGame(context, new SimonModel.SequenceResponseHandler() {
            @Override
            public void response(List<String> sequence) {
                piSequence = sequence;
                setColorButtonClickListeners();
                setSubmitButtonClickListener();
            }

            @Override
            public void error() {
                Toaster.showGeneralErrorToast(context);
            }
        });
    }

    private boolean isCorrectSequence() {
        return userSequence.equals(piSequence);
    }

    private void handleCorrectSequence()  {
        Toaster.showToast(context, "Correct!");

        removeColorButtonClickListeners();
        removeSubmitButtonClickListener();

        userSequence.clear();

        currentScore++;

        pause();

        Sequence sequence = new Sequence(piSequence);
        (new SimonModel()).sendSequence(context, sequence, new SimonModel.SequenceResponseHandler() {
            @Override
            public void response(List<String> sequence) {
                piSequence = sequence;
                setColorButtonClickListeners();
                setSubmitButtonClickListener();
            }

            @Override
            public void error() {
                Toaster.showGeneralErrorToast(context);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void handleIncorrectSequence() {
        Bundle bundle = new Bundle();

        if (currentScore > player.highScore) {
            (new SimonModel()).updateScore(context, player.simonId, currentScore, new SimonModel.UpdateScoreResponseHandler() {
                @SuppressLint("DefaultLocale")
                @Override
                public void response() {
                    player.highScore = currentScore;
                    bundle.putParcelable("player", player);
                    Navigation.findNavController(view).navigate(R.id.action_simonGameFragment_to_simonGameOverFragment, bundle);
                    
                    Toaster.showToast(context, String.format("New High Score: %d", currentScore));
                }

                @Override
                public void error() {
                    Toaster.showGeneralErrorToast(context);
                }
            });
        } else {
            bundle.putParcelable("player", player);
            Navigation.findNavController(view).navigate(R.id.action_simonGameFragment_to_simonGameOverFragment, bundle);

            Toaster.showToast(context, String.format("Your Score: %d", currentScore));
        }
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

    private void setSubmitButtonClickListener() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCorrectSequence()) {
                    handleCorrectSequence();
                } else {
                    handleIncorrectSequence();
                }
            }
        });
    }

    private void removeColorButtonClickListeners() {
        for (Map.Entry<Button, String> buttonStringEntry : colorButtonsMap.entrySet()) {
            Button button = buttonStringEntry.getKey();
            button.setOnClickListener(null);
        }
    }

    private void removeSubmitButtonClickListener() {
        submitButton.setOnClickListener(null);
    }

    private void pause() {
        try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
    }
}
