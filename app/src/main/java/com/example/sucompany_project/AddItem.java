package com.example.sucompany_project;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sucompany_project.db.DatabaseHelper;

import static com.example.sucompany_project.db.DatabaseHelper.COL_DEPARTMENT;
import static com.example.sucompany_project.db.DatabaseHelper.COL_NAME;
import static com.example.sucompany_project.db.DatabaseHelper.COL_AGE;
import static com.example.sucompany_project.db.DatabaseHelper.COL_POSITION;
import static com.example.sucompany_project.db.DatabaseHelper.COL_TEL;
import static com.example.sucompany_project.db.DatabaseHelper.TABLE_NAME;

public class AddItem extends AppCompatActivity {

    private static final String TAG = AddItem.class.getName();

    private DatabaseHelper mHelper;
    private SQLiteDatabase mDb;
    private String mLogoFilename = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mHelper = new DatabaseHelper(this);
        mDb = mHelper.getWritableDatabase();

        Button saveButton = findViewById(R.id.save_button3);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doInsertPhoneItem();
            }
        });

        Button back_to_activity = findViewById(R.id.back_button);
        back_to_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddItem.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void doInsertPhoneItem() {
        EditText nameEditText = findViewById(R.id.name_edit_text1);
        EditText ageEditText = findViewById(R.id.age_edit_text1);
        EditText positionEditText = findViewById(R.id.position_edit_text1);
        EditText departmentEditText = findViewById(R.id.department_edit_text1);
        EditText telEditText = findViewById(R.id.tel_edit_text1);

        String name = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String position = positionEditText.getText().toString();
        String department = departmentEditText.getText().toString();
        String tel = telEditText.getText().toString();

        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_AGE, age);
        cv.put(COL_POSITION, position);
        cv.put(COL_DEPARTMENT, department);
        cv.put(COL_TEL, tel);

        mDb.insert(TABLE_NAME, null, cv);

        finish();
    }
}
