package com.example.nevzat.semesterproject.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nevzat.semesterproject.PersonLab;
import com.example.nevzat.semesterproject.ProjectDbHelper;
import com.example.nevzat.semesterproject.R;
import com.example.nevzat.semesterproject.adapters.SerialAdapter;
import com.example.nevzat.semesterproject.models.Person;

import java.util.ArrayList;


public class ContactActivity extends AppCompatActivity {
    public ProjectDbHelper dbHelper;
    public PersonLab personLab;
    public SerialAdapter dataAdapter;
    PersonLab.ContactCursorWrapper ccw;
    Person person;
    int count=0;
    String pid;
    EditText name,surname,email,homePhone,mobilePhone,workPhone,homeAddress,workAddress;
    Button editButton,deleteButton;

    ArrayList<EditText> editTextArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        personLab = new PersonLab(getApplicationContext());
        String whereClause = "pid = ?";
        String[] whereArgs = new String[] {pid};
        personLab = new PersonLab(getApplicationContext());
        ccw =  personLab.queryPersons(whereClause, whereArgs);
        ccw.moveToFirst();
        person = personLab.getContact(ccw) ;
        editTextArrayList = new ArrayList<>();
        controllerInit();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count%2==1)
                    editContact(pid);
                else
                    saveContact(pid);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact(pid);
            }
        });


    }

    public void editContact(String pid){
        editButton.setText("Save");
        for (int i=0;i<editTextArrayList.size();i++){
            editTextArrayList.get(i).setClickable(true);
            editTextArrayList.get(i).setFocusable(true);
        }
    }
    public void saveContact(String pid){
        editButton.setText("Edit");
        for (int i=0;i<editTextArrayList.size();i++){
            editTextArrayList.get(i).setClickable(false);
            editTextArrayList.get(i).setFocusable(false);
        }


    }

    public void deleteContact(String pid){
        personLab.deletePerson(pid);
        Toast.makeText(getApplicationContext(),person.getName()+" "
                +person.getSurname()+" silindi",Toast.LENGTH_LONG);
    }

    public void controllerInit(){
        name = (EditText) findViewById(R.id.editTextName);
        surname=(EditText) findViewById(R.id.editTextSurname);
        email=(EditText) findViewById(R.id.editTextEmail);
        homePhone=(EditText) findViewById(R.id.editTextHomePhone);
        mobilePhone=(EditText) findViewById(R.id.editTextMobilePhone);
        workPhone=(EditText) findViewById(R.id.editTextWorkPhone);
        homeAddress=(EditText) findViewById(R.id.editTextHomeAddress);
        workAddress = (EditText) findViewById(R.id.editTextWorkAddress);
        editButton = (Button) findViewById(R.id.editButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        
        editTextArrayList.add(name);
        editTextArrayList.add(surname);
        editTextArrayList.add(email);
        editTextArrayList.add(homePhone);
        editTextArrayList.add(workPhone);
        editTextArrayList.add(mobilePhone);
        editTextArrayList.add(homeAddress);
        editTextArrayList.add(workAddress);

        name.setText(person.getName());
        surname.setText(person.getSurname());
        email.setText(person.geteMail());
        homePhone.setText(person.getPhone().get(0).getPhoneNumber());
        mobilePhone.setText(person.getName());
        workPhone.setText(person.getName());
        homeAddress.setText(person.getLocation().get(0).getLat()+" "+person.getLocation().get(0).getLng());
        workAddress.setText(person.getName());
    }
}
