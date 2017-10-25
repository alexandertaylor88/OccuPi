package occupi.occupi;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_ENABLE_BT = 1;     //Request code for identifying bluetooth activation activity.
    static final int REQUEST_PERMISSIONS = 2;   //Request code for identifying permissions request activity.
    static final int BT_LOOP_TIME_SECONDS = 30; //How often in seconds that the bluetooth service is ran.
    public static Intent bluetooth;
    private ScheduledExecutorService executorService;
    public static Boolean isAppForeground;
    public static long scanTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        DataBaseHelper db = new DataBaseHelper(this);
        bluetooth = new Intent(MainActivity.this, BluetoothLE.class);
        executorService = Executors.newSingleThreadScheduledExecutor();
        isAppForeground = true;

        //Checks if database is built. If not, builds it. db.getAppState() doesn't do anything here, just used to see if the db needs to be rebuilt.
        try { db.getAppState(); }
        catch (Exception e) { db.createDatabase(); }

        permissionsRequest();
    }//end onCreate()


    @Override
    protected void onResume(){
        super.onResume();
        //Toast.makeText(getApplicationContext(), "onResume() was called!", Toast.LENGTH_LONG).show();
        isAppForeground = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(getApplicationContext(), "onPause() was called!", Toast.LENGTH_LONG).show();
        isAppForeground = false;
    }

    @Override
    protected void onStop(){
        super.onStop();
        //For testing purposes.
        //Toast.makeText(getApplicationContext(), "onStop() was called!", Toast.LENGTH_LONG).show();
    }

    //Called when app is killed, as the main activity is the last to be cleared from memory.
    //THIS FUNCTION MUST CONTAIN "executorService.shutdown()" AND "stopService(bluetooth)" TO AVOID MEMORY LEAKS.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
        stopService(bluetooth);
        //For testing purposes.
        Toast.makeText(getApplicationContext(), "Main Activity onDestroy() was called!", Toast.LENGTH_LONG).show();
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
                //startActivity(new Intent(this, occupi.occupi.Rally.class));
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    /*                                 ########## READ ME ###########
          To ensure the bluetooth and permission checks are ran, the next activity cannot start until they finish.
       The requests are chained into each other, one calling the next once it finishes, and the last one calling the
       startActivities() method.
       So the flow is permissions requests -> bluetooth request -> start activities.
       Note: Bluetooth requires the location permission, so permission requests should run BEFORE the bluetooth enable check.
       This way, the bluetooth and permission requests will finish before the app moves away from the main activity, ensuring
       that they receive a user response.
     */

    //Method to handle requesting needed permissions.
    //If permissions are needed, starts activity to request them. If they are not needed, calls
    //bluetoothEnableRequest() to check if bluetooth is enabled.
    public void permissionsRequest() {
        String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION};

        if(!hasPermissions(permissions)) {
            requestPermissions(permissions, REQUEST_PERMISSIONS);
        } else {
            bluetoothEnableRequest();
        }
    }//end permissionsRequest()

    //Defining action for results of requesting permissions.
    //Calls bluetoothEnableRequest() when finishes successfully.
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Boolean grantedCheck = true;

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                grantedCheck = false;
            }
        }

        if(requestCode == 2) {
            if(grantResults.length > 0 && grantedCheck == true) {
                bluetoothEnableRequest();
            } else {
                Toast.makeText(getApplicationContext(), "App requires permissions for the Rally features to function. Please allow these permissions.", Toast.LENGTH_LONG).show();
//##            //Request permissions here again or to startActivities()? Will the app crash without these permissions?
                permissionsRequest();
            }
        }
    }//end onRequestPermissionsResult()

    //Checks if bluetooth is enabled or not. If not, starts and activity to request it.
    // If it is enabled, starts the bluetooth service and calls startActivities().
    public void bluetoothEnableRequest() {
        BluetoothManager manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter adapter = manager.getAdapter();

        if (adapter == null || !adapter.isEnabled()) {
            Intent btRequestIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(btRequestIntent, REQUEST_ENABLE_BT);
        } else {
            startBluetooth();
            startActivities();
        }
    }//end bluetoothEnableRequest()

    //Defining action for results of asking to enable bluetooth. Always calls startActivities() after finishing.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_ENABLE_BT) {
            if(resultCode == RESULT_OK) {
                startBluetooth();
            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Bluetooth is required for app opperation. Please enable Bluetooth.", Toast.LENGTH_LONG).show();
            }
            startActivities();
        }
    }//end onActivityResult()

    //This method checks if the passed string of permissions are granted or not. If any are not, returns false.
    //Simply a utility method to assist permissionsRequest().
    private boolean hasPermissions(String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M && permissions != null) {
            for (String permission : permissions) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }//end hasPermissions()

    //Begins the app's activities. Should only be called once permissions and bluetooth statuses are checked and handled.
    //Change if you would like the app to have a different landing page after loading.
    private void startActivities() {
        Intent mapIntent = new Intent(this, occupi.occupi.Map.class);
        startActivity(mapIntent);
    }//end startActivities()

    //Sets up the thread that runs the BluetoothLE service at a fixed interval of BT_LOOP_TIME_SECONDS seconds.
    //Should call this when you want to first start the bluetooth.
    //Make sure the main activity onDestroy() contains executiveService.shutdown() to avoid memory leaks!
    private void startBluetooth() {
        int i = 0;
        isAppForeground = true;
        scanTime = 0;
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //Add global var if check here.
                if (isAppForeground && (scanTime + 30000) < System.currentTimeMillis()) {
                    scanTime = System.currentTimeMillis();
                    startService(bluetooth);
                }
            }
        }, 0, BT_LOOP_TIME_SECONDS, TimeUnit.SECONDS);
    }//end startBluetooth()
}//end Main Activity