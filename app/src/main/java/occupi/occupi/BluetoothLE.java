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
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class BluetoothLE extends Service {
    private static final int MANUFACTURER_ID = 0xBC05;
    private static final int BYTE_ARRAY_LENGTH = 14;
    private static final int FLOORS = 4;

    private BluetoothLeScanner scanner;
    private ScanCallback callback;
    private Handler handler;
    private List<ScanFilter> filters;
    private ScanSettings settings;
    private boolean[] floors;

    @Override
    public void onCreate() {
        BluetoothManager manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter adapter = manager.getAdapter();

        scanner = adapter.getBluetoothLeScanner();

        //Toast for testing purposes.
        //Toast.makeText(getApplicationContext(), "Bluetooth onCreate() was called!", Toast.LENGTH_LONG).show();

        callback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                ScanRecord record = result.getScanRecord();
                byte[] manufacturerData = record.getManufacturerSpecificData(MANUFACTURER_ID);

                if (manufacturerData != null) {
                    DataBaseHelper db = new DataBaseHelper(BluetoothLE.this);
                    int floor = (int) (((manufacturerData[2] & 0xFF) << 8) | (manufacturerData[3] & 0xFF));
                    if (!floors[floor - 1]) {
                        floors[floor - 1] = true;
                        byte[] roomData = new byte[BYTE_ARRAY_LENGTH];
                        for (int i = 0; i < BYTE_ARRAY_LENGTH; i++) {
                            roomData[i] = manufacturerData[i + 4];
                        }
                        db.updateOccupancy(floor, roomData);
                    }
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
        floors = new boolean[FLOORS];
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Arrays.fill(floors, false);
        //start scan
        scanner.startScan(null, settings, callback);

        //stop scan
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scanner.stopScan(callback);
            }
        }, 10000);

        //Toast for testing purposes.
        //Toast.makeText(getApplicationContext(), "Bluetooth onStartCommand() was called!", Toast.LENGTH_LONG).show();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast for testing purposes.
        //Toast.makeText(getApplicationContext(), "The bluetooth service was destroyed!", Toast.LENGTH_LONG).show();
    }
}