package com.example.shopbuddy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

public class Expense extends AppCompatActivity {

    //public static ArrayList<Double> sums=new ArrayList<Double>();
    private double finalSum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        final EditText results=(EditText)findViewById(R.id.results);
        SharedPreferences sharedPreferences = getSharedPreferences("SUMS", MODE_PRIVATE);
        Map<String, ?> allEntries=sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

            //results.append(entry.getKey()+"                               $"+entry.getValue().toString()+"\n");

            String temp=String.format("%-20s%s%s %n",entry.getKey(),"$",entry.getValue().toString());
            results.append(temp);
            finalSum+=Double.parseDouble(entry.getValue().toString());
        }

        TextView finSum=(TextView) findViewById(R.id.finSum);
        finSum.setText("TOTAL= $"+Double.toString(finalSum));

        Button clearHis=(Button)findViewById(R.id.clearHis);
        clearHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHistory();
                results.setText("");
            }
        });





    }

    public void clearHistory(){
        SharedPreferences sharedPreferences = getSharedPreferences("SUMS", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear().commit();
    }




}
