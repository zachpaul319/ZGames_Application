package com.example.zgames;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.zgames.model.UserModel;
import com.example.zgames.tools.FieldChecker;
import com.example.zgames.tools.Toaster;
import com.example.zgames.types.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    EditText[] fields;
    Button loginButton, signUpButton;
    String username, password;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        fields = new EditText[2];
        fields[0] = view.findViewById(R.id.usernameField);
        fields[1] = view.findViewById(R.id.passwordField);

        loginButton = view.findViewById(R.id.loginButton);
        signUpButton = view.findViewById(R.id.signUpButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FieldChecker.allFieldsFilledOut(fields)) {
                    username = fields[0].getText().toString();
                    password = fields[1].getText().toString();
                    // TODO: CHANGE ME
                    (new UserModel()).getUserByAuth(getContext(), "zpaul20", "s3cr3t", new UserModel.GetUserResponseHandler() {
                        @Override
                        public void response(User user) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("user", user);
                            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment, bundle);
                        }

                        @Override
                        public void error() {
                            Toaster.showToast(getContext(), "Invalid Login");
                        }
                    });
                } else {
                    FieldChecker.changeBorderColors(fields);
                    FieldChecker.showIncompleteFieldsToast(getContext());
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment);
            }
        });

        return view;
    }
}