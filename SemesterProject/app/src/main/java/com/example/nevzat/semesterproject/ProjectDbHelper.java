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
                        +PersonTable.Cols.PID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                        +PersonTable.Cols.NAME+" TEXT NOT NULL,"
                        +PersonTable.Cols.SURNAME+ " TEXT NOT NULL,"
                        +PersonTable.Cols.EMAIL+" TEXT, "
                        //+"PRIMARY KEY(NAME, SURNAME), "
                        +"CONSTRAINT ID UNIQUE (NAME,SURNAME))"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ LocationTable.TABLE_NAME+"("
                        +LocationTable.Cols.LID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                        +LocationTable.Cols.LATITUDE+" DOUBLE NOT NULL,"
                        +LocationTable.Cols.LONGITUDE+ " DOUBLE NOT NULL,"
                        +LocationTable.Cols.TYPE+" TEXT NOT NULL,"
                        +LocationTable.Cols.PID+ " INTEGER NOT NULL,"
                        +"FOREIGN KEY(PID) REFERENCES PersonTable(PID) ON DELETE CASCADE)"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ PhoneTable.TABLE_NAME+"("
                        +PhoneTable.Cols.PHID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                        +PhoneTable.Cols.NUMBER+" TEXT NOT NULL,"
                        +PhoneTable.Cols.TYPE+ " TEXT NOT NULL,"
                        +PhoneTable.Cols.PID+ " INTEGER NOT NULL,"
                        +"FOREIGN KEY(PID) REFERENCES PersonTable(PID) ON DELETE CASCADE)"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ ActivityStatisticTable.TABLE_NAME+"("
                        +ActivityStatisticTable.Cols.ASID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                        +ActivityStatisticTable.Cols.MISSINGCALLS+" INTEGER DEFAULT 0,"
                        +ActivityStatisticTable.Cols.SENTMESSAGES+ " INTEGER DEFAULT 0,"
                        +ActivityStatisticTable.Cols.RECIEVEDMESSAGES+ " INTEGER DEFAULT 0,"
                        +ActivityStatisticTable.Cols.INCOMINGCALLSNUMBER+ " INTEGER DEFAULT 0,"
                        +ActivityStatisticTable.Cols.OUTGOINGCALLSNUMBER+ " INTEGER DEFAULT 0,"
                        +ActivityStatisticTable.Cols.INCOMINGCALLSDURATION+ " INTEGER DEFAULT 0,"
                        +ActivityStatisticTable.Cols.OUTGOINGCALLSDURATION+" INTEGER DEFAULT 0,"
                        +ActivityStatisticTable.Cols.PID+ " INTEGER NOT NULL,"
                        +"FOREIGN KEY(PID) REFERENCES PersonTable(PID) ON DELETE CASCADE)"
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
