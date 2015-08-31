package com.codepath.todoappkanaga;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Button;

public class EditItemActivity extends Activity {
    EditText editText;
    Integer p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String item = getIntent().getStringExtra("ITEM");
        p = getIntent().getIntExtra("POSITION", 0);
        editText = ( EditText) findViewById(R.id.editText);
        editText.setText("");
        editText.append(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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

    public void onEditItem(View view) {
       Intent intent = new Intent();
       intent.putExtra("NEW_VALUE", editText.getText().toString());
       intent.putExtra("POSITION", p);
       setResult(RESULT_OK, intent);
        finish();
    }
}
