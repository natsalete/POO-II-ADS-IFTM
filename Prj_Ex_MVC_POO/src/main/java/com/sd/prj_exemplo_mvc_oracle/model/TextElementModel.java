/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sd.prj_exemplo_mvc_oracle.model;

import com.sd.prj_exemplo_mvc_oracle.controller.DefaultController;
import java.awt.Font;

/**
 *
 * @author clc
 */
public class TextElementModel extends AbstractModel{
    private String text;
    private Font font;
    private Integer x;
    private Integer y;
    private Integer opacity;
    private Integer rotation;
    
    public TextElementModel()
    {
    }
 
    public void initDefault() {
        
        setOpacity(89);
        setRotation(0);
        setText("Sample Text");
        setFont(new Font("Arial", Font.BOLD, 24));
        setX(50);
        setY(50);
        
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        
        String oldText = this.text;
        this.text = text;
        
        firePropertyChange(DefaultController.ELEMENT_TEXT_PROPERTY, oldText, text);
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        
        Font oldFont = this.font;
        this.font = font;
        
        firePropertyChange(DefaultController.ELEMENT_FONT_PROPERTY, oldFont, font);
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        
        Integer oldX = this.x;
        this.x = x;
        
        firePropertyChange(DefaultController.ELEMENT_X_PROPERTY, oldX, x);
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        
        Integer oldY = this.y;
        this.y = y;
        
        firePropertyChange(DefaultController.ELEMENT_Y_PROPERTY, oldY, y);
    }

    public Integer getOpacity() {
        return opacity;
    }

    public void setOpacity(Integer opacity) {
        
        Integer oldOpacity = this.opacity;
        this.opacity = opacity;
        
        firePropertyChange(DefaultController.ELEMENT_OPACITY_PROPERTY, oldOpacity, opacity);
        
    }
    
    public Integer getRotation() {
        return rotation;
    }

    public void setRotation(Integer rotation) {
        
        Integer oldRotation = this.rotation;
        this.rotation = rotation;
        
        firePropertyChange(DefaultController.ELEMENT_ROTATION_PROPERTY, oldRotation, rotation);
        
    }
}
