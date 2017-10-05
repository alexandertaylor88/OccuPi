package occupi.occupi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Map extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        permissionsRequest();
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

    public void goRoomDetail(View view) {
        Intent intent = new Intent(this, RoomDetail.class);
        startActivity(intent);
    }

    public void permissionsRequest() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) || (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED)) {
                if ((checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) && (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED)) {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS},0);
                }
                else if ((checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED)){
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},0);
                }
                else{requestPermissions(new String[]{Manifest.permission.SEND_SMS, Manifest.permission.SEND_SMS},0);}
            }
        }
    }

    public void goList(View view) {
        Intent intent = new Intent(this, List.class);
        startActivity(intent);
    }

}
