/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impCondition.timerCondition;

import com.nextone.common.ConstKey;
import com.nextone.contest.AbsContest;
import com.nextone.contest.impCondition.AbsTimerCondition;

/**
 *
 * @author Admin
 */
public class TimeOutContest extends AbsTimerCondition {

    private final AbsContest contest;

    public TimeOutContest(AbsContest contest) {
        super(contest.getTimeout(), false);
        this.contest = contest;
        this.setContestDataModel(contest.getContestModel());
    }

    @Override
    protected boolean signal() {
        AbsContest a = contest;
        return a != null && a.getStatus() == AbsContest.RUNNING;
    }

    @Override
    protected void action() {
        setErrorcode(ConstKey.ERR.TIME_LIMIT_EXCEEDED);
    }

}
