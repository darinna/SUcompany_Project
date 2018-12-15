package com.example.sucompany_project;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sucompany_project.adapter.EmpListAdapter;
import com.example.sucompany_project.db.DatabaseHelper;
import com.example.sucompany_project.model.EmpItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.sucompany_project.db.DatabaseHelper.COL_AGE;
import static com.example.sucompany_project.db.DatabaseHelper.COL_DEPARTMENT;
import static com.example.sucompany_project.db.DatabaseHelper.COL_ID;
import static com.example.sucompany_project.db.DatabaseHelper.COL_NAME;
import static com.example.sucompany_project.db.DatabaseHelper.COL_POSITION;
import static com.example.sucompany_project.db.DatabaseHelper.COL_TEL;
import static com.example.sucompany_project.db.DatabaseHelper.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private DatabaseHelper mHelper;
    private SQLiteDatabase mDb;
    private List<EmpItem> mEmpItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new DatabaseHelper(MainActivity.this);
        mDb = mHelper.getWritableDatabase();

        Button addPhoneItemButton = findViewById(R.id.add_emp_detail_button);
        addPhoneItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddItem.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPhoneData();
        setupListView();
    }
    private void loadPhoneData() {
        Cursor c = mDb.query(TABLE_NAME, null, null, null, null, null, null);

        mEmpItemList = new ArrayList<>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(COL_ID));
            String name = c.getString(c.getColumnIndex(COL_NAME));
            String age = c.getString(c.getColumnIndex(COL_AGE));
            String position = c.getString(c.getColumnIndex(COL_POSITION));
            String department= c.getString(c.getColumnIndex(COL_DEPARTMENT));
            String tel= c.getString(c.getColumnIndex(COL_TEL));
            EmpItem item = new EmpItem(id, name, age, position , department ,tel);
            mEmpItemList.add(item);
        }
        c.close();
    }
    private void setupListView() {
        EmpListAdapter adapter = new EmpListAdapter(
                MainActivity.this,
                R.layout.activity_emp_list_adapter,
                mEmpItemList
        );
        ListView lv = findViewById(R.id.result_list_view);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                EmpItem item = mEmpItemList.get(position);


                Toast t = Toast.makeText(MainActivity.this, item.name, Toast.LENGTH_SHORT);
                t.show();

                Intent intent = new Intent(MainActivity.this , ViewDetail.class);
                intent.putExtra("name",item.name);
                intent.putExtra("age",item.age);
                intent.putExtra("position",item.position);
                intent.putExtra("department",item.department);
                intent.putExtra("tel",item.tel);
                startActivity(intent);

            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                String[] items = new String[]{
                        "Edit",
                        "Delete"
                };

                new AlertDialog.Builder(MainActivity.this)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final EmpItem phoneItem = mEmpItemList.get(position);

                                switch (i) {
                                    case 0: // Edit
                                        Intent intent = new Intent(MainActivity.this, EditItem.class);
                                        intent.putExtra("name", phoneItem.name);
                                        intent.putExtra("age", phoneItem.age);

                                        intent.putExtra("id", phoneItem._id);
                                        intent.putExtra("position", phoneItem.position);
                                        intent.putExtra("department", phoneItem.department);
                                        intent.putExtra("tel", phoneItem.tel);
                                        startActivity(intent);
                                        break;
                                    case 1: // Delete
                                        new AlertDialog.Builder(MainActivity.this)
                                                .setMessage("ต้องการลบข้อมูลพนักงานคนนี้ ใช่หรือไม่")
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        mDb.delete(
                                                                TABLE_NAME,
                                                                COL_ID + " = ?",
                                                                new String[]{String.valueOf(phoneItem._id)}
                                                        );
                                                        loadPhoneData();
                                                        setupListView();
                                                    }
                                                })
                                                .setNegativeButton("No", null)
                                                .show();
                                        break;
                                }
                            }
                        })
                        .show();

                return true;
            }
        });
    }

}
