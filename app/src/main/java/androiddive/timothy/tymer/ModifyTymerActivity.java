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
    private EditText nameText,lenText,soundText,repText;
    private EditText repT2,repT3,repT4,lenT2,lenT3,lenT4;
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
        repText= (EditText) findViewById(R.id.rep_edittext);

        lenT2= (EditText) findViewById(R.id.editText8);
        lenT3= (EditText) findViewById(R.id.editText11);
        lenT4= (EditText) findViewById(R.id.editText9);

        repT2= (EditText) findViewById(R.id.editText10);
        repT3= (EditText) findViewById(R.id.editText13);
        repT4= (EditText) findViewById(R.id.editText12);





        soundText= (EditText)findViewById(R.id.sound_edittext);
        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);
        alarmBtn = (Button) findViewById(R.id.btn_ring);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String len = intent.getStringExtra("len");
        String rep = intent.getStringExtra("rep");
        String len2 = intent.getStringExtra("len2");
        String rep2 = intent.getStringExtra("rep2");
        String len3 = intent.getStringExtra("len3");
        String rep3 = intent.getStringExtra("rep3");
        String len4 = intent.getStringExtra("len4");
        String rep4 = intent.getStringExtra("rep4");
        String sound = intent.getStringExtra("sound");


        _id = Long.parseLong(id);
        nameText.setText(name);
        lenText.setText(len);

        repText.setText(rep);
        lenT2.setText(len2);
        repT2.setText(rep2);
        lenT3.setText(len3);
        repT3.setText(rep3);
        lenT4.setText(len4);
        repT4.setText(rep4);
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
                String rep = repText.getText().toString();
                String len2 = lenT2.getText().toString();
                String rep2 = repT2.getText().toString();
                String len3 = lenT3.getText().toString();
                String rep3 = repT3.getText().toString();
                String len4 = lenT4.getText().toString();
                String rep4 = repT4.getText().toString();

                dbManager.update(_id, title, desc, rep, len2,rep2,len3,rep3,len4,rep4,sound);
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