package androiddive.timothy.tymer;

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
public class AddTymerActivity extends Activity implements OnClickListener {

    private Button addTymerBtn;
    private EditText nameEditText;
    private EditText lenEditText;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add The FUCKING Timer");

        LayoutParams linLayoutParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setContentView(R.layout.activity_add_record);

        nameEditText = (EditText) findViewById(R.id.subject_edittext);
        lenEditText = (EditText) findViewById(R.id.description_edittext);

        addTymerBtn = (Button) findViewById(R.id.add_record);

        dbManager = new DBManager(this);
        dbManager.open();
        addTymerBtn.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_record:

                final String name = nameEditText.getText().toString();
                final String desc = lenEditText.getText().toString();

                dbManager.insert(name, desc);

                Intent main = new Intent(AddTymerActivity.this, TymerActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);
                break;
        }
    }
}
