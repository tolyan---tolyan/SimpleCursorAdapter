package com.example.hunter.simplecursoradapter;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {



        private static final int CM_DELETE_ID = 1;
        ListView lvData;
        DB db;
        SimpleCursorAdapter scAdapter;
        Cursor cursor;
        EditText editText;

        /** Called when the activity is first created. */
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main);

            editText = (EditText)findViewById(R.id.editText);

            db = new DB(this);
            db.open();

            cursor = db.getAllData();
            startManagingCursor(cursor);


            String[] from = new String[] { DB.COLUMN_TXT };
            int[] to = new int[] {  R.id.tvText };


            scAdapter = new SimpleCursorAdapter(this, R.layout.layout, cursor, from, to);
            lvData = (ListView) findViewById(R.id.lvData);
            lvData.setAdapter(scAdapter);

            registerForContextMenu(lvData);
        }


        public void onButtonClick(View view) {
            db.addRec(editText.getText().toString());
            cursor.requery();
        }

        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
        }

        public boolean onContextItemSelected(MenuItem item) {
            if (item.getItemId() == CM_DELETE_ID) {
                AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                db.delRec(acmi.id);
                cursor.requery();
                return true;
            }
            return super.onContextItemSelected(item);
        }

        protected void onDestroy() {
            super.onDestroy();
            db.close();
        }

}
