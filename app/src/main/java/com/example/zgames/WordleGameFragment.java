package com.example.zgames;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.zgames.types.WordlePlayer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WordleGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WordleGameFragment extends Fragment {
    GridLayout wordleGridLayout;
    String word;
    WordlePlayer player;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WordleGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WordleGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WordleGameFragment newInstance(String param1, String param2) {
        WordleGameFragment fragment = new WordleGameFragment();
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
        View view = inflater.inflate(R.layout.fragment_wordle_game, container, false);
        assert getArguments() != null;
        player = getArguments().getParcelable("player");
        word = getArguments().getString("word");

        wordleGridLayout = view.findViewById(R.id.wordleGridLayout);
        addTilesToGrid();

        return view;
    }

    private void addTilesToGrid() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                TextView tile = createTile();
                wordleGridLayout.addView(tile);

                tile.setTag(i * 5 + j);
            }
        }
    }

    private TextView createTile() {
        TextView tile = new TextView(requireContext());
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        // Set both width and height to the same value to make it a square
        params.width = getResources().getDimensionPixelSize(R.dimen.tile_size);
        params.height = params.width * 2 - 10;
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        tile.setLayoutParams(params);
        tile.setGravity(Gravity.CENTER);
        tile.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.tile_background));

        return tile;
    }

}