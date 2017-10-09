package occupi.occupi;

import android.app.Service;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
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
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

public class BluetoothLE extends Service {
    private BluetoothManager manager;
    private BluetoothAdapter adapter;
    private BluetoothLeScanner scanner;
    private ScanRecord record;
    private Handler handler;
    private List<ScanFilter> filters;
    private ScanSettings settings;
    private ScanCallback callback;
    private SparseArray<byte[]> manufacturerData;
    private int byte_array_length = 14; //Double check this value.

    @Override
    public void onCreate() {
        manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        adapter = manager.getAdapter();
        scanner = adapter.getBluetoothLeScanner();

        //Checks if Bluetooth is enabled. Displays error prompting user to enable if not enabled.
        //Needs testing to make sure it works right. Might need to change to startActivityForResult().
        if (adapter == null || !adapter.isEnabled()) {
            Intent btIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(btIntent);
        }

        //filter uuids
        filters = new ArrayList<>();
        ParcelUuid serviceUuid = ParcelUuid.fromString("05b3fe32-0000-0000-0000-000000000000");
        ParcelUuid mask = ParcelUuid.fromString("11111111-0000-0000-0000-000000000000");
        ScanFilter.Builder builder = new ScanFilter.Builder();
        builder.setServiceUuid(serviceUuid, mask);
        filters.add(builder.build());

        settings = null;
        callback = new ScanCallback() {};
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //start scan
        scanner.startScan(filters, settings, callback);
        //timer
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //callback checks

        //stop scan
        scanner.stopScan(callback);
        //callback checks

        //get room data
        manufacturerData = record.getManufacturerSpecificData();

        //put room data into database
        byte[] roomData = new byte[byte_array_length];
        DataBaseHelper db = new DataBaseHelper(this);
        int floor = 0; //Update once floor is determined.

        //Parse room data from advertisement into roomData array here.

        db.updateOccupancy(floor, roomData);



        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




}
