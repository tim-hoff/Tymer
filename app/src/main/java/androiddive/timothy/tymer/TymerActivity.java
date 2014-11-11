package androiddive.timothy.tymer;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


/**
 * Created by Timothy on 11/10/2014.
 */
public class TymerActivity extends ActionBarActivity{
    private DBManager dbManager;
    private ListView listView;


    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper._ID, DatabaseHelper.TYMER_NAME,
            DatabaseHelper.TYMER_LEN,DatabaseHelper.TYMER_REP, DatabaseHelper.TYMER_LEN2,
            DatabaseHelper.TYMER_REP2,DatabaseHelper.TYMER_LEN3,DatabaseHelper.TYMER_REP3,
            DatabaseHelper.TYMER_LEN4,DatabaseHelper.TYMER_REP4,DatabaseHelper.TYMER_SOUND};

    final int[] to = new int[] {R.id.id,R.id.name,R.id.len,R.id.rep,R.id.len2,R.id.rep2,
            R.id.len3,R.id.rep3,R.id.len4,R.id.rep4,R.id.sound};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_emp_list);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);


        // OnCLickListiner For List Items
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView titleTextView = (TextView) view.findViewById(R.id.name);
                TextView descTextView = (TextView) view.findViewById(R.id.len);
                TextView r1 = (TextView) view.findViewById(R.id.rep);
                TextView l2 = (TextView) view.findViewById(R.id.len2);
                TextView r2 = (TextView) view.findViewById(R.id.rep2);
                TextView l3 = (TextView) view.findViewById(R.id.len3);
                TextView r3 = (TextView) view.findViewById(R.id.rep3);
                TextView l4 = (TextView) view.findViewById(R.id.len4);
                TextView r4 = (TextView) view.findViewById(R.id.rep4);

                TextView soundTextView = (TextView) view.findViewById(R.id.sound);



                String id = idTextView.getText().toString();
                String name = titleTextView.getText().toString();
                String len = descTextView.getText().toString();
                String re1=r1.getText().toString();
                String re2=r2.getText().toString();
                String re3=r3.getText().toString();
                String re4=r4.getText().toString();
                String le2=l2.getText().toString();
                String le3=l3.getText().toString();
                String le4=l4.getText().toString();
                String sound = soundTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), ModifyTymerActivity.class);
                modify_intent.putExtra("name", name);
                modify_intent.putExtra("len", len);
                modify_intent.putExtra("re1", re1);
                modify_intent.putExtra("re2", re2);
                modify_intent.putExtra("re3", re3);
                modify_intent.putExtra("re4", re4);
                modify_intent.putExtra("le2", le2);
                modify_intent.putExtra("le3", le3);
                modify_intent.putExtra("le4", le4);
                modify_intent.putExtra("id", id);
                modify_intent.putExtra("sound",sound);


                startActivity(modify_intent);
                return true;
            }
        });
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView titleTextView = (TextView) view.findViewById(R.id.name);
                TextView descTextView = (TextView) view.findViewById(R.id.len);
                TextView soundTextView = (TextView) view.findViewById(R.id.sound);


                String title = titleTextView.getText().toString();
                String timer1 = descTextView.getText().toString();
                String sound1 = soundTextView.getText().toString();

                Intent intent_t = new Intent(getApplicationContext(), Timer.class);
                intent_t.putExtra("title", title);
                intent_t.putExtra("timer1", timer1);
                intent_t.putExtra("sound1", sound1);

                Log.v("asdf", "Creating Timer");
                startActivity(intent_t);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add_record) {

            Intent add_mem = new Intent(this, AddTymerActivity.class);
            startActivity(add_mem);

        }
        return super.onOptionsItemSelected(item);
    }
}
