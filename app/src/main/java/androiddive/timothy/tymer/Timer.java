package androiddive.timothy.tymer;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Timothy on 11/10/2014.
 */
public class Timer extends Activity{
    private String title;
    private String timer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_view);



    }



    public Timer(String title, String timer1)
    {
        this.title = title;
        this.timer1 = timer1;
    }

    public void start()
    {
//        start the timer
    }

    public void idle()
    {

    }

    public void reset()
    {

    }
}
