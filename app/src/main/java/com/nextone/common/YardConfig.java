/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.common;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.nextone.model.MyContextManagement;
import com.nextone.model.yardConfigMode.YardConfigModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import lombok.Getter;

/**
 * @author Admin
 */
public class YardConfig {

    private static volatile YardConfig instance;
    private final String path;
    @Getter
    private YardConfigModel yardConfigModel;

    private YardConfig() {
        Context context = MyContextManagement.getInstance().getAplicationContext();
        this.yardConfigModel = new YardConfigModel();
        this.path = new File(context.getFilesDir(), Setting.getInstance().getYardConfigPath()).getAbsolutePath();
        try {
            File f = new File(this.path);
            if (!f.exists()) {
                update();
                return;
            }
            String data = this.readFile(f);
            if (data != null && !data.isBlank()) {
                this.yardConfigModel = new Gson().fromJson(data, YardConfigModel.class);
            } else {
                update();
            }
        } catch (Exception e) {
            Log.e(getClass().getName(), "YardConfig:", e);
            update();
        }
    }

    private String readFile(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            return new String(data);
        }catch (Exception e){
            Log.e(getClass().getName(), "readFile:", e);
            return null;
        }
    }

    private void writeFile(String path, String content) throws IOException {
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

    public static YardConfig getInstance() {
        YardConfig ins = instance;
        if (ins == null) {
            synchronized (YardConfig.class) {
                ins = instance;
                if (ins == null) {
                    ins = instance = new YardConfig();
                }
            }
        }
        return ins;
    }

    public synchronized final void update() {
        try {
            this.writeFile(this.path, MyObjectMapper.toJsonString(this.yardConfigModel));
        } catch (Exception e) {
            Log.e(getClass().getName(), "update:", e);
        }
    }
}
