package com.example.nevzat.semesterproject.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nevzat.semesterproject.PersonLab;
import com.example.nevzat.semesterproject.ProjectDbHelper;
import com.example.nevzat.semesterproject.R;
import com.example.nevzat.semesterproject.adapters.SerialAdapter;
import com.example.nevzat.semesterproject.models.Person;


public class ContactActivity extends AppCompatActivity {
    public ProjectDbHelper dbHelper;
    public PersonLab personLab;
    public SerialAdapter dataAdapter;
    PersonLab.ContactCursorWrapper ccw;
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Intent intent = getIntent();
        String pid = intent.getStringExtra("pid");
        String whereClause = "pid = ?";
        String[] whereArgs = new String[] {pid};
        ccw =  personLab.queryPersons(whereClause, whereArgs);
        ccw.moveToFirst();
        person = personLab.getContact(ccw) ;

    }
}
