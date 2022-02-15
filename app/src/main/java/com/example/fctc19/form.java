package com.example.fctc19;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class form extends AppCompatActivity {
    Boolean x=true;

    EditText temp;
    TextView vax;
     //firebase
    String StuID;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    String course,locationval;
    Spinner locationI;
     //
    Button submit;
    //checkbox values
    ArrayList<String> mIssue;
    CheckBox rule1,rule2,rule3,rule4,rule5,rule6,fever,drycough,runnynose,sore,bodyaches,headache,shortness,tiredness,lossofsmell,none;
    public boolean ri,rii,riii,riv,rv,rvi,bfever,bdrycough,brunnynose,bsore,bbodyaches,bheadache,bshortness,btiredness,blossofsmell,bnone;

    TextView studentname;
    TextView studentID;
    private  String []courselist = new String[10] ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mIssue = new ArrayList<>();
        locationI = findViewById(R.id.location);

       //Hooks for Form content - for sysmptoms
        rule1 = findViewById(R.id.rule1);
        rule2 = findViewById(R.id.rule2);
        rule3 = findViewById(R.id.rule3);
        rule4 = findViewById(R.id.rule4);
        rule5 = findViewById(R.id.rule5);
        rule6 = findViewById(R.id.rule6);
        fever = findViewById(R.id.fever);
        drycough = findViewById(R.id.drycough);
        runnynose = findViewById(R.id.runnynose);
        sore= findViewById(R.id.sore);
        bodyaches = findViewById(R.id.bodyaches);
        headache = findViewById(R.id.headache);
        shortness = findViewById(R.id.shortness);
        lossofsmell = findViewById(R.id.lossofsmell);
        tiredness= findViewById(R.id.tiredness);
        none= findViewById(R.id.none);
        mIssue.add("## Diagnosed As Covid 19 - Last 14 Days ## ");
        mIssue.add("## Close contact with Covid-19 Patient ##");
        mIssue.add("## Returned from Abroad ##");
        mIssue.add("## Close Contact with Someone from Abroad ##");
        mIssue.add("## HouseHold members quarantined ##");
        mIssue.add("## Live in a risky Community ##");
        vax = findViewById(R.id.vaxstat);
        temp=findViewById(R.id.Tempreture);


   submit=findViewById(R.id.submit_form);


        //Calendar calendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        String currentDate = df.format(new Date());

        TextView textView = findViewById(R.id.date);
        textView.setText(currentDate);

        studentname = findViewById(R.id.s_name);
        studentID = findViewById(R.id.sid);
        Intent intent = getIntent();
        StuID = intent.getStringExtra("s_id");
        studentname.setText(intent.getStringExtra("s_name"));
        studentID.setText(intent.getStringExtra("s_id"));
        vax.setText(intent.getStringExtra("vac_state"));

        Spinner mySpinner = findViewById(R.id.spinner);

        DatabaseReference reference = FirebaseDatabase.getInstance("https://fct-5b4af-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("courses");
        Query checkUser = reference;

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int i = 0;
                    String value = snapshot.child(String.valueOf(i)).getValue(String.class);

                        while(!(snapshot.child(String.valueOf(i)).getValue(String.class)==null)) {

                            courselist[i] = snapshot.child(String.valueOf(i)).getValue(String.class);

                            i++;
                            System.out.println(courselist[1]);
                        }

                    System.out.println(courselist[3]);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        courselist = Arrays.stream(courselist)
                                .filter(s -> (s != null && s.length() > 0))
                                .toArray(String[]::new);

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(form.this,
                            android.R.layout.simple_spinner_dropdown_item, courselist);
                    mySpinner.setAdapter(adapter);
                    mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            course = adapterView.getItemAtPosition(i).toString();


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });



                }else{

                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(form.this,R.array.alternatecourse, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mySpinner.setAdapter(adapter);
                        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                course = adapterView.getItemAtPosition(i).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       rule1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(rule1.isChecked()){
                   ri=false;
                   mIssue.remove("## Diagnosed As Covid 19 - Last 14 Days ## ");
                   }
               else{
                   ri=true;
                   mIssue.add("## Diagnosed As Covid 19 - Last 14 Days ## ");


               }
           }
       });

       rule2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(rule2.isChecked()){
                  rii=false;
                   mIssue.remove("## Close contact with Covid-19 Patient ##");
               }else{
                   rii=true;
                   mIssue.add("## Close contact with Covid-19 Patient ##");

               }
           }
       });

       rule3.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(rule3.isChecked()){
                   riii=false;
                   mIssue.remove("## Returned from Abroad ##");
               }else{
                   riii=true;

                   mIssue.add("## Returned from Abroad ##");
           }
       }

    });

        rule4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rule4.isChecked()){
                    riv=false;
                    mIssue.remove("## Close Contact with Someone from Abroad ##");
                }else{
                    riv=true;

                    mIssue.add("## Close Contact with Someone from Abroad ##");
                }
            }

        });


        rule5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rule5.isChecked()){
                    rv=false;
                    mIssue.remove("## HouseHold members quarantined ##");
                }else{
                    rv=true;

                    mIssue.add("## HouseHold members quarantined ##");
                }
            }

        });


        rule6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rule6.isChecked()){
                    rvi=false;
                    mIssue.remove("## Live in a risky Community ##");
                }else{
                    rvi=true;

                    mIssue.add("## Live in a risky Community ##");
                }
            }

        });

       fever.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(fever.isChecked()){
                   bfever = true;
                   mIssue.add("-- Fever --");

               }else{
                   bfever = false;
                   mIssue.remove("-- Fever --");
               }
           }
       });

        drycough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drycough.isChecked()){
                    bdrycough = true;
                    mIssue.add("-- Dry Cough --");

                }else{
                    bfever = false;
                    mIssue.remove("-- Dry Cough --");
                }
            }
        });


        runnynose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(runnynose.isChecked()){
                    brunnynose = true;
                    mIssue.add("-- Runny Nose --");

                }else{
                    brunnynose = false;
                    mIssue.remove("-- Runny Nose --");
                }
            }
        });


        sore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sore.isChecked()){
                    bsore = true;
                    mIssue.add("-- Sore Throat --");

                }else{
                    bsore = false;
                    mIssue.remove("-- Sore Throat --");
                }
            }
        });


        bodyaches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bodyaches.isChecked()){
                    bbodyaches = true;
                    mIssue.add("-- Body Aches --");

                }else{
                    bbodyaches = false;
                    mIssue.remove("-- Body Aches --");
                }
            }
        });


        headache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(headache.isChecked()){
                    bheadache = true;
                    mIssue.add("-- Head Ache --");

                }else{
                    bheadache = false;
                    mIssue.remove("-- Head Ache --");
                }
            }
        });

        shortness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shortness.isChecked()){
                    bshortness = true;
                    mIssue.add("-- Shortness of Breath --");

                }else{
                    bshortness = false;
                    mIssue.remove("-- Shortness of Breath --");
                }
            }
        });

        tiredness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tiredness.isChecked()){
                    btiredness = true;
                    mIssue.add("-- Tiredness --");

                }else{
                    btiredness = false;
                    mIssue.remove("-- Tiredness --");
                }
            }
        });

        lossofsmell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lossofsmell.isChecked()){
                    blossofsmell = true;
                    mIssue.add("-- Loss of Smell --");

                }else{
                    blossofsmell = false;
                    mIssue.remove("-- Loss of Smell --");
                }
            }
        });

        none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(none.isChecked()){
                    bnone = true;
                    mIssue.add("NO Symptoms");

                }else{
                    bnone = false;
                    mIssue.remove("NO Symptoms");
                }
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance("https://fct-5b4af-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("records/"+currentDate);

                reference.child(StuID+"/Tempreture").setValue(temp.getText().toString());
                if((ri||rii||riii||riv||rv)||(bfever||bdrycough||brunnynose||bsore||bbodyaches||bheadache||bshortness||btiredness||blossofsmell||bnone)){
                    int i =1;
                    for(String issues: mIssue){

                        reference.child(StuID+"/Issues").child("Issue"+String.valueOf(i)).setValue(issues).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(form.this,"Issue while parsing data",Toast.LENGTH_LONG).show();
                                x = false;
                            }
                        });

                        i++;
                    }

                }

                reference.child(StuID+"/Course").setValue(course).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(form.this,"Issue while parsing data",Toast.LENGTH_LONG).show();
                        x=false;
                    }
                });
                reference.child(StuID+"/Location").setValue(locationval).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(form.this,"Issue while parsing data",Toast.LENGTH_LONG).show();
                        x=false;
                    }
                });
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(x){
                            Toast.makeText(form.this,"Data Added Succesfully!",Toast.LENGTH_LONG).show();

                        }else
                        {Toast.makeText(form.this,"There is a issue, check your connection or contact admin.",Toast.LENGTH_LONG).show();
                            }
                    }
                },500);


            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.locate, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationI.setAdapter(adapter);
        locationI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                locationval = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    }