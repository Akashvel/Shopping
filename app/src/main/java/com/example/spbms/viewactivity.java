package com.example.spbms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class viewactivity extends AppCompatActivity {

    ListView mylistview;
    List<String> productname;
    ArrayAdapter<String> adapter;
    private List<Productadd> uploadList = new ArrayList<>();
    DatabaseReference myref;
    int i=0,l,j=0,count=0;
    int kk;
    Button b2;
    TextView tv;
    String[] names=new String[101];
    String[] cost=new String[101];
    String[] iyoo = new String[101];
    int[] particularitemcount = new int[15];
    int iyoolen=0;
    String qty,oo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewactivity);

        for(i=0;i<particularitemcount.length;i++){
            particularitemcount[i] = 1;
        }

        Intent intent = getIntent();
        String str = intent.getStringExtra("c1");
        tv=(TextView)findViewById(R.id.tv1);
        tv.setText(tv.getText()+str);
        mylistview = (ListView)findViewById(R.id.mylistview);

        myref = FirebaseDatabase.getInstance().getReference("PRODUCTS");
        b2=(Button)findViewById(R.id.proceed);





        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postsnapshot:dataSnapshot.getChildren())
                {
                    Productadd pd = postsnapshot.getValue(Productadd.class);
                    names[l]=pd.getName();
                    cost[l]=pd.getPrice();
                    l++;
                    uploadList.add(pd);
                }
                String[] uploads = new String[uploadList.size()];
                ArrayList<String> list = new ArrayList<String>();
                for (i = 0; i < uploads.length; i++) {
                    String s1 = uploadList.get(i).getName() + "  " +uploadList.get(i).getQuantity()+"  "+"RS:"+uploadList.get(i).getPrice();
                    //uploads[i] = uploadList.get(i).getName();
                    list.add(uploadList.get(i).getName());
                    uploads[i] = s1;
                }
                //Arrays.sort(uploads);
                MyCustomAdapter adapter = new MyCustomAdapter(list, viewactivity.this,iyoo,oo,particularitemcount,uploads);
                //ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_multiple_choice,uploads);
                mylistview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                mylistview.setAdapter(adapter);
                //LocalBroadcastManager.getInstance(viewactivity.this).registerReceiver(mMessageReceiver,
                  //      new IntentFilter("custom-message"));
                //mylistview.setAdapter(adapter1);
                //adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listout();
            }
        });
    }
    public Context getContext() {
        return this;
    }
    public void listout(){
        SparseBooleanArray checked = mylistview.getCheckedItemPositions();
        String[] sample = new String[101];
        count=0;
        for(i=0;i<iyoo.length;i++){
            try {
                int len = iyoo[i].length();
                if(len>0){
                    count++;
                }
            }
            catch (Exception e){

            }
        }
        String[] clicked = new String[count];
        int[] clickedcount = new int[count];
        String[] clickedcost = new String[count];
        for(i=0;i<count;i++){
            clicked[i] = iyoo[i];
            for(j=0;j<names.length;j++){
                if(clicked[i].equals(names[j])){
                    clickedcount[i] = particularitemcount[j];
                    clickedcost[i] = cost[j];
                }
            }
        }

        ArrayList<String> selectedItems = new ArrayList<String>();
        ArrayList<String> selectedItemscost = new ArrayList<String>();
        for (i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);
            if(mylistview.isItemChecked(position)==true) {
                selectedItems.add(names[position]);
                selectedItemscost.add(cost[position]);
            }
        }
        String[] selectedItemsArr = new String[selectedItems.size()];
        String[] selectedItemscostArr = new String[selectedItems.size()];
        for (i = 0; i < selectedItems.size(); i++) {
            selectedItemsArr[i] = selectedItems.get(i);
            selectedItemscostArr[i]=selectedItemscost.get(i);
        }
        Intent intent = new Intent(getApplicationContext(),listactivity.class);
        intent.putExtra("selectedItems", clicked);
        intent.putExtra("selectedItemscount", clickedcount);
        intent.putExtra("selectedItemcost",clickedcost);
        //Toast.makeText(viewactivity.this,String.valueOf(particularitemcount[0])+String.valueOf(particularitemcount[1]),Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            //String ItemName = intent.getStringExtra("item");
            qty = intent.getStringExtra("quantity");
            Toast.makeText(viewactivity.this,qty,Toast.LENGTH_SHORT).show();
        }
    };
}
