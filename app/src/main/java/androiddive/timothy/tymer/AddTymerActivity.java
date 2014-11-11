package androiddive.timothy.tymer;

import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Timothy on 11/10/2014.
 */
public class AddTymerActivity extends Activity implements OnClickListener {

    private Button addTymerBtn,alarmBtn;
    private EditText nameEditText,lenEditText,soundEditText;


    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Timer");

        LayoutParams linLayoutParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setContentView(R.layout.activity_add_record);

        nameEditText = (EditText) findViewById(R.id.subject_edittext);
        lenEditText = (EditText) findViewById(R.id.description_edittext);
        soundEditText = (EditText) findViewById(R.id.sound_edittext);

        addTymerBtn = (Button) findViewById(R.id.add_record);
        alarmBtn = (Button) findViewById(R.id.btn_ring);

        dbManager = new DBManager(this);
        dbManager.open();
        addTymerBtn.setOnClickListener(this);
        alarmBtn.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_ring) {
            Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALL);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
            this.startActivityForResult(intent, 5);
        }
        else if(v.getId() == R.id.add_record) {
            final String name  = nameEditText.getText().toString();
            final String desc  = lenEditText.getText().toString();
            final String sound = soundEditText.getText().toString();

            dbManager.insert(name, desc, sound);


            Intent main = new Intent(AddTymerActivity.this, TymerActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        }
    }
    @Override
    protected void onActivityResult(final int requestCode,final int resultCode, final Intent intent)
    {
        if (resultCode == Activity.RESULT_OK && requestCode == 5)
        {
            Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (uri != null)
            {
                soundEditText.setText(uri.toString());
            }
            else
            {
                soundEditText.setText(null);
            }
        }
    }
}
