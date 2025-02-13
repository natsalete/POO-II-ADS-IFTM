/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sd.prj_exemplo_mvc_oracle.controller;

import com.sd.prj_exemplo_mvc_oracle.model.AbstractModel;
import com.sd.prj_exemplo_mvc_oracle.view.AbstractViewPanel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 *
 * @author clc
 */
public abstract class AbstractController implements PropertyChangeListener{
    private final ArrayList<AbstractViewPanel> registeredViews;
    private final ArrayList<AbstractModel> registeredModels;
    
    public AbstractController() {
        registeredViews = new ArrayList<>();
        registeredModels = new ArrayList<>();
    }

    public void addModel(AbstractModel model) {
        registeredModels.add(model);
        model.addPropertyChangeListener(this);
    }

    public void removeModel(AbstractModel model) {
        registeredModels.remove(model);
        model.removePropertyChangeListener(this);
    }
    
    public void addView(AbstractViewPanel view) {
        registeredViews.add(view);
    }

    public void removeView(AbstractViewPanel view) {
        registeredViews.remove(view);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        
        for (AbstractViewPanel view: registeredViews) {
            view.modelPropertyChange(evt);
        }
    }
    
    protected void setModelProperty(String propertyName, Object newValue) {
        
        for (AbstractModel model: registeredModels) {
            try {
                
                Method method = model.getClass().
                    getMethod("set"+propertyName, new Class[] {
                                                      newValue.getClass()
                                                  }
                             );
                method.invoke(model, newValue);
                
            } catch (Exception ex) {
                //  Handle exception
            }
        }
    }
}
