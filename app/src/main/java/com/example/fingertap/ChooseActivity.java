package com.example.fingertap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChooseActivity extends AppCompatActivity implements View.OnClickListener{
    RadioGroup mRadioGroup;
    RadioButton r1,r2,r3;
    Button next;
    EditText mEditText;
    String startNext=null,choose = null;
    int dur = 0;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open("tapping.json")));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String myjsonstring = sb.toString();
        try {

            JSONObject jsonObjMain = new JSONObject(myjsonstring);
            String hands = (String) jsonObjMain.get("hands");
            int duration = (Integer) jsonObjMain.getInt("duration");
            choose = hands;
            dur = duration;


        }
        catch (JSONException je){

        }
        next = (Button) findViewById(R.id.next);
//        r1 = (RadioButton) findViewById(R.id.right);
//        r2 = (RadioButton) findViewById(R.id.left);
//        r3 = (RadioButton) findViewById(R.id.both);
        //mEditText = (EditText) findViewById(R.id.counter);
        //next.setOnClickListener(this);
//        r1.setOnClickListener(this);
//        r2.setOnClickListener(this);
//        r3.setOnClickListener(this);

        switch (choose){
            case "right":
                startNext = "right";
                intent= new Intent(ChooseActivity.this,RightActivity.class);
                break;
            case "left":
                startNext = "left";
                intent= new Intent(ChooseActivity.this,MainActivity.class);
                break;
            case "both":
                startNext = "both";
                intent= new Intent(ChooseActivity.this,MainActivity.class);
                break;

            default:
                break;

        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startNext!=null) {
                    //intent.putExtra("counter",String.valueOf(mEditText));
                    intent.putExtra("option", startNext);
                    intent.putExtra("duration",dur);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        next.setClickable(true);
        r1.setClickable(true);
        r2.setClickable(true);
        r3.setClickable(true);
        int id = v.getId();

    }

}
