package com.example.shopbuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Open extends AppCompatActivity {

    public static ArrayList<String> entryNames=new ArrayList<String>();
    ListView lists1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        lists1=(ListView)findViewById(R.id.lists1);

            SharedPreferences sharedPreferences = getSharedPreferences("NAMES", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("NAMES", null);
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            entryNames = gson.fromJson(json, type);
            
          //   if(!(entryNames==null)) {
               ArrayAdapter<String> myadpater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, entryNames);
                lists1.setAdapter(myadpater);
                lists1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MainActivity.listname = (String) lists1.getItemAtPosition(position);
                        Intent startIntent = new Intent(getApplicationContext(), Create.class);
                        startActivity(startIntent);

                    }
                });
         //   }


    }
}
