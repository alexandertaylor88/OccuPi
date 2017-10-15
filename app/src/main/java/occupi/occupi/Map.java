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
        DataBaseHelper db = new DataBaseHelper(this);
        db.saveAppState(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.map:
            startActivity(new Intent(this, occupi.occupi.Map.class));
            return(true);
        case R.id.list:
            startActivity(new Intent(this, occupi.occupi.List.class));
            return(true);
        case R.id.rally:
            startActivity(new Intent(this, occupi.occupi.Rally.class));
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

}
