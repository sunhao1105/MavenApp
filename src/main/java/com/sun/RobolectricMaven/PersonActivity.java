package com.sun.RobolectricMaven;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class PersonActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person);

        TextView textView = (TextView) findViewById(R.id.personString);
        Bundle bundle = this.getIntent().getExtras();
        String getName = bundle.getString("name");

        SQLiteDatabase db = openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
        db.execSQL("drop table if exists person");
        db.execSQL("create table if not exists person (_id integer primary key autoincrement, name varchar, age integer)");

        for (int i=1; i<5; i++)
        {
            db.execSQL("insert into person values(null, ?, ?)", new Object[]{("sunhao" + i), (20 + i)});
        }

        Cursor cursor = db.rawQuery("select age from person where name = ?", new String[]{getName});
        if (cursor.moveToFirst())
        {
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            Log.i("tag", String.valueOf(age));
            textView.setText(String.valueOf(age));
        }
        else textView.setText("no this name");

        cursor.close();
        db.close();
    }
}
