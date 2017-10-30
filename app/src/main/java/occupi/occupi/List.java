package occupi.occupi;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
public class List extends AppCompatActivity {
    TextView room_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        DataBaseHelper db = new DataBaseHelper(this);
        db.saveAppState(this);

/*        public ArrayList<HashMap<String, String>> getFilterRoomList(String filter) {
            String id;
            String type;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String selectQuery = "SELECT  " +
                    Room.KEY_ID + "," +
                    Room.KEY_type + "," +
                    Room.KEY_occupied +
                    " FROM " + Room.TABLE
                    + " WHERE " +
                    Room.KEY_type + " = '" + filter + "'";
            ArrayList<HashMap<String, String>> roomList = new ArrayList<HashMap<String, String>>();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> room = new HashMap<String, String>();
                    id = cursor.getString(cursor.getColumnIndex(Room.KEY_ID));
                    type = cursor.getString(cursor.getColumnIndex(Room.KEY_type));
                    room.put("id", id);
                    room.put("type", type);
                    roomList.add(room);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return roomList;
        }
*/
            ArrayList<HashMap<String, String>> roomList = null;
            final ListView list = (ListView) findViewById(R.id.roomList);
            Button reset = (Button) findViewById(R.id.resetButton);
            Button sort = (Button) findViewById(R.id.sortButton);
            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        recreate();
                }
            });
            try {
                roomList = db.getEmptyRoomList();
            } catch (Exception e) {
                db.createDatabase();
                roomList = db.getEmptyRoomList();
            }
            if (roomList.size() != 0) {
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        room_Id = (TextView) view.findViewById(R.id.room_Id);
                        String roomId = room_Id.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(), RoomStatus.class);
                        objIndent.putExtra("room_Id", Integer.parseInt(roomId));
                        startActivity(objIndent);
                    }
                });
                list.setAdapter(new SimpleAdapter(List.this, roomList, R.layout.view_room_entry, new String[]{"id", "formattedData"}, new int[]{R.id.room_Id, R.id.room_Num}));
            } else {
                Toast.makeText(this, "No Rooms", Toast.LENGTH_SHORT).show();
            }
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteBoardButton;
                loungeButton = (RadioButton) findViewById(R.id.loungeButton);
                mediaButton = (RadioButton) findViewById(R.id.mediaButton);
                officeButton = (RadioButton) findViewById(R.id.officeButton);
                outlookButton = (RadioButton) findViewById(R.id.outlookButton);
                treadmillButton = (RadioButton) findViewById(R.id.treadmillButton);
                whiteBoardButton = (RadioButton) findViewById(R.id.whiteboardButton);
                ArrayList<HashMap<String, String>> roomList = null;
                if (loungeButton.isChecked()) {
                    filterList(roomList, list, String.valueOf(loungeButton.getText()));
                } else if (mediaButton.isChecked()) {
                    filterList(roomList, list, String.valueOf(mediaButton.getText()));
                } else if (officeButton.isChecked()) {
                    filterList(roomList, list, String.valueOf(officeButton.getText()));
                } else if (outlookButton.isChecked()) {
                    filterList(roomList, list, String.valueOf(outlookButton.getText()));
                } else if (treadmillButton.isChecked()) {
                    filterList(roomList, list, String.valueOf(treadmillButton.getText()));
                } else if (whiteBoardButton.isChecked()) {
                    filterList(roomList, list, String.valueOf(whiteBoardButton.getText()));
                } else {
                    Toast.makeText(getApplicationContext(), "No Filter Selected.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void filterList(ArrayList<HashMap<String, String>> roomList, ListView list, String type) {
        DataBaseHelper db = new DataBaseHelper(List.this);
        roomList = db.filterRoomList(type);
        list.setAdapter(new SimpleAdapter(List.this, roomList, R.layout.view_room_entry, new String[]{"id", "formattedData"}, new int[]{R.id.room_Id, R.id.room_Num}));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map:
                Intent intentMap = new Intent(this, occupi.occupi.Map.class);
                startActivity(intentMap);
                finish();
                return (true);
            case R.id.list:
                Intent intentList = new Intent(this, occupi.occupi.List.class);
                startActivity(intentList);
                finish();
                return (true);
            case R.id.rally:
                Intent intentRally = new Intent(this, occupi.occupi.Rally.class);
                startActivity(intentRally);
                finish();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.isAppForeground = true;
        if ((MainActivity.scanTime + 120000) < System.currentTimeMillis() && MainActivity.bluetoothEnabled){
            MainActivity.scanTime = System.currentTimeMillis();
            startService(MainActivity.bluetooth);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.isAppForeground = false;
    }

    @Override
    public void onBackPressed() {
    }

}

