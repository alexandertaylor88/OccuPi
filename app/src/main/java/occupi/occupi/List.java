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
import android.widget.CheckBox;
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
    CheckBox loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteBoardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        DataBaseHelper db = new DataBaseHelper(this);
        db.saveAppState(this);

            ArrayList<HashMap<String, String>> roomList = null;
            final ListView list = (ListView) findViewById(R.id.roomList);
            Button reset = (Button) findViewById(R.id.resetButton);
            Button sort = (Button) findViewById(R.id.sortButton);
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
                int numChecked = 0;
                String queryString = "";
                loungeButton = (CheckBox) findViewById(R.id.loungeButton);
                mediaButton = (CheckBox) findViewById(R.id.mediaButton);
                officeButton = (CheckBox) findViewById(R.id.officeButton);
                outlookButton = (CheckBox) findViewById(R.id.outlookButton);
                treadmillButton = (CheckBox) findViewById(R.id.treadmillButton);
                whiteBoardButton = (CheckBox) findViewById(R.id.whiteboardButton);
                ArrayList<HashMap<String, String>> roomList = null;
                if(loungeButton.isChecked() || mediaButton.isChecked() || officeButton.isChecked() || outlookButton.isChecked() || treadmillButton.isChecked() || whiteBoardButton.isChecked()) {
                    if (loungeButton.isChecked()) {
                        queryString += (numChecked++ == 0) ? " AND (" + Room.KEY_type + "= 'Lounge'" : " OR " + Room.KEY_type + "= 'Lounge'";
                    }
                    if (mediaButton.isChecked()) {
                        queryString += (numChecked++ == 0) ? " AND (" + Room.KEY_type + "= 'Media'" : " OR " + Room.KEY_type + "= 'Media'";
                    }
                    if (officeButton.isChecked()) {
                        queryString += (numChecked++ == 0) ? " AND (" + Room.KEY_type + "= 'Office'" : " OR " + Room.KEY_type + "= 'Office'";
                    }
                    if (outlookButton.isChecked()) {
                        queryString += (numChecked++ == 0) ? " AND (" + Room.KEY_type + "= 'Outook'" : " OR " + Room.KEY_type + "= 'Outlook'";
                    }
                    if (treadmillButton.isChecked()) {
                        queryString += (numChecked++ == 0) ? " AND (" + Room.KEY_type + "= 'Treadmill'" : " OR " + Room.KEY_type + "= 'Treadmill'";
                    }
                    if (whiteBoardButton.isChecked()) {
                        queryString += (numChecked++ == 0) ? " AND (" + Room.KEY_type + "= 'Whiteboard'" : " OR " + Room.KEY_type + "= 'Whiteboard'";
                    }
                    try {
                        filterList(roomList, list, queryString);
                    }catch(Exception e){Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();}
                }
                else {
                    Toast.makeText(getApplicationContext(), "No Filter Selected.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loungeButton.setChecked(false);
                mediaButton.setChecked(false);
                officeButton.setChecked(false);
                outlookButton.setChecked(false);
                treadmillButton.setChecked(false);
                whiteBoardButton.setChecked(false);
                recreate();
            }
        });
    }

    public void filterList(ArrayList<HashMap<String, String>> roomList, ListView list, String query) {
        DataBaseHelper db = new DataBaseHelper(List.this);
        roomList = db.filterRoomList(query);
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

