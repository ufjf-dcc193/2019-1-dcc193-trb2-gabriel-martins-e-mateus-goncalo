package br.ufjf.dcc193.trab02.Models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class AreaDeConhecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    
    @OneToMany(mappedBy = "trabalhoAreaDeConhecimento", cascade = CascadeType.ALL)
    private Set<Trabalho> trabalhos;

    public AreaDeConhecimento(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public AreaDeConhecimento() {
    }

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