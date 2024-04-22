package com.ideau.api.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pessoas")
public class Pessoa {

    // Atributos da classe
    @Id // Anotação que indica que o atributo é uma chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estratégia de geração automática de valor para a chave primária
    private int codigo;
    private String nome;
    private int idade;

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }

    // Método toString para representação em string da classe
    public String toString(){
        return "ID: " + codigo + "  -   Nome: " + nome + "  -   Idade: " + idade;
    }
    
}
