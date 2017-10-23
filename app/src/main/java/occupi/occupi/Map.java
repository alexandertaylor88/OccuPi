package occupi.occupi;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Map extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.map);
            DataBaseHelper db = new DataBaseHelper(this);
            db.saveAppState(this);
        }catch(Exception e){setContentView(R.layout.test);}
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
                intentMap.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentMap);
                return (true);
            case R.id.list:
                Intent intentList = new Intent(this, occupi.occupi.List.class);
                intentList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentList);
                //startActivity(new Intent(this, occupi.occupi.List.class));
                return (true);
            case R.id.rally:
                Intent intentRally = new Intent(this, occupi.occupi.Rally.class);
                intentRally.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentRally);
//            startActivity(new Intent(this, occupi.occupi.Rally.class));
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.isAppForeground = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.isAppForeground = false;
    }

}//end class