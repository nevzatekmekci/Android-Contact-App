package com.example.nevzat.semesterproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.nevzat.semesterproject.ContactDBSchema.LocationTable;
import com.example.nevzat.semesterproject.ContactDBSchema.PhoneTable;
import com.example.nevzat.semesterproject.ContactDBSchema.ActivityStatisticTable;
import com.example.nevzat.semesterproject.ContactDBSchema.PersonTable;


public class ProjectDbHelper extends SQLiteOpenHelper {

    private static final int VERSION=1;
    private static final String DATABASE_NAME ="semesterProject.db";

    public ProjectDbHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ PersonTable.TABLE_NAME+"("
                        +PersonTable.Cols.PID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        +PersonTable.Cols.NAME+" TEXT,"
                        +PersonTable.Cols.SURNAME+ " TEXT,"
                        +PersonTable.Cols.EMAIL+" TEXT"+")"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ LocationTable.TABLE_NAME+"("
                        +LocationTable.Cols.LID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        +LocationTable.Cols.LATITUDE+" DOUBLE,"
                        +LocationTable.Cols.LONGITUDE+ " DOUBLE,"
                        +LocationTable.Cols.TYPE+" TEXT"
                        +LocationTable.Cols.PID+ " INTEGER FOREIGN KEY"+")"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ PhoneTable.TABLE_NAME+"("
                        +PhoneTable.Cols.PHID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                        +PhoneTable.Cols.NUMBER+" TEXT,"
                        +PhoneTable.Cols.TYPE+ " TEXT"
                        +PhoneTable.Cols.PID+ " INTEGER FOREIGN KEY"+")"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ ActivityStatisticTable.TABLE_NAME+"("
                        +ActivityStatisticTable.Cols.ASID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        +ActivityStatisticTable.Cols.MISSINGCALLS+" TEXT,"
                        +ActivityStatisticTable.Cols.SENTMESSAGES+ " TEXT,"
                        +ActivityStatisticTable.Cols.RECIEVEDMESSAGES+ " TEXT,"
                        +ActivityStatisticTable.Cols.INCOMINGCALLSNUMBER+ " TEXT,"
                        +ActivityStatisticTable.Cols.OUTGOINGCALLSNUMBER+ " TEXT,"
                        +ActivityStatisticTable.Cols.INCOMINGCALLSDURATION+ " TEXT,"
                        +ActivityStatisticTable.Cols.OUTGOINGCALLSDURATION+" TEXT"
                        +ActivityStatisticTable.Cols.PID+ " INTEGER FOREIGN KEY"+")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+PersonTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+PhoneTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ActivityStatisticTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+LocationTable.TABLE_NAME);
        onCreate(db);
    }
}
