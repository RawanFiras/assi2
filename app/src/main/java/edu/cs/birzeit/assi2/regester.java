package edu.cs.birzeit.assi2;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

import edu.cs.birzeit.assi2.R;

public class regester extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText, emailEditText, birthDateEditText, bioEditText;
    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;
    private Button RegisterButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);

        usernameEditText = findViewById(R.id.registerUsername);
        passwordEditText = findViewById(R.id.registerPassword);
        emailEditText = findViewById(R.id.registerEmail);
        birthDateEditText = findViewById(R.id.registerBirthDate);
        genderRadioGroup = findViewById(R.id.registerGenderRadioGroup);
        maleRadioButton = findViewById(R.id.radioMale);
        femaleRadioButton = findViewById(R.id.radioFemale);
        bioEditText = findViewById(R.id.registerBio);
        RegisterButton = findViewById(R.id.registerButton);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Set up the birth date picker
        birthDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the input values
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String birthDate = birthDateEditText.getText().toString().trim();
                String gender = getSelectedGender();
                String bio = bioEditText.getText().toString().trim();

                // Store data in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("password", password);
                editor.putString("email", email);
                editor.putString("birthDate", birthDate);
                editor.putString("gender", gender);
                editor.putString("bio", bio);
                editor.apply();

                // Navigate to MainActivity
                Intent intent = new Intent(regester.this, MainActivity.class);
                startActivity(intent);
            }
        });

    };


    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        birthDateEditText.setText(selectedDate);
                    }
                },
                year,
                month,
                day
        );
        datePickerDialog.show();
    }

    private String getSelectedGender() {
        int selectedId = genderRadioGroup.getCheckedRadioButtonId();

        if (selectedId == maleRadioButton.getId()) {
            return "Male";
        } else if (selectedId == femaleRadioButton.getId()) {
            return "Female";
        } else {
            return "";
        }
    }
}
