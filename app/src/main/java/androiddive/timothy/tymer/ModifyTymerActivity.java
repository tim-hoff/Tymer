package androiddive.timothy.tymer;

import android.app.Activity;
import android.media.RingtoneManager;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Timothy on 11/10/2014.
 */
public class ModifyTymerActivity extends Activity implements OnClickListener{
    private EditText nameText,lenText,soundText;
    private Button updateBtn, deleteBtn, alarmBtn;



    private long _id;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Edit Timer");
        setContentView(R.layout.activity_modify_record);

        dbManager = new DBManager(this);
        dbManager.open();

        nameText = (EditText) findViewById(R.id.subject_edittext);
        lenText = (EditText) findViewById(R.id.description_edittext);
        soundText= (EditText)findViewById(R.id.sound_edittext);
        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);
        alarmBtn = (Button) findViewById(R.id.btn_ring);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String len = intent.getStringExtra("len");
        String sound = intent.getStringExtra("sound");

        _id = Long.parseLong(id);
        nameText.setText(name);
        lenText.setText(len);
        soundText.setText(sound);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
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
         else if(v.getId()==R.id.btn_update ) {
                String title = nameText.getText().toString();
                String desc = lenText.getText().toString();
                String sound = soundText.getText().toString();
                dbManager.update(_id, title, desc, sound);
                this.returnHome();
            }
        else if (v.getId()==R.id.btn_delete ) {
                dbManager.delete(_id);
                this.returnHome();
        }

    }
    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), TymerActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
    @Override
     protected void onActivityResult(final int requestCode,final int resultCode, final Intent intent)
    {
        if (resultCode == Activity.RESULT_OK && requestCode == 5)
        {
            Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (uri != null)
            {
                soundText.setText(uri.toString());
            }
            else
            {
                soundText.setText(null);
            }
        }
    }
}