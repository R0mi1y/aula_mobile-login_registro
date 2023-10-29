package com.example.applicationteste.activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View; // Importe a classe View

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.applicationteste.R;
import com.example.applicationteste.dao.UserDAO;
import com.example.applicationteste.model.User;

public class LoginActivity extends AppCompatActivity {

    EditText email_input, password_input;
    Button register_button, login_button;

    UserDAO uDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_login);

        email_input = findViewById(R.id.email_input);
        password_input = findViewById(R.id.password_input);

        register_button = findViewById(R.id.register_button);
        login_button = findViewById(R.id.login_button);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it_register = new Intent(LoginActivity.this, RegisterActivity.class);
                it_register.putExtra("email", email_input.getText().toString());
                startActivity(it_register);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!email_input.getText().toString().isEmpty() && !password_input.getText().toString().isEmpty()) {
                    uDao = new UserDAO(getApplicationContext(), new User(
                            email_input.getText().toString(),
                            password_input.getText().toString()
                        )
                    );

                    if (uDao.login()) {
                        Intent it = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(it);
                    } else {
                        Toast.makeText(LoginActivity.this, "Dados incorretos!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}