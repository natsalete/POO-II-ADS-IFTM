/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.sd.prj_exemplo_mvc_oracle;

import com.sd.prj_exemplo_mvc_oracle.controller.DefaultController;
import com.sd.prj_exemplo_mvc_oracle.model.DocumentModel;
import com.sd.prj_exemplo_mvc_oracle.model.TextElementModel;
import com.sd.prj_exemplo_mvc_oracle.view.DisplayViewPanel;
import com.sd.prj_exemplo_mvc_oracle.view.PropertiesViewPanel;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author clc
 */
public class Prj_Exemplo_MVC_Oracle {

    public Prj_Exemplo_MVC_Oracle() {
        
        // Cria o model
        TextElementModel textElementModel = new TextElementModel();
        DocumentModel documentModel = new DocumentModel();

        // Cria o controller
        DefaultController controller = new DefaultController();
        
        // Cria painéis de visualização e conecta ao controlador
        DisplayViewPanel displayViewPanel = new DisplayViewPanel(controller);       
        PropertiesViewPanel propertiesViewPanel = new PropertiesViewPanel(controller);
        
        // Registrar visualizações e modelos com o controlador
        controller.addView(displayViewPanel);
        controller.addView(propertiesViewPanel);
        controller.addModel(textElementModel);
        controller.addModel(documentModel);
        
        // Configurar dados para modelos
        textElementModel.initDefault();
        documentModel.initDefault();        
        
        // Criar objetos GUI
        JFrame displayFrame = new JFrame("Display (View 1)");
        displayFrame.getContentPane().add(displayViewPanel, BorderLayout.CENTER);
        displayFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        displayFrame.pack();
        
        JDialog propertiesDialog = new JDialog(displayFrame, "Properties (View 2)");
        propertiesDialog.setModal(false);
        propertiesDialog.getContentPane().add(propertiesViewPanel, BorderLayout.CENTER);
        propertiesDialog.pack();
        
        // Mostra formulários (objetos GUI)
        displayFrame.setVisible(true);
        propertiesDialog.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Prj_Exemplo_MVC_Oracle();
    }
}
