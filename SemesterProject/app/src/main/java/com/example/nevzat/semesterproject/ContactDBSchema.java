package com.example.nevzat.semesterproject;



public class ContactDBSchema {
    public static final class PersonTable{
        public static final String TABLE_NAME = "persons";
        public static final class Cols{
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String SURNAME = "surname";
            public static final String EMAIL = "email";
        }

    }
    public static final class ActivityStatisticTable{
        public static final String TABLE_NAME = "activities";
        public static final class Cols{
            public static final String ID = "id";
            public static final String MISSINGCALLS = "missing_calls";
            public static final String SENTMESSAGES = "sent_messages";
            public static final String RECIEVEDMESSAGES = "received_messages";
            public static final String INCOMINGCALLSNUMBER = "incoming_calls_number";
            public static final String OUTGOINGCALLSNUMBER = "outgoing_calls_number";
            public static final String INCOMINGCALLSDURATION = "incoming_calls_duration";
            public static final String OUTGOINGCALLSDURATION = "outgoing_calls_duration";
        }
    }

    public static final class PhoneTable{
        public static final String TABLE_NAME = "phone";
        public static final class Cols {
            public static final String ID = "id";
            public static final String NUMBER = "number";
            public static final String TYPE = "type";
        }
    }
    public static final class LocationTable{
        public static final String TABLE_NAME = "location";
        public static final class Cols {
            public static final String ID = "id";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
            public static final String TYPE = "type";
        }
    }
}
