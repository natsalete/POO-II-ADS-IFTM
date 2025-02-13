package com.sd.prj_prova_cad_inscricao.models;

/**
 *
 * @author natsa
 */
public class Nsr_Inscricao {
    private String nome;
    private String cpf;
    private String estado;
    private String atividade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }
     
}
