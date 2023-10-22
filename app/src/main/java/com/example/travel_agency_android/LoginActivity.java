package com.example.travel_agency_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.travel_agency_android.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SharedPreferences sharedPreferences = getSharedPreferences("register", Context.MODE_PRIVATE);
//        String sharedEmail = sharedPreferences.getString("email", "");
//        String sharedPassword = sharedPreferences.getString("password", "");

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.loginEmail.getText().toString();
                String password = binding.loginPassword.getText().toString();

//                Intent intent = new Intent(getApplicationContext(), TravelFormActivity.class);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

//                if (email.equals("") || password.equals(""))
//                    Toast.makeText(LoginActivity.this, "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
//                else {
//                    Boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);
//                    if (checkCredentials == true) {
//                        Toast.makeText(LoginActivity.this, "Conectado com sucesso!", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
//                    }
//                }
            }
        });
        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}