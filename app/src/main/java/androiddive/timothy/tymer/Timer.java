package androiddive.timothy.tymer;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.TextView;


public class Timer extends Activity implements View.OnClickListener {
    private TextView tname,ttotallen,ttotalms,iteration,interval;
    private TextView trep1,tlen1,trep2,tlen2,trep3,tlen3;
    private Button buttonStartTimer,buttonStopTimer, buttonPauseTimer,buttonResetTimer,buttonResumeTimer;

    private CountDownTimer countDownTimer,cdt1,cdt2,cdt3;
    private int hours,mins,secs;
    private long totalTime,startTime, resumeTime;

    private long len1time,len2time,len3time;
    private long len1resume,len2resume,len3resume;
    private int turn = 1;
    private int increp1=1,increp2=1,increp3=1; // n
    private int numrep1,numrep2,numrep3; // d


    private String sound;
    Typeface bold,regular;

    private DBManager dbManager;
    private Ringtone r,l;
    private Boolean t1a=true,t2a=true,t3a=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_view);

        dbManager = new DBManager(this);
        dbManager.open();

        tname = (TextView) findViewById(R.id.tname);
        ttotallen = (TextView) findViewById(R.id.ttotallen);
        ttotalms= (TextView)findViewById(R.id.ttotalms);
        iteration=(TextView)findViewById(R.id.iteration);
        interval=(TextView)findViewById(R.id.interval);
        trep1 = (TextView)findViewById(R.id.trep1);
        tlen1= (TextView)findViewById(R.id.tlen1);
        trep2 = (TextView)findViewById(R.id.trep2);
        tlen2= (TextView)findViewById(R.id.tlen2);
        trep3 = (TextView)findViewById(R.id.trep3);
        tlen3= (TextView)findViewById(R.id.tlen3);

        buttonStartTimer = (Button)findViewById(R.id.buttonStartTimer);
        buttonStopTimer = (Button)findViewById(R.id.buttonStopTimer);
        buttonPauseTimer = (Button)findViewById(R.id.buttonPauseTimer);
        buttonResetTimer=(Button)findViewById(R.id.buttonResetTimer);
        buttonResumeTimer = (Button)findViewById(R.id.buttonResumeTimer);

        bold=Typeface.createFromAsset(getAssets(),"fonts/Play-Bold.ttf");
        regular=Typeface.createFromAsset(getAssets(),"fonts/Play-Regular.ttf");
        tname.setTypeface(bold);

        buttonStartTimer.setTypeface(regular);
        buttonStopTimer.setTypeface(regular);
        buttonPauseTimer.setTypeface(regular);
        buttonResetTimer.setTypeface(regular);
        buttonResumeTimer.setTypeface(regular);

        trep1.setTypeface(regular);
        tlen1.setTypeface(regular);
        trep2.setTypeface(regular);
        tlen2.setTypeface(regular);
        trep3.setTypeface(regular);
        tlen3.setTypeface(regular);

        ttotallen.setTypeface(regular);
        ttotalms.setTypeface(regular);
        iteration.setTypeface(bold);
        interval.setTypeface(bold);


        Intent intent_i = getIntent();
        String id = intent_i.getStringExtra("id");
        String name = intent_i.getStringExtra("name");
        String totallen=intent_i.getStringExtra("totallen");
        String len1 = intent_i.getStringExtra("len1");
        String rep1=intent_i.getStringExtra("rep1");
        String len2 = intent_i.getStringExtra("len2");
        String rep2=intent_i.getStringExtra("rep2");
        String len3 = intent_i.getStringExtra("len3");
        String rep3=intent_i.getStringExtra("rep3");
        sound=intent_i.getStringExtra("sound");


        // Passing needed infromation to the timer.
        tname.setText(name);
        ttotallen.setText(totallen);
        tlen1.setText(len1);
        trep1.setText(rep1);
        tlen2.setText(len2);
        trep2.setText(rep2);
        tlen3.setText(len3);
        trep3.setText(rep3);

        increp1=increp2=increp3=1;
        numrep1 = Integer.valueOf(rep1.toString());
        numrep2 = Integer.valueOf(rep2.toString());
        numrep3 = Integer.valueOf(rep3.toString());

        //getting value for total time

        String inputTime = totallen;
        try{
            String[] split = inputTime.split(":");
            hours = Integer.valueOf(split[0]);
            mins = Integer.valueOf(split[1]);
            secs = Integer.valueOf(split[2]);
            totalTime=(hours*3600+mins*60+secs)*1000;
            } catch (NumberFormatException nfe){
            Log.e("inputTime", "could not parse: " + inputTime);
        }

        startTime=totalTime;

        buttonStartTimer.setOnClickListener(this);
        buttonStopTimer.setOnClickListener(this);
        buttonPauseTimer.setOnClickListener(this);
        buttonResetTimer.setOnClickListener(this);
        buttonResumeTimer.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_record) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonStartTimer){
            buttonStartTimer.setVisibility(View.GONE);
            buttonPauseTimer.setVisibility(View.VISIBLE);
            buttonStartTimer.setText("Start");
            startTimer();
        }
        else if(v.getId() == R.id.buttonPauseTimer)
        {
            countDownTimer.cancel();
            buttonPauseTimer.setVisibility(View.GONE);
            buttonResumeTimer.setVisibility(View.VISIBLE);
            buttonResetTimer.setVisibility(View.VISIBLE);
            }
        else if(v.getId()==R.id.buttonResumeTimer){
            buttonPauseTimer.setVisibility(View.VISIBLE);
            buttonResetTimer.setVisibility(View.GONE);
            buttonResumeTimer.setVisibility(View.GONE);
            totalTime=resumeTime;
            len1time=len1resume;
            startTimer();
        }
        else if(v.getId()==R.id.buttonResetTimer){
            buttonStartTimer.setVisibility(View.VISIBLE);
            buttonResumeTimer.setVisibility(View.GONE);
            buttonResetTimer.setVisibility(View.GONE);
            totalTime=startTime;
            long s=totalTime/1000;
            ttotallen.setText((String.format("%02d",s/3600))+":"+
                    (String.format("%02d",(s/60)%60))+":"+
                    (String.format("%02d",s%60)));
            ttotalms.setText("00");

        }
        else if(v.getId()==R.id.buttonStopTimer){
            r.stop();
            buttonStopTimer.setVisibility(View.GONE);
            buttonStartTimer.setVisibility(View.VISIBLE);
            totalTime=startTime;
            long s=totalTime/1000;
            ttotallen.setText((String.format("%02d",s/3600))+":"+
                              (String.format("%02d",(s/60)%60))+":"+
                              (String.format("%02d",s%60)));
            ttotalms.setText("00");
        }
    }

    public void startTimer(){
        long s=startTime/1000;
        ttotallen.setText((String.format("%02d",s/3600))+":"+
                          (String.format("%02d",(s/60)%60))+":"+
                          (String.format("%02d",s%60)));
        ttotalms.setText("00");

        countDownTimer=new CountDownTimer(totalTime,22)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                long sec = millisUntilFinished/1000;
                ttotallen.setText((String.format("%02d",sec/3600))+":"+
                               (String.format("%02d",(sec/60)%60))+":"+
                               (String.format("%02d",sec%60)));
                ttotalms.setText(String.format("%02d",(millisUntilFinished/10)%100));
                resumeTime=millisUntilFinished;
            }

            @Override
            public void onFinish() {
                ttotalms.setText("00");
                buttonStopTimer.setVisibility(View.VISIBLE);
                buttonStopTimer.setText("Stop");
                buttonPauseTimer.setVisibility(View.GONE);
                buttonStartTimer.setVisibility(View.GONE);
                buttonResumeTimer.setVisibility(View.GONE);
                r = RingtoneManager.getRingtone(getApplicationContext(),Uri.parse(sound));
                r.play();
            }
        }.start();
        startSubTimer();
    }



    public void startSubTimer(){
        if(t1a||t2a||t3a){
            if(turn==1&&increp1<=numrep1&&t1a){
                trep1.setText(Integer.toString(increp1)+"/" +Integer.toString(numrep1));
                increp1=increp1+1;
                turn=2;
            }
            else if(turn==2&&increp2<=numrep2&&t2a){
                trep2.setText(Integer.toString(increp2)+"/" +Integer.toString(numrep2));
                increp2=increp2+1;
                turn=3;
            }
            else if(turn==3&&increp3<=numrep3&&t3a){
                trep3.setText(Integer.toString(increp3)+"/" +Integer.toString(numrep3));
                increp3=increp3+1;
                turn=1;
            }
            else{
                if(increp1>numrep1)
                    t1a=false;
                if(increp2>numrep2)
                    t2a=false;
                if(increp3>numrep3)
                    t3a = false;

                if(turn==3)
                    turn=1;
                else
                    turn=turn+1;
                startSubTimer();

            }
        }
    }
}
