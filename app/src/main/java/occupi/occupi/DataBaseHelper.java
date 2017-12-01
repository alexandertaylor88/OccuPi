/*
 * Copyright (c) 2017, Team OccuPi - Erik Brown, Tony Klingele, Alexander Taylor, Ethan Wright
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

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

    //Initial creation of the database for first time users or with new updates. This contains information
    //on all of the rooms.
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
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('301','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('302','Treadmill','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('303','Media','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('304','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('305','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('306','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('307','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('308','Outlook','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('311','Outlook','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('312','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('313','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('314','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('315','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('316','Whiteboard','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('317','Whiteboard','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('318','Media','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('319','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('320','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('321','Media','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('322','Whiteboard','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('323','Whiteboard','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('324','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('325','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('326','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('327','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('328','Outlook','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('329','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('330','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('331','Outlook','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('332','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('333','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('334','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('335','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('336','Media','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('337','Treadmill','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('338','Outlook','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('339','Outlook','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('401','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('402','Treadmill','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('403','Media','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('404','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('405','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('406','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('407','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('408','Outlook','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('411','Outlook','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('412','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('413','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('414','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('415','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('416','Whiteboard','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('417','Whiteboard','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('418','Media','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('419','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('420','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('421','Media','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('422','Whiteboard','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('423','Whiteboard','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('424','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('425','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('426','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('427','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('428','Outlook','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('429','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('430','Office','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('431','Outlook','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('432','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('433','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('434','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('435','Lounge','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('436','Media','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('437','Treadmill','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('438','Outlook','0')");
        db.execSQL("INSERT INTO " + Room.TABLE + " (" + Room.KEY_ID + ", " + Room.KEY_type + ", " + Room.KEY_occupied + ") " +
                "VALUES('439','Outlook','0')");
        db.close();
    }

    //Called by the BluetoothLE class whenever packets of room occupancies are scanned
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

    //Standardized query of the full list of unoccupied rooms from all of the floors
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

    //Specific query for the map page that queries the room occupancies of a specific floor
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

    //Specific query for the list page that queries the room occupancies of a specific type
    public ArrayList<HashMap<String, String>> filterRoomList(String queryFilter) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery;
        String type;
        String id;
        queryFilter = ( queryFilter.equals("")) ? "" : queryFilter + ")";
        selectQuery = "SELECT  " +
                Room.KEY_ID + "," +
                Room.KEY_type + "," +
                Room.KEY_occupied +
                " FROM " + Room.TABLE
                + " WHERE " + Room.KEY_occupied + "=0"
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

    //Query that returns a Room object based on that room's id
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

    //Query to determine the last page (map or list) accessed by the user
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

    //If the user accesses the map or list page, the database is updated accordingly so as to provide
    //an instant redirect to that page when the app is opened again.
    public void saveAppState(Context page) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppState.KEY_state, (page.getClass().getSimpleName().equals("Map")) ? 0 : 1);
        db.update(AppState.TABLE, values, AppState.KEY_ID + "= ?", new String[]{"1"});
        db.close();
    }

    //Formats data for the list page to display the floor number, room number and room type all on one line
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