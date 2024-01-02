package edu.cs.birzeit.assi2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    private static final int EDIT_PROFILE_REQUEST_CODE = 1;

    private TextView welcomeMessage;
    private TextView usernameTextView, emailTextView, birthDateTextView, genderTextView, bioTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        welcomeMessage = findViewById(R.id.welcomeMessage);
        usernameTextView = findViewById(R.id.homeUsername);
        emailTextView = findViewById(R.id.homeEmail);
        birthDateTextView = findViewById(R.id.homeBirthDate);
        genderTextView = findViewById(R.id.homeGender);
        bioTextView = findViewById(R.id.homeBio);

        Button editProfileButton = findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open EditProfileActivity for result
                Intent intent = new Intent(HomePage.this, EditProfileActivity.class);
                startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE);
            }
        });


        displayUserInfo();


        Button schoolButton = findViewById(R.id.schoolButton);
        Button universityButton = findViewById(R.id.universityButton);

        schoolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, SchoolActivity.class));
            }
        });

        universityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, UniversityActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == RESULT_OK) {

            displayUserInfo();
        }
    }

    private void displayUserInfo() {

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");


        welcomeMessage.setText("WELCOME! " + username);


        String email = sharedPreferences.getString("email", "");
        String birthDate = sharedPreferences.getString("birthDate", "");
        String gender = sharedPreferences.getString("gender", "");
        String bio = sharedPreferences.getString("bio", "");

        usernameTextView.setText("Username: " + username);
        emailTextView.setText("Email: " + email);
        birthDateTextView.setText("Birth Date: " + birthDate);
        genderTextView.setText("Gender: " + gender);
        bioTextView.setText("Bio: " + bio);
    }
}
