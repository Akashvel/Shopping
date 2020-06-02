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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginactivity extends AppCompatActivity {
    EditText e1,e2;
    Button b1,b2;
    FirebaseAuth myauth;
    DatabaseReference myref;
    String cname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        e1=(EditText)findViewById(R.id.email);
        e2=(EditText)findViewById(R.id.password);
        b2=(Button)findViewById(R.id.login);
        myauth=FirebaseAuth.getInstance();
        myref = FirebaseDatabase.getInstance().getReference("Customers");
        b2.setOnClickListener(view -> check());
    }
    public void check(){
        final String s1 = e1.getText().toString().trim();
        final String s2 = e2.getText().toString().trim();
        myauth.signInWithEmailAndPassword(s1,s2).addOnCompleteListener(loginactivity.this, task -> {
            if(!task.isSuccessful()){
                Toast.makeText(loginactivity.this,"Fail",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(loginactivity.this,"Success",Toast.LENGTH_SHORT).show();
                myref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot postsnapshot:dataSnapshot.getChildren())
                        {
                            User user = postsnapshot.getValue(User.class);
                            String email = user.getEmail();
                            if(s1.equals(email)){
                                cname = user.getCustomername();
                                Intent intent  = new Intent(getApplicationContext(),viewactivity.class);
                                intent.putExtra("c1",user.getCustomername());
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
