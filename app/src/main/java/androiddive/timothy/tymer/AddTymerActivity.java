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
import android.widget.LinearLayout;

/**
 * Created by Timothy on 11/10/2014.
 */
public class AddTymerActivity extends Activity implements OnClickListener {


    private EditText aname,alen1,arep1,alen2,arep2,alen3,arep3,asound;
    private Button btn_asound,btn_addrep,btn_addtimer;
    private LinearLayout he2,he3;
    private String atotallen;

    private DBManager dbManager;

    private int addT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Timer");

        LayoutParams linLayoutParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setContentView(R.layout.activity_add_record);

        aname = (EditText) findViewById(R.id.aname);
        alen1 = (EditText) findViewById(R.id.alen1);
        arep1 = (EditText) findViewById(R.id.arep1);
        alen2= (EditText) findViewById(R.id.alen2);
        arep2= (EditText) findViewById(R.id.arep2);
        alen3= (EditText) findViewById(R.id.alen3);
        arep3= (EditText) findViewById(R.id.arep3);
        asound= (EditText) findViewById(R.id.asound);

        btn_asound = (Button) findViewById(R.id.btn_asound);
        btn_addrep = (Button) findViewById(R.id.btn_addrep);
        btn_addtimer=(Button)findViewById(R.id.btn_addtimer);

        he2 = (LinearLayout)findViewById(R.id.h2);
        he3 = (LinearLayout)findViewById(R.id.h3);

        addT = 0;

        dbManager = new DBManager(this);
        dbManager.open();

        btn_asound.setOnClickListener(this);
        btn_addrep.setOnClickListener(this);
        btn_addtimer.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_asound) {
            Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALL);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
            this.startActivityForResult(intent, 5);
        }
        else if(v.getId() == R.id.btn_addtimer) {
            final String name = aname.getText().toString();


            final String len1 = alen1.getText().toString();
            final String rep1 = arep1.getText().toString();
            final String len2 = alen2.getText().toString();
            final String rep2 = arep2.getText().toString();
            final String len3 = alen3.getText().toString();
            final String rep3 = arep3.getText().toString();
            final String sound = asound.getText().toString();
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


            String totallen=((String.format("%02d",timeT/3600))+":"+(String.format("%02d",(timeT/60)%60))+":"+(String.format("%02d",timeT%60)));



            dbManager.insert(name, totallen, len1, rep1, len2, rep2, len3, rep3, sound);




            Intent main = new Intent(AddTymerActivity.this, TymerActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        }
        else if (v.getId()==R.id.btn_addrep);
        {
            if(addT==0) {
                he2.setVisibility(View.VISIBLE);
                addT=1;
            } else if(addT==1){
                he3.setVisibility(View.VISIBLE);
                addT=2;
                btn_addrep.setEnabled(false);
            }
        }
    }
    @Override
    protected void onActivityResult(final int requestCode,final int resultCode, final Intent intent)
    {
        if (resultCode == Activity.RESULT_OK && requestCode == 5)
        {
            Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (uri != null)
                asound.setText(uri.toString());
            else
                asound.setText(null);
        }
    }
}
