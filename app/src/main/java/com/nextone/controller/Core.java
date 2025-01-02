/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller;

import com.nextone.controller.modeController.ModeManagement;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.input.socket.YardModelHandle;
import com.nextone.output.SoundPlayer;
import com.nextone.pretreatment.KeyEventManagement;
import com.nextone.pretreatment.KeyEventsPackage;

import lombok.Getter;
/**
 * @author Admin
 */
@Getter
public class Core {

    private static volatile Core instance;
    private final ModeManagement modeManagement;
    private final KeyEventManagement eventManagement;
    private final KeyEventsPackage eventsPackage;

    private Core() {
        this.modeManagement = ModeManagement.getInstance();
        this.eventManagement = KeyEventManagement.getInstance();
        this.eventsPackage = new KeyEventsPackage(getClass().getSimpleName());
    }

    public static Core getInstance() {
        Core ins = instance;
        if (ins == null) {
            synchronized (Core.class) {
                ins = instance;
                if (ins == null) {
                    instance = ins = new Core();
                }
            }
        }
        return ins;
    }

    private boolean first = true;

    public void start() {
        if (!this.first) {
            return;
        }
        this.first = false;
        MCUSerialHandler.getInstance().start();
        SoundPlayer.getInstance().sayWelcome();
        YardModelHandle.getInstance().start();
        KeyEventManagement.getInstance().start();
        this.eventManagement.start();
        this.eventManagement.addKeyEventBackAge(this.eventsPackage);
    }

}
