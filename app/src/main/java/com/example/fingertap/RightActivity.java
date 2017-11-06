package com.example.fingertap;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RightActivity extends AppCompatActivity {
    Button b1,b2,next;
    TextView t1,skip;
    boolean counterStarted = false;
    String getOption;
    int c1=0;
    String leftTaps = null;
    String averageLeftStamps = null;
    Bundle bundle;
    long initialtime = 0;
    long currentTime = 0;
    long total = 0;
    long diff = 0;
    long temp = 0;
    long average = 0;
    int progress = 100;
    ProgressBar progressBar;
    int counter = 0;
    LinearLayout infoPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right);
        b1 = (Button) findViewById(R.id.push_button1);
        b2 = (Button) findViewById(R.id.push_button2);
        skip = (TextView) findViewById(R.id.skip);
        t1 = (TextView) findViewById(R.id.first);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        progressBar.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.buttonColor), android.graphics.PorterDuff.Mode.SRC_IN);
        infoPage = (LinearLayout) findViewById(R.id.informationPage1);
        infoPage.setVisibility(View.VISIBLE);
        next = (Button) findViewById(R.id.next);
        Intent i = getIntent();
        bundle = getIntent().getExtras();

        getOption = bundle.getString("option");
        //counter = bundle.getInt("duration");
        if(getOption.equals("both"))    {
            skip.setVisibility(View.VISIBLE);

        }
        leftTaps = bundle.getString("leftTaps");
        averageLeftStamps = bundle.getString("averageLeft");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoPage.setVisibility(View.GONE);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!counterStarted){
                    startCounter();
                    skip.setVisibility(View.INVISIBLE);
                    counterStarted = true;
                    initialtime = System.currentTimeMillis();
                    temp = initialtime;
                }

                c1=c1+1;
                t1.setText(String.valueOf(c1));
                if(temp!=0){
                    currentTime = System.currentTimeMillis();
                    diff = currentTime - temp;
                    Log.i("diff",String.valueOf(diff));
                    total = total + diff;
                    temp = currentTime;
                }

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!counterStarted){
                    startCounter();
                    skip.setVisibility(View.INVISIBLE);
                    counterStarted = true;
                    initialtime = System.currentTimeMillis();
                    temp = initialtime;
                }
                c1=c1+1;
                t1.setText(String.valueOf(c1));
                if(temp!=0){
                    currentTime = System.currentTimeMillis();
                    diff = currentTime - temp;
                    Log.i("diff",String.valueOf(diff));
                    total = total + diff;
                    temp = currentTime;
                }

            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RightActivity.this,Main2Activity.class);
                intent.putExtra("leftTaps",leftTaps);
                intent.putExtra("show","both");
                intent.putExtra("rightTaps",t1.getText());
                startActivity(intent);
            }
        });


    }
    int start = 1;
    public void startCounter(){
        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
//                progressBar.setProgress(progress);
//                progress = progress+100;
                ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), 700);
                progressAnimator.setDuration(5000);
                progressAnimator.setInterpolator(new LinearInterpolator());
                progressAnimator.start();

            }

            public void onFinish() {
               // progressBar.setProgress(progress);4
                String count = String.valueOf(t1.getText());
                average = total/Long.valueOf(count);
                switch (getOption){
                    case "both":
                        Intent intent = new Intent(RightActivity.this,Main2Activity.class);
                        intent.putExtra("leftTaps",leftTaps);
                        intent.putExtra("show","both");
                        intent.putExtra("averageLeft",averageLeftStamps);
                        intent.putExtra("average",String.valueOf(average));
                        intent.putExtra("rightTaps",t1.getText());
                        startActivity(intent);
                        break;
                    case "right":
                        Intent intent1 = new Intent(RightActivity.this,Main2Activity.class);
                        intent1.putExtra("average",String.valueOf(average));
                        intent1.putExtra("show","onlyRight");
                        intent1.putExtra("rightTaps",t1.getText());
                        startActivity(intent1);
                        break;
                    default:
                        break;
                }

            }
        }.start();
    }
//    public String checkDigit(int number) {
//        return number <= 9 ? "0" + number : String.valueOf(number);
//    }
}
