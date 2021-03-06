package com.example.nevzat.project.activities;


import com.example.nevzat.project.PersonLab;
import com.example.nevzat.project.ProjectDbHelper;
import com.example.nevzat.project.adapters.SerialAdapter;
import com.example.nevzat.project.models.ActivityStatistic;
import com.example.nevzat.project.models.Location;
import com.example.nevzat.project.models.Person;
import com.example.nevzat.project.models.Phone;
import com.example.nevzat.semesterproject.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ProjectDbHelper dbHelper;
    public PersonLab personLab;
    public SerialAdapter dataAdapter;
    Person person;
    Button addButton;
    ListView listView;
    ArrayList<Location> locations;
    ArrayList<Phone> phones;
    ActivityStatistic statistic;
    EditText filteredEditText;
    List<Person> personList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new ProjectDbHelper(this);
        personLab = new PersonLab(getApplicationContext());

        //Clean all data
        /*
        personLab.deleteAllContacts();
        phones = new ArrayList<>();
        phones.add(new Phone("05545824594", PhoneType.MOBILE));
        locations = new ArrayList<>();
        locations.add(new Location(22.2, 22.3, LocationType.WORK));
        statistic = new ActivityStatistic();


        personLab.addContact(new Person("Nevzat", "Ekmekçi", phones, "n@com", locations, statistic));
        personLab.addContact(new Person("Mehmet", "Ekmekçi", phones, "a@com", locations, statistic));
        personLab.addContact(new Person("Vildan", "Ekmekçi", phones, "b@com", locations, statistic));
        personLab.addContact(new Person("Merve", "Ekmekçi", phones, "c@com", locations, statistic));
        personLab.addContact(new Person("Ahmet", "Ekmekçi", phones, "d@com", locations, statistic));
*/
        listView = (ListView) findViewById(R.id.listView);
        filteredEditText = (EditText) findViewById(R.id.filterEditText);
        addButton = (Button) findViewById(R.id.addButton);
        personList = personLab.getContacts(null,null);
        dataAdapter = new SerialAdapter(MainActivity.this, personList);
        listView.setAdapter(dataAdapter);


        filteredEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String whereClause = "name LIKE ? OR surname LIKE ?";
                String[] whereArgs = new String[]{
                        filteredEditText.getText().toString() + '%',
                        filteredEditText.getText().toString() + '%'
                };
                personList.clear();
                personList = personLab.getContacts(whereClause, whereArgs);
                dataAdapter = new SerialAdapter(MainActivity.this, personList);
                listView.setAdapter(dataAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        filteredEditText.removeTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String whereClause = "name LIKE ? OR surname LIKE ?";
                String[] whereArgs = new String[]{
                        filteredEditText.getText().toString() + '%',
                        filteredEditText.getText().toString() + '%'
                };
                personList.clear();
                personList = personLab.getContacts(whereClause, whereArgs);
                dataAdapter = new SerialAdapter(MainActivity.this, personList);
                listView.setAdapter(dataAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddContactActivity.class);
                startActivity(intent);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                listViewElementSelection(listView, view, position, id);
            }
        });

    }

    public void listViewElementSelection(AdapterView<?> listView, View view,
                                         int position, long id){
        // Get the cursor, positioned to the corresponding row in the result set
        person = (Person) listView.getItemAtPosition(position);
        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra("pid", person.getPid());
        startActivity(intent);
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