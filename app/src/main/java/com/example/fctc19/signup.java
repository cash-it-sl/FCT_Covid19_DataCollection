package com.example.fctc19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class signup extends AppCompatActivity {
    TextInputLayout regName,regStudentNo,regEmail,regPhoneNo,regPassword,retypepass,batchT,YearT,StNumT;
    Button regBtn;
    Spinner spinner;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    String vaccinationState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
   //New Hooks
        batchT = findViewById(R.id.batchtype);
        YearT = findViewById(R.id.YearType);
        StNumT = findViewById(R.id.Snotype);

        spinner = findViewById(R.id.spinnersignup);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.vaccination, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vaccinationState = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //-------------
     regName=findViewById(R.id.name);
   //  regStudentNo=findViewById(R.id.student_ID);
     regEmail=findViewById(R.id.email);
     regPhoneNo=findViewById(R.id.phoneNo);
     regPassword=findViewById(R.id.spassword);
     retypepass = findViewById(R.id.r_password);
     regBtn = findViewById(R.id.signUp);

   regBtn.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
          if((regPassword.getEditText().getText().toString()).equals(retypepass.getEditText().getText().toString())){
          rootNode = FirebaseDatabase.getInstance("https://fct-5b4af-default-rtdb.asia-southeast1.firebasedatabase.app");
           reference = rootNode.getReference("users");
           //Get All the values

         String Name = regName.getEditText().getText().toString().trim();

            String StudentID = batchT.getEditText().getText().toString().toUpperCase(Locale.ROOT)+"-"+YearT.getEditText().getText().toString()+"-"+StNumT.getEditText().getText().toString();
          String Email = regEmail.getEditText().getText().toString();
           String phoneNo = regPhoneNo.getEditText().getText().toString();
           String Password1=regPassword.getEditText().getText().toString();
           String Password2=retypepass.getEditText().getText().toString();
           String imagestate = "false" ;
           String vaccinationcard = "No Vaccination Card Uploaded, Please upload if available";
           String verficationstat = "Account is not verified";
           String imageUrl = "empty";
              if(!(StudentID.equals("")||Email.equals("")||phoneNo.equals("")||Password1.equals("")||Password2.equals("")||batchT.getEditText().getText().toString().equals("")||YearT.getEditText().getText().toString().equals("")||StNumT.getEditText().getText().toString().equals(""))){
          UserHelperClass helperClass = new UserHelperClass(Name,StudentID,Email,phoneNo,Password1,vaccinationcard,verficationstat,imageUrl,imagestate,vaccinationState);
          reference.child(StudentID).setValue(helperClass).addOnSuccessListener(new OnSuccessListener<Void>() {
              @Override
              public void onSuccess(Void unused) {
                  Toast.makeText(signup.this,"Sign Up Completed, Directing to Login!",Toast.LENGTH_LONG).show();
                 new Handler().postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         Intent intent = new Intent(signup.this,login.class);
                         startActivity(intent);
                         finish();
                     }
                 },2000);
              }
          });



              }else{
                  Toast.makeText(signup.this,"Make Sure All the Fields are Filled",Toast.LENGTH_LONG).show();
              }
       }else{
              Toast.makeText(signup.this,"Password Mismatch",Toast.LENGTH_LONG).show();
          }}
   });

    }

    public void goBack(View view) {
        Intent intent = new Intent(signup.this,login.class);
        startActivity(intent);
        finish();
    }
}