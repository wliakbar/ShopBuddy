package com.example.shopbuddy;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Create extends AppCompatActivity {

     ArrayList<Item> itemList=new ArrayList<Item>();
        ListView createList;
        String fileName=MainActivity.listname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
      //  final ArrayList<Item> itemList2=new ArrayList<Item>();
        createList=(ListView)findViewById(R.id.createList);
        final EditText itemName=(EditText)findViewById(R.id.itemName);


        final EditText quantity=(EditText)findViewById(R.id.quantity);
        final EditText price=(EditText)findViewById(R.id.price);

        TextView listsname=(TextView)findViewById(R.id.listsname);
        listsname.setText(fileName);
                                                //CLICLINKING LIST
        createList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showInputBox(itemList.get(position),position);
            }
        });

        if(MainActivity.newList==true){
            //MainActivity.newList=false;
            loadData();
        }

        Button addBtn=(Button)findViewById(R.id.addBtn);    //ADDING ITEM
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView emptyWrn=(TextView)findViewById(R.id.emptyWrn);
                if(itemName.getText().toString().isEmpty() || quantity.getText().toString().isEmpty() || price.getText().toString().isEmpty()){

                    emptyWrn.setVisibility(View.VISIBLE);
                }
                else {
                    emptyWrn.setVisibility(View.INVISIBLE);
                    String name = itemName.getText().toString();
                    int quan = Integer.parseInt(quantity.getText().toString());
                    double Price = Double.parseDouble(price.getText().toString());
                    Item temp = new Item(name, quan, Price);
                    itemList.add(temp);
                    update();

                    itemName.setText("");
                    quantity.setText("");
                    price.setText("");
                }
            }
        });

        Button deleteBtn=(Button)findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("FILES", MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.remove(fileName).commit();

                int index=Open.entryNames.indexOf(fileName);
                Open.entryNames.remove(index);

                SharedPreferences sharedPreferences2 = getSharedPreferences("NAMES", MODE_PRIVATE);
                SharedPreferences.Editor editor2=sharedPreferences2.edit();
                editor2.clear();
                Gson gson2=new Gson();
                String json2=gson2.toJson(Open.entryNames);
                editor2.putString("NAMES",json2);
                editor2.apply();

                Intent startIntent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(startIntent);


            }
        });

        Button doneBtn=(Button)findViewById(R.id.doneBtn);      //PRESSING DONE
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.newList==true){

                    SharedPreferences sharedPreferences = getSharedPreferences("FILES", MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.remove(fileName);
                    Gson gson=new Gson();
                    String json=gson.toJson(itemList);
                    editor.putString(fileName,json);
                    editor.apply();

                    Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(startIntent);
                }
                else {
                    Open.entryNames.add(fileName);
                    SharedPreferences sharedPreferences = getSharedPreferences("FILES", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(itemList);
                    editor.putString(fileName, json);
                    editor.apply();

                    SharedPreferences sharedPreferences2 = getSharedPreferences("NAMES", MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                    Gson gson2 = new Gson();
                    String json2 = gson2.toJson(Open.entryNames);
                    editor2.putString("NAMES", json2);
                    editor2.apply();

                    Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(startIntent);
                }

                getSum();
            }
        });
    }

    public void showInputBox(Item name, final int index){

        final Dialog dialog=new Dialog(Create.this);
        dialog.setContentView(R.layout.inputbox);
        final EditText iName=(EditText)dialog.findViewById(R.id.iName);
        final EditText iQuan=(EditText)dialog.findViewById(R.id.iQuan);
        final EditText iPrice=(EditText)dialog.findViewById(R.id.iPrice);

        iName.setText(name.getName());
        iQuan.setText(""+name.getQuantity());
        iPrice.setText(""+name.getPrice());

        Button updateBtn=(Button)dialog.findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=iName.getText().toString();
                int quan=Integer.parseInt(iQuan.getText().toString());
                double Price=Double.parseDouble(iPrice.getText().toString());
                Item temp=new Item(name,quan,Price);
                itemList.set(index,temp);
                update2();
                dialog.dismiss();
            }
        });

        Button cancelBtn=(Button)dialog.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button delBtn=(Button)dialog.findViewById(R.id.delBtn);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.remove(index);
                update2();
                dialog.dismiss();
            }
        });
        dialog.show();




    /*    AlertDialog.Builder update=new AlertDialog.Builder((Create.this));
        update.setTitle("Update");
        final EditText iName=new EditText((Create.this));
        final EditText iQuan=new EditText((Create.this));
        final EditText iPrice=new EditText((Create.this));
        update.setView(iName);
        update.setView(iQuan);
        update.setView(iPrice);
        iName.setText(name.getName());
        iQuan.setText(""+name.getQuantity());
        iPrice.setText(""+name.getPrice());
        update.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name=iName.getText().toString();
                int quan=Integer.parseInt(iQuan.getText().toString());
                double Price=Double.parseDouble(iPrice.getText().toString());
                Item temp=new Item(name,quan,Price);
                itemList.set(index,temp);
                update2();
            }
        });
        update.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemList.remove(index);
                update2();
            }
        });
        AlertDialog temp2=update.create();
        temp2.show();
*/

    }

    public void update(){
        customAdapter myadpater=new customAdapter(Create.this,itemList);
        createList.setAdapter(myadpater);

    }
    public void update2(){
        customAdapter myadpater=new customAdapter(Create.this,itemList);
        createList.setAdapter(myadpater);


        SharedPreferences sharedPreferences = getSharedPreferences("FILES", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(fileName);
        Gson gson=new Gson();
        String json=gson.toJson(itemList);
        editor.putString(fileName,json);
        editor.apply();

    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("FILES", MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString(fileName,null);
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        itemList=gson.fromJson(json,type);
        update();

    }

    public void getSum(){
        double sum=0;
        int size=itemList.size();
        for(int i=0;i<size;i++){
            sum+=(itemList.get(i).getPrice()*itemList.get(i).getQuantity());
        }
       // Expense.sums.add(sum);

        SharedPreferences sharedPreferences = getSharedPreferences("SUMS", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(sum);
        editor.putString(fileName,json);
        editor.apply();


    }

}
