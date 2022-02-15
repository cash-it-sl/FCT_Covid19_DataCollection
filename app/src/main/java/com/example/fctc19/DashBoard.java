package com.example.fctc19;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DashBoard extends AppCompatActivity {
    String stu_name ;
    String stu_ID ;
    String stu_email;
    String stu_phoneNo ;
    String verification ;
    String vaccination;
    String url ;
    String state;
    String vaccinationState;
    Boolean A = true;


    LinearLayout vac;
    TextView username;
    TextView userID;
    TextView accstate;
    TextView vaccinationimagestatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        username =findViewById(R.id.userName);
        userID = findViewById(R.id.userID);
        accstate = findViewById(R.id.verificationMessage);
        vaccinationimagestatus = findViewById(R.id.vaccinationcardmessage);
        vac = findViewById(R.id.vaclay);
        //Show All Data
        if(savedInstanceState==null) {
            showAlluserData();
        }


    }

    @Override
    public void onBackPressed() {
       new AlertDialog.Builder(this).setMessage("Do you want to Exit?")
               .setCancelable(false)
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       finish();
                   }
               })
               .setNegativeButton("No",null)
               .show();
    }

    private void showAlluserData() {
        Intent intent = getIntent();
       stu_name =   intent.getStringExtra("name") ;
        stu_ID =   intent.getStringExtra("studentNo") ;
         stu_email = intent.getStringExtra("email"); ;
       stu_phoneNo =   intent.getStringExtra("phoneNo") ;
        verification = intent.getStringExtra("verification");
       vaccination = intent.getStringExtra("vaccination");
       url = intent.getStringExtra("url");
       state = intent.getStringExtra("imgstate");
       vaccinationState=intent.getStringExtra("vacstate");
        username.setText(stu_name);
        userID.setText(stu_ID);
        if(verification.equals("Account is verified")){
            vac.setBackgroundColor(Color.GREEN);
            accstate.setText(verification);
        }else{
            accstate.setText(verification);
        }
        if(state.equals("false")){
                vaccinationimagestatus.setText("Please Upload Vaccination Card if available");
    }else{
        vaccinationimagestatus.setText("  ");

    }

    }

    public void newform(View view) {
        Intent intent = new Intent(DashBoard.this,form.class);
        intent.putExtra("s_name",stu_name);
        intent.putExtra("s_id",stu_ID);
        intent.putExtra("vac_state",vaccinationState);
        startActivity(intent);

    }

    public void addcard(View view) {
        Intent intent = new Intent(DashBoard.this,imageuploader.class);
        intent.putExtra("s_name",stu_name);
        intent.putExtra("s_id",stu_ID);
        startActivity(intent);

    }


    public void cbotlaunch(View view) {
        Toast.makeText(DashBoard.this,"The feature will be available in next version(B.2.0)",Toast.LENGTH_LONG).show();
    }

    public void updateinfo(View view) {
        Toast.makeText(DashBoard.this,"The feature will be available in next version(B.2.0)",Toast.LENGTH_LONG).show();

    }


    public void records(View view) {
        Toast.makeText(DashBoard.this,"The feature will be available in next version(B.2.0)",Toast.LENGTH_LONG).show();

    }
}