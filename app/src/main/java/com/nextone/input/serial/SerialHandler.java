package com.nextone.input.serial;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.nextone.common.communication.Serial.UsbSerial;
import com.nextone.common.communication.socket.Unicast.commons.Interface.IReceiver;

import lombok.Getter;
import lombok.Setter;

public class SerialHandler implements Runnable {

    private static final String TAG = "SerialHandler";

    @Getter
    private final String serialName;
    private final int baudRate;
    private final CheckConnect checkConntect;
    private boolean connect = false;
    @Setter
    private IReceiver<UsbSerial> receiver;
    private UsbSerial usbSerial;

    @Setter
    private Runnable connectAction;
    @Setter
    private Runnable disConnectAction;

    private boolean stop;

    public SerialHandler(String serialName, int baudRate) {
        this.baudRate = baudRate;
        this.serialName = serialName;
        this.checkConntect = new CheckConnect(2000);
    }
    private boolean checkConnecting() {
        if (!isConnect()) {
            return false;
        }
        return send("isConnect");
    }

    public boolean isConnect() {
        return connect && usbSerial != null && usbSerial.isConnect();
    }

    public synchronized boolean send(String command, Object... params) {
        if (!isConnect()) {
            return false;
        }
        if (this.usbSerial.send(command, params)) {
            this.checkConntect.update();
            return true;
        }
        return false;
    }

    public void stop(){
        this.stop = true;
    }

    @Override
    public void run() {
        stop = false;
        while (!stop) {
            connect = false;
            usbSerial = null;
            try (UsbSerial serial = new UsbSerial()) {
                serial.connect(serialName, baudRate);
                while (!stop && !serial.isConnect()) {
                    try {
                        Thread.sleep(2000);
                        serial.connect(serialName, baudRate);
                    } catch (InterruptedException ex) {
                        Log.e(TAG, "Interrupted while waiting to reconnect", ex);
                    }
                }
                if(stop) break;
                Log.i(TAG, "Reconnected to " + serialName);
                usbSerial = serial;
                connect = true;
                if (connectAction != null) {
                    connectAction.run();
                }
                String line;
                this.checkConntect.start();
                while (!stop && isConnect()) {
                    line = serial.readLine();
                    if (line == null) {
                        if (this.checkConntect.isNoResponse()) {
                            connect = false;
                        }
                        continue;
                    }
                    this.checkConntect.update();
                    if (line.equalsIgnoreCase("isConnect")) {
                        continue;
                    }
                    if (receiver != null) {
                        receiver.receiver(serial, line.trim());
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error in SerialHandler run loop", e);
            }
            this.checkConntect.stop();
            if(this.disConnectAction != null){
                this.disConnectAction.run();
            }
        }
    }

    private class CheckConnect {

        private final Handler handler;
        private final int time;
        private boolean hasCheck = false;
        private boolean noResponse = false;
        private Runnable checkTask;

        private CheckConnect(int time) {
            this.handler = new Handler(Looper.getMainLooper());
            this.time = Math.max(time, 1000);

            this.checkTask = () -> {
                if (!hasCheck) {
                    if (checkConnecting()) {
                        hasCheck = true;
                    } else {
                        noResponse = true;
                    }
                } else {
                    if (isConnect()) {
                        usbSerial.stopRead();
                    }
                    noResponse = true;
                }
                handler.postDelayed(checkTask, this.time);
            };
        }

        private void update() {
            noResponse = false;
            hasCheck = false;
            handler.removeCallbacks(checkTask);
            handler.postDelayed(checkTask, time);
        }

        private boolean isNoResponse() {
            return noResponse;
        }

        private void start() {
            noResponse = false;
            hasCheck = false;
            handler.removeCallbacks(checkTask);
            handler.postDelayed(checkTask, time);
        }

        private void stop() {
            handler.removeCallbacks(checkTask);
        }
    }
}
