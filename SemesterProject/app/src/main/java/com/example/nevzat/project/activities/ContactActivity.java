package com.example.nevzat.project.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nevzat.project.PersonLab;
import com.example.nevzat.semesterproject.R;
import com.example.nevzat.project.models.Location;
import com.example.nevzat.project.models.LocationType;
import com.example.nevzat.project.models.Person;
import com.example.nevzat.project.models.Phone;
import com.example.nevzat.project.models.PhoneType;

import java.util.ArrayList;


public class ContactActivity extends AppCompatActivity {
    public PersonLab personLab;
    PersonLab.ContactCursorWrapper ccw;
    Person person;
    ArrayList<Phone> phone;
    ArrayList<Location> location;
    int gate;
    String pid;
    double homeLat,homeLng,workLat,workLng;
    EditText name, surname, email, homePhone, mobilePhone, workPhone, homeAddress, workAddress;
    Button editButton, deleteButton, callMobileButton,callWorkButton, callHomeButton, smsButton,workMapButton,homeMapButton;
    ArrayList<EditText> editTextArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        homeLat=41.0;homeLng=28.56;workLat=41.0;workLng=28.56;
        pid = intent.getStringExtra("pid");
        personLab = new PersonLab(getApplicationContext());
        String whereClause = "pid = ?";
        String[] whereArgs = new String[]{pid};
        personLab = new PersonLab(getApplicationContext());
        ccw = personLab.queryPersons(whereClause, whereArgs);
        ccw.moveToFirst();
        person = personLab.getContact(ccw);
        editTextArrayList = new ArrayList<>();
        controllerInit();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = requireControl();
                if (value==0)
                    saveContact(pid);
            }
        });
        homeMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("Latitude",homeLat);
                intent.putExtra("Longitude",homeLng);
                startActivityForResult(intent, 1);
            }
        });
        workMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("Latitude",workLat);
                intent.putExtra("Longitude",workLng);
                startActivityForResult(intent, 2);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact(pid);
            }
        });
        callWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!workPhone.getText().toString().trim().equals(""))
                    callButtonClick(workPhone.getText().toString(),pid);
                else
                    Toast.makeText(getApplicationContext(),"Work Number not found",Toast.LENGTH_LONG).show();
            }
        });
        callMobileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mobilePhone.getText().toString().trim().equals(""))
                    callButtonClick(mobilePhone.getText().toString(),pid);
                else
                    Toast.makeText(getApplicationContext(),"Mobile Number not found",Toast.LENGTH_LONG).show();
            }
        });
        callHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!homePhone.getText().toString().trim().equals(""))
                    callButtonClick(homePhone.getText().toString(),pid);
                else
                    Toast.makeText(getApplicationContext(),"Home Number not found",Toast.LENGTH_LONG).show();
            }
        });
        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsButtonClick();
            }
        });


    }

    public void callButtonClick(String phone,String pid) {
        String uri = "tel:" + phone;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        startActivity(intent);
    }

    public void smsButtonClick() {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
        smsIntent.setType("vnd.android-dir/mms-sms");
        String uri = "sms:" + person.getPhone().get(0).getPhoneNumber().trim();
        smsIntent.setData(Uri.parse(uri));
        startActivity(smsIntent);
    }


    public void saveContact(String pid){

        Toast.makeText(getApplicationContext(),"Contact saved",Toast.LENGTH_LONG).show();
        personLab.deleteLocation(pid);
        personLab.deletePhone(pid);
        location = new ArrayList<>();
        phone = new ArrayList<>();
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
        personLab = new PersonLab(getApplicationContext());
        personLab.updatePerson(person);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) { //x is the requestCode you chose above
            if(resultCode == Activity.RESULT_OK){
                homeLat = Double.parseDouble(data.getStringExtra("latitude"));
                homeLng = Double.parseDouble(data.getStringExtra("longitude"));
                homeAddress.setText(homeLat+" "+homeLng);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //ActivityB was closed before you put any results
            }
        }
        else if (requestCode==2){
            if(resultCode == Activity.RESULT_OK){
                workLat = Double.parseDouble(data.getStringExtra("latitude"));
                workLng = Double.parseDouble(data.getStringExtra("longitude"));
                workAddress.setText(workLat+" "+workLng);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //ActivityB was closed before you put any results
            }
        }
    }

    public int requireControl(){
        gate=0;
        if (name.getText().toString().trim().equals("")){
            name.setError("Name is required!");
            gate=1;
        }
        if (surname.getText().toString().trim().equals("")){
            surname.setError("Surname is required!");
            gate=1;
        }
        if (homePhone.getText().toString().trim().equals("")&&
                workPhone.getText().toString().trim().equals("")&&mobilePhone.getText().toString().trim().equals("")){
            gate=1;
            if (mobilePhone.getText().toString().trim().equals(""))
                mobilePhone.setError("Home Phone is required!");
            else if (homePhone.getText().toString().trim().equals(""))
                homePhone.setError("Work Phone is required!");
            else
                workPhone.setError("Mobile Phone is required!");
        }
        if (homeAddress.getText().toString().trim().equals("")&&
                workAddress.getText().toString().trim().equals("")){
            gate=1;
            if (homeAddress.getText().toString().trim().equals(""))
                homeAddress.setError("Home Address is required!");
            else
                workAddress.setError("Work Address is required!");
        }
        return gate;
    }


    public void deleteContact(String pid){
        personLab.deletePerson(pid);
        Toast.makeText(getApplicationContext(), person.getName() + " "
                + person.getSurname() + " Deleted", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

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
        callHomeButton = (Button) findViewById(R.id.buttonCallHome);
        callMobileButton = (Button) findViewById(R.id.buttonCallMobile);
        callWorkButton = (Button) findViewById(R.id.buttonCallWork);
        smsButton = (Button) findViewById(R.id.smsButton);
        workMapButton = (Button) findViewById(R.id.buttonWorkMap);
        homeMapButton = (Button) findViewById(R.id.buttonHomeMap);

        editTextArrayList.add(name);
        editTextArrayList.add(surname);
        editTextArrayList.add(email);
        editTextArrayList.add(homePhone);
        editTextArrayList.add(workPhone);
        editTextArrayList.add(mobilePhone);
        editTextArrayList.add(homeAddress);
        editTextArrayList.add(homeAddress);
        /*
        for (EditText editText:editTextArrayList){
            editText.setClickable(false);
            editText.setFocusable(false);
        }*/
        name.setText(person.getName());
        surname.setText(person.getSurname());
        email.setText(person.geteMail());


        for(Phone phone: person.getPhone()){
            if(phone.getPhoneType().equals(PhoneType.HOME))
                homePhone.setText(phone.getPhoneNumber());
            else if(phone.getPhoneType().equals(PhoneType.WORK))
                workPhone.setText(phone.getPhoneNumber());
            else
                mobilePhone.setText(phone.getPhoneNumber());
        }

        for(Location location:person.getLocation()){
            if(location.getLocationType().equals(LocationType.HOME)){
                homeLat=location.getLat();
                homeLng=location.getLng();
                homeAddress.setText(homeLat+" "+ homeLng);
            }
            else{
                workLat=location.getLat();
                workLng=location.getLng();
                homeAddress.setText(workLat+" "+ workLng);
            }
        }
    }
}
