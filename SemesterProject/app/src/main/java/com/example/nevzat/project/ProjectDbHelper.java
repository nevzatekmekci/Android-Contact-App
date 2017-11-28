package com.example.nevzat.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ProjectDbHelper extends SQLiteOpenHelper {

    private static final int VERSION=1;
    private static final String DATABASE_NAME ="semesterProject.db";

    public ProjectDbHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ ContactDBSchema.PersonTable.TABLE_NAME+"("
                        + ContactDBSchema.PersonTable.Cols.PID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                        + ContactDBSchema.PersonTable.Cols.NAME+" TEXT NOT NULL,"
                        + ContactDBSchema.PersonTable.Cols.SURNAME+ " TEXT NOT NULL,"
                        + ContactDBSchema.PersonTable.Cols.EMAIL+" TEXT, "
                        //+"PRIMARY KEY(NAME, SURNAME), "
                        +"CONSTRAINT ID UNIQUE (NAME,SURNAME))"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ ContactDBSchema.LocationTable.TABLE_NAME+"("
                        + ContactDBSchema.LocationTable.Cols.LID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                        + ContactDBSchema.LocationTable.Cols.LATITUDE+" DOUBLE NOT NULL,"
                        + ContactDBSchema.LocationTable.Cols.LONGITUDE+ " DOUBLE NOT NULL,"
                        + ContactDBSchema.LocationTable.Cols.TYPE+" TEXT NOT NULL,"
                        + ContactDBSchema.LocationTable.Cols.PID+ " INTEGER NOT NULL,"
                        +"FOREIGN KEY(PID) REFERENCES PersonTable(PID) ON DELETE CASCADE)"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ ContactDBSchema.PhoneTable.TABLE_NAME+"("
                        + ContactDBSchema.PhoneTable.Cols.PHID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                        + ContactDBSchema.PhoneTable.Cols.NUMBER+" TEXT NOT NULL,"
                        + ContactDBSchema.PhoneTable.Cols.TYPE+ " TEXT NOT NULL,"
                        + ContactDBSchema.PhoneTable.Cols.PID+ " INTEGER NOT NULL,"
                        +"FOREIGN KEY(PID) REFERENCES PersonTable(PID) ON DELETE CASCADE)"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ ContactDBSchema.ActivityStatisticTable.TABLE_NAME+"("
                        + ContactDBSchema.ActivityStatisticTable.Cols.ASID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                        + ContactDBSchema.ActivityStatisticTable.Cols.MISSINGCALLS+" INTEGER DEFAULT 0,"
                        + ContactDBSchema.ActivityStatisticTable.Cols.SENTMESSAGES+ " INTEGER DEFAULT 0,"
                        + ContactDBSchema.ActivityStatisticTable.Cols.RECIEVEDMESSAGES+ " INTEGER DEFAULT 0,"
                        + ContactDBSchema.ActivityStatisticTable.Cols.INCOMINGCALLSNUMBER+ " INTEGER DEFAULT 0,"
                        + ContactDBSchema.ActivityStatisticTable.Cols.OUTGOINGCALLSNUMBER+ " INTEGER DEFAULT 0,"
                        + ContactDBSchema.ActivityStatisticTable.Cols.INCOMINGCALLSDURATION+ " INTEGER DEFAULT 0,"
                        + ContactDBSchema.ActivityStatisticTable.Cols.OUTGOINGCALLSDURATION+" INTEGER DEFAULT 0,"
                        + ContactDBSchema.ActivityStatisticTable.Cols.PID+ " INTEGER NOT NULL,"
                        +"FOREIGN KEY(PID) REFERENCES PersonTable(PID) ON DELETE CASCADE)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ ContactDBSchema.PersonTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ ContactDBSchema.PhoneTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ ContactDBSchema.ActivityStatisticTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ ContactDBSchema.LocationTable.TABLE_NAME);
        onCreate(db);
    }
}
