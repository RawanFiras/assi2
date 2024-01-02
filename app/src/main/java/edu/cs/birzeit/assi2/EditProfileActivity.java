package edu.cs.birzeit.assi2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editUsername, editEmail, editBirthDate, editBio;
    private RadioGroup genderRadioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editUsername = findViewById(R.id.editUsername);
        editEmail = findViewById(R.id.editEmail);
        editBirthDate = findViewById(R.id.editBirthDate);
        editBio = findViewById(R.id.editBio);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);

        // Retrieve current user information and set in EditTexts
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editUsername.setText(sharedPreferences.getString("username", ""));
        editEmail.setText(sharedPreferences.getString("email", ""));
        editBirthDate.setText(sharedPreferences.getString("birthDate", ""));
        editBio.setText(sharedPreferences.getString("bio", ""));

        // Set the selected gender from SharedPreferences
        String gender = sharedPreferences.getString("gender", "");
        if (gender.equals("Male")) {
            genderRadioGroup.check(R.id.maleRadioButton);
        } else if (gender.equals("Female")) {
            genderRadioGroup.check(R.id.femaleRadioButton);
        }

        Button saveChangesButton = findViewById(R.id.saveChangesButton);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", editUsername.getText().toString());
                editor.putString("email", editEmail.getText().toString());
                editor.putString("birthDate", editBirthDate.getText().toString());


                int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);
                String selectedGender = selectedGenderRadioButton.getText().toString();

                editor.putString("gender", selectedGender);
                editor.putString("bio", editBio.getText().toString());
                editor.apply();

                // Set result and finish to update HomePage
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
