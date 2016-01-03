package com.example.nevzat.semesterproject.activities;

import android.content.Intent;
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

public class AddContactActivity extends AppCompatActivity {
    Button addButton,homeMapButton,workMapButton;
    Person person;
    ArrayList<Phone> phone;
    ArrayList<Location> location;
    ActivityStatistic statistics;
    PersonLab personLab;
    int gate=0;
    double homeLat,homeLng,workLat,workLng;
    EditText name, surname, email, homePhone, mobilePhone, workPhone, homeAddress, workAddress;
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

        homeMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                if (homeLat!=0&&homeLng!=0){
                    intent.putExtra("Lat",homeLat);
                    intent.putExtra("Lng",homeLng);

                }
                startActivity(intent);
            }
        });
        workMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                if (workLat!=0&&workLng!=0){
                    intent.putExtra("Lat",workLat);
                    intent.putExtra("Lng",workLng);

                }
                startActivity(intent);
            }
        });

    }
    public void addContact(){
        gate=requireControl();
        person = new Person();
        location=new ArrayList<>();
        phone = new ArrayList<>();
        statistics = new ActivityStatistic();
        if (gate!=1){
            person.setName(name.getText().toString());
            person.setSurname(surname.getText().toString());
            person.seteMail(email.getText().toString());
            if (!(homeAddress.getText().toString().trim().equals("")))
                location.add(new Location(homeLat,homeLng, LocationType.HOME));
            if (!(workAddress.getText().toString().trim().equals("")))
                location.add(new Location(workLat,workLng, LocationType.WORK));
            if (!(homePhone.getText().toString().trim().equals("")))
                phone.add(new Phone(homePhone.getText().toString(), PhoneType.HOME));
            if (!(workPhone.getText().toString().trim().equals("")))
                phone.add(new Phone(workPhone.getText().toString(), PhoneType.WORK));
            if (!(mobilePhone.getText().toString().trim().equals("")))
                phone.add(new Phone(mobilePhone.getText().toString(), PhoneType.MOBILE));
            person.setPhone(phone);
            person.setLocation(location);
            person.setStatistic(statistics);
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
        if (homePhone.getText().toString().trim().equals("")&&
                workPhone.getText().toString().trim().equals("")&&mobilePhone.getText().toString().trim().equals("")){
            gate=1;
            if (homePhone.getText().toString().trim().equals(""))
                homePhone.setError("Home Phone is required!");
            else if (workPhone.getText().toString().trim().equals(""))
                workPhone.setError("Work Phone is required!");
            else
                mobilePhone.setError("Mobile Phone is required!");
        }
        if (homeAddress.getText().toString().trim().equals("")&&
                workAddress.getText().toString().trim().equals("")){
            gate=1;
            if (homeAddress.getText().toString().trim().equals(""))
                homePhone.setError("Home Address is required!");
            else
                workAddress.setError("Work Address is required!");
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
        homeAddress=(EditText) findViewById(R.id.editTextHomeAddress);
        workAddress = (EditText) findViewById(R.id.editTextWorkAddress);
        addButton = (Button) findViewById(R.id.deleteButton);
        homeMapButton = (Button) findViewById(R.id.buttonHomeMap);
        workMapButton = (Button) findViewById(R.id.buttonWorkMap);


    }
}
