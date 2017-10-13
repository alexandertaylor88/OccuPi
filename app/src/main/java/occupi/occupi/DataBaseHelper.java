package occupi.occupi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class DataBaseHelper {
    private DataBaseConn dbHelper;

    public DataBaseHelper(Context context) {
        dbHelper = new DataBaseConn(context);
    }

    public int insert(Room room) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Room.KEY_occupied,room.occupied);
        values.put(Room.KEY_type, room.type);
        long room_Id = db.insert(Room.TABLE, null, values);
        db.close();
        return (int) room_Id;
    }

    public void insertState() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        AppState state = new AppState();
        values.put(state.KEY_state, 0);
        values.put(state.KEY_ID, 1);
        db.insert(state.TABLE, null, values);
        db.close();
    }

    public void delete(int room_Id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Room.TABLE, Room.KEY_ID + "= ?", new String[] { String.valueOf(room_Id) });
        db.close();
    }

    public void updateRoom(Room room) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Room.KEY_occupied,room.occupied);
        values.put(Room.KEY_type, room.type);
        db.update(Room.TABLE, values, Room.KEY_ID + "= ?", new String[] { String.valueOf(room.room_ID) });
        db.close();
    }

    public void updateOccupancy(int floor, byte[] roomArray) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        int roomNum = 1;
        int mask;
        for (int byteCount = 0; byteCount < roomArray.length; byteCount++) {
            for (int bitCount = 0; bitCount < 8; bitCount++) {
                mask = 1 << bitCount;
                  values.put(Room.KEY_occupied, (((roomArray[byteCount] & mask) == 0) ? 0 : 1));
                  db.update(Room.TABLE, values, Room.KEY_ID + "= ?", new String[] { floor + String.format("%02d", roomNum++) });
            }
        }
        db.close();
    }

    public ArrayList<HashMap<String, String>> getRoomList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Room.KEY_ID + "," +
                Room.KEY_type + "," +
                Room.KEY_occupied +
                " FROM " + Room.TABLE;
        ArrayList<HashMap<String, String>> roomList = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> room = new HashMap<String, String>();
                room.put("id", cursor.getString(cursor.getColumnIndex(Room.KEY_ID)));
                room.put("type", cursor.getString(cursor.getColumnIndex(Room.KEY_type)));
                roomList.add(room);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return roomList;

    }

    public ArrayList<HashMap<String, String>> getEmptyRoomList() {
        String id;
        String type;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Room.KEY_ID + "," +
                Room.KEY_type + "," +
                Room.KEY_occupied +
                " FROM " + Room.TABLE
                + " WHERE " +
                Room.KEY_occupied + "=0";
        ArrayList<HashMap<String, String>> roomList = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> room = new HashMap<String, String>();
                id = cursor.getString(cursor.getColumnIndex(Room.KEY_ID));
                type = cursor.getString(cursor.getColumnIndex(Room.KEY_type));
                room.put("id", id);
                room.put("type", type);
                room.put("formattedData", formatListData(Integer.valueOf(id), type));
                roomList.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return roomList;
    }

    public Room getRoomById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Room.KEY_ID + "," +
                Room.KEY_type + "," +
                Room.KEY_occupied +
                " FROM " + Room.TABLE
                + " WHERE " +
                Room.KEY_ID + "=?";
        Room room = new Room();
        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );
        if (cursor.moveToFirst()) {
            do {
                room.room_ID =cursor.getInt(cursor.getColumnIndex(Room.KEY_ID));
                room.type =cursor.getString(cursor.getColumnIndex(Room.KEY_type));
                room.occupied  =cursor.getString(cursor.getColumnIndex(Room.KEY_occupied));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return room;
    }

    public int getAppState() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                AppState.KEY_ID + "," +
                AppState.KEY_state +
                " FROM " + AppState.TABLE
                + " WHERE " +
                AppState.KEY_ID + "=?";
        AppState appState = new AppState();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{"1"});
        if (cursor.moveToFirst()) {
            do {
                appState.state = cursor.getInt(cursor.getColumnIndex(AppState.KEY_state));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return appState.state;
    }

    public void saveAppState(Context page) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppState.KEY_state, (page.getClass().getSimpleName().equals("Map")) ? 0 : 1);
        db.update(AppState.TABLE, values, AppState.KEY_ID + "= ?", new String[]{"1"});
        db.close();
    }

    public String formatListData(int id, String type) {
        int floor = id / 100;
        int room = id - (100 * floor);
        if (room < 10) {
            return String.format("%1$-" + 30 + "s", "Floor " + floor + " Room " + room) + " " + type;
        } else {
            return String.format("%1$-" + 30 + "s", "Floor " + floor + " Room " + room) + type;
        }
    }

}