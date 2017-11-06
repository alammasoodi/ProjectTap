package com.example.fingertap;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button b1,b2,next;
    TextView t1,timer,skip;
    boolean counterStarted = false;
    LinearLayout infoPage;
    String getOption;
    boolean isInfoShown = true;
    int c1=0;
    int progress = 100;
    long initialtime = 0;
    long currentTime = 0;
    long total = 0;
    long diff = 0;
    long temp = 0;
    long average = 0;
    int counter= 0;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.push_button1);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        progressBar.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.buttonColor), android.graphics.PorterDuff.Mode.SRC_IN);
        b2 = (Button) findViewById(R.id.push_button2);
        infoPage = (LinearLayout) findViewById(R.id.informationPage);
        infoPage.setVisibility(View.VISIBLE);
        skip = (TextView) findViewById(R.id.skip);
        next = (Button) findViewById(R.id.next);
        t1 = (TextView) findViewById(R.id.first);
        Intent i = getIntent();
        getOption = i.getStringExtra("option");
        counter = i.getIntExtra("duration",0);

        if(getOption.equals("both")){
            skip.setVisibility(View.VISIBLE);
        }
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
                Intent intent1 = new Intent(MainActivity.this,RightActivity.class);
                intent1.putExtra("option", "both");
                intent1.putExtra("leftTaps",t1.getText());
                startActivity(intent1);
            }
        });

    }

    public void startCounter(){
        new CountDownTimer(counter, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.i("counter","called");
                ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), 700);
                progressAnimator.setDuration(counter);
                progressAnimator.setInterpolator(new LinearInterpolator());
                progressAnimator.start();

            }

            public void onFinish() {
                String count = String.valueOf(t1.getText());
                average = total/Long.valueOf(count);
                progressBar.setProgress(progress);
                switch (getOption){
                    case "left":
                        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                        intent.putExtra("leftTaps",t1.getText());
                        intent.putExtra("averageLeft",String.valueOf(average));
                        intent.putExtra("show","onlyLeft");
                        startActivity(intent);
                        break;
                    case "both":
                        Intent intent1 = new Intent(MainActivity.this,RightActivity.class);
                        intent1.putExtra("duration",counter);
                        intent1.putExtra("option", "both");
                        intent1.putExtra("averageLeft",String.valueOf(average));
                        intent1.putExtra("leftTaps",t1.getText());
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
