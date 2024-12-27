/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.common;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.nextone.model.MyContextManagement;

/**
 *
 * @author Admin
 */
public class Setting {

    public static final String TAG = "Setting class";
    private static volatile Setting instance;
    private final Properties filePath;
    private final Properties apiProperties;
    private final Context context;

    private Setting() {
        this.filePath = new Properties();
        this.apiProperties = new Properties();
        this.context = MyContextManagement.getInstance().getAplicationContext();
        try {
            loadProperties(filePath,"config.properties");
        } catch (IOException ex) {
            Log.e(TAG, "setDefaultConfig:", ex);
        }
    }

    public void setSaHinhMode() {
        try {
            loadProperties(apiProperties,"ShConfig.properties");
        } catch (IOException ex) {
            Log.e(TAG, "setDefaultConfig:", ex);
        }
    }

    public void setDuongTruongMode() {
        try {
            loadProperties(apiProperties,"DtConfig.properties");
        } catch (IOException ex) {
            Log.e(TAG, "setDefaultConfig:", ex);
        }
    }
    private void loadProperties(Properties properties, String fileName) throws IOException {
        try (InputStream inputStream = context.getAssets().open(fileName)) {
            properties.load(inputStream);
        }
    }

    public static Setting getInstance() {
        Setting ins = instance;
        if (ins == null) {
            synchronized (Setting.class) {
                ins = instance;
                if (ins == null) {
                    ins = instance = new Setting();
                }
            }
        }
        return ins;
    }

    public String getBackUpLogDir() {
        return filePath.getProperty(ConstKey.PATH.DIR_BACKUP_LOG, "logBackup");
    }

    public String getLogDir() {
        return filePath.getProperty(ConstKey.PATH.DIR_LOG, "log");
    }

    public String getConfigPath() {
        return filePath.getProperty(ConstKey.PATH.CONFIG_PATH, "config/carConfig.json");
    }

    public String getYardConfigPath() {
        return filePath.getProperty(ConstKey.PATH.YARD_CONFIG_PATH, "config/yardConfig.json");
    }

    public String getServerPingIp() {
        return apiProperties.getProperty(ConstKey.URL.SERVER_PING_ADDR);
    }

    public String getSendDataUrl() {
        return apiProperties.getProperty(ConstKey.URL.SEND_DATA_URL);
    }

    public String getCheckCarIdUrl() {
        return apiProperties.getProperty(ConstKey.URL.CHECK_CAR_ID_URL);
    }

    public String getCheckUserIdUrl() {
        return apiProperties.getProperty(ConstKey.URL.CHECK_USER_ID_URL);
    }

    public String getCheckCommandUrl() {
        return apiProperties.getProperty(ConstKey.URL.CHECK_COMMAND_URL);
    }

    public String getCancelRequestUrl() {
        return apiProperties.getProperty(ConstKey.URL.CANCEL_REQUEST_URL);
    }

    public String getCheckRunnableUrl() {
        return apiProperties.getProperty(ConstKey.URL.RUNNABLE_URL);
    }

    public String getcheckCarPairUrl() {
        return apiProperties.getProperty(ConstKey.URL.CAR_PAIR_URL);
    }
}
