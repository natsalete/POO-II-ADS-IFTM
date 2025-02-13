package com.poo2.prj_sistema_de_gestao_financeira;

import com.sd.prj_mvc_view.MainView;
import javax.swing.SwingUtilities;


public class Prj_Sistema_de_Gestao_financeira {

    public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            mainView.setVisible(true);
        });
    }
}
