package com.sarkstechsolution.groceryhouse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {

    EditText email, pass;
    TextView forgetPass;
    Button login;
    float v=0;
    private ProgressBar progressbar;

    private FirebaseAuth mAuth;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);
        mAuth = FirebaseAuth.getInstance();

        email = root.findViewById(R.id.Lemail);
        pass = root.findViewById(R.id.Lpass);
        forgetPass = root.findViewById(R.id.forget_pass);
        login = root.findViewById(R.id.login_button);
        progressbar = root.findViewById(R.id.progressBar);
        progressbar.setVisibility(View.GONE);

        email.setTranslationX(800);
        pass.setTranslationX(800);
        forgetPass.setTranslationX(800);
        login.setTranslationX(800);


        email.setAlpha(v);
        pass.setAlpha(v);
        forgetPass.setAlpha(v);
        login.setAlpha(v);


        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });


        return  root;

    }

    private void loginUserAccount()
    {

        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);
        login.setEnabled(false);


        // Take the value of two edit texts in Strings
        String emailText, passwordText;
        emailText = email.getText().toString();
        passwordText = pass.getText().toString();

        // validations for input email and password
        if (emailText.isEmpty()) {
            email.setError("Enter valid Email");
            email.requestFocus();
            login.setEnabled(true);
            progressbar.setVisibility(View.GONE);
            return;
        }

        if (passwordText.isEmpty() || passwordText.length() < 6) {
            pass.setError("Enter valid 6 character password");
            pass.requestFocus();
            login.setEnabled(true);
            progressbar.setVisibility(View.GONE);
            return;
        }

        // signin existing user
        mAuth.signInWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Login successful!!", Toast.LENGTH_LONG).show();

                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);
                            login.setEnabled(true);
                            // if sign-in is successful
                            // intent to home activity


                            Intent intent = new Intent(getActivity(), MainActivity.class);

                            startActivity(intent);
                            getActivity().finish();


                        }

                        else {

                            // sign-in failed
                            Toast.makeText(getContext(), "Login failed!!", Toast.LENGTH_LONG).show();

                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);
                            login.setEnabled(true);
                        }
                    }
                });
    }

}
