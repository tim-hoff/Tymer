package androiddive.timothy.tymer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;


public class Timer extends Activity {
    private  TextView timerN,timerL;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_view);
        dbManager = new DBManager(this);
        dbManager.open();
        Log.i("asdf", "on create called ");
        timerN = (TextView) findViewById(R.id.timerName);
        timerL = (TextView) findViewById(R.id.timerLen);

        Intent intent_i = getIntent();
        String title = intent_i.getStringExtra("title");
        Log.i("title", "title" + title);

        String timer1 = intent_i.getStringExtra("timer1");
        Log.i("len", "len" + timer1);



        timerN.setText(title);
        timerL.setText(timer1);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
