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

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ProjectDbHelper dbHelper;
    public PersonLab personLab;
    public SerialAdapter dataAdapter;
    PersonLab.ContactCursorWrapper ccw,ccw2;

    Location location;
    Person person,person1;
    ArrayList<Location> locations;
    ArrayList<Phone> phones;
    Phone phone;
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
        locations.add(new Location(22.2,22.3, LocationType.WORK));
        statistic = new ActivityStatistic();

        personLab.addContact(new Person("Nevzat", "Ekmekçi", phones, "n@com", locations,statistic));
        personLab.addContact(new Person("Mehmet", "Ekmekçi", phones, "a@com", locations,statistic));
        personLab.addContact(new Person("Vildan", "Ekmekçi", phones, "b@com", locations,statistic));
        personLab.addContact(new Person("Merve", "Ekmekçi", phones, "c@com", locations,statistic));
        personLab.addContact(new Person("Ahmet", "Ekmekçi", phones, "d@com", locations,statistic));

        ListView listView = (ListView) findViewById(R.id.listView);

        List<Person> personList = personLab.getContacts();
        dataAdapter = new SerialAdapter(MainActivity.this,personList);
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                person = (Person) listView.getItemAtPosition(position);
                String whereClause = "pid = ?";
                String[] whereArgs = new String[] {
                        person.getPid()
                };
                ccw =  personLab.queryPersons(whereClause, whereArgs);
                ccw.moveToFirst();
                person1 = personLab.getContact(ccw) ;

                // Get the state's capital from this row in the database.
                String name = person1.getPhone().get(0).getPhoneNumber();
                Toast.makeText(getApplicationContext(),
                        name, Toast.LENGTH_SHORT).show();

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
