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
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupTabFragment extends Fragment {

    EditText name, email, password, confirmPass,mobileNo;
    String emailText, passwordText, nameText, cPassword, phone;
    Button signup;
    float v=0;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();

        name = root.findViewById(R.id.name);
        email = root.findViewById(R.id.email);
        password = root.findViewById(R.id.password);
        confirmPass = root.findViewById(R.id.confirm_password);
        mobileNo = root.findViewById(R.id.mobileNo);
        signup = root.findViewById(R.id.signup_button);
        progressbar = root.findViewById(R.id.progressBar2);
        progressbar.setVisibility(View.INVISIBLE);

        email.setTranslationX(800);
        password.setTranslationX(800);
        confirmPass.setTranslationX(800);
        mobileNo.setTranslationX(800);
        signup.setTranslationX(800);


        email.setAlpha(v);
        password.setAlpha(v);
        confirmPass.setAlpha(v);
        mobileNo.setAlpha(v);
        signup.setAlpha(v);


        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        confirmPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        mobileNo.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });


        return  root;

    }

    private void registerNewUser()
    {

        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);
        signup.setEnabled(false);

        // Take the value of two edit texts in Strings


        emailText = email.getText().toString();
        passwordText = password.getText().toString();
        nameText = name.getText().toString();
        cPassword = confirmPass.getText().toString();
        phone = mobileNo.getText().toString();


        // Validations for input email and password
        if (nameText.isEmpty()) {
            name.setError("Enter valid Email");
            name.requestFocus();
            progressbar.setVisibility(View.GONE);
            signup.setEnabled(true);
            return;
        }
        if (emailText.isEmpty()) {
            email.setError("Enter valid Email");
            email.requestFocus();
            progressbar.setVisibility(View.GONE);
            signup.setEnabled(true);
            return;
        }
        if (phone.isEmpty()) {
            mobileNo.setError("Enter valid mobile No");
            mobileNo.requestFocus();
            progressbar.setVisibility(View.GONE);
            signup.setEnabled(true);
            return;
        }
        if (passwordText.isEmpty()) {
            password.setError("Enter valid Email");
            password.requestFocus();
            progressbar.setVisibility(View.GONE);
            signup.setEnabled(true);
            return;
        }
        if (!cPassword.equals(passwordText)) {
            confirmPass.setError("Password doesn't match");
            confirmPass.requestFocus();
            progressbar.setVisibility(View.GONE);
            signup.setEnabled(true);
            return;
        }

        // create new user or register new user
        mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                            SaveUserInfoToDatabase();
                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);
                            signup.setEnabled(true);

                            // if the user created intent to login activity
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        else {

                            // Registration failed
                            String message = task.getException().toString();
                            Toast.makeText(getContext(), message + " Registration failed!!", Toast.LENGTH_SHORT).show();


                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);
                            signup.setEnabled(true);
                        }
                    }
                });
    }

    private  void SaveUserInfoToDatabase()
    {
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("username",nameText);
        userMap.put("email", emailText);
        userMap.put("phone", phone);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Info").updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(getContext(), "info saved to database:   "+userId, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String message = task.getException().toString();
                    Toast.makeText(getContext(), "Error:"+message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
