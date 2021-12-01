package com.example.soundrecording.dataBase;

public class ConstantsDb {
    public static final int VERSION = 1;
    public static final String LIST_ITEM_INTENT = "list_item_intent";
    public static final String DB_NAME = "audioRecord.db";
    public static final String TABLE_NAME = "audioRecords";
    public static final String _ID = "_id";
    public static final String RECORDING_NAME = "recording_name";
    public static final String FILE_PATH = "file_path";
    public static final String RECORDING_LENGTH = "recording_length";
    public static final String TIME_ADDED = "time_added";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY, " +
                    RECORDING_NAME + " TEXT," + FILE_PATH + " TEXT," + RECORDING_LENGTH + " INTEGER," +
                    TIME_ADDED + " INTEGER)";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
