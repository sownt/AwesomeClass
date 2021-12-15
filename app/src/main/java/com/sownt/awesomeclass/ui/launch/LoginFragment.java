package com.sownt.awesomeclass.ui.launch;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.sownt.awesomeclass.R;

public class LoginFragment extends Fragment {
    private TextInputLayout textUsername;
    private TextInputLayout textPassword;
    private Button forgotPassword;
    private Button signIn;
    private Button signUp;

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textUsername = view.findViewById(R.id.text_username);
        textPassword = view.findViewById(R.id.text_password);
        forgotPassword = view.findViewById(R.id.btn_forgot);
        signIn = view.findViewById(R.id.btn_sign_in);
        signUp = view.findViewById(R.id.btn_sign_up);

        bindingEvents();
    }

    private void bindingEvents() {
        signIn.setOnClickListener(v -> login());
    }

    public void login() {
        if (textUsername.getEditText() == null || textPassword.getEditText() == null) return;
        login(textUsername.getEditText().getText().toString(),
                textPassword.getEditText().getText().toString());
    }

    public void login(String username, String password) {
        try {
            validateUsername(username);
            validatePassword(password);
        } catch (Exception e) {

        }
    }

    private void validateUsername(String username) throws Exception {
        if (username == null) {
            textUsername.setErrorContentDescription("Username is null");
            throw new Exception("Username is null");
        }
        if (username.isEmpty())
            throw new Exception("Username is empty");
    }

    private void validatePassword(String password) throws Exception {
        if (password == null)
            throw new Exception("Password is null");
        if (password.isEmpty())
            throw new Exception("Password is empty");
    }
}