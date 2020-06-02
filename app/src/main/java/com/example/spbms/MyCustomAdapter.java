package com.example.spbms;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.Arrays;

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    viewactivity vw = new viewactivity();
    String[] iitems = new String[101];
    int iyoolen=0;
    int ll=0;
    String sss;
    int purchasecount=0;
    String[] fulldisplay = new String[101];
    int[] itemcount = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};


    public MyCustomAdapter(ArrayList<String> list, Context context, String[] iyoo, String oo, int[] particularitemcount) {
        this.list = list;
        this.context = context;

    }

    public MyCustomAdapter(ArrayList<String> list, Context context, String[] iitems,String iyoolen, int[] particularitemcount,String[] uploads) {
        this.list = list;
        this.context = context;
        this.iitems = iitems;
        this.sss = iyoolen;
        this.itemcount = particularitemcount;
        this.fulldisplay=uploads;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return ll;
        //just return 0 if your list items do not have an Id variable.
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_custom_list_layout, null);
        }
        //Handle TextView and display string from your list





        TextView listItemText = (TextView)view.findViewById(R.id.itemslistooo);
        TextView qntyy = (TextView)view.findViewById(R.id.listooo);
        listItemText.setText(fulldisplay[position]);
        qntyy.setText(String.valueOf(itemcount[position]));
        //Handle buttons and add onClickListeners
        ImageView deleteBtn = (ImageView) view.findViewById(R.id.addbutt);
        ImageView addBtn = (ImageView) view.findViewById(R.id.subbutt);
        CheckBox cb = (CheckBox)view.findViewById(R.id.checkk);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb.isChecked()){
                    iitems[ll] = list.get(position);
                    vw.iyoo[ll] = list.get(position);
                    ll++;
                    iyoolen++;
                    String sss = String.valueOf(ll);
                    Toast.makeText(context,list.get(position)+" is Added"+vw.iyoo[0],Toast.LENGTH_SHORT).show();
                }
                else {
                    for(int j=0;j<ll;j++)
                    {
                        if(iitems[j].equals(list.get(position))){
                            for(int k=j;k<ll;k++){
                                iitems[k] = iitems[k+1];
                                vw.iyoo[k] = vw.iyoo[k+1];
                            }
                            Toast.makeText(context,list.get(position)+" is Removed"+(String.valueOf(vw.iyoolen-1)),Toast.LENGTH_SHORT).show();
                            ll--;
                            iyoolen--;
                            String sss = String.valueOf(ll);
                        }
                    }
                    //Toast.makeText(context,String.valueOf(l)+" "+String.valueOf(iitems.length),Toast.LENGTH_SHORT).show();
                }
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                //list.remove(position); //or some other task
                //Toast.makeText(context,String.valueOf(ll),Toast.LENGTH_SHORT).show();
                purchasecount--;
                itemcount[position]++;
                qntyy.setText(String.valueOf(itemcount[position]));
                notifyDataSetChanged();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                //Toast.makeText(context,String.valueOf(ll),Toast.LENGTH_SHORT).show();

                purchasecount++;
                itemcount[position]--;
                qntyy.setText(String.valueOf(itemcount[position]));
                notifyDataSetChanged();
            }
        });

        Intent intent = new Intent("custom-message");
        //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
        intent.putExtra("quantity",String.valueOf(ll));
        //intent.putExtra("item","Akash");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        return view;
    }

}