package com.example.soundrecording.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.soundrecording.adapters.ListAudio;

import java.util.ArrayList;
import java.util.List;

public class ManagerDb {

    private Context context;
    private HelperDb helper;
    private SQLiteDatabase database;

    public ManagerDb(Context context) {
        this.context = context;
        helper = new HelperDb(context);
    }

    public void openDb(){ helper.getReadableDatabase();}

    public void insertToDb(String recName, String filePath, int recordingLength, int addedTime){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantsDb.RECORDING_NAME, recName);
        contentValues.put(ConstantsDb.FILE_PATH, filePath);
        contentValues.put(ConstantsDb.RECORDING_LENGTH, recordingLength);
        contentValues.put(ConstantsDb.TIME_ADDED, addedTime);
        database.insert(ConstantsDb.DB_NAME, null, contentValues);
    }

    public void updateFromDb(String recName, String filePath, int recordingLength, int addedTime, int id){
        String selection = ConstantsDb._ID + "=" + id;
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantsDb.RECORDING_NAME, recName);
        contentValues.put(ConstantsDb.FILE_PATH, filePath);
        contentValues.put(ConstantsDb.RECORDING_LENGTH, recordingLength);
        contentValues.put(ConstantsDb.TIME_ADDED, addedTime);
        database.update(ConstantsDb.DB_NAME, contentValues, selection, null);
    }

    public List<ListAudio> getFromDb(String searchText){
        List<ListAudio> listAudios = new ArrayList<>();
        String selection = ConstantsDb.RECORDING_NAME +  " like ?";
        Cursor cursor = database.query(ConstantsDb.TABLE_NAME, null, selection,
                new String[]{"%" + searchText + "%"}, null, null, null); // ПОИСК ПО ЗАГОЛОВКУ
        while (cursor.moveToNext()){
            ListAudio audio = new ListAudio();
            int _id = cursor.getInt(cursor.getColumnIndex(ConstantsDb._ID));
            String recName = cursor.getString(cursor.getColumnIndex(ConstantsDb.RECORDING_NAME));
            String filePath = cursor.getString(cursor.getColumnIndex(ConstantsDb.FILE_PATH));
            String recLength = cursor.getString(cursor.getColumnIndex(ConstantsDb.RECORDING_LENGTH));
            String timeAdded = cursor.getString(cursor.getColumnIndex(ConstantsDb.TIME_ADDED));
            audio.setId(_id);
            audio.setRecName(recName);
            audio.setFilePath(filePath);
            audio.setRecLength(recLength);
            audio.setAddedTime(timeAdded);
            listAudios.add(audio);
        }
        cursor.close();
        return listAudios;
    }

    public void deleteFromDb(int id){
        String selection = ConstantsDb._ID + "=" + id;
        database.delete(ConstantsDb.DB_NAME, selection, null);
    }

    public void closeDb(){helper.close();}
}
