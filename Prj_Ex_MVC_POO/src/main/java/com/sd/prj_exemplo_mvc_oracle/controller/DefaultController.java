/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sd.prj_exemplo_mvc_oracle.controller;

import java.awt.Font;

/**
 *
 * @author clc
 */
public class DefaultController extends AbstractController{
    
    public static final String DOCUMENT_NAME_PROPERTY = "Name";
    public static final String DOCUMENT_WIDTH_PROPERTY = "Width";
    public static final String DOCUMENT_HEIGHT_PROPERTY = "Height";
    public static final String ELEMENT_TEXT_PROPERTY = "Text";
    public static final String ELEMENT_FONT_PROPERTY = "Font";
    public static final String ELEMENT_X_PROPERTY = "X";
    public static final String ELEMENT_Y_PROPERTY = "Y";    
    public static final String ELEMENT_OPACITY_PROPERTY = "Opacity";
    public static final String ELEMENT_ROTATION_PROPERTY = "Rotation";
        
    public void changeDocumentName(String newName) {
        setModelProperty(DOCUMENT_NAME_PROPERTY, newName);                                 
    }
    
    public void changeDocumentWidth(int newWidth) {
        setModelProperty(DOCUMENT_WIDTH_PROPERTY, newWidth);                                 
    }
        
    public void changeDocumentHeight(int newHeight) {
        setModelProperty(DOCUMENT_HEIGHT_PROPERTY, newHeight);                         
    }
        
    public void changeElementText(String newText) {
        setModelProperty(ELEMENT_TEXT_PROPERTY, newText);                 
    }
        
    public void changeElementFont(Font newFont) {
        setModelProperty(ELEMENT_FONT_PROPERTY, newFont);
    }
    
    public void changeElementXPosition(int newX) {
        setModelProperty(ELEMENT_X_PROPERTY, newX);         
    }
    
    public void changeElementYPosition(int newY) {
        setModelProperty(ELEMENT_Y_PROPERTY, newY);        
    }
        
    public void changeElementOpacity(int newOpacity) {
        setModelProperty(ELEMENT_OPACITY_PROPERTY, newOpacity);
    }
    
    public void changeElementRotation(int newRotation) {
        setModelProperty(ELEMENT_ROTATION_PROPERTY, newRotation);        
    }
}
