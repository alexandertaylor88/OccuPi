package occupi.occupi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Intent bluetooth, intent;
        DataBaseHelper db = new DataBaseHelper(this);
        permissionsRequest();
        //bluetooth = new Intent(this, BluetoothLE.class);
        //startService(bluetooth);
        try {
            if (db.getAppState() == 0) {
                intent = new Intent(this, occupi.occupi.Map.class);
            } else {
                intent = new Intent(this, occupi.occupi.List.class);
            }
            this.startActivity(intent);
        } catch (Exception e) {
            db.createDatabase();
            intent = new Intent(this, occupi.occupi.Map.class);
            this.startActivity(intent);
        }

       /* final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Intent bluetooth = new Intent(MainActivity.this, BluetoothLE.class);
                startService(bluetooth);
            }
        }, 0, 30, TimeUnit.SECONDS);*/
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent bluetooth = new Intent(this, BluetoothLE.class);
        startService(bluetooth);
    }

    @Override
    protected void onStop(){
        super.onStop();
        Intent bluetooth = new Intent(this, BluetoothLE.class);
        //stopService(bluetooth);
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

    public void permissionsRequest() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) || (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED)) {
                if ((checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) && (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED)) {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS}, 0);
                } else if ((checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED)) {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 0);
                } else {
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS, Manifest.permission.SEND_SMS}, 0);
                }
            }
        }
    }
}