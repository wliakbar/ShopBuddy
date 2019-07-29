package com.example.shopbuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Remind extends AppCompatActivity {
    public static String reminder;
    public static boolean remind=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);
        final EditText remindMsg=(EditText)findViewById(R.id.remindMsg);

       remindMsg.setText(Remind.reminder);



        Button remindBtn=(Button)findViewById(R.id.remindBtn);
        remindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remind=true;
                reminder=remindMsg.getText().toString();


                SharedPreferences sharedPreferences2 = getSharedPreferences("REMIND", MODE_PRIVATE);
                SharedPreferences.Editor editor2=sharedPreferences2.edit();
                editor2.clear();


                editor2.putString("Remind",reminder);
                editor2.putBoolean("STATE",remind);
                editor2.apply();


                Intent startIntent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(startIntent);

            }
        });

        Button stopBtn=(Button)findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remind=false;
                reminder="";
                SharedPreferences sharedPreferences2 = getSharedPreferences("REMIND", MODE_PRIVATE);
                SharedPreferences.Editor editor2=sharedPreferences2.edit();
                editor2.clear();

                editor2.putString("Remind",reminder);
                editor2.putBoolean("STATE",remind);
                editor2.apply();

            }
        });



    }
}
