package com.example.spbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText e1,e2,e3;
    Button b1,b2;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=(EditText)findViewById(R.id.prod);
        e2=(EditText)findViewById(R.id.quan);
        e3=(EditText)findViewById(R.id.price);
        b1=(Button)findViewById(R.id.save);
        myref = FirebaseDatabase.getInstance().getReference("PRODUCTS");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();
                String s3 = e3.getText().toString();
                String id = myref.push().getKey();
                Productadd pd = new Productadd(id,s1,s2,s3);
                myref.child(id).setValue(pd);
                Toast.makeText(MainActivity.this,"Added Successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
