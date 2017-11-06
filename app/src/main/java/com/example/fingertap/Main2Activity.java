package com.example.fingertap;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {
    public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    TextView t1,t2,rt,lt,again,again2,resultText;
    String tap1,tap2;
    String av1,av2;
    LinearLayout result;
    AlertDialog.Builder builder1;
    String option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        t1 = (TextView) findViewById(R.id.left);
        t2 = (TextView) findViewById(R.id.right);
        again = (TextView) findViewById(R.id.again);
        again2 = (TextView) findViewById(R.id.again2);
        result = (LinearLayout) findViewById(R.id.resultPage);
        resultText = (TextView) findViewById(R.id.result);
        builder1 = new AlertDialog.Builder(this);
        Intent i = getIntent();
        tap2 = i.getStringExtra("rightTaps");
        tap1 = i.getStringExtra("leftTaps");
        av1 = i.getStringExtra("average");
        av2 = i.getStringExtra("averageLeft");
        option = i.getStringExtra("show");
        final JSONObject tappingObject = new JSONObject();
        try {
            if(av2!=null)
            tappingObject.put("av_left_tap_interval",av2+" ms");
            if(av1!=null)
            tappingObject.put("av_right_tap_interval",av1+" ms");
            tappingObject.put("total_left_taps",tap1);
            tappingObject.put("total_right_taps",tap2);
            tappingObject.put("entry_date", String.valueOf(df.format(Calendar.getInstance().getTime())));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this,ChooseActivity.class);
                startActivity(intent);
            }
        });
        again2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this,ChooseActivity.class);
                startActivity(intent);
            }
        });
        switch (option){
            case "onlyRight":
//                lt.setVisibility(View.GONE);
//                t2.setText(tap2);
                builder1.setTitle("You have successfully completed this active task");
                builder1.setMessage("Right tap count: "+tap2+"\nAverage Tapping interval: "+av1+"ms");
                builder1.setPositiveButton("Show Results",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.setVisibility(View.VISIBLE);
                        resultText.setText(tappingObject.toString());
                        Log.i("tappin json",tappingObject.toString());

                    }
                });
                builder1.setNegativeButton("Ok",null);
                builder1.setCancelable(false);
                builder1.show();
                break;
            case "onlyLeft":
//                t1.setText(tap1);
//                rt.setVisibility(View.GONE);
                builder1.setTitle("You have successfully completed this active task");
                builder1.setMessage("Left tap count: "+tap1+"\nAverage Tapping interval: "+av2+"ms");
                builder1.setPositiveButton("Show Results",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.setVisibility(View.VISIBLE);
                        resultText.setText(tappingObject.toString());
                    }
                });
                builder1.setNegativeButton("Ok",null);
                builder1.setCancelable(false);
                builder1.show();
                break;
            case "both":
//                t1.setText(tap1);
//                t2.setText(tap2);
                builder1.setTitle("You have successfully completed this active task");
                builder1.setMessage("Right tap count: "+tap2+" Av tap interval: "+av1+"ms"+"\nLeft tap count: "+tap1+" Av tap interval:: "+av2+"ms");
                //builder1.setMessage(tappingObject.toString());
                builder1.setPositiveButton("Show Results", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       result.setVisibility(View.VISIBLE);
                        resultText.setText(tappingObject.toString());
                    }
                });
                builder1.setNegativeButton("Ok",null);
                builder1.setCancelable(false);
                builder1.show();
                break;
            default:
                break;
        }


    }
}
