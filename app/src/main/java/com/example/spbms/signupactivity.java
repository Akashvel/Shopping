package com.example.spbms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signupactivity extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5;
    Button b1;
    FirebaseAuth mfirebaseauth;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);
        e1=(EditText)findViewById(R.id.name);
        e2=(EditText)findViewById(R.id.email);
        e3=(EditText)findViewById(R.id.phonenumber);
        e4=(EditText)findViewById(R.id.password);
        e5=(EditText)findViewById(R.id.conpassword);
        b1=(Button)findViewById(R.id.signup);
        mfirebaseauth=FirebaseAuth.getInstance();
        String ph = e2.getText().toString();
        myref = FirebaseDatabase.getInstance().getReference("Customers").child(ph);
        //myref= FirebaseDatabase.getInstance().getReference();
        b1.setOnClickListener(v->aduser());
    }
    public void aduser()
    {
        String s1 = e2.getText().toString().trim();
        String s2 = e4.getText().toString().trim();
        mfirebaseauth.createUserWithEmailAndPassword(s1,s2)
                .addOnCompleteListener(signupactivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(signupactivity.this,"Added Successfull",Toast.LENGTH_LONG).show();
                            String s3 = e1.getText().toString();
                            String s4 = e3.getText().toString();
                            String s5 = myref.push().getKey();
                            User user = new User(s5,s3,s4,s1);
                            myref.child(s5).setValue(user);
                            Intent intent = new Intent(getApplicationContext(),loginactivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(signupactivity.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
