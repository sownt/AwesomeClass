package com.sownt.awesomeclass.ui.launch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.sownt.awesomeclass.R;

public class RegisterFragment extends Fragment {
    private TextInputLayout textFullName;
    private TextInputLayout textUsername;
    private TextInputLayout textPassword;
    private TextInputLayout textRetypePassword;
    private Button hasAccount;
    private Button signUp;

    public RegisterFragment() {
        super(R.layout.fragment_register);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textFullName = view.findViewById(R.id.text_full_name);
        textUsername = view.findViewById(R.id.text_username);
        textPassword = view.findViewById(R.id.text_password);
        textRetypePassword = view.findViewById(R.id.text_retype_password);
        hasAccount = view.findViewById(R.id.btn_has_account);
        signUp = view.findViewById(R.id.btn_sign_up);

        bindingEvents();
    }

    private void bindingEvents() {
        signUp.setOnClickListener(v -> register());
    }

    private void register() {
        if (textFullName.getEditText() == null
                || textUsername.getEditText() == null
                || textPassword.getEditText() == null
                || textRetypePassword.getEditText() == null
        ) return;
        register(textFullName.getEditText().getText().toString().trim(),
                textUsername.getEditText().getText().toString().trim(),
                textPassword.getEditText().getText().toString().trim(),
                textRetypePassword.getEditText().getText().toString().trim());
    }

    private void register(String fullName, String username, String password, String retypePassword) {
        try {
            validateFullName(fullName);
            validateUsername(username);
            validatePassword(password);
            validateRetypePassword(password, retypePassword);
        } catch (Exception e) {

        }
    }

    private void validateFullName(String fullName) throws Exception {
        if (fullName == null) {
            textFullName.setErrorContentDescription("Full name is null");
            throw new Exception("Full name is null");
        }
        if (fullName.isEmpty()) {
            textFullName.setErrorContentDescription("Full name is empty");
            throw new Exception("Full name is empty");
        }
    }

    private void validateUsername(String username) throws Exception {
        if (username == null) {
            textUsername.setErrorContentDescription("Username is null");
            throw new Exception("Username is null");
        }
        if (username.isEmpty()) {
            textUsername.setErrorContentDescription("Username is empty");
            throw new Exception("Username is empty");
        }
    }

    private void validatePassword(String password) throws Exception {
        if (password == null) {
            textPassword.setErrorContentDescription("Password is null");
            throw new Exception("Password is null");
        }
        if (password.isEmpty()) {
            textPassword.setErrorContentDescription("Password is empty");
            throw new Exception("Password is empty");
        }
    }

    private void validateRetypePassword(String password, String retypePassword) throws Exception {
        if (retypePassword == null) {
            textRetypePassword.setErrorContentDescription("Retype password is null");
            throw new Exception("Retype password is null");
        }
        if (retypePassword.isEmpty()) {
            textRetypePassword.setErrorContentDescription("Retype password is empty");
            throw new Exception("Retype password is empty");
        }
        if (!retypePassword.equals(password)) {
            textRetypePassword.setErrorContentDescription("Password is not match");
            throw new Exception("Password is not match");
        }
    }

}
