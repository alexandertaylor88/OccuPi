package occupi.occupi;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_ENABLE_BT = 1;
    static final int REQUEST_PERMISSIONS = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        DataBaseHelper db = new DataBaseHelper(this);

        //Checks if database is built. If not, builds it. db.getAppState() doesn't do anything here, just used to see if the db needs to be rebuilt.
        try { db.getAppState(); }
        catch (Exception e) { db.createDatabase(); }


        bluetoothEnableRequest();



        //Threading code for later use.
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
    }

    @Override
    protected void onStop(){
        super.onStop();
        //For testing purposes. Toast.makeText(getApplicationContext(), "onStop() was called!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //For testing purposes. Toast.makeText(getApplicationContext(), "onDestroy() was called!", Toast.LENGTH_LONG).show();
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
       To accomplish this, the method bluetoothEnableRequest() is called first to start the bluetooth enabling process
       if bluetooth is disabled. If bluetooth is already enabled, the bluetoothEnableRequest method will start the service
       and then call the permissionsRequest method to request the app's needed permissions. If bluetooth is not enabled, then
       once the onActivityResult method finishes processing the results of the bluetooth enable request, it will call the
       permissionsRequest() method. If the permissions are not granted yet, a request is sent to the user to allow them.
       The method onRequestPermissionResult then handles the response to the permission request, then starts the map activity.

       So the flow is bluetooth request -> permissions requests -> start activities.

       This way, the bluetooth and permission requests will finish before the app moves away from the main activity, ensuring
       that they recieve a user response.
     */

    //Checks if bluetooth is enabled or not. If not, starts and activity to request it.
    // If it is enabled, starts the bluetooth service and calls requestPermissions().
    public void bluetoothEnableRequest() {
        BluetoothManager manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter adapter = manager.getAdapter();

        if (adapter == null || !adapter.isEnabled()) {
            Intent btIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(btIntent, REQUEST_ENABLE_BT);
        } else {
            Intent bluetooth = new Intent(this, BluetoothLE.class);
            startService(bluetooth);
            permissionsRequest();
        }
    }//end bluetoothEnableRequest()

    //Method to handle requesting needed permissions.
    //If permissions are needed, starts activity to request them. If they are not needed, simply
    //goes on to start the main app activities.
    public void permissionsRequest() {
        String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS};

        if(!hasPermissions(permissions)) {
            requestPermissions(permissions, REQUEST_PERMISSIONS);
        } else {
            startActivities();
        }
    }//end permissionsRequest()

    //Defining action for results of requesting SMS and CONTACTS permissions. Will need to be edited if future permissions are required.
    //Calls startActivites() when finished.
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
                startActivities();
            } else {
                Toast.makeText(getApplicationContext(), "App requires SMS and CONTACT permissions for the Rally features to function.", Toast.LENGTH_LONG).show();
//##            //Request permissions here again instead of going to startActivities()? Will the app crash without these permissions?
                startActivities(); //Might need to remove this or replace with permissionsRequest() again to loop asking.
            }
        }
    }//end onRequestPermissionsResult()

    //Defining action for results of asking to enable bluetooth. Always calls permissionsRequest() after finishing.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_ENABLE_BT) {
            if(resultCode == RESULT_OK) {

//##            //Do threading here.
                Intent bluetooth = new Intent(this, BluetoothLE.class);
                startService(bluetooth);
            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Bluetooth is required for app opperation. Please enable Bluetooth.", Toast.LENGTH_LONG).show();
            }
            permissionsRequest();
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
}