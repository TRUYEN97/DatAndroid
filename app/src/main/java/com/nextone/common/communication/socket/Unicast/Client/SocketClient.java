/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.common.communication.socket.Unicast.Client;

import android.util.Log;

import com.nextone.common.communication.socket.Unicast.commons.Interface.IIsConnect;
import com.nextone.common.communication.socket.Unicast.commons.Interface.IReceiver;
import com.nextone.common.communication.socket.Unicast.commons.Interface.Idisconnect;
import com.nextone.common.communication.socket.Unicast.commons.Keywords;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Administrator
 */
public class SocketClient implements Runnable, Idisconnect, IIsConnect, Closeable {
    private static final String TAG = "SocketClient";
    @Getter
    private final String host;
    @Getter
    private final String hostName;
    @Getter
    private final int port;
    private final IReceiver<SocketClient> clientReceiver;
    private Socket socket;
    private PrintWriter outputStream;
    private BufferedReader inputStream;
    private boolean connect;
    @Setter
    private boolean debug;

    public SocketClient(String host, int port, IReceiver<SocketClient> objectAnalysis) {
        this(Keywords.SERVER, host, port, objectAnalysis);
    }

    public SocketClient(String hostName, String host, int port, IReceiver<SocketClient> objectAnalysis) {
        this.host = host;
        this.hostName = hostName;
        this.port = port;
        this.clientReceiver = objectAnalysis;
        this.debug = false;
    }

    public String readLine() throws IOException {
        return this.inputStream.readLine();
    }

    public boolean connect() {
        try {
            this.socket = new Socket(host, port);
            this.outputStream = new PrintWriter(socket.getOutputStream(), true);
            this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean send(String data) {
        try {
            if (!isConnect()) {
                return false;
            }
            this.outputStream.println(data);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error send: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public void run() {
        try {
            String data;
            while (isConnect() && (data = readLine()) != null) {
                this.connect = true;
                if (data.trim().isBlank()) {
                    continue;
                }
                data = data.replaceAll("<newline>", "\r\n");
                if (this.clientReceiver != null) {
                    this.clientReceiver.receiver(this, data);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error run: " + e.getMessage(), e);
        } finally {
            disconnect();
        }
    }

    @Override
    public boolean isConnect() {
        return this.socket != null && this.socket.isConnected() && connect;
    }

    @Override
    public boolean disconnect() {
        if (!isConnect()) {
            return true;
        }
        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
            connect = false;
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error disconnecting: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public void close() throws IOException {
        disconnect();
    }

}
