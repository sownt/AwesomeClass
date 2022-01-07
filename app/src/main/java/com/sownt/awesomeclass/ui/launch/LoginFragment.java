package com.sownt.awesomeclass.ui.launch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sownt.awesomeclass.MainActivity;
import com.sownt.awesomeclass.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private TextInputLayout textUsername;
    private TextInputLayout textPassword;
    private Button forgotPassword;
    private Button signIn;
    private TextView signUp;

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
        signUp.setOnClickListener(v -> getParentFragmentManager().beginTransaction().replace(
                R.id.launch_container,
                RegisterFragment.class,
                null
        ).commit());
    }

    public void login() {
        if (textUsername.getEditText() == null || textPassword.getEditText() == null) return;

        login(textUsername.getEditText().getText().toString(),
                textPassword.getEditText().getText().toString());
        Log.d("Login", textUsername.getEditText().getText().toString());
    }

    public void login(String username, String password) {
        try {
            validateUsername(username);
            validatePassword(password);
            doLogin(username, password);
        } catch (Exception e) {
        }
    }

    private void doLogin(String username, String password) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("AwesomeClass", "signInWithEmail:success");
                        if (getView() != null) Snackbar.make(getView(), "Successful.", Snackbar.LENGTH_SHORT).show();
                        Intent intent = new Intent(this.getContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("AwesomeClass", "signInWithEmail:failure", task.getException());
                        if (getView() != null) Snackbar.make(getView(), "Authentication failed.", Snackbar.LENGTH_SHORT).show();
                    }

                });
    }

    private void validateUsername(String username) throws Exception {
        if (username == null) {
            throw new Exception("Username is null");
        } else if (username.isEmpty()) {
            textUsername.setError("Email is empty.");
            throw new Exception("Username is empty");
        } else if (!validate(username)) {
            textUsername.setError("Invalid Email.");
            throw new Exception("Invalid Email!");
        } else {
            textUsername.setError(null);
            textUsername.setErrorEnabled(false);
        }
    }

    private void validatePassword(String password) throws Exception {
        if (password == null)
            throw new Exception("Password is null");
        if (password.isEmpty())
            throw new Exception("Password is empty");
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}