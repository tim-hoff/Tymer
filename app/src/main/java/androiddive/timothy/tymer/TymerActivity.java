package androiddive.timothy.tymer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
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
            DatabaseHelper.TYMER_LEN1,DatabaseHelper.TYMER_REP1, DatabaseHelper.TYMER_LEN2,
            DatabaseHelper.TYMER_REP2,DatabaseHelper.TYMER_LEN3,DatabaseHelper.TYMER_REP3,
            DatabaseHelper.TYMER_SOUND};

    final int[] to = new int[] {R.id.id,R.id.name,R.id.len1,R.id.rep1,R.id.len2,R.id.rep2,
            R.id.len3,R.id.rep3, R.id.sound};

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
                TextView idv = (TextView) view.findViewById(R.id.id);
                TextView namev = (TextView) view.findViewById(R.id.name);
                TextView len1v = (TextView) view.findViewById(R.id.len1);
                TextView rep1v = (TextView) view.findViewById(R.id.rep1);
                TextView len2v = (TextView) view.findViewById(R.id.len2);
                TextView rep2v = (TextView) view.findViewById(R.id.rep2);
                TextView len3v = (TextView) view.findViewById(R.id.len3);
                TextView rep3v = (TextView) view.findViewById(R.id.rep3);
                TextView soundv = (TextView) view.findViewById(R.id.sound);

                String id = idv.getText().toString();
                String name = namev.getText().toString();
                String len1 = len1v.getText().toString();
                String rep1 = rep1v.getText().toString();
                String len2 = len2v.getText().toString();
                String rep2 = rep2v.getText().toString();
                String len3 = len3v.getText().toString();
                String rep3 = rep3v.getText().toString();
                String sound = soundv.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), ModifyTymerActivity.class);
                modify_intent.putExtra("id", id);
                modify_intent.putExtra("name", name);
                modify_intent.putExtra("len1", len1);
                modify_intent.putExtra("rep1", rep1);
                modify_intent.putExtra("len2", len2);
                modify_intent.putExtra("rep2", rep2);
                modify_intent.putExtra("len3", len3);
                modify_intent.putExtra("rep3", rep3);
                modify_intent.putExtra("sound",sound);
                startActivity(modify_intent);
                return true;
            }
        });
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView namev = (TextView) view.findViewById(R.id.name);
                TextView len1v = (TextView) view.findViewById(R.id.len1);
                TextView rep1v = (TextView) view.findViewById(R.id.rep1);
                TextView len2v = (TextView) view.findViewById(R.id.len2);
                TextView rep2v = (TextView) view.findViewById(R.id.rep2);
                TextView len3v = (TextView) view.findViewById(R.id.len3);
                TextView rep3v = (TextView) view.findViewById(R.id.rep3);
                TextView soundv = (TextView) view.findViewById(R.id.sound);

                String name = namev.getText().toString();
                String len1 = len1v.getText().toString();
                String rep1 = rep1v.getText().toString();
                String len2 = len2v.getText().toString();
                String rep2 = rep2v.getText().toString();
                String len3 = len3v.getText().toString();
                String rep3 = rep3v.getText().toString();
                String sound = soundv.getText().toString();

                Intent intent_t = new Intent(getApplicationContext(), Timer.class);
                intent_t.putExtra("name", name);
                intent_t.putExtra("len1", len1);
                intent_t.putExtra("rep1", rep1);
                intent_t.putExtra("len2", len2);
                intent_t.putExtra("rep2", rep2);
                intent_t.putExtra("len3", len3);
                intent_t.putExtra("rep3", rep3);
                intent_t.putExtra("sound", sound);
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
