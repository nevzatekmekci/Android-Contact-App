package com.example.nevzat.semesterproject.activities;


import com.example.nevzat.semesterproject.PersonLab;
import com.example.nevzat.semesterproject.ProjectDbHelper;
import com.example.nevzat.semesterproject.R;
import com.example.nevzat.semesterproject.adapters.SerialAdapter;
import com.example.nevzat.semesterproject.models.ActivityStatistic;
import com.example.nevzat.semesterproject.models.Location;
import com.example.nevzat.semesterproject.models.LocationType;
import com.example.nevzat.semesterproject.models.Person;
import com.example.nevzat.semesterproject.models.Phone;
import com.example.nevzat.semesterproject.models.PhoneType;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ProjectDbHelper dbHelper;
    public PersonLab personLab;
    public SerialAdapter dataAdapter;
    Person person;
    Button callButton,smsButton;
    ArrayList<Location> locations;
    ArrayList<Phone> phones;
    ActivityStatistic statistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new ProjectDbHelper(this);
        personLab = new PersonLab(getApplicationContext());

        //Clean all data
        personLab.deleteAllContacts();
        phones = new ArrayList<>();
        phones.add(new Phone("12344", PhoneType.HOME));
        locations = new ArrayList<>();
        locations.add(new Location(22.2, 22.3, LocationType.WORK));
        statistic = new ActivityStatistic();

        personLab.addContact(new Person("Nevzat", "Ekmekçi", phones, "n@com", locations, statistic));
        personLab.addContact(new Person("Mehmet", "Ekmekçi", phones, "a@com", locations, statistic));
        personLab.addContact(new Person("Vildan", "Ekmekçi", phones, "b@com", locations, statistic));
        personLab.addContact(new Person("Merve", "Ekmekçi", phones, "c@com", locations, statistic));
        personLab.addContact(new Person("Ahmet", "Ekmekçi", phones, "d@com", locations, statistic));

        ListView listView = (ListView) findViewById(R.id.listView);

        List<Person> personList = personLab.getContacts();
        dataAdapter = new SerialAdapter(MainActivity.this, personList);
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                listViewElementSelection(listView, view, position, id);
            }
        });

    }

    public void callButtonClick(View v) {
        View parentRow = (View) v.getParent();
        ListView listView = (ListView) parentRow.getParent();
        int position = listView.getPositionForView(parentRow);
        person = (Person) listView.getItemAtPosition(position);
        String uri = "tel:" + person.getPhone().get(0).getPhoneNumber().trim();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }
    public void smsButtonClick(View v) {
        View parentRow = (View) v.getParent();
        ListView listView = (ListView) parentRow.getParent();
        int position = listView.getPositionForView(parentRow);
        person = (Person) listView.getItemAtPosition(position);
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", "12125551212");
        smsIntent.putExtra("sms_body", "Body of Message");
        startActivity(smsIntent);
    }

    public void listViewElementSelection(AdapterView<?> listView, View view,
                                         int position, long id){
        // Get the cursor, positioned to the corresponding row in the result set
        person = (Person) listView.getItemAtPosition(position);
        Intent intent = new Intent(this,ContactActivity.class);
        intent.putExtra("pid", person.getPid());
        startActivity(intent);




        callButton = (Button) findViewById(R.id.callPhoneButton);
        smsButton = (Button) findViewById(R.id.smsButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callButtonClick(v);
            }
        });
        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsButtonClick(v);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}