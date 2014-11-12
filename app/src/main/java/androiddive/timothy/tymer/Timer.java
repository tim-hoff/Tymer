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
    private TextView timerName,timerLen,timerMs,intvCheck,soundCheck;
    private Button buttonStartTimer,buttonPauseTimer,buttonResumeTimer,buttonStopTimer;

    private CountDownTimer countDownTimer;
    private int hours,mins,secs;
    private long totalTime,startTime, resumeTime;
    private String sound;

    private DBManager dbManager;
    private Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_view);
        timerName = (TextView) findViewById(R.id.timerName);
        timerLen = (TextView) findViewById(R.id.timerLen);
        timerMs= (TextView)findViewById(R.id.timerMs);
        intvCheck= (TextView)findViewById(R.id.intvCheck);
        soundCheck=(TextView)findViewById(R.id.soundCheck);

        buttonStartTimer = (Button)findViewById(R.id.buttonStartTimer);
        buttonPauseTimer = (Button)findViewById(R.id.buttonPauseTimer);
        buttonResumeTimer = (Button)findViewById(R.id.buttonResumeTimer);
        buttonStopTimer = (Button)findViewById(R.id.buttonStopTimer);

        dbManager = new DBManager(this);
        dbManager.open();

        Intent intent_i = getIntent();
        String id = intent_i.getStringExtra("id");
        String name = intent_i.getStringExtra("name");
        String len1 = intent_i.getStringExtra("len1");
        String rep1=intent_i.getStringExtra("rep1");
        String len2 = intent_i.getStringExtra("len2");
        String rep2=intent_i.getStringExtra("rep2");
        String len3 = intent_i.getStringExtra("len3");
        String rep3=intent_i.getStringExtra("rep3");
        sound=intent_i.getStringExtra("sound");


        // Passing needed infromation to the timer.
        timerName.setText(name);
        timerLen.setText(len1);
        intvCheck.setText(rep1);
        soundCheck.setText(sound);

        int NumRep = Integer.valueOf(rep1);
        String inputTime = len1;
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
        buttonPauseTimer.setOnClickListener(this);
        buttonResumeTimer.setOnClickListener(this);
        buttonStopTimer.setOnClickListener(this);
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
            buttonPauseTimer.setVisibility(View.VISIBLE);
            buttonResumeTimer.setVisibility(View.GONE);
            buttonStartTimer.setVisibility(View.GONE);
            buttonStartTimer.setText("Start");
            totalTime=startTime;
            long s=totalTime/1000;
            timerLen.setText((String.format("%02d",s/3600))+":"+
                (String.format("%02d",(s/60)%60))+":"+
                (String.format("%02d",s%60)));
            timerMs.setText("00");
            startTimer();
        }
        else if(v.getId() == R.id.buttonPauseTimer){
                countDownTimer.cancel();
            buttonPauseTimer.setVisibility(View.GONE);
            buttonStartTimer.setText("Restart");
            buttonResumeTimer.setVisibility(View.VISIBLE);
            buttonStartTimer.setVisibility(View.VISIBLE);
            }
        else if(v.getId()==R.id.buttonResumeTimer){
            buttonPauseTimer.setVisibility(View.VISIBLE);
            buttonResumeTimer.setVisibility(View.GONE);
            buttonStartTimer.setText("Start");
            buttonStartTimer.setVisibility(View.GONE);
            totalTime=resumeTime;
            startTimer();
        }
        else if(v.getId()==R.id.buttonStopTimer){
            buttonStopTimer.setVisibility(View.GONE);
            r.stop();
            buttonStartTimer.setVisibility(View.VISIBLE);
            totalTime=startTime;
            long s=totalTime/1000;
            timerLen.setText((String.format("%02d",s/3600))+":"+
                    (String.format("%02d",(s/60)%60))+":"+
                    (String.format("%02d",s%60)));
            timerMs.setText("00");
        }
    }

    public void startTimer(){
        countDownTimer=new CountDownTimer(totalTime,22)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                long sec = millisUntilFinished/1000;
                timerLen.setText((String.format("%02d",sec/3600))+":"+
                               (String.format("%02d",(sec/60)%60))+":"+
                               (String.format("%02d",sec%60)));
                timerMs.setText(String.format("%02d",(millisUntilFinished/10)%100));
                resumeTime=millisUntilFinished;
            }

            @Override
            public void onFinish() {
                buttonStopTimer.setVisibility(View.VISIBLE);
                buttonStopTimer.setText("Stop");
                buttonPauseTimer.setVisibility(View.GONE);
                buttonStartTimer.setVisibility(View.GONE);
                buttonResumeTimer.setVisibility(View.GONE);
                timerLen.setText("TIME");
                timerMs.setText("");
                r = RingtoneManager.getRingtone(getApplicationContext(),Uri.parse(sound));
                r.play();
            }
        }.start();
    }
}
