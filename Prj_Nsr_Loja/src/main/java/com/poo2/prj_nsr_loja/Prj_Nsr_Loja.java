package com.poo2.prj_nsr_loja;


public class Prj_Nsr_Loja {

    public static void main(String[] args) {
        System.out.println("Hello World!");
     
    /*    
    CREATE TABLE Cliente (
	idCliente SERIAL PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	data_nascimento DATE NOT NULL,
	cpf VARCHAR(14) NOT NULL
    );

    CREATE TABLE Venda(
            idvenda SERIAL PRIMARY KEY,
            data_venda DATE NOT NULL,
            total_venda DOUBLE PRECISION NOT NULL,
            idCliente INTEGER NOT NULL,
        FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente)
    );


    CREATE TABLE Produto(
            idProduto SERIAL PRIMARY KEY,
            nome VARCHAR(100) NOT NULL,
            marca VARCHAR(60) NOT NULL,
            modelo VARCHAR(50) NOT NULL,
            valor DOUBLE PRECISION NOT NULL
    );

    CREATE TABLE Venda_Produto(
            quantidade DOUBLE PRECISION NOT NULL,
            total_item DOUBLE PRECISION NOT NULL,
            desconto_item DOUBLE PRECISION NOT NULL,
            idVenda INTEGER NOT NULL,
            idProduto INTEGER NOT NULL,
            FOREIGN KEY(idVenda) REFERENCES Venda(idVenda),
            FOREIGN KEY(idProduto) REFERENCES Produto(idProduto)
    );

    ALTER TABLE Venda
    ALTER COLUMN data_venda SET DEFAULT CURRENT_DATE;
    ALTER TABLE Venda_Produto
    ADD PRIMARY KEY (idVenda, idProduto);*/



    }
}
