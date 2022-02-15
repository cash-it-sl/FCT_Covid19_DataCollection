package com.example.fctc19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class login extends AppCompatActivity {
    Button login;
    ImageView image;
    TextView logoText;


    TextInputLayout loginbatcht,loginyeart,loginnumbert,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Hooks
        login = findViewById(R.id.login);
        image = findViewById(R.id.logoimage);
        logoText = findViewById(R.id.logoname);
        password= findViewById(R.id.password);

        //New Hooks

        loginbatcht = findViewById(R.id.batchtypelogin);
        loginyeart = findViewById(R.id.YearTypelogin);
        loginnumbert = findViewById(R.id.Snotypelogin);


    }

    private Boolean validateUserID(){
        String val1 = loginbatcht.getEditText().getText().toString();
        String val2 = loginyeart.getEditText().getText().toString();
        String val3 = loginnumbert.getEditText().getText().toString();

        if(val1.isEmpty()||val2.isEmpty() ||val3.isEmpty()){
            loginnumbert.setError("Field Cannot be Empty");
            return false;
        }else{
            loginnumbert.setError(null);
            loginnumbert.setErrorEnabled(false);
            return true;
        }


    }

    private Boolean validatePass(){
        String val = password.getEditText().getText().toString();
        if(val.isEmpty()){
            password.setError("Field Cannot be Empty");
            return false;
        }else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }



    public void signup(View view) {
        Intent intent = new Intent(login.this,signup.class);

        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View,String>(image,"logo_image");
        pairs[1] = new Pair<View,String>(logoText,"logo_text");
        pairs[2] = new Pair<View,String>(login,"but_trans");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(login.this, pairs);
            startActivity(intent,options.toBundle());
            finish();

        }
        else{startActivity(intent);
        finish();}

    }

    public void loginUser(View view) {
        if(!validateUserID() | !validatePass()){
            return;
        }
        else{
            isUser();
        }
    }

    private void isUser() {

        String userEnteredUsername = loginbatcht.getEditText().getText().toString().toUpperCase(Locale.ROOT)+"-"+loginyeart.getEditText().getText().toString()+"-"+loginnumbert.getEditText().getText().toString();
        Toast.makeText(login.this,userEnteredUsername,Toast.LENGTH_LONG).show();
        String userEnteredPasswrod = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance("https://fct-5b4af-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users");

        Query checkUser = reference.orderByChild("studentNo").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                  if(snapshot.exists()){

                      loginbatcht.setError(null);
                      loginyeart.setError(null);
                      loginnumbert.setError(null);
                      loginbatcht.setErrorEnabled(false);
                      loginyeart.setErrorEnabled(false);
                      loginnumbert.setErrorEnabled(false);

                      String passwordFromDB = snapshot.child(userEnteredUsername).child("password1").getValue(String.class);

                      if(passwordFromDB.equals(userEnteredPasswrod)){
                          loginbatcht.setError(null);
                          loginyeart.setError(null);
                          loginnumbert.setError(null);
                          loginbatcht.setErrorEnabled(false);
                          loginyeart.setErrorEnabled(false);
                          loginnumbert.setErrorEnabled(false);

                          String emailFromDB = snapshot.child(userEnteredUsername).child("email").getValue(String.class);
                          String nameFromDB = snapshot.child(userEnteredUsername).child("name").getValue(String.class);
                          String phoneFromDB = snapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);
                          String studentNoFromDB = snapshot.child(userEnteredUsername).child("studentNo").getValue(String.class);
                          String studentVacStatFromDB = snapshot.child(userEnteredUsername).child("vaccinationStatus").getValue(String.class);
                          String studentVerificationFromDB = snapshot.child(userEnteredUsername).child("verificationStatus").getValue(String.class);
                        //  String studentURLfromDB = snapshot.child(userEnteredUsername).child("imageurl").getValue(String.class);
                          String studentimageStateFromDB = snapshot.child(userEnteredUsername).child("imageState").getValue(String.class);
                          String vaccinationStateFromDB = snapshot.child(userEnteredUsername).child("vaccinationState").getValue(String.class);
                            Intent intent = new Intent(getApplicationContext(),DashBoard.class);
                            intent.putExtra("name",nameFromDB);
                            intent.putExtra("email",emailFromDB);
                            intent.putExtra("phoneNo",phoneFromDB);
                            intent.putExtra("studentNo",studentNoFromDB);
                            intent.putExtra("vaccination",studentVacStatFromDB);
                            intent.putExtra("verification",studentVerificationFromDB);
                            intent.putExtra("imgstate",studentimageStateFromDB);
                            intent.putExtra("vacstate",vaccinationStateFromDB);


                            startActivity(intent);
                            finish();

                      }
                      else{password.setError("Wrong Password");
                          password.requestFocus();
                      }

                  }
                  else {Toast.makeText(login.this,"No Such User",Toast.LENGTH_LONG).show();
                      loginnumbert.setError("Wrong");
                      loginbatcht.requestFocus();
                      loginyeart.requestFocus();
                      loginnumbert.requestFocus();}


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}