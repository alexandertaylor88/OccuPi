package occupi.occupi;

import android.app.Service;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.support.annotation.Nullable;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BluetoothLE extends Service {
    private static final int MANUFACTURER_ID = 0xBC05;
    private static final int BYTE_ARRAY_LENGTH = 14;

    private BluetoothLeScanner scanner;
    private ScanCallback callback;
    private Handler handler;
    private List<ScanFilter> filters;
    private ScanSettings settings;

    @Override
    public void onCreate() {
        BluetoothManager manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter adapter = manager.getAdapter();

        //Checks if Bluetooth is enabled. Displays error prompting user to enable if not enabled.
        //Needs testing to make sure it works right. Might need to change to startActivityForResult().
        if (adapter == null || !adapter.isEnabled()) {
            Intent btIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(btIntent);
        }

        scanner = adapter.getBluetoothLeScanner();

        callback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                ScanRecord record = result.getScanRecord();
                byte[] manufacturerData = record.getManufacturerSpecificData(MANUFACTURER_ID);

                if(manufacturerData != null) {
                    DataBaseHelper db = new DataBaseHelper(BluetoothLE.this);
                    int floor = (int)(((manufacturerData[2] & 0xFF) << 8) | (manufacturerData[3] & 0xFF));
                    byte[] roomData = new byte[BYTE_ARRAY_LENGTH];
                    for(int i = 0; i < BYTE_ARRAY_LENGTH; i++) {
                        roomData[i] = manufacturerData[i + 4];
                    }
                    db.updateOccupancy(floor, roomData);

                    Toast.makeText(getApplicationContext(),
                            record.toString(),
                            Toast.LENGTH_LONG).show();
                }
            }
        };

        //filter uuids
        filters = new ArrayList<>();
        ParcelUuid serviceUuid = ParcelUuid.fromString("05b3fe32-0000-0000-0000-000000000000");
        ParcelUuid uuidMask = ParcelUuid.fromString("11111111-0000-0000-0000-000000000000");
        ScanFilter.Builder filterBuilder = new ScanFilter.Builder();
        filterBuilder.setServiceUuid(serviceUuid, uuidMask);
        filters.add(filterBuilder.build());

        //scan settings
        ScanSettings.Builder settingsBuilder = new ScanSettings.Builder();
        settingsBuilder.setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES);
        settingsBuilder.setNumOfMatches(ScanSettings.MATCH_NUM_FEW_ADVERTISEMENT);
        settings = settingsBuilder.build();

        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //start scan
        scanner.startScan(null, settings, callback);

        //stop scan
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scanner.stopScan(callback);
            }
        }, 10000);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}