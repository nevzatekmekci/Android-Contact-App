package com.example.nevzat.semesterproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.example.nevzat.semesterproject.models.ActivityStatistic;
import com.example.nevzat.semesterproject.models.Location;
import com.example.nevzat.semesterproject.models.LocationType;
import com.example.nevzat.semesterproject.models.Person;
import com.example.nevzat.semesterproject.models.Phone;
import com.example.nevzat.semesterproject.models.PhoneType;

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
        ContentValues statisticValues = getContentStatisticValues(person);
        database.update(ContactDBSchema.PersonTable.TABLE_NAME, personValues, ContactDBSchema.PersonTable.Cols.PID + "=?", new String[]{pid});
        database.update(ContactDBSchema.PhoneTable.TABLE_NAME, phoneValues, ContactDBSchema.PhoneTable.Cols.PID + "=?", new String[]{pid});
        database.update(ContactDBSchema.LocationTable.TABLE_NAME, locationValues, ContactDBSchema.LocationTable.Cols.PID + "=?", new String[]{pid});
        database.update(ContactDBSchema.ActivityStatisticTable.TABLE_NAME, statisticValues, ContactDBSchema.ActivityStatisticTable.Cols.PID + "=?", new String[]{pid});
    }
    public void addContact(Person person){
        addPerson(person);
        addPhone(person);
        addLocation(person);
        addStatistic(person);
    }
    public void addStatistic(Person person){
        ContentValues statisticValues = getContentStatisticValues(person);
        database.insert(ContactDBSchema.ActivityStatisticTable.TABLE_NAME, null, statisticValues);
    }
    public void addPhone(Person person){
        ContentValues phoneValues = getContentPhoneValues(person);
        database.insert(ContactDBSchema.PhoneTable.TABLE_NAME, null,phoneValues);
    }
    public void addLocation(Person person){
        ContentValues locationValues = getContentLocationValues(person);
        database.insert(ContactDBSchema.LocationTable.TABLE_NAME, null, locationValues);
    }
    public void addPerson(Person person){
        ContentValues personValues = getContentPersonValues(person);
        database.insert(ContactDBSchema.PersonTable.TABLE_NAME, null, personValues);
        String whereClause = "name = ? and surname = ?";
        String[] whereArgs = new String[] {
                person.getName(),
                person.getSurname()
        };
        ContactCursorWrapper ccw=  queryPersons(whereClause, whereArgs);
        ccw.moveToFirst();
        person.setPid(getPerson(ccw).getPid());
    }

    public static ContentValues getContentPersonValues(Person person){
        ContentValues values = new ContentValues();
        if (person!=null){
            values.put(ContactDBSchema.PersonTable.Cols.NAME, person.getName().toString());
            values.put(ContactDBSchema.PersonTable.Cols.SURNAME, person.getSurname().toString());
            values.put(ContactDBSchema.PersonTable.Cols.EMAIL, person.geteMail().toString());
            return values;
        }
        return null;

    }
    public static ContentValues getContentStatisticValues(Person person){
        ContentValues values = new ContentValues();
        if(person.getStatistic()!=null){
            values.put(ContactDBSchema.ActivityStatisticTable.Cols.PID, person.getPid());
            values.put(ContactDBSchema.ActivityStatisticTable.Cols.MISSINGCALLS, person.getStatistic().getMissingCalls());
            values.put(ContactDBSchema.ActivityStatisticTable.Cols.RECIEVEDMESSAGES, person.getStatistic().getReceivedMessages());
            values.put(ContactDBSchema.ActivityStatisticTable.Cols.SENTMESSAGES, person.getStatistic().getSentMessages());
            values.put(ContactDBSchema.ActivityStatisticTable.Cols.INCOMINGCALLSNUMBER, person.getStatistic().getIncomingCallsNumber());
            values.put(ContactDBSchema.ActivityStatisticTable.Cols.OUTGOINGCALLSNUMBER, person.getStatistic().getOutgoingCallsNumber());
            values.put(ContactDBSchema.ActivityStatisticTable.Cols.INCOMINGCALLSDURATION, person.getStatistic().getIncomingCallsDuration());
            values.put(ContactDBSchema.ActivityStatisticTable.Cols.OUTGOINGCALLSDURATION, person.getStatistic().getOutgoingCallsDuration());
            return values;
        }
        return null;

    }

    public static ContentValues getContentPhoneValues(Person person) {
        ContentValues values = new ContentValues();
        if (person.getPhone()!=null){
            for (int i = 0; i < person.getPhone().size(); i++) {
                values.put(ContactDBSchema.PhoneTable.Cols.NUMBER, person.getPhone().get(i).getPhoneNumber().toString());
                values.put(ContactDBSchema.PhoneTable.Cols.TYPE, person.getPhone().get(i).getPhoneType().toString());
                values.put(ContactDBSchema.PhoneTable.Cols.PID, person.getPid());
            }
            return values;
        }
        return null;
    }

    public static ContentValues getContentLocationValues(Person person){
        ContentValues values = new ContentValues();
        if (person.getLocation()!=null){
            for (int i=0;i< person.getLocation().size();i++){
                values.put(ContactDBSchema.LocationTable.Cols.TYPE, person.getLocation().get(i).getLocationType().toString());
                values.put(ContactDBSchema.LocationTable.Cols.PID, person.getPid());
                values.put(ContactDBSchema.LocationTable.Cols.LATITUDE, person.getLocation().get(i).getLat());
                values.put(ContactDBSchema.LocationTable.Cols.LONGITUDE, person.getLocation().get(i).getLng());
            }
            return values;
        }
        return null;

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
            String uuid = getString(getColumnIndex(ContactDBSchema.PersonTable.Cols.PID));
            String name = getString(getColumnIndex(ContactDBSchema.PersonTable.Cols.NAME));
            String surname = getString(getColumnIndex(ContactDBSchema.PersonTable.Cols.SURNAME));
            String email = getString(getColumnIndex(ContactDBSchema.PersonTable.Cols.EMAIL));
*/
        Person person = new Person();
        person.setPid(uuid);
        person.setName(name);
        person.setSurname(surname);
        person.seteMail(email);
        return person;

    }

    public Location getLocation(ContactCursorWrapper cursorWrapper){

        Location location = new Location();
        String lType = cursorWrapper.getString(3);
        double lat = cursorWrapper.getDouble(1);
        double lng = cursorWrapper.getDouble(2);
        LocationType type=  LocationType.valueOf(lType);
        location.setLat(lat);
        location.setLng(lng);
        location.setLocationType(type);
        return location;
    }

    public ActivityStatistic getStatistic(ContactCursorWrapper cursorWrapper){

        ActivityStatistic statistic = new ActivityStatistic();
        if (cursorWrapper.getCount()!=0){
            int missingCalls = cursorWrapper.getInt(1);
            int sentMessages = cursorWrapper.getInt(2);
            int receivedMessages = cursorWrapper.getInt(3);
            int incomingCallsNumber = cursorWrapper.getInt(4);
            int outgoingCallsNumber = cursorWrapper.getInt(5);
            int incomingCallsDuration=cursorWrapper.getInt(6);
            int outgoingCallsDuration=cursorWrapper.getInt(7);



            statistic.setMissingCalls(missingCalls);
            statistic.setSentMessages(sentMessages);
            statistic.setReceivedMessages(receivedMessages);
            statistic.setIncomingCallsNumber(incomingCallsNumber);
            statistic.setOutgoingCallsNumber(outgoingCallsNumber);
            statistic.setIncomingCallsDuration(incomingCallsDuration);
            statistic.setOutgoingCallsDuration(outgoingCallsDuration);
            return statistic;
        }
        return null;

    }

    public Phone getPhone(ContactCursorWrapper cursorWrapper){

        Phone phone = new Phone();
        String pType = cursorWrapper.getString(2);
        String number = cursorWrapper.getString(1);
        PhoneType type=  PhoneType.valueOf(pType);
        phone.setPhoneNumber(number);
        phone.setPhoneType(type);
        return phone;
    }

    public Person getContact(ContactCursorWrapper cursorWrapper){
        Person person;
        ContactCursorWrapper ccw;
        ArrayList<Location> locations = new ArrayList<>();
        ArrayList<Phone> phones = new ArrayList<>();

        person =  getPerson(cursorWrapper);
        String whereClause = "pid = ?";
        String[] whereArgs = new String[] {
                person.getPid()
        };

        ccw = queryPhones(whereClause, whereArgs);

        try{
            ccw.moveToFirst();
            while (!ccw.isAfterLast()){
                phones.add(getPhone(ccw));
                ccw.moveToNext();
            }
        }
        finally {
            ccw.close();
        }

        ccw = queryLocations(whereClause, whereArgs);
        try{
            ccw.moveToFirst();
            while (!ccw.isAfterLast()){
               locations.add(getLocation(ccw));
                ccw.moveToNext();
            }
        }
        finally {
            ccw.close();
        }

        ccw = queryStatistics(whereClause, whereArgs);
        try{
            ccw.moveToFirst();
            person.setStatistic(getStatistic(ccw));
        }
        finally {
            ccw.close();
        }
        person.setLocation(locations);
        person.setPhone(phones);
        return person;

    }

    public ContactCursorWrapper queryPersons(String whereClause,String [] whereArgs){
        Cursor cursor = database.query(ContactDBSchema.PersonTable.TABLE_NAME, null, whereClause, whereArgs, null, null, null);
        return new ContactCursorWrapper(cursor);
    }
    public ContactCursorWrapper queryPhones(String whereClause,String [] whereArgs){
        Cursor cursor = database.query(ContactDBSchema.PhoneTable.TABLE_NAME, null, whereClause, whereArgs, null, null, null);
        return new ContactCursorWrapper(cursor);
    }
    public ContactCursorWrapper queryLocations(String whereClause,String [] whereArgs){
        Cursor cursor = database.query(ContactDBSchema.LocationTable.TABLE_NAME, null, whereClause, whereArgs, null, null, null);
        return new ContactCursorWrapper(cursor);
    }
    public ContactCursorWrapper queryStatistics(String whereClause,String [] whereArgs){
        Cursor cursor = database.query(ContactDBSchema.ActivityStatisticTable.TABLE_NAME, null, whereClause, whereArgs, null, null, null);
        return new ContactCursorWrapper(cursor);
    }

    public List<Person> getContacts(){
        ArrayList<Person> contacts = new ArrayList<Person>();
        ContactCursorWrapper ccw = queryPersons(null, null);

        try{
            ccw.moveToFirst();
            while(!ccw.isAfterLast()){
                contacts.add(getContact(ccw));
                ccw.moveToNext();
            }
        }
        finally {
            ccw.close();
        }
        return contacts;
    }





}