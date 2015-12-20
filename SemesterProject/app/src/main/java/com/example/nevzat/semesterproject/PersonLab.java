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
        ContentValues personValues = getContentPersonValues(person);
        ContentValues phoneValues = getContentPhoneValues(person);
        ContentValues locationValues = getContentLocationValues(person);
        ContentValues statisticValues = getContentStatisticValues(person);
        database.insert(ContactDBSchema.PersonTable.TABLE_NAME, null, personValues);
        database.insert(ContactDBSchema.PhoneTable.TABLE_NAME, null,     phoneValues);
        database.insert(ContactDBSchema.PhoneTable.TABLE_NAME, null, locationValues);
        database.insert(ContactDBSchema.PhoneTable.TABLE_NAME, null, statisticValues);
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

    public Location getLocation(ContactCursorWrapper cursorWrapper){

        Location location = new Location();
        String lType = cursorWrapper.getString(2);
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
        ArrayList<Location> locations = new ArrayList<Location>();
        ArrayList<Phone> phones = new ArrayList<Phone>();
        ContactCursorWrapper ccw = queryPersons(null, null);
        Person person;
        ActivityStatistic statistic;

        try{
            ccw.moveToFirst();
            while(!ccw.isAfterLast()){
                locations.clear();
                phones.clear();
                ContactCursorWrapper ccw2 = queryPhones(null, null);
                try{
                    ccw2.moveToFirst();
                    while (!ccw2.isAfterLast()){
                        phones.add(getPhone(ccw2));
                        ccw2.moveToNext();
                    }
                }
                finally {
                    ccw2.close();
                }

                ccw2 = queryLocations(null, null);
                try{
                    ccw2.moveToFirst();
                    while (!ccw2.isAfterLast()){
                        locations.add(getLocation(ccw2));
                        ccw2.moveToNext();
                    }
                }
                finally {
                    ccw2.close();
                }

                ccw2 = queryStatistics(null, null);
                try{
                    ccw2.moveToFirst();
                    statistic = getStatistic(ccw2);
                }
                finally {
                    ccw2.close();
                }


                person = getPerson(ccw);
                person.setLocation(locations);
                person.setPhone(phones);
                person.setStatistic(statistic);
                contacts.add(person);
                ccw.moveToNext();
            }
        }
        finally {
            ccw.close();
        }
        return contacts;
    }





}
