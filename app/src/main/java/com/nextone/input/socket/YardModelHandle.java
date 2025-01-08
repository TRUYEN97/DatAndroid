/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.input.socket;

import android.util.Log;

import com.nextone.common.CarConfig;
import com.nextone.common.Util;
import com.nextone.common.YardConfig;
import com.nextone.common.communication.socket.Unicast.Client.SocketClient;
import com.nextone.common.communication.socket.Unicast.commons.Interface.IReceiver;
import com.nextone.model.input.yard.YardModel;
import com.nextone.model.input.yard.YardRankModel;
import com.nextone.model.yardConfigMode.ContestConfig;
import com.nextone.model.yardConfigMode.YardConfigModel;
import com.nextone.model.yardConfigMode.YardRankConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import lombok.Getter;

/**
 * @author Admin
 */
public class YardModelHandle {

    public static final String YARD_MODEL_HANDLE = "YardModelHandle";
    private static volatile YardModelHandle instance;
    private static final String INPUTS = "inputs";
    private static final String TRAFFIC_LIGHT_MODEL = "trafficLightModel";
    private static final String TRAFFIC_LIGHT_MODEL1 = "trafficLightModel1";
    private static final String TRAFFIC_LIGHT = "trafficLight";
    private static final String TIME = "time";
    private final SocketClient socketClient;
    private final CarConfig carConfig;
    private final YardConfigModel yardConfig;
    @Getter
    private final YardModel yardModel;
    private Thread thread;

    private YardModelHandle() {
        this.carConfig = CarConfig.getInstance();
        this.yardConfig = YardConfig.getInstance().getYardConfigModel();
        IReceiver<SocketClient> receiver = (SocketClient commnunication, String data) -> analysisReciver(data);
        this.socketClient = new SocketClient(this.carConfig.getYardIp(),
                this.carConfig.getYardPort(), receiver);
        this.yardModel = new YardModel();
    }

    public static YardModelHandle getInstance() {
        YardModelHandle ins = instance;
        if (ins == null) {
            synchronized (YardModelHandle.class) {
                ins = instance;
                if (ins == null) {
                    ins = instance = new YardModelHandle();
                }
            }
        }
        return ins;
    }

    private void analysisReciver(String data) {
        if (data == null || data.isBlank()) {
            return;
        }
        try {
            JSONObject ob = new JSONObject(data);
            if (ob.isNull(INPUTS)) {
                JSONArray inputs = ob.getJSONArray(INPUTS);
                updateRank(this.yardConfig.getB(), inputs, this.yardModel.getRankB());
                updateRank(this.yardConfig.getC(), inputs, this.yardModel.getRankC());
                updateRank(this.yardConfig.getD(), inputs, this.yardModel.getRankD());
                updateRank(this.yardConfig.getE(), inputs, this.yardModel.getRankE());
            }
            if (ob.isNull(TRAFFIC_LIGHT_MODEL)) {
                JSONObject tl = ob.getJSONObject(TRAFFIC_LIGHT_MODEL);
                if (this.yardModel.getTrafficLightModel() != null) {
                    this.yardModel.getTrafficLightModel()
                            .setTrafficLight(tl.getInt(TRAFFIC_LIGHT));
                    this.yardModel.getTrafficLightModel()
                            .setTime(tl.getInt(TIME));
                }
            }
            if (ob.isNull(TRAFFIC_LIGHT_MODEL1)) {
                JSONObject tl = ob.getJSONObject(TRAFFIC_LIGHT_MODEL1);
                if (this.yardModel.getTrafficLightModel1() != null) {
                    this.yardModel.getTrafficLightModel1()
                            .setTrafficLight(tl.getInt(TRAFFIC_LIGHT));
                    this.yardModel.getTrafficLightModel1()
                            .setTime(tl.getInt(TIME));
                }
            }
        } catch (Exception e) {
            Log.e(YARD_MODEL_HANDLE, "analysisReciver: ", e);
        }
    }

    private void updateRank(YardRankConfig rankConfig, JSONArray inputs, YardRankModel rankModel) {
        updateContest(rankConfig.getDoXeDoc(), inputs, rankModel.getPackings());

        updateContest(rankConfig.getDoXeNgang(), inputs, rankModel.getPackings1());

        updateContest(rankConfig.getDuongS(), inputs, rankModel.getRoadSs());

        updateContest(rankConfig.getVetBanhXe(), inputs, rankModel.getRoadZs());

        updateContest(rankConfig.getDuongVuongGoc(), inputs, rankModel.getRoadZs1());
    }

    private void updateContest(List<ContestConfig> contestConfigs, JSONArray inputs, List<Boolean> contestInputs) {
        try {
            if (contestConfigs == null || inputs == null || contestInputs == null) {
                return;
            }
            Integer indexOfYardInput;
            ContestConfig contestConfig;
            boolean val;
            for (int i = 0; i < contestConfigs.size(); i++) {
                val = false;
                if ((contestConfig = contestConfigs.get(i)) != null
                        && (indexOfYardInput = contestConfig.getIndexOfYardInput()) != null) {
                    if (indexOfYardInput>= 0 && indexOfYardInput < inputs.length()) {
                        val = inputs.getBoolean(indexOfYardInput);
                    }
                }
                if (contestInputs.size() <= i) {
                    contestInputs.add(val);
                } else {
                    contestInputs.set(i, val);
                }
            }
        } catch (Exception e) {
            Log.e(YARD_MODEL_HANDLE, "updateContest: ", e);
        }
    }

    private boolean stop = false;

    public void start() {
        try {
            if (thread != null && thread.isAlive()) {
                return;
            }
            stop = false;
            this.thread = new Thread(() -> {
                while (!stop) {
                    try {
                        Log.i(YARD_MODEL_HANDLE, " yard start");
                        Util.delay(1000);
                        while (!this.socketClient.connect() && !stop) {
                            Util.delay(2000);
                        }
                        if (!stop) {
                            while (!sendApplyConnect()) {
                                Util.delay(1000);
                            }
                            Log.i(YARD_MODEL_HANDLE, " yard connected");
                            this.socketClient.run();
                            Log.i(YARD_MODEL_HANDLE, " yard disconnected");
                        }
                    } catch (Exception e) {
                        Log.e(YARD_MODEL_HANDLE, "start: while (!stop)", e);
                    }
                }
            });
            this.thread.start();
        } catch (Exception e) {
            Log.e(YARD_MODEL_HANDLE, "start:", e);
        }
    }

    private boolean sendApplyConnect() {
        return this.socketClient.send(
                new JSONObject(
                        Map.of("username", this.carConfig.getYardUser(),
                                "password", this.carConfig.getYardPassword()))
                        .toString());
    }

    public void stop() {
        try {
            stop = true;
            this.socketClient.disconnect();
            if (thread == null || !thread.isAlive()) {
                return;
            }
            while (thread.isAlive()) {
                thread.stop();
                if (!thread.isAlive()) {
                    break;
                }
                Util.delay(500);
            }
        } catch (Exception e) {
            Log.e(YARD_MODEL_HANDLE, "stop:", e);
        }
    }

    public boolean isConnect() {
        return this.socketClient.isConnect();
    }

}
