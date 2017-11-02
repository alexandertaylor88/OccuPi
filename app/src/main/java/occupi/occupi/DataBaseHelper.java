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

    public void createDatabase() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + Room.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + AppState.TABLE);

        db.execSQL("CREATE TABLE " + Room.TABLE + "("
                + Room.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Room.KEY_type + " TEXT, "
                + Room.KEY_occupied + " INTEGER)");

        db.execSQL("CREATE TABLE " + AppState.TABLE + "("
                + AppState.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + AppState.KEY_state + " INTEGER)");

        db.execSQL("INSERT INTO " + AppState.TABLE + " (" + AppState.KEY_ID + ", " + AppState.KEY_state + ") " +
                "VALUES('1','0')");

        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('201','Lounge','1')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('202','Treadmill','1')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('203','Media','1')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('204','Office','1')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('205','Office','1')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('206','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('207','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('208','Outlook','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('211','Outlook','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('212','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('213','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('214','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('215','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('216','Whiteboard','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('217','Whiteboard','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('218','Media','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('219','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('220','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('221','Media','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('222','Whiteboard','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('223','Whiteboard','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('224','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('225','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('226','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('227','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('228','Outlook','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('229','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('230','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('231','Outlook','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('232','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('233','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('234','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('235','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('236','Media','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('237','Treadmill','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('238','Outlook','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('239','Outlook','0')");
        db.close();
    }

    public int insert(Room room) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Room.KEY_occupied, room.occupied);
        values.put(Room.KEY_type, room.type);
        long room_Id = db.insert(Room.TABLE, null, values);
        db.close();
        return (int) room_Id;
    }

    public void delete(int room_Id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Room.TABLE, Room.KEY_ID + "= ?", new String[]{String.valueOf(room_Id)});
        db.close();
    }

    public void updateRoom(Room room) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Room.KEY_occupied, room.occupied);
        values.put(Room.KEY_type, room.type);
        db.update(Room.TABLE, values, Room.KEY_ID + "= ?", new String[]{String.valueOf(room.room_ID)});
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
                db.update(Room.TABLE, values, Room.KEY_ID + "= ?", new String[]{floor + String.format("%02d", roomNum++)});
            }
        }
        db.close();
    }

    public ArrayList<HashMap<String, String>> getRoomList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
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

    public ArrayList<HashMap<String, String>> getRoomMap(String flr) {
        int floor = Integer.parseInt(flr.replaceAll("\\D+","")) * 100;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery;
        String id;
        String type;
        String occupancy;
        selectQuery = "SELECT  " +
                Room.KEY_ID + "," +
                Room.KEY_type + "," +
                Room.KEY_occupied +
                " FROM " + Room.TABLE
                + " WHERE " +
                Room.KEY_ID + " BETWEEN " +
                floor + " AND " + (floor + 99);
        ;
        ArrayList<HashMap<String, String>> roomList = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> room = new HashMap<String, String>();
                id = cursor.getString(cursor.getColumnIndex(Room.KEY_ID));
                type = cursor.getString(cursor.getColumnIndex(Room.KEY_type));
                occupancy = cursor.getString(cursor.getColumnIndex(Room.KEY_occupied));
                room.put("id", id);
                room.put("type", type);
                room.put("occupancy", occupancy);
                roomList.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return roomList;
    }

    public ArrayList<HashMap<String, String>> filterRoomList(String queryFilter) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery;
        String type;
        String id;
        selectQuery = "SELECT  " +
                Room.KEY_ID + "," +
                Room.KEY_type + "," +
                Room.KEY_occupied +
                " FROM " + Room.TABLE
                + queryFilter;
        ;
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

    public Room getRoomById(int Id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Room.KEY_ID + "," +
                Room.KEY_type + "," +
                Room.KEY_occupied +
                " FROM " + Room.TABLE
                + " WHERE " +
                Room.KEY_ID + "=?";
        Room room = new Room();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});
        if (cursor.moveToFirst()) {
            do {
                room.room_ID = cursor.getInt(cursor.getColumnIndex(Room.KEY_ID));
                room.type = cursor.getString(cursor.getColumnIndex(Room.KEY_type));
                room.occupied = cursor.getString(cursor.getColumnIndex(Room.KEY_occupied));
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