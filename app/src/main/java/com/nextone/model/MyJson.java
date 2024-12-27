package com.nextone.model;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class MyJson extends JSONObject {

    public MyJson(@NonNull String json) throws JSONException {
        super(json);
    }

    public MyJson() throws JSONException {
        super();
    }

    public boolean getBoolean(@NonNull String name, boolean defValue) {
        try {
            return has(name) ? getBoolean(name) : defValue;
        } catch (JSONException e) {
            return defValue;
        }
    }

    public int getInt(@NonNull String name, int defValue) {
        try {
            return has(name) ? getInt(name) : defValue;
        } catch (JSONException e) {
            return defValue;
        }
    }

    public double getDouble(@NonNull String name, double defValue) {
        try {
            return has(name) ? getDouble(name) : defValue;
        } catch (JSONException e) {
            return defValue;
        }
    }

    public String getString(@NonNull String name, String defValue) {
        try {
            return has(name) ? getString(name) : defValue;
        } catch (JSONException e) {
            return defValue;
        }
    }
    public JSONObject getJSONObject(@NonNull String name, JSONObject defValue) {
        try {
            return has(name) ? getJSONObject(name) : defValue;
        } catch (JSONException e) {
            return defValue;
        }
    }
}
