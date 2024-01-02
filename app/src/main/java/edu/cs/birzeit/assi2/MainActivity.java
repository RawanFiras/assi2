package edu.cs.birzeit.assi2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_NAME = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        CheckBox rememberCheckbox = findViewById(R.id.rememberCheckbox);

        MaterialButton loginbtn = findViewById(R.id.loginbtn);
        MaterialButton registerButton = findViewById(R.id.registerButton);

        // Retrieve values from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean rememberMeChecked = sharedPreferences.getBoolean("rememberMe", false);

        if (rememberMeChecked) {
            // If "Remember Me" was checked, pre-fill the username and password fields
            username.setText(sharedPreferences.getString("username", ""));
            password.setText(sharedPreferences.getString("password", ""));
            rememberCheckbox.setChecked(true);
        }

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get entered credentials
                String enteredUsername = username.getText().toString();
                String enteredPassword = password.getText().toString();

                if (rememberCheckbox.isChecked()) {
                    // Save credentials in SharedPreferences if "Remember Me" is checked
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", enteredUsername);
                    editor.putString("password", enteredPassword);
                    editor.putBoolean("rememberMe", true);
                    editor.apply();
                } else {
                    // Clear stored credentials if "Remember Me" is not checked
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("username");
                    editor.remove("password");
                    editor.remove("rememberMe");
                    editor.apply();
                }

                // Rest of your login logic...
                // Check credentials and open HomePage if successful
                if (checkCredentials(enteredUsername, enteredPassword)) {
                    // Successful login
                    Toast.makeText(MainActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    // Open HomePage
                    Intent intent = new Intent(MainActivity.this, HomePage.class);
                    startActivity(intent);
                } else {
                    // Failed login
                    Toast.makeText(MainActivity.this, "LOGIN FAILED", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open RegisterActivity
                Intent intent = new Intent(MainActivity.this, regester.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkCredentials(String username, String password) {

        return true;
    }
}
