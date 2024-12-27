package com.nextone.common;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import com.nextone.model.config.MCU_CONFIG_MODEL;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import com.nextone.model.MyContextManagement;

public class CarConfig {

    private static final String MCU_KEY = "MCU";
    private static final String CAR_ID_KEY = "carId";
    private static final String TEST_STATUS_KEY = "testStatus";
    private static final String PASSWORD = "password";
    private static final String YARD_IP = "yardIp";
    private static final String EXAM_ID = "examId";
    private static final String YARD_PORT = "yardPort";
    private static final String MODE_INDEX = "modeIndex";
    private static final String CENTER_NAME = "centerName";
    private static final String YARDUSERNAME = "yardusername";
    private static final String YARDPASSWORD = "yardpassword";
    public static final String TAG = "CarConfig";
    private static volatile CarConfig instance;

    private JSONObject jsono;
    private final String path;
    private final Context context;

    private CarConfig() {
        this.context = MyContextManagement.getInstance().getAplicationContext();
        this.path = new File(context.getFilesDir(), "carConfig.json").getAbsolutePath();
        try {
            File file = new File(this.path);
            if (!file.exists()) {
                setDefaultConfig();
                return;
            }
            String data = readFile(file);
            if (!data.isBlank()) {
                this.jsono = new JSONObject(data);
            } else {
                setDefaultConfig();
            }
        } catch (Exception e) {
            Log.e(TAG, "CarConfig:", e);
            setDefaultConfig();
        }
    }

    public static CarConfig getInstance() {
        CarConfig ins = instance;
        if (ins == null) {
            synchronized (CarConfig.class) {
                ins = instance;
                if (ins == null) {
                    ins = instance = new CarConfig();
                }
            }
        }
        return ins;
    }

    public String getCarId() {
        try {
            String val = this.jsono.getString(CAR_ID_KEY);
            return val.isBlank() ? "0" : val;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String getExamId() {
        return getString(EXAM_ID, "0");
    }

    public String getTestStatusValue() {
        try {
            return this.jsono.getString(TEST_STATUS_KEY);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeTestStatusValue() {
        try {
            this.jsono.put(TEST_STATUS_KEY, null);
            update();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCurrentTestStatusValue(String id, String examId) {
        try {
            this.jsono.put(TEST_STATUS_KEY, String.format("%s,%s", id, examId));
            update();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void setExamId(String examid) {
        try {
            this.jsono.put(EXAM_ID, examid == null ? "0" : examid);
            update();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMcuConfig(MCU_CONFIG_MODEL mcu_config_model) {
        if (mcu_config_model == null) {
            return;
        }
        try {
            jsono.put(MCU_KEY, MyObjectMapper.convertValue(mcu_config_model, JSONObject.class));
            update();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public MCU_CONFIG_MODEL getMcuConfig() {
        try {
            if (jsono.has(MCU_KEY)) {
                JSONObject ob = jsono.getJSONObject(MCU_KEY);
                if (ob != null) {
                    MCU_CONFIG_MODEL mcu_config_model = MyObjectMapper.map(ob, MCU_CONFIG_MODEL.class);
                    if (mcu_config_model != null) {
                        return mcu_config_model;
                    }
                }
            }
        } catch (Exception e) {
            setDefaultConfig();
        }
        return new MCU_CONFIG_MODEL();
    }

    public String getCenterName() {
        String centerName = "";
        try {
            if (jsono.has(CENTER_NAME)) {
                centerName = jsono.getString(CENTER_NAME);
            }
            return centerName;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String getString(String key) {
        try {
            return this.jsono.getString(key);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void setModeIndex(int index) {
        try {
            this.jsono.put(MODE_INDEX, index < 0 ? 0 : index);
            update();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public int getIndexOfModel() {
        try {
            return this.jsono.getInt(MODE_INDEX);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getYardPassword() {
        return this.getString(YARDPASSWORD, "f3cea34ed1507b50f09c236045bb1067");
    }

    public String getYardUser() {
        return this.getString(YARDUSERNAME, "client");
    }
    private void setDefaultConfig() {
        if (this.jsono == null) {
            this.jsono = new JSONObject();
        }
        try {
            this.jsono.put(MCU_KEY, new MCU_CONFIG_MODEL());
            this.jsono.put(CAR_ID_KEY, "0");
            this.jsono.put(CENTER_NAME, "");
            this.jsono.put(EXAM_ID, "0");
            this.jsono.put(PASSWORD, "e10adc3949ba59abbe56e057f20f883e");
            this.jsono.put(YARD_IP, "192.168.1.168");
            this.jsono.put(YARD_PORT, 6868);
            this.jsono.put(MODE_INDEX, 0);
            this.jsono.put(YARDPASSWORD, "f3cea34ed1507b50f09c236045bb1067");
            this.jsono.put(YARDUSERNAME, "client");
            update();
        } catch (Exception e) {
            Log.e(TAG, "setDefaultConfig:", e);
        }
    }

    private String readFile(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            return new String(data);
        }
    }

    private void writeFile(String path, String content) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(content.getBytes());
        }
    }

    public synchronized final void update() {
        try {
            writeFile(this.path, this.jsono.toString());
        } catch (Exception e) {
            Log.e(TAG, "update:", e);
            setDefaultConfig();
        }
    }

    public void setCarId(String carId) {
        try {
            jsono.put(CAR_ID_KEY, carId == null ? "0" : carId);
            update();
        } catch (JSONException e) {
            Log.e(TAG, "setCarId:", e);
        }
    }

    public String getPassword() {
        return getString(PASSWORD, "e10adc3949ba59abbe56e057f20f883e");
    }

    public void setPassword(String pw) {
        try {
            jsono.put(PASSWORD, pw == null || pw.isBlank() ? "e10adc3949ba59abbe56e057f20f883e" : pw);
            update();
        } catch (JSONException e) {
            Log.e(TAG, "setPassword:", e);
        }
    }

    public String getString(String key, String defaultValue) {
        try {
            return jsono.has(key) ? jsono.getString(key) : defaultValue;
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public String getYardIp() {
        return getString(YARD_IP, "192.168.1.168");
    }

    public int getYardPort() {
        return 6868;
    }
    // Các hàm getter/setter khác tương tự...
}
