package com.example.fctc19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class imageuploader extends AppCompatActivity {
      ImageView imageView;
      ProgressBar progressBar;
      Button uploadBtn,showAllBtn;
    String StuID;
    DatabaseReference root;


      private StorageReference reference = FirebaseStorage.getInstance("gs://fct-5b4af.appspot.com").getReference();
      private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageuploader);
        Intent intent = getIntent();
        StuID = intent.getStringExtra("s_id");
        root = FirebaseDatabase.getInstance("https://fct-5b4af-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users"+"/"+StuID).child("imageurl/");
        uploadBtn = findViewById(R.id.imgUpload);
        showAllBtn =findViewById(R.id.uploadedImg);
        progressBar = findViewById(R.id.progress);
        imageView = findViewById(R.id.uploadImage);

        progressBar.setVisibility(View.INVISIBLE);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);

            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageUri != null){
                    uploadToFireBase(imageUri);
                }else{
                    Toast.makeText(imageuploader.this,"Please Select your vaccination card Image", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==2 && resultCode == RESULT_OK && data != null ){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void  uploadToFireBase(Uri uri){
        StorageReference fileRef = reference.child(StuID+"."+getFileExtention(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Model model = new Model(uri.toString());
                       // String modelID = root.push().getKey();
                        root.setValue(model);
                        FirebaseDatabase.getInstance("https://fct-5b4af-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users"+"/"+StuID).child("imageState").setValue("true");
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(imageuploader.this,"Uploaded Succesfully",Toast.LENGTH_LONG).show();

                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(imageuploader.this,"Upload Failed",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private String getFileExtention(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cr.getType(uri));
    }
}