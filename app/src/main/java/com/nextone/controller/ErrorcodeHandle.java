/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller;

import android.util.Log;

import com.nextone.common.ConstKey;
import com.nextone.datandroid.R;
import com.nextone.model.modelTest.Errorcode;
import com.nextone.model.modelTest.ErrorcodeWithContestNameModel;
import com.nextone.model.modelTest.contest.ContestDataModel;
import com.nextone.model.modelTest.process.ProcessModel;
import com.nextone.output.SoundPlayer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

/**
 *
 * @author Admin
 */
public class ErrorcodeHandle {

    public static final String TAG = "ErrorcodeHandle";
    private static volatile ErrorcodeHandle instance;
    private final ProcessModel processModel;
    private final SoundPlayer soundPlayer;
    private final Map<String, Errorcode> mapErrorcodes;
    @Getter
    private final List<ErrorcodeWithContestNameModel> ewcnms;

    private ErrorcodeHandle() {
        ProcessModelHandle processModelHandle = ProcessModelHandle.getInstance();
        this.processModel = processModelHandle.getProcessModel();
        this.soundPlayer = SoundPlayer.getInstance();
        this.mapErrorcodes = new HashMap<>();
        this.ewcnms = new ArrayList<>();
        initErrorCode();
    }

    public static ErrorcodeHandle getInstance() {
        ErrorcodeHandle ins = instance;
        if (ins == null) {
            synchronized (ErrorcodeHandle.class) {
                ins = instance;
                if (ins == null) {
                    instance = ins = new ErrorcodeHandle();
                }
            }
        }
        return ins;
    }

    private void initErrorCode() {
        putErrorCode(ConstKey.ERR.SPEED_LIMIT_EXCEEDED, "Chạy quá tốc độ", 1, R.raw.speed_limit_exceeded);
        putErrorCode(ConstKey.ERR.OVERALL_TIME_EXCEEDED, "Quá tổng thời gian", 1, R.raw.overall_time_exceeded);
        putErrorCode(ConstKey.ERR.USED_GEAR_3_UNDER_20KMH, "Tay số không phù hợp", 2, R.raw.used_gear_3_under_20_kmh);
        putErrorCode(ConstKey.ERR.TIME_LIMIT_EXCEEDED, "Quá thời gian 1 bài", 5, R.raw.time_limit_exceeded);
        putErrorCode(ConstKey.ERR.WHEEL_CROSSED_LINE, "Bánh xe đè vạch", 5, R.raw.wheel_crossed_line);
        putErrorCode(ConstKey.ERR.IGNORED_STOP_SIGNAL, "Không C.H tín hiệu dừng", 5, R.raw.ignored_stop_signal);
        putErrorCode(ConstKey.ERR.ERR_PAUSE_MOVE2, "errPauseMove2", 5, R.raw.error);
        putErrorCode(ConstKey.ERR.OVER_20S_TO_START, "Không K.H sau 20s", 5, R.raw.over_20_s_to_start);
        putErrorCode(ConstKey.ERR.NO_START_SIGNAL_LEFT, "không xi nhan trái", 5, R.raw.no_start_signal_left);
        putErrorCode(ConstKey.ERR.SIGNAL_KEPT_ON_OVER_5M, "xi nhan quá 5m", 5, R.raw.signal_kept_on_over_5_m);
        putErrorCode(ConstKey.ERR.STOP_BEFORE_DES, "Dừng chưa đến vị trí", 5, R.raw.stop_before_des);
        putErrorCode(ConstKey.ERR.STOP_AFTER_DES, "Dừng xe quá vị trí", 5, R.raw.stop_after_des);
        putErrorCode(ConstKey.ERR.DONT_STOP_AS_REQUIRED, "Không dừng xe", 5,  R.raw.dont_stop_as_required);
        putErrorCode(ConstKey.ERR.FAILED_PASS_INTERSECTION_OVER_20S, "Ko qua ngã tư sau 20s", 5, R.raw.failed_pass_intersection_over_20s);
        putErrorCode(ConstKey.ERR.PARCKED_WRONG_POS, "Ghép xe sai vị trí", 5, R.raw.parcked_wrong_pos);
        putErrorCode(ConstKey.ERR.INCORRECT_GEAR_SHIFT, "Tăng số sai", 5, R.raw.incorrect_gear_shift);
        putErrorCode(ConstKey.ERR.FAILED_TO_REACH_REQUIRED_SPEED, "Không đạt tốc độ", 5, R.raw.failed_to_reach_required_speed);
        putErrorCode(ConstKey.ERR.FAILED_SHIFTUP_GEAR_IN_100M, "Không tăng số", 5, R.raw.failed_shiftup_gear_in_100m);
        putErrorCode(ConstKey.ERR.FAILED_TO_SHIFT_HIGH_GEAR, "Không tăng số", 5, R.raw.failed_to_shift_high_gear);
        putErrorCode(ConstKey.ERR.FAILED_SHIFTDOWN_GEAR_IN_100M, "Không giảm số", 5, R.raw.failed_shiftdown_gear_in_100m);
        putErrorCode(ConstKey.ERR.FAILED_TO_SHIFT_LOW_GEAR, "Không giảm số", 5, R.raw.failed_to_shift_low_gear);
        putErrorCode(ConstKey.ERR.INCORRECT_GEAR_DOWNSHIFT, "Giảm số sai", 5, R.raw.incorrect_gear_downshift);
        putErrorCode(ConstKey.ERR.NO_SIGNAL_RIGHT_END, "Không xi nhan phải", 5, R.raw.no_signal_right_end);
        putErrorCode(ConstKey.ERR.NO_SIGNAL_TURN_LEFT, "Không xi nhan trái", 5, R.raw.no_signal_turn_left);
        putErrorCode(ConstKey.ERR.NO_SIGNAL_TURN_RIGHT, "Không xi nhan phải", 5, R.raw.no_signal_turn_right);
        putErrorCode(ConstKey.ERR.ENGINE_STALLED, "Chết máy", 5, R.raw.engine_stalled);
        putErrorCode(ConstKey.ERR.INCORRECT_PARCKING, "Ghép xe sai", 5, R.raw.incorrect_parcking);
        putErrorCode(ConstKey.ERR.ENGINE_SPEED_EXCEEDED_4000_RPM, "ĐC quá 4000 vòng/phút", 5, R.raw.engine_speed_exceeded_4000_rpm);
        putErrorCode(ConstKey.ERR.SEATBELT_NOT_FASTENED, "Không thắt dây an toàn", 5, R.raw.seatbelt_not_fastened);
        putErrorCode(ConstKey.ERR.FAILED_APPLY_PARKING_BRAKE, "Không kéo phanh tay", 5, R.raw.failed_apply_parking_brake);
        putErrorCode(ConstKey.ERR.PARKING_BRAKE_NOT_RELEASED, "Không nhả phanh tay", 5, R.raw.parking_brake_not_released);
        putErrorCode(ConstKey.ERR.FAILED_SHIFTTO_NEUTRAL, "Không về hết số", 5, R.raw.failed_shiftto_neutral);
        putErrorCode(ConstKey.ERR.HEAVY_SHAKING, "Rung xe", 5, R.raw.heavy_shaking);
        putErrorCode(ConstKey.ERR.FAILED_SHIFTUP_GEAR_IN_15M, "15m Không tăng số", 5, R.raw.failed_shiftup_gear_in_15m);
        putErrorCode(ConstKey.ERR.RAN_A_RED_LIGHT, "Vượt đèn đỏ", 10, R.raw.ran_a_red_light);
        putErrorCode(ConstKey.ERR.NO_EMERGENCY_SIGNAL, "Ko tuân thủ T.H khẩn cấp", 10, R.raw.no_emergency_signal);
        putErrorCode(ConstKey.ERR.VIOLATION_TRAFFIC_RULES, "Quy tắc giao thông", 10, R.raw.violation_traffic_rules);
        putErrorCode(ConstKey.ERR.IGNORED_INSTRUCTIONS, "Không tuân thu hiệu lệnh", 25, R.raw.ignored_instructions);
        putErrorCode(ConstKey.ERR.CAUSED_AN_ACCIDENT, "Gây tai nạn", 25, R.raw.caused_an_accident);
        putErrorCode(ConstKey.ERR.SWERVED_OUT_OF_LANE, "Gây tai nạn", 25, R.raw.swerved_out_of_lane);
        putErrorCode(ConstKey.ERR.FAILED_PASS_INTERSECTION_OVER_30S, "Ko qua ngã tư sau 30s", 25, R.raw.failed_pass_intersection_over_30s);
        putErrorCode(ConstKey.ERR.OVER_30S_TO_START, "Không K.H sau 30s", 25, R.raw.over_30_s_to_start);
        putErrorCode(ConstKey.ERR.STOP_AFTER_DES_2, "Dừng xe quá vị trí", 25, R.raw.stop_after_des_2);
        putErrorCode(ConstKey.ERR.DONT_STOP_AS_REQUIRED_2, "Không dừng xe", 25, R.raw.dont_stop_as_required_2);
        putErrorCode(ConstKey.ERR.ROLLED_BACK_OVER_50M, "Trôi dốc quá 50cm", 25, R.raw.rolled_back_over_50_m);
        putErrorCode(ConstKey.ERR.WHEEL_OUT_OF_PATH, "không qua vệt bánh xe", 25, R.raw.wheel_out_of_path);
        putErrorCode(ConstKey.ERR.INCORRECTLY_FINISHED, "Kết thúc sai", 25, R.raw.incorrectly_finished);
        putErrorCode(ConstKey.ERR.WRONG_LANE, "Sai làn đường", 25, R.raw.wrong_lane);
        putErrorCode(ConstKey.ERR.WRONG_WAY, "Đi sai đường", 25, R.raw.wrong_way);
        putErrorCode(ConstKey.ERR.IGNORED_PARKING, "Không ghép xe", 25, R.raw.incorrect_parcking);
        putErrorCode(ConstKey.ERR.FAILED_COMPLETE_PARKING, "Không hoàn thành ghép xe", 25, R.raw.failed_complete_parking);
        putErrorCode(ConstKey.ERR.DISQUALIFIED, "Bị truất quyền thi", 25, R.raw.disqualified);
        /////////////////////
        this.putErrorCode(
                ConstKey.ERR.TT,
                new Errorcode("TT", 5, "KO TANG TOC DO", R.raw.tt));
        this.putErrorCode(ConstKey.ERR.GT,
                new Errorcode("GT", 5, "KO GIAM TOC DO", R.raw.gt));
    }

    public void putErrorCode(String errName, String description, int point, int soundId) {
        putErrorCode(errName, errName, description, point, soundId);
    }

    public void putErrorCode(String errCode, String errName, String description, int point, int soundId) {
        if (errCode == null || errName == null) {
            return;
        }
        this.putErrorCode(errCode, new Errorcode(errName, point, description, soundId));
    }

    public void clear() {
        this.ewcnms.clear();
    }

    public void putErrorCode(String key, Errorcode errorcode) {
        if (errorcode == null) {
            return;
        }
        this.mapErrorcodes.put(key, errorcode);
    }

    public void addBaseErrorCode(String key) {
        addBaseErrorCode(key, null);
    }

    public void addBaseErrorCode(String key, JSONObject jsono) {
        try {
            Errorcode errorcode = this.mapErrorcodes.get(key);
            if (errorcode == null || errorcode.getErrKey() == null) {
                return;
            }
            this.processModel.addErrorcode(errorcode);
            this.ewcnms.add(new ErrorcodeWithContestNameModel(errorcode, ""));
            this.soundPlayer.sayErrorCode(errorcode.getSoundId());
            this.processModel.subtract(errorcode.getErrPoint());
        } catch (Exception e) {
            Log.e(TAG, "addBaseErrorCode: ", e);
        }
    }

    public void addContestErrorCode(String key, ContestDataModel dataModel) {
        try {
            if (dataModel == null) {
                return;
            }
            Errorcode errorcode = this.mapErrorcodes.get(key);
            if (errorcode == null || errorcode.getErrKey() == null) {
                return;
            }
            this.ewcnms.add(new ErrorcodeWithContestNameModel(errorcode, dataModel.getContestName()));
            this.soundPlayer.sayErrorCode(errorcode.getSoundId());
            dataModel.addErrorCode(errorcode);
            this.processModel.subtract(errorcode.getErrPoint());
        } catch (Exception e) {
            Log.e(TAG, "addContestErrorCode: ", e);
        }
    }

    public Errorcode getErrorCode(String errKey) {
        return this.mapErrorcodes.get(errKey);
    }
}
