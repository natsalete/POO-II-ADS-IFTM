/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sd.prj_exemplo_mvc_oracle.model;

import com.sd.prj_exemplo_mvc_oracle.controller.DefaultController;

/**
 *
 * @author clc
 */
public class DocumentModel extends AbstractModel{
    private String name;
    private Integer width;
    private Integer height;
   
    public DocumentModel()
    {   
    }
    
    public void initDefault() {
        
        setName("Sample Document");
        setWidth(500);
        setHeight(500);
        
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        
        String oldName = this.name;
        this.name = name;
        
        firePropertyChange(
        		DefaultController.DOCUMENT_NAME_PROPERTY, oldName, name);
    }


    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        
        Integer oldWidth = this.width;
        this.width = width;
        
        firePropertyChange(DefaultController.DOCUMENT_WIDTH_PROPERTY, oldWidth, width);
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        
        Integer oldHeight = this.height;
        this.height = height;
        
        firePropertyChange(DefaultController.DOCUMENT_HEIGHT_PROPERTY, oldHeight, height);
    } 
}
