package androiddive.timothy.tymer;

import android.app.Activity;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
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
    private EditText mname,mlen1,mrep1,mlen2,mrep2,mlen3,mrep3,msound;
    private Button btn_update, btn_delete, btn_msound;

    private long _id;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Edit Timer");
        setContentView(R.layout.activity_modify_record);

        dbManager = new DBManager(this);
        dbManager.open();

        mname = (EditText) findViewById(R.id.mname);
        mlen1 = (EditText) findViewById(R.id.mlen1);
        mrep1= (EditText) findViewById(R.id.mrep1);
        mlen2= (EditText) findViewById(R.id.mlen2);
        mrep2= (EditText) findViewById(R.id.mrep2);
        mlen3= (EditText) findViewById(R.id.mlen3);
        mrep3= (EditText) findViewById(R.id.mrep3);
        msound = (EditText) findViewById(R.id.msound);

        btn_update = (Button) findViewById(R.id.btn_update);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_msound = (Button) findViewById(R.id.btn_msound);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String totallen = intent.getStringExtra("totallen");
        String len1 = intent.getStringExtra("len1");
        String rep1 = intent.getStringExtra("rep1");
        String len2 = intent.getStringExtra("len2");
        String rep2 = intent.getStringExtra("rep2");
        String len3 = intent.getStringExtra("len3");
        String rep3 = intent.getStringExtra("rep3");
        String sound = intent.getStringExtra("sound");



        _id = Long.parseLong(id);
        mname.setText(name);
        mlen1.setText(len1);
        mrep1.setText(rep1);
        mlen2.setText(len2);
        mrep2.setText(rep2);
        mlen3.setText(len3);
        mrep3.setText(rep3);
        msound.setText(sound);




        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_msound.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
          if(v.getId()==R.id.btn_msound) {
            Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALL);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
            this.startActivityForResult(intent, 5);

          }
         else if(v.getId()==R.id.btn_update ) {
              String name = mname.getText().toString();
              String len1 = mlen1.getText().toString();
              String rep1 = mrep1.getText().toString();
              String len2 = mlen2.getText().toString();
              String rep2 = mrep2.getText().toString();
              String len3 = mlen3.getText().toString();
              String rep3 = mrep3.getText().toString();
              String sound = msound.getText().toString();
            int hours=0,mins=0,secs=0;
            int timeT;

            String[] split = len1.split(":");
            String[] split2 = len2.split(":");
            String[] split3 = len3.split(":");
            hours = hours+Integer.valueOf(rep1)*Integer.valueOf(split[0])+
                    Integer.valueOf(rep2)*Integer.valueOf(split2[0])+
                    Integer.valueOf(rep3)*Integer.valueOf(split3[0]);

            mins = mins+Integer.valueOf(rep1)*Integer.valueOf(split[1])+
                    Integer.valueOf(rep2)*Integer.valueOf(split2[1])+
                    Integer.valueOf(rep3)*Integer.valueOf(split3[1]);

            secs = secs+Integer.valueOf(rep1)*Integer.valueOf(split[2])+
                    Integer.valueOf(rep2)* Integer.valueOf(split2[2])+
                    Integer.valueOf(rep3)*Integer.valueOf(split3[2]);


            timeT=(hours*3600+mins*60+secs);


            String totallen=((String.format("%02d",timeT/3600))+":"
                    +(String.format("%02d",(timeT/60)%60))+":"
                    +(String.format("%02d",timeT%60)));



            dbManager.update(_id, name, totallen,len1, rep1, len2,rep2,len3,rep3, sound);

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
                msound.setText(uri.toString());
            }
        }
    }
}