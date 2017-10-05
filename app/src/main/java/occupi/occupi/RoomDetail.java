package occupi.occupi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class RoomDetail extends ActionBarActivity {

    TextView textType;
    TextView textID;
    private int _Room_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_status);

        textID = (TextView)findViewById(R.id.text_num);
        textType = (TextView)findViewById(R.id.text_type);

    _Room_Id = 0;
    Intent intent = getIntent();
    _Room_Id = intent.getIntExtra("room_Id", 0);
    DataBaseHelper repo = new DataBaseHelper(this);
    Room room = new Room();
    room = repo.getRoomById(_Room_Id);

    textType.setText(room.type);
    textID.setText(Integer.toString(_Room_Id));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.search:
            startActivity(new Intent(this, Map.class));
            return(true);
        case R.id.login:
            startActivity(new Intent(this, Login.class));
            return(true);
        case R.id.rally:
            startActivity(new Intent(this, Rally.class));
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

}
