package com.example.spbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class masterlogin extends AppCompatActivity {
    EditText e1,e2;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterlogin);
        e1=(EditText)findViewById(R.id.username);
        e2=(EditText)findViewById(R.id.pwd);
        b1=(Button)findViewById(R.id.sgn);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();

                if(s1.equals("kishore")&&s2.equals("kishore")){
                    Intent intent = new Intent(masterlogin.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(masterlogin.this,"Login Failed",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
