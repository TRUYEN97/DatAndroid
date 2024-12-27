/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.modelTest.process;

import com.nextone.pretreatment.KeyEventManagement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author Admin
 */
@Builder
@AllArgsConstructor
@Getter
public class ModeParam {
    private final KeyEventManagement eventManagement;
    private final ProcessModel processModel;
}
