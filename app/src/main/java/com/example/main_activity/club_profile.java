package com.example.main_activity;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;
        import java.util.Calendar;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import java.util.Locale;

public class club_profile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button save;
    private EditText dateEditText;
    private EditText contactNameEditText, numberEditText, instagramEditText, clubNameEditText;
    private ImageView imagePreview; // ImageView for the logo
    private Uri imageUri; // URI of the selected image
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String username;
    private DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_profile);

        // Initialize Firebase components
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("user");

        // Get the username from the intent
        username = getIntent().getStringExtra("userName");

        // Initialize UI components
        save = findViewById(R.id.save);
        dateEditText = findViewById(R.id.date);
        contactNameEditText = findViewById(R.id.contactName);
        numberEditText = findViewById(R.id.number);
        instagramEditText = findViewById(R.id.instagram);
        clubNameEditText= findViewById(R.id.name);
        imagePreview = findViewById(R.id.image_preview); // ImageView for displaying the selected logo

        Button buttonChooseImage = findViewById(R.id.button_choose_image);
        buttonChooseImage.setOnClickListener(v -> openGallery());

        dateEditText.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            picker = new DatePickerDialog(club_profile.this, (view, year1, monthOfYear, dayOfMonth) -> {
                dateEditText.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year1)); // Date format: DD/MM/YYYY
            }, year, month, day);
            picker.show();
        });

        save.setOnClickListener(view -> saveClubProfile());
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imagePreview.setImageURI(imageUri); // Set the ImageView to display the selected image
        }
    }

    private void saveClubProfile() {
        String contactName = contactNameEditText.getText().toString().trim();
        String number = numberEditText.getText().toString().trim();
        String instagramLink = instagramEditText.getText().toString().trim();
        String foundationDate = dateEditText.getText().toString().trim();
        String name = clubNameEditText.getText().toString().trim();

        // Check if any of the fields are empty
        if (contactName.isEmpty() || number.isEmpty() || instagramLink.isEmpty() || foundationDate.isEmpty()) {
            Toast.makeText(club_profile.this, "All fields must be filled out", Toast.LENGTH_LONG).show();
            return; // Stop execution of the method here
        }

        if (!number.matches("[0-9]{10}")) {
            Toast.makeText(club_profile.this, "Phone number must be 10 digits and numeric", Toast.LENGTH_LONG).show();
            return;
        }

        // Instagram handle validation
        if (!instagramLink.startsWith("@")) {
            Toast.makeText(club_profile.this, "Instagram link must start with @", Toast.LENGTH_LONG).show();
            return;
        }

        // Proceed with creating the club profile object
        ClubProfile clubProfile = new ClubProfile(contactName, number, instagramLink, foundationDate, name);

        // Use the username to save the club profile data
        if (username != null && !username.isEmpty()) {
            databaseReference.child(username).child("club profile").setValue(clubProfile)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(club_profile.this, "Profile saved!", Toast.LENGTH_SHORT).show();
                            // Handle success
                        } else {
                            Toast.makeText(club_profile.this, "Failed to save profile!", Toast.LENGTH_SHORT).show();
                            // Handle failure
                        }
                    });
        } else {
            Toast.makeText(club_profile.this, "Username is undefined. Cannot save profile.", Toast.LENGTH_LONG).show();
        }
    }
}