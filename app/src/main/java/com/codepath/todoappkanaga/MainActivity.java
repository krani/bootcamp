package com.codepath.todoappkanaga;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemLongClickListener;
import android.content.Intent;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;


public class MainActivity extends Activity {
    ArrayList<String> toDoItems;
    ArrayAdapter<String> toDoAdapter;
    ListView lvItems;
    EditText eEditText;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(toDoAdapter);
        eEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                toDoItems.remove(position);
                toDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                 String s = toDoItems.get(position);
                 //intent.putExtra("ITEM","YYY");
                 intent.putExtra("ITEM", s);
                 intent.putExtra("POSITION", position);
                 startActivityForResult(intent, 1);
             }
        });
    }

    public void populateArrayItems() {
        toDoItems = new ArrayList<String>();
        /*
        toDoItems.add("Item1");
        toDoItems.add("Item2");
        toDoItems.add("Item3");
        */
        readItems();
        toDoAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, toDoItems);

    }

    private void readItems() {
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try {
            toDoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {

        }
    }

    private void writeItems() {
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try {
            FileUtils.writeLines(file, toDoItems);
        } catch (IOException e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == 1) {
            String s = data.getExtras().getString("NEW_VALUE");
            Integer pVal = data.getIntExtra("POSITION",0);
            //Toast.makeText(this, s,Toast.LENGTH_LONG).show();
            //Toast.makeText(this, pVal.toString(),Toast.LENGTH_LONG).show();
            toDoItems.set(pVal, s);
            toDoAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View view) {
        toDoAdapter.add(eEditText.getText().toString());
        eEditText.setText("");
        writeItems();
    }
}
