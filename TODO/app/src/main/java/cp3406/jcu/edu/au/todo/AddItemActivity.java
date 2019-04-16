package cp3406.jcu.edu.au.todo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class AddItemActivity extends AppCompatActivity {

    SharedPreferences dataSource;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        dataSource = getSharedPreferences("todo items", Context.MODE_PRIVATE);

        editText = findViewById(R.id.editbox);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onBackPressed(){
        Toast.makeText(this,"Cancelled", Toast.LENGTH_LONG).show();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                editText = findViewById(R.id.editbox);
                String entry = editText.getText().toString();
                if (entry.matches("")){
                    Toast.makeText(this,"no item name given", Toast.LENGTH_SHORT).show();
                    return true;
                }

                Set<String> items = dataSource.getStringSet("items", new HashSet<String>());
                assert items != null;
                items.add(entry);
                dataSource.edit().clear().putStringSet("items", items).apply();

                Toast.makeText(this, "added", Toast.LENGTH_SHORT).show();
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}


