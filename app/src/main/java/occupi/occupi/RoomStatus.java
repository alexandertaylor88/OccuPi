package occupi.occupi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class RoomStatus extends ActionBarActivity {

    TextView textType;
    TextView textID;
    private int _Room_Id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_status);

        textID = (TextView) findViewById(R.id.text_num);
        textType = (TextView) findViewById(R.id.text_type);

        _Room_Id = 0;
        Intent intent = getIntent();
        _Room_Id = intent.getIntExtra("room_Id", 0);
        DataBaseHelper db = new DataBaseHelper(this);
        Room room = new Room();
        room = db.getRoomById(_Room_Id);

        textType.setText(room.type);
        textID.setText(roomNameFormatting(_Room_Id));
    }

    @Override
    public void onResume(){
        super.onResume();
        Intent bluetooth = new Intent(this, BluetoothLE.class);
        startService(bluetooth);
    }

    @Override
    public void onPause(){
        super.onPause();
        Intent bluetooth = new Intent(this, BluetoothLE.class);
        stopService(bluetooth);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Intent bluetooth = new Intent(this, BluetoothLE.class);
        stopService(bluetooth);
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
                startActivity(new Intent(this, occupi.occupi.Map.class));
                return (true);
            case R.id.list:
                startActivity(new Intent(this, occupi.occupi.List.class));
                return (true);
            case R.id.rally:
                startActivity(new Intent(this, occupi.occupi.Rally.class));
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    private String roomNameFormatting(int roomNum) {
        int floor = roomNum / 100;
        return "Floor " + floor + " Room " + (roomNum - (100 * floor));
    }

    public void goRally(View v) {
        Intent intent = new Intent(this, Rally.class);
        intent.putExtra("id", Integer.toString(_Room_Id));
        startActivity(intent);
    }

}
