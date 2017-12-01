/*
 * Copyright (c) 2017, Team OccuPi - Erik Brown, Tony Klingele, Alexander Taylor, Ethan Wright
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

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
import java.util.ArrayList;
import java.util.Arrays;
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
    }
}