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
    private  TextView timerN,timerL;
    private TextView soundText,intvz;
    private Button bStart,bStop,bRes;
    private CountDownTimer countDownTimer;
    private long totalTime;
    private int hours,mins,secs;
    private long resumeTime;
    private DBManager dbManager;
    private Ringtone r;
    String timer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_view);
        soundText=(TextView)findViewById(R.id.sound_edittext);
        intvz = (TextView)findViewById(R.id.rep);
        bStart = (Button)findViewById(R.id.buttonStartTimer);
        bStop = (Button)findViewById(R.id.buttonStopTimer);
        bRes = (Button)findViewById(R.id.buttonResumeTimer);
        dbManager = new DBManager(this);
        dbManager.open();
        Log.i("asdf", "on create called ");
        timerN = (TextView) findViewById(R.id.timerName);
        timerL = (TextView) findViewById(R.id.timerLen);

        Intent intent_i = getIntent();
        String title = intent_i.getStringExtra("title");
        Log.i("title", "title" + title);

        timer1 = intent_i.getStringExtra("timer1");
        Log.i("len", "len" + timer1);

        timerN.setText(title);
        timerL.setText(timer1);
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

        bStart.setOnClickListener(this);
        bStop.setOnClickListener(this);
        bRes.setOnClickListener(this);
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
                bStop.setVisibility(View.VISIBLE);
                bRes.setVisibility(View.GONE);
                bStart.setVisibility(View.GONE);
                bStart.setText("Start");
                startTimer();}
        else if(v.getId() == R.id.buttonStopTimer){

                countDownTimer.cancel();
                bStop.setVisibility(View.GONE);
                bStart.setText("Restart");
                bRes.setVisibility(View.VISIBLE);
                bStart.setVisibility(View.VISIBLE);
            }

        else if(v.getId()==R.id.buttonResumeTimer){
            bStop.setVisibility(View.VISIBLE);
            bRes.setVisibility(View.GONE);
            bStart.setText("Start");
            bStart.setVisibility(View.GONE);
            totalTime=resumeTime;
            startTimer();
        }
    }

    public void startTimer(){
        countDownTimer=new CountDownTimer(totalTime,500)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                long sec = millisUntilFinished/1000;
                timerL.setText((String.format("%02d",sec/3600))+":"+
                               (String.format("%02d",(sec/60)%60))+":"+
                               (String.format("%02d",sec%60)));
                resumeTime=millisUntilFinished;
            }

            @Override
            public void onFinish() {
                bStop.setVisibility(View.VISIBLE);
                bStop.setText("Stop");
                bStart.setVisibility(View.GONE);
                bRes.setVisibility(View.GONE);
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();


                timerL.setText("TIME");

            }
        }.start();
    }

}
