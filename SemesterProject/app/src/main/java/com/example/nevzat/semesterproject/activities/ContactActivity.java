package com.example.nevzat.semesterproject.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

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
    EditText name,surname,email,homePhone,mobilePhone,workPhone,homeAddress,workAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Intent intent = getIntent();
        String pid = intent.getStringExtra("pid");
        String whereClause = "pid = ?";
        String[] whereArgs = new String[] {pid};
        personLab = new PersonLab(getApplicationContext());
        ccw =  personLab.queryPersons(whereClause, whereArgs);
        ccw.moveToFirst();
        person = personLab.getContact(ccw) ;

        name = (EditText) findViewById(R.id.editTextName);
        surname=(EditText) findViewById(R.id.editTextSurname);
        email=(EditText) findViewById(R.id.editTextEmail);
        homePhone=(EditText) findViewById(R.id.editTextHomePhone);
        mobilePhone=(EditText) findViewById(R.id.editTextMobilePhone);
        workPhone=(EditText) findViewById(R.id.editTextWorkPhone);
        homeAddress=(EditText) findViewById(R.id.editTextHomeAddress);
        workAddress = (EditText) findViewById(R.id.editTextWorkAddress);

        name.setText(person.getName());
        surname.setText(person.getSurname());
        email.setText(person.geteMail());
        homePhone.setText(person.getPhone().get(0).getPhoneNumber());
        //mobilePhone.setText(person.getName());
        //workPhone.setText(person.getName());
        homeAddress.setText(person.getLocation().get(0).getLat()+" "+person.getLocation().get(0).getLng());
        //workAddress.setText(person.getName());


    }
}
