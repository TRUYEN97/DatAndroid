package com.nextone.common.communication.Serial;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.nextone.common.timer.WaitTime.AbsTime;
import com.nextone.common.timer.WaitTime.Class.TimeH;
import com.nextone.model.MyContextManagement;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsbSerial implements Closeable {

    public static final String TAG = "SERIAL";
    private UsbSerialPort serialPort;
    private final UsbManager usbManager;
    private boolean stopRead;
    private final Context context;
    private static final String ACTION_USB_PERMISSION = "com.nextone.common.communication.Serial.USB_PERMISSION";
    private int baudRate;
    private UsbSerialDriver driver;
    private boolean registerReceiver;
    private String productName;

    public UsbSerial() {
        this.context = MyContextManagement.getInstance().getAplicationContext();
        this.usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        this.baudRate = 115200;
        this.registerReceiver = false;
        registerReceiver();
    }

    public void registerReceiver() {
        if (this.registerReceiver) return;
        this.registerReceiver = true;
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        filter.addAction(ACTION_USB_PERMISSION);
        context.registerReceiver(usbBroadcastReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
    }

    protected void requestPermission(UsbSerialDriver device) {
        try {
            if (device == null) return;
            registerReceiver();
            Intent intent = new Intent(ACTION_USB_PERMISSION);
            PendingIntent permissionIntent = PendingIntent.getBroadcast(
                    context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
            usbManager.requestPermission(device.getDevice(), permissionIntent);
        } catch (Exception e) {
            Log.e(TAG, "requestPermission: ", e);
        }
    }

    public void connect(String productName, int baudRate) {
        try {
            if (productName == null || baudRate <= 0) return;
            this.baudRate = baudRate;
            this.productName = productName;
            UsbSerialDriver driver = getUsbDriver(productName);
            if (driver != null && usbManager.hasPermission(driver.getDevice())) {
                closeThis();
                serialPort = driver.getPorts().get(0);
                UsbDeviceConnection deviceConnection = usbManager.openDevice(driver.getDevice());
                if (deviceConnection == null) {
                    requestPermission(driver);
                    return;
                } else {
                    this.driver = driver;
                }
                serialPort.open(deviceConnection);
                serialPort.setParameters(baudRate, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
                serialPort.setDTR(true);
            }else{
                requestPermission(driver);
            }
        } catch (Exception e) {
            Log.e(TAG, "connectToUsbDevice: ", e);
        }
    }

    private UsbSerialDriver getUsbDriver(String deviceName) {
        if (deviceName == null) return null;
        List<UsbSerialDriver> drivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager);
        for (UsbSerialDriver driver : drivers) {
            String name = driver.getDevice().getProductName();
            Log.i(TAG, "Checking device: " + name);
            if (name != null && name.equalsIgnoreCase(deviceName)) {
                return driver;
            }
        }
        Log.e(TAG, "No matching USB device found for: " + deviceName);
        return null;
    }

    public boolean isConnect() {
        return driver != null && serialPort != null && serialPort.isOpen();
    }


    public List<UsbSerialDriver> getCommPorts() {
        return UsbSerialProber.getDefaultProber().findAllDrivers(usbManager);
    }

    public synchronized boolean send(String command, Object... params) {
        if (!isConnect() || command == null) {
            return false;
        }
        try {
            String cmd;
            if (params.length == 0) {
                cmd = String.format("%s\r\n", command);
            } else {
                cmd = String.format("%s\r\n", String.format(command, params));
            }
            serialPort.write(cmd.getBytes(), 1000);
            return true;
        } catch (Exception ex) {
            Log.e(TAG, "send: ", ex);
            return false;
        }
    }

    public String read() {
        return read(1000);
    }

    public String read(int timeout) {
        try {
            if (!isConnect()) return null;
            byte[] buffer = new byte[1024];
            int received = serialPort.read(buffer, timeout <= 0 ? 1 : timeout);
            if (received > 0) {
                return new String(buffer, 0, received);
            }
            return null;
        } catch (Exception ex) {
            Log.e(TAG, "readLine: ", ex);
            return null;
        }
    }

    public String readLine() {
        return readLine(new TimeH(Double.MAX_VALUE));
    }

    public String readLine(AbsTime ticker) {
        try {
            if (!isConnect()) return null;
            StringBuilder result = new StringBuilder();
            byte[] buffer = new byte[64];
            if (ticker == null || !ticker.onTime()) {
                return null;
            }
            this.stopRead = false;
            int length = 0;
            while (!stopRead && ticker.onTime()) {
                if ((length = serialPort.read(buffer, 100)) > 0) {
                    for (int i = 0; i < length; i++) {
                        result.append((char) buffer[i]);
                    }
                    if (result.toString().contains("\n")) {
                        break;
                    }
                }
            }
            return result.toString().trim().isBlank() ? null : result.toString().trim();
        } catch (Exception ex) {
            Log.e(TAG, "readLine: ", ex);
            return null;
        }
    }


    public void stopRead() {
        this.stopRead = true;
    }


    public String getName() {
        if (this.serialPort == null) return null;
        return this.serialPort.getDevice().getProductName();
    }

    @Override
    public void close() throws IOException {
        this.driver = null;
        unregisterReceiver();
        closeThis();
    }

    private void closeThis() throws IOException {
        if (serialPort != null && serialPort.isOpen()) {
            serialPort.close();
        }
    }

    private final BroadcastReceiver usbBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i(TAG, "Received action: " + action + ", intent: " + intent);
            UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            Log.i(TAG, "Device from intent: " + device);
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                if (driver != null && device != null && device.equals(driver.getDevice())) {
                    try {
                        connect(productName, baudRate);
                    } catch (Exception e) {
                        Log.e(TAG, "onReceive.ACTION_USB_DEVICE_ATTACHED: ", e);
                    }
                }
            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                if (device != null && driver != null && device.equals(driver.getDevice())) {
                    try {
                        closeThis();
                    } catch (IOException e) {
                        Log.e(TAG, "onReceive.ACTION_USB_DEVICE_DETACHED: ", e);
                    }
                }
            } else if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    Log.i(TAG, "onReceive.ACTION_USB_PERMISSION: " + device);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        try {
                            Log.i(TAG, "onReceive.ACTION_USB_PERMISSION: " + device);
                            connect(productName, baudRate);
                        } catch (Exception e) {
                            Log.e(TAG, "onReceive.ACTION_USB_DEVICE_ATTACHED: ", e);
                        }
                    }
                }
            }
        }
    };


    public void unregisterReceiver() {
        if (!this.registerReceiver) return;
        this.registerReceiver = false;
        context.unregisterReceiver(usbBroadcastReceiver);
    }
}
