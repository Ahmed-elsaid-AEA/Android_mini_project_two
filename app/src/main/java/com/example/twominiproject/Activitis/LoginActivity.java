package com.example.twominiproject.Activitis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.twominiproject.DataClasses.AdminClass;
import com.example.twominiproject.Databases.Mydatabase;
import com.example.twominiproject.R;
import com.example.twominiproject.databinding.ActivityLoginBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    public static int REQ_LOGIN_ACTIVITY = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        binding.TIETUserName.setText("a");
        binding.TIETPassword.setText("ahmed");

        binding.BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.TIETUserName.getText().toString();
                String password = binding.TIETPassword.getText().toString();
                Mydatabase db = new Mydatabase(getApplicationContext());
                boolean found = db.checkExist(new AdminClass(username, password));
                if (username.isEmpty() || password.isEmpty()) {
                    Snackbar.make(binding.BtnLogin, "PLease Enter  User name And password", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (found) {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivityForResult(intent, REQ_LOGIN_ACTIVITY);
                } else {
                    Snackbar.make(binding.BtnLogin, "PLease Enter Correct User name And password", Snackbar.LENGTH_LONG).show();

                }
            }
        });


    }
}