/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impContest.shB2;

import com.nextone.common.ConstKey;
import com.nextone.contest.impCondition.OnOffImp.CheckDistanceIntoContest;
import com.nextone.contest.impContest.AbsSaHinhContest;
import com.nextone.model.yardConfigMode.ContestConfig;

import java.util.List;

/**
 * @author Admin
 */
public abstract class AbsContestHasMutiLine extends AbsSaHinhContest {

    protected final CheckDistanceIntoContest distanceIntoContest;
    private int index = -1;

    public AbsContestHasMutiLine(String name, int soundId, int timeout, List<ContestConfig> contestConfigs) {
        super(name, soundId, false, timeout);
        this.distanceIntoContest = new CheckDistanceIntoContest(contestConfigs);
    }

    public ContestConfig getContestConfig() {
        return this.distanceIntoContest.getContestConfig();
    }

    @Override
    protected boolean isIntoContest() {
        if (this.distanceIntoContest.isDistanceOutOfSpec(0)) {
            addErrorCode(ConstKey.ERR.WRONG_LANE);
            this.stop();
            return false;
        }
        if (this.distanceIntoContest.isEnoughMinDistanceSpec(0) && isAccept()
                && checkIntoContest()) {
            this.carModel.resetDistance();
            if (index >= 0) {
                this.soundPlayer.sayNumber(index + 1);
            }
            return true;
        }
        return false;
    }

    protected abstract boolean isAccept();

    protected boolean getWheelCrosserLineStatus(List<Boolean> status) {
        if (status.size() <= index || index == -1) {
            return false;
        }
        return status.get(index);
    }

    private boolean checkIntoContest() {
        this.index = this.distanceIntoContest.getIndexOfLine(0);
        if (this.index == -1) {
            addErrorCode(ConstKey.ERR.WRONG_LANE);
            this.stop();
            return false;
        } else {
            return true;
        }
    }

}
