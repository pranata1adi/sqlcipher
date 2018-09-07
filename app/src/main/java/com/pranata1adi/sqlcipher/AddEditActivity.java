package com.pranata1adi.sqlcipher;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class AddEditActivity extends AppCompatActivity {
    TextView textViewName, textViewPriority;
    MyDatabase database;
    Boolean edit = false;
    Item itemIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewName = findViewById(R.id.enter_name);
        textViewPriority = findViewById(R.id.enter_priority);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = textViewName.getText().toString();
                int prio = Integer.parseInt(textViewPriority.getText().toString());
                if (edit) {
                    Item item = new Item(itemIntent.getId(), name, prio);
                    database.updateItem(item);
                } else {
                    Item item = new Item(name, prio);
                    database.addItem(item);
                }

                Intent intent = new Intent(AddEditActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        if (getIntent().getParcelableExtra("ITEM") != null) {
            itemIntent = getIntent().getParcelableExtra("ITEM");
            textViewName.setText(itemIntent.getName());
            textViewPriority.setText(String.valueOf(itemIntent.getPriority()));
            edit = true;
        }
        database = new MyDatabase(AddEditActivity.this);
    }
}