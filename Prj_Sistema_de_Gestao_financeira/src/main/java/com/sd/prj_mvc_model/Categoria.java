package com.sd.prj_mvc_model;

public class Categoria {
    private Long id;
    private String nome;

    public Categoria() {
    }
       
    // Construtor
    public Categoria(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    
    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
