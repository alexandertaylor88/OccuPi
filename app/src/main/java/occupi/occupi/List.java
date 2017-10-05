package occupi.occupi;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class List extends ListActivity{

    TextView room_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

            DataBaseHelper repo = new DataBaseHelper(this);
        /////////////////////////////////////////////////////////////////////////////////////////////
//        byte arr[] = new byte[] { 0, 0, 0 };
        byte arr[] = new byte[] { 10, 20, 30 };
        Toast.makeText(this,repo.updateOccupancy(2, arr), Toast.LENGTH_SHORT).show();
        /////////////////////////////////////////////////////////////////////////////////////////////
            ArrayList<HashMap<String, String>> roomList =  repo.getEmptyRoomList();
            if(roomList.size()!=0) {
                ListView lv = getListView();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        room_Id = (TextView) view.findViewById(R.id.room_Id);
                        String roomId = room_Id.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(),RoomDetail.class);
                        objIndent.putExtra("room_Id", Integer.parseInt( roomId));
                        startActivity(objIndent);
                    }
                });

                    ListAdapter adapter = new SimpleAdapter( List.this,roomList, R.layout.view_room_entry, new String[] { "id", "id"}, new int[] {R.id.room_Id, R.id.room_Num});
                    setListAdapter(adapter);
            }else{
                Toast.makeText(this,"No Rooms", Toast.LENGTH_SHORT).show();
            }
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

