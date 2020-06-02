package com.example.spbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class listactivity extends AppCompatActivity implements View.OnClickListener{
    ListView mylist;
    Button b1,b2;
    TextView tv,tv1;
    private IntentIntegrator qrScan;
    String[] itemsscanned = new String[101];
    ArrayList<String> iscanned = new ArrayList<String>();
    String[] stringArray;
    String[] costArray;

    int[] itemcountt;
    int sum=0;
    int scannedcount=0;
    int l=0,j,flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listactivity);
        mylist = (ListView)findViewById(R.id.mylist);
        b1=(Button)findViewById(R.id.scanbutton);
        b2=(Button)findViewById(R.id.amountbutton);
        qrScan = new IntentIntegrator(this);
        tv1 = (TextView)findViewById(R.id.tv1);
        mylist.setEnabled(false);
        //mylist.setMovementMethod(new ScrollingMovementMethod());
        Intent intent = getIntent();
        stringArray = intent.getStringArrayExtra("selectedItems");
        itemcountt = intent.getIntArrayExtra("selectedItemscount");
        costArray = intent.getStringArrayExtra("selectedItemcost");
        //Arrays.sort(stringArray);
        String[] displayarray = new String[stringArray.length];
        for(int i=0;i<stringArray.length;i++){
            displayarray[i] = stringArray[i] +"     "+ String.valueOf(itemcountt[i]) +"      RS: "+ costArray[i];
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_multiple_choice, displayarray);

        mylist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mylist.setAdapter(adapter);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        tv1.setText("AMOUNT :  0RS");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //super.onActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null)
            {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            }
            else
            {
                try
                {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    String s1 = obj.getString("name");
                    for(j=0;j<stringArray.length;j++)
                    {
                        if(s1.equals(stringArray[j]))
                        {
                            mylist.setItemChecked(j,true);
                            Toast.makeText(this,obj.getString("price"), Toast.LENGTH_LONG).show();
                            String ct = obj.getString("price");
                            int ct1 = Integer.parseInt(ct);
                            sum+=(ct1*itemcountt[j]);
                            scannedcount++;
                            tv1.setText("AMOUNT : "+String.valueOf(sum)+"RS");
                        }
                    }
                    itemsscanned[l] = obj.getString("name");
                    iscanned.add(s1);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onClick(View view)
    {
        //Button b = (Button)view;
        switch (view.getId())
        {
            case R.id.scanbutton:{
                qrScan.initiateScan();
                break;
            }
            case R.id.amountbutton:{
                String ss = String.valueOf(sum);
                if(scannedcount==stringArray.length) {
                    Intent intent = new Intent(getApplicationContext(), billactivity.class);
                    intent.putExtra("cc", ss);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this,"Missed An Item",Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Can't Go Back",Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(listactivity.class);
        //startActivity(intent);
        moveTaskToBack(false);
    }
}
