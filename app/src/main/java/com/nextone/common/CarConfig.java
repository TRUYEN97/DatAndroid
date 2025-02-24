package com.nextone.common;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.nextone.model.MyContextManagement;
import com.nextone.model.MyJson;
import com.nextone.model.config.MCU_CONFIG_MODEL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private static final String YARD_USER_NAME = "yardUserName";
    private static final String YARD_PASS_WORD = "yardPassword";
    public static final String TAG = "CarConfig";
    private static volatile CarConfig instance;

    private MyJson jsonb;
    private final String path;

    private CarConfig() {
        Context context = MyContextManagement.getInstance().getAplicationContext();
        this.path = new File(context.getFilesDir(), "carConfig.json").getAbsolutePath();
        try {
            File file = new File(this.path);
            if (!file.exists()) {
                setDefaultConfig();
                return;
            }
            String data = readFile(file);
            if (data != null && !data.isBlank()) {
                this.jsonb = new MyJson(data);
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
            String val = this.jsonb.getString(CAR_ID_KEY);
            return val.isBlank() ? "0" : val;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String getExamId() {
        return this.jsonb.getString(EXAM_ID, "0");
    }

    public String getTestStatusValue() {
        try {
            return this.jsonb.getString(TEST_STATUS_KEY);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeTestStatusValue() {
        try {
            this.jsonb.put(TEST_STATUS_KEY, null);
            update();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCurrentTestStatusValue(String id, String examId) {
        try {
            this.jsonb.put(TEST_STATUS_KEY, String.format("%s,%s", id, examId));
            update();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void setExamId(String examId) {
        try {
            this.jsonb.put(EXAM_ID, examId == null ? "0" : examId);
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
            jsonb.put(MCU_KEY, new JSONObject(new Gson().toJson(mcu_config_model)));
            update();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public MCU_CONFIG_MODEL getMcuConfig() {
        try {
            if (jsonb.has(MCU_KEY)) {
                JSONObject ob = jsonb.getJSONObject(MCU_KEY);
                MCU_CONFIG_MODEL mcu_config_model = new Gson().fromJson(ob.toString(), MCU_CONFIG_MODEL.class);
                if (mcu_config_model != null) {
                    return mcu_config_model;
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
            if (jsonb.has(CENTER_NAME)) {
                centerName = jsonb.getString(CENTER_NAME);
            }
            return centerName;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String getString(String key) {
        try {
            return this.jsonb.getString(key);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void setModeIndex(int index) {
        try {
            this.jsonb.put(MODE_INDEX, Math.max(index, 0));
            update();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public int getIndexOfModel() {
        try {
            return this.jsonb.getInt(MODE_INDEX);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getYardPassword() {
        return this.jsonb.getString(YARD_PASS_WORD, "f3cea34ed1507b50f09c236045bb1067");
    }

    public String getYardUser() {
        return this.jsonb.getString(YARD_USER_NAME, "client");
    }


    private void setDefaultConfig() {
        if (this.jsonb == null) {
            this.jsonb = new MyJson();
        }
        try {
            this.jsonb.put(MCU_KEY,  new JSONObject(new Gson().toJson(new MCU_CONFIG_MODEL())));
            this.jsonb.put(CAR_ID_KEY, "0");
            this.jsonb.put(CENTER_NAME, "");
            this.jsonb.put(EXAM_ID, "0");
            this.jsonb.put(PASSWORD, "e10adc3949ba59abbe56e057f20f883e");
            this.jsonb.put(YARD_IP, "192.168.137.1");
            this.jsonb.put(YARD_PORT, 28686);
            this.jsonb.put(MODE_INDEX, 0);
            this.jsonb.put(YARD_PASS_WORD, "f3cea34ed1507b50f09c236045bb1067");
            this.jsonb.put(YARD_USER_NAME, "client");
            update();
        } catch (Exception e) {
            Log.e(TAG, "setDefaultConfig:", e);
        }
    }

    private String readFile(File file) throws IOException {
        if (!file.exists()) return null;
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            return new String(data);
        }
    }

    private void writeFile(String path, String content){
        try {
            File file = new File(path);
            if (!file.exists()) {
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(content.getBytes());
            }
        } catch (Exception e) {
            Log.e(getClass().getName(), "writeFile:", e);
        }
    }

    public synchronized final void update() {
        try {
            writeFile(this.path, this.jsonb.toString());
        } catch (Exception e) {
            Log.e(TAG, "update:", e);
            setDefaultConfig();
        }
    }

    public void setCarId(String carId) {
        try {
            jsonb.put(CAR_ID_KEY, carId == null ? "0" : carId);
            update();
        } catch (JSONException e) {
            Log.e(TAG, "setCarId:", e);
        }
    }

    public String getPassword() {
        return this.jsonb.getString(PASSWORD, "e10adc3949ba59abbe56e057f20f883e");
    }

    public void setPassword(String pw) {
        try {
            jsonb.put(PASSWORD, pw == null || pw.isBlank() ? "e10adc3949ba59abbe56e057f20f883e" : pw);
            update();
        } catch (JSONException e) {
            Log.e(TAG, "setPassword:", e);
        }
    }

    public String getYardIp() {
        return this.jsonb.getString(YARD_IP, "192.168.137.1");
    }

    public int getYardPort() {
        return this.jsonb.getInt(YARD_PORT, 28686);
    }

    public void setYardIp(String ip) {
        try {
            this.jsonb.put(YARD_IP, ip);
        } catch (JSONException e) {
            Log.e(TAG, "setYardIp:", e);
        }
    }

    public void setYardPort(int port) {
        try {
            this.jsonb.put(YARD_PORT, port);
        } catch (JSONException e) {
            Log.e(TAG, "setYardPort:", e);
        }
    }
}
