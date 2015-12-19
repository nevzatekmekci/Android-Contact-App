package com.example.nevzat.semesterproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.example.nevzat.semesterproject.models.Location;
import com.example.nevzat.semesterproject.models.Person;
import com.example.nevzat.semesterproject.models.Phone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nevzat on 18/12/15.
 */
public class PersonLab {
    Context context;
    SQLiteDatabase database;

    public PersonLab(Context context){
        this.context = context;
        this.database = new ProjectDbHelper(context).getWritableDatabase();
    }

    public void updatePerson(Person person){
        String pid = person.getPid().toString();
        ContentValues personValues = getContentPersonValues(person);
        ContentValues phoneValues = getContentPhoneValues(person);
        ContentValues locationValues = getContentLocationValues(person);
        database.update(ContactDBSchema.PersonTable.TABLE_NAME, personValues, ContactDBSchema.PersonTable.Cols.PID + "=?", new String[]{pid});
        database.update(ContactDBSchema.PhoneTable.TABLE_NAME, phoneValues, ContactDBSchema.PhoneTable.Cols.PID + "=?", new String[]{pid});
        database.update(ContactDBSchema.LocationTable.TABLE_NAME, locationValues, ContactDBSchema.LocationTable.Cols.PID + "=?", new String[]{pid});
    }
    public void addContact(Person person){
        ContentValues personValues = getContentPersonValues(person);
        ContentValues phoneValues = getContentPhoneValues(person);
        ContentValues locationValues = getContentLocationValues(person);
        database.insert(ContactDBSchema.PersonTable.TABLE_NAME, null, personValues);
        database.insert(ContactDBSchema.PhoneTable.TABLE_NAME, null, phoneValues);
        database.insert(ContactDBSchema.PhoneTable.TABLE_NAME, null, locationValues);
    }
    private static ContentValues getContentPersonValues(Person person){
        ContentValues values = new ContentValues();
        values.put(ContactDBSchema.PersonTable.Cols.PID, person.getPid().toString());
        values.put(ContactDBSchema.PersonTable.Cols.NAME, person.getName().toString());
        values.put(ContactDBSchema.PersonTable.Cols.SURNAME, person.getSurname().toString());
        values.put(ContactDBSchema.PersonTable.Cols.EMAIL, person.geteMail().toString());
        return values;
    }

    public static ContentValues getContentPhoneValues(Person person) {
        ContentValues values = new ContentValues();
        for (int i = 0; i < person.getPhone().size(); i++) {
            values.put(ContactDBSchema.PhoneTable.Cols.NUMBER, person.getPhone().get(i).getPhoneNumber().toString());
            values.put(ContactDBSchema.PhoneTable.Cols.TYPE, person.getPhone().get(i).getPhoneType().toString());
        }
        return values;
    }

    public static ContentValues getContentLocationValues(Person person){
        ContentValues values = new ContentValues();
        for (int i=0;i< person.getLocation().size();i++){
            values.put(ContactDBSchema.LocationTable.Cols.TYPE, person.getLocation().get(i).getLocationType().toString());
            values.put(ContactDBSchema.LocationTable.Cols.LATITUDE, person.getLocation().get(i).getLat());
            values.put(ContactDBSchema.LocationTable.Cols.LONGITUDE, person.getLocation().get(i).getLng());
        }
        return values;
    }

    public void deletePerson(String id){
        database.delete(ContactDBSchema.PersonTable.TABLE_NAME, ContactDBSchema.PersonTable.Cols.PID + " = ?",
                new String[]{String.valueOf(id)});
    }
    public boolean deleteAllContacts() {

        int doneDelete = 0;
        doneDelete = database.delete(ContactDBSchema.PersonTable.TABLE_NAME, null , null);
        return doneDelete > 0;
    }

    public class ContactCursorWrapper extends CursorWrapper {
        public ContactCursorWrapper(Cursor cursor) {
            super(cursor);
        }
    }
    public Person getPerson(ContactCursorWrapper cursorWrapper){
        String uuid = cursorWrapper.getString(0);
        String name = cursorWrapper.getString(1);
        String surname = cursorWrapper.getString(2);
        String email = cursorWrapper.getString(3);
        /*
        String uuid = getString(getColumnIndex(ContactDBSchema.ContactTable.Cols.UUID));
        String name = getString(getColumnIndex(ContactDBSchema.ContactTable.Cols.USERNAME));
        String surname = getString(getColumnIndex(ContactDBSchema.ContactTable.Cols.SURNAME));
        String phone = getString(getColumnIndex(ContactDBSchema.ContactTable.Cols.PHONE));
*/
        Person contact = new Person();
        contact.setPid(uuid);
        contact.setName(name);
        contact.setSurname(surname);
        contact.seteMail(email);
        return contact;

    }
    private ContactCursorWrapper queryCrimes(String whereClause,String [] whereArgs){
        Cursor cursor = database.query(ContactDBSchema.PersonTable.TABLE_NAME, null, whereClause, whereArgs, null, null, null);
        return new ContactCursorWrapper(cursor);
    }

    public List<Person> getContacts(){
        List<Person> contacts = new ArrayList<Person>();
        ContactCursorWrapper ccw = queryCrimes(null, null);

        try{
            ccw.moveToFirst();
            while(!ccw.isAfterLast()){
                contacts.add(getPerson(ccw));
                ccw.moveToNext();
            }
        }
        finally {
            ccw.close();
        }
        return contacts;
    }





}
