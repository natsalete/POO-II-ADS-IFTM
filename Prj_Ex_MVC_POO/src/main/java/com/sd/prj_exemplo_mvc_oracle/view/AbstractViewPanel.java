/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sd.prj_exemplo_mvc_oracle.view;

import java.beans.PropertyChangeEvent;
import javax.swing.JPanel;

/**
 *
 * @author clc
 */
public abstract class AbstractViewPanel extends JPanel{
    
    public abstract void modelPropertyChange(PropertyChangeEvent evt);
}
