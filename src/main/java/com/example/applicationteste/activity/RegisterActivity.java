package com.example.applicationteste.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.applicationteste.dao.UserDAO;
import com.example.applicationteste.helper.DBHelper;

import com.example.applicationteste.R;
import com.example.applicationteste.model.User;

public class RegisterActivity extends AppCompatActivity {
    EditText email_input, nome_input, telefone_input, senha_input;
    Button register_button;
    UserDAO uDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email_input = findViewById(R.id.editTextTextEmailAddress);
        nome_input = findViewById(R.id.editTextTextName);
        telefone_input = findViewById(R.id.editTextTextPhone);
        senha_input = findViewById(R.id.editTextTextPassword);

        register_button = findViewById(R.id.buttonRegister);

        Intent it = getIntent();
        email_input.setText(it.getStringExtra("email"));

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (
                    !email_input.getText().toString().trim().isEmpty() &&
                    !nome_input.getText().toString().trim().isEmpty() &&
                    !telefone_input.getText().toString().trim().isEmpty() &&
                    !senha_input.getText().toString().isEmpty()
                ) {
                    if (!email_input.getText().toString().contains("@")) {
                        Toast.makeText(RegisterActivity.this, "Preencha com um email válido!" , Toast.LENGTH_SHORT).show();
                    } else if (senha_input.getText().toString().length() < 8) {
                        Toast.makeText(RegisterActivity.this, "A senha deve ter no mínimo 8 caracteres!", Toast.LENGTH_SHORT).show();
                    } else {
                        uDao = new UserDAO(
                            getApplicationContext(),
                            new User(
                                nome_input.getText().toString().trim(),
                                telefone_input.getText().toString().trim(),
                                email_input.getText().toString().trim(),
                                senha_input.getText().toString()
                            )
                        );

                        if (!uDao.register()) {
                            Toast.makeText(RegisterActivity.this, "Falha!", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent redirecionar = new Intent(RegisterActivity.this, LoginActivity.class);
                            redirecionar.putExtra("email", email_input.getText().toString().trim());
                            startActivity(redirecionar);
                        }
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Preencha todos os campos" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}