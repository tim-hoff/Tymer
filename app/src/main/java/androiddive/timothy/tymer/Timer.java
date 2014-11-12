package androiddive.timothy.tymer;

import android.app.Activity;
import android.database.Cursor;
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
    private TextView timerN,timerL,timerM,inC,snC;
    private Button bStart,bStop,bPause,bRes;
    private CountDownTimer countDownTimer;
    private long totalTime,resumeTime;
    private int hours,mins,secs,ms;
    private DBManager dbManager;
    private long startTime;
    private Ringtone r;
    private String sound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_view);
        timerN = (TextView) findViewById(R.id.timerName);
        timerL = (TextView) findViewById(R.id.timerLen);
        timerM= (TextView)findViewById(R.id.timerMs);
        inC= (TextView)findViewById(R.id.intvCheck);
        snC=(TextView)findViewById(R.id.soundCheck);

        bStart = (Button)findViewById(R.id.buttonStartTimer);
        bPause = (Button)findViewById(R.id.buttonPauseTimer);
        bRes = (Button)findViewById(R.id.buttonResumeTimer);
        bStop= (Button) findViewById(R.id.buttonStopTimer);

        dbManager = new DBManager(this);
        dbManager.open();

        Intent intent_i = getIntent();
        String title = intent_i.getStringExtra("title");
        String timer1 = intent_i.getStringExtra("timer1");
        String reps=intent_i.getStringExtra("reps");
        sound=intent_i.getStringExtra("sound");


        // Passing needed infromation to the timer.
        timerN.setText(title);
        timerL.setText(timer1);
        inC.setText(reps);
        snC.setText(sound);

        int NumRep = Integer.valueOf(reps);
        String inputTime = timer1;
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

        bStart.setOnClickListener(this);
        bPause.setOnClickListener(this);
        bRes.setOnClickListener(this);
        bStop.setOnClickListener(this);
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
            bPause.setVisibility(View.VISIBLE);
            bRes.setVisibility(View.GONE);
            bStart.setVisibility(View.GONE);
            bStart.setText("Start");
            totalTime=startTime;
            long s=totalTime/1000;
            timerL.setText((String.format("%02d",s/3600))+":"+
                (String.format("%02d",(s/60)%60))+":"+
                (String.format("%02d",s%60)));
            timerM.setText("00");
            startTimer();
        }
        else if(v.getId() == R.id.buttonPauseTimer){
                countDownTimer.cancel();
                bPause.setVisibility(View.GONE);
                bStart.setText("Restart");
                bRes.setVisibility(View.VISIBLE);
                bStart.setVisibility(View.VISIBLE);
            }
        else if(v.getId()==R.id.buttonResumeTimer){
            bPause.setVisibility(View.VISIBLE);
            bRes.setVisibility(View.GONE);
            bStart.setText("Start");
            bStart.setVisibility(View.GONE);
            totalTime=resumeTime;
            startTimer();
        }
        else if(v.getId()==R.id.buttonStopTimer){
            bStop.setVisibility(View.GONE);
             r.stop();
            bStart.setVisibility(View.VISIBLE);
            totalTime=startTime;
            long s=totalTime/1000;
            timerL.setText((String.format("%02d",s/3600))+":"+
                    (String.format("%02d",(s/60)%60))+":"+
                    (String.format("%02d",s%60)));
            timerM.setText("00");
        }
    }

    public void startTimer(){
        countDownTimer=new CountDownTimer(totalTime,22)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                long sec = millisUntilFinished/1000;
                timerL.setText((String.format("%02d",sec/3600))+":"+
                               (String.format("%02d",(sec/60)%60))+":"+
                               (String.format("%02d",sec%60)));
                timerM.setText(String.format("%02d",(millisUntilFinished/10)%100));
                resumeTime=millisUntilFinished;
            }

            @Override
            public void onFinish() {
                bStop.setVisibility(View.VISIBLE);
                bStop.setText("Stop");
                bPause.setVisibility(View.GONE);
                bStart.setVisibility(View.GONE);
                bRes.setVisibility(View.GONE);
                timerL.setText("TIME");
                r = RingtoneManager.getRingtone(getApplicationContext(),Uri.parse(sound));
                r.play();
            }
        }.start();
    }
}
