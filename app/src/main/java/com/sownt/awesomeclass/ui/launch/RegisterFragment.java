package com.sownt.awesomeclass.ui.launch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.sownt.awesomeclass.MainActivity;
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
        hasAccount.setOnClickListener(v -> getParentFragmentManager().beginTransaction().replace(
                R.id.launch_container,
                LoginFragment.class,
                null
        ).commit());
    }

    private void register() {
        if (textFullName.getEditText() == null
                || textUsername.getEditText() == null
                || textPassword.getEditText() == null
                || textRetypePassword.getEditText() == null) return;

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
            createUser(fullName, username, password);
        } catch (Exception e) {
        }
    }

    private void createUser(String fullName, String username, String password) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update user's information
                        Log.d("AwesomeClass", "createUserWithEmail:success");
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(fullName)
                                .build();
                        if (user != null) {
                            user.updateProfile(request).addOnCompleteListener(task1 -> {
                                if (task.isSuccessful()) {
                                    Log.d("AwesomeClass", "User profile updated.");
                                    if (getView() != null) Snackbar.make(getView(), "Register successful.", Snackbar.LENGTH_SHORT).show();
                                }
                            });
                        }
                        Intent intent = new Intent(this.getContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("AwesomeClass", "createUserWithEmail:failure", task.getException());
                        if (getView() != null) Snackbar.make(getView(), "Authentication failed.", Snackbar.LENGTH_SHORT).show();
                    }
                });
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
        } else if (!LoginFragment.validate(username)) {
            textUsername.setError("Invalid Email.");
            throw new Exception("Username is empty");
        } else {
            textUsername.setError(null);
            textUsername.setErrorEnabled(false);
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
        if (!password.equals(retypePassword)) {
            textRetypePassword.setError("Passwords do not match.");
        } else {
            textRetypePassword.setError(null);
            textRetypePassword.setErrorEnabled(false);
        }
    }

}
