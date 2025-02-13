package com.sd.prj_prova_cad_inscricao.controllers;

import com.sd.prj_prova_cad_inscricao.models.Nsr_Inscricao;
import com.sd.prj_prova_cad_inscricao.views.Nsr_FormInscricaoEstado;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author natsa
 */
public class Nsr_InscricaoController {


        private List<Nsr_Inscricao> lstIncricoes;

        public Nsr_InscricaoController() {
            lstIncricoes = new ArrayList<>();
        }

        public boolean salvar(Nsr_FormInscricaoEstado view) {
            /*try {
                Nsr_Inscricao inscricao = new Nsr_Inscricao();
                

                //lstIncricoes.add(inscricao.getNome(), inscricao.getCpf() + inscricao.getEstado() + inscricao.getAtividade());

            } catch (NumberFormatException e) {
                return false;
            }*/return false;
            
        }
}
