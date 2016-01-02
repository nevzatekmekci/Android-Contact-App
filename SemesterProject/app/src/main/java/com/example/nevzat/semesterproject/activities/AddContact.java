package com.example.nevzat.semesterproject.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nevzat.semesterproject.PersonLab;
import com.example.nevzat.semesterproject.R;
import com.example.nevzat.semesterproject.models.ActivityStatistic;
import com.example.nevzat.semesterproject.models.Location;
import com.example.nevzat.semesterproject.models.LocationType;
import com.example.nevzat.semesterproject.models.Person;
import com.example.nevzat.semesterproject.models.Phone;
import com.example.nevzat.semesterproject.models.PhoneType;

import java.util.ArrayList;

public class AddContact extends AppCompatActivity {
    Button addButton;
    Person person;
    ArrayList<Phone> phone;
    ArrayList<Location> location;
    PersonLab personLab;
    int gate=0;
    EditText name, surname, email, homePhone, mobilePhone, workPhone, homeAddressLat, workAddressLng,homeAddressLng, workAddressLat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controllerInit();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
            }
        });

    }
    public void addContact(){
        gate=requireControl();
        person = new Person();location=new ArrayList<>();phone = new ArrayList<>();
        if (gate!=1){
            person.setName(name.getText().toString());
            person.setSurname(surname.getText().toString());
            person.seteMail(email.getText().toString());
            if (homeAddressLat.getText().toString().trim().equals("")&&homeAddressLng.getText().toString().trim().equals(""))
                location.add(new Location(Double.parseDouble(homeAddressLat.getText().toString()), Double.parseDouble(homeAddressLat.getText().toString()), LocationType.HOME));
            if (workAddressLat.getText().toString().trim().equals("")&&workAddressLng.getText().toString().trim().equals(""))
                location.add(new Location(Double.parseDouble(workAddressLat.getText().toString()), Double.parseDouble(workAddressLng.getText().toString()), LocationType.WORK));
            person.setLocation(location);
            if (homePhone.getText().toString().trim().equals(""))
                phone.add(new Phone(homePhone.getText().toString(), PhoneType.HOME));
            if (workPhone.getText().toString().trim().equals(""))
                phone.add(new Phone(workPhone.getText().toString(), PhoneType.WORK));
            if (mobilePhone.getText().toString().trim().equals(""))
                phone.add(new Phone(mobilePhone.getText().toString(), PhoneType.MOBILE));
            person.setPhone(phone);
            personLab = new PersonLab(getApplicationContext());
            personLab.addContact(person);
        }
    }

    public int requireControl(){
        gate=0;
        if (name.getText().toString().trim().equals("")){
            name.setError("Name is required!");
            gate=1;
        }
        if (surname.getText().toString().trim().equals("")){
            name.setError("Surname is required!");
            gate=1;
        }
        if (homePhone.getText().toString().trim().equals("")||workPhone.getText().toString().trim().equals("")){
            gate=1;
            if (homePhone.getText().toString().trim().equals(""))
                homePhone.setError("Home Phone is required!");
            else
                workPhone.setError("Work Phone is required!");
        }
        return gate;
    }

    public void controllerInit(){
        name = (EditText) findViewById(R.id.editTextName);
        surname=(EditText) findViewById(R.id.editTextSurname);
        email=(EditText) findViewById(R.id.editTextEmail);
        homePhone=(EditText) findViewById(R.id.editTextHomePhone);
        mobilePhone=(EditText) findViewById(R.id.editTextMobilePhone);
        workPhone=(EditText) findViewById(R.id.editTextWorkPhone);
        homeAddressLat=(EditText) findViewById(R.id.editTextHomeAddressLat);
        workAddressLat = (EditText) findViewById(R.id.editTextWorkAddressLat);
        homeAddressLng=(EditText) findViewById(R.id.editTextHomeAddressLng);
        workAddressLng = (EditText) findViewById(R.id.editTextWorkAddressLng);
        addButton = (Button) findViewById(R.id.addButton);


    }
}
