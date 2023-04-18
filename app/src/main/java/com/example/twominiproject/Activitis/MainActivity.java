package com.example.twominiproject.Activitis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.twominiproject.R;
import com.example.twominiproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
    }
}