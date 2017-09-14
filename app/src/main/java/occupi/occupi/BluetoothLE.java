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
import android.support.annotation.Nullable;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.util.SparseArray;

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

    @Override
    public void onCreate() {
        manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        adapter = manager.getAdapter();
        scanner = adapter.getBluetoothLeScanner();
        //filters = uuids of devices
        //settings
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //start scan
        scanner.startScan(filters, settings, callback);
        //timer
        //callback checks

        //stop scan
        scanner.stopScan(callback);
        //callback checks

        //get room data
        manufacturerData = record.getManufacturerSpecificData();

        //put room data into database


        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




}
