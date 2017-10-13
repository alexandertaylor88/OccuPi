package occupi.occupi;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Intent bluetooth, intent;
        DataBaseHelper db = new DataBaseHelper(this);
        permissionsRequest(db);
        bluetooth = new Intent(this, BluetoothLE.class);
        startService(bluetooth);

            if(db.getAppState() == 0){
                intent = new Intent(this, Map.class);
               this.startActivity(intent);
            }
            else{
                intent = new Intent(this, List.class);
                this.startActivity(intent);
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.map:
            startActivity(new Intent(this, Map.class));
            return(true);
        case R.id.list:
            startActivity(new Intent(this, List.class));
            return(true);
        case R.id.rally:
            startActivity(new Intent(this, Rally.class));
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

    public void permissionsRequest(DataBaseHelper db) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) || (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED)) {
                if ((checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) && (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED)) {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS},0);
                    db.insertState();
                }
                else if ((checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED)){
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},0);
                }
                else{requestPermissions(new String[]{Manifest.permission.SEND_SMS, Manifest.permission.SEND_SMS},0);}
            }
        }
    }
}
