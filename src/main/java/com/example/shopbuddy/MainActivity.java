package com.example.shopbuddy;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String listname;
    public static boolean newList=false;
    public ArrayList<String> entries=new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("NAMES", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("NAMES", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        entries = gson.fromJson(json, type);

        SharedPreferences sharedPreferences2 = getSharedPreferences("REMIND", MODE_PRIVATE);
        Remind.remind=sharedPreferences2.getBoolean("STATE",false);
        Remind.reminder=sharedPreferences2.getString("Remind",null);

        if(Remind.remind==true){

            NotificationManager notifyMgr =
                    (NotificationManager)
                            getSystemService(NOTIFICATION_SERVICE);


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                String CHANNEL_ID = "reminder";
                CharSequence name = "remind";
                String Description = "remind me";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                mChannel.setDescription(Description);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mChannel.setShowBadge(false);
                notifyMgr.createNotificationChannel(mChannel);
            }

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this,"reminder")
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("Notification")
                            .setContentText(Remind.reminder);

            int notificationId = 101;

            notifyMgr.notify(notificationId, builder.build());

        }


        final TextView warning=(TextView)findViewById(R.id.warning);
        Button openBtn=(Button)findViewById(R.id.openBtn);  //Open Button
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(entries==null || entries.size()==0){
                    warning.setText("No Lists to Show");
                    warning.setVisibility(View.VISIBLE);

                }
                else {
                    warning.setVisibility(View.INVISIBLE);
                    newList = true;
                    Intent startIntent = new Intent(getApplicationContext(), Open.class);
                    startActivity(startIntent);
                }
            }
        });
        Button createBtn=(Button)findViewById(R.id.createBtn);  //Create Button
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText listName2=(EditText)findViewById(R.id.listName2);
                String temp=listName2.getText().toString();


                if(Open.entryNames != null && Open.entryNames.contains(temp)) {

                    warning.setText("**LIST ALREADY EXISTS**");
                    warning.setVisibility(View.VISIBLE);
                }
                else if(temp.isEmpty()){
                    warning.setText("FILE NAME IS EMPTY");
                    warning.setVisibility(View.VISIBLE);

                }

                else {
                    warning.setVisibility(View.INVISIBLE);
                    listname=temp;
                    listName2.setText("");
                    Intent startIntent = new Intent(getApplicationContext(), Create.class);
                    startActivity(startIntent);
                }


              //  EditText listName2=(EditText)findViewById(R.id.listName2);
                //listname=listName2.getText().toString();
                //Intent startIntent=new Intent(getApplicationContext(),Create.class);
                //startActivity(startIntent);
            }
        });
        Button expenseBtn=(Button)findViewById(R.id.expenseBtn);    //Expense
        expenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent=new Intent(getApplicationContext(),Expense.class);
                startActivity(startIntent);
            }
        });

        Button remindBtn=(Button)findViewById(R.id.remindBtn);
        remindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent=new Intent(getApplicationContext(),Remind.class);
                startActivity(startIntent);

            }
        });
    }
}
