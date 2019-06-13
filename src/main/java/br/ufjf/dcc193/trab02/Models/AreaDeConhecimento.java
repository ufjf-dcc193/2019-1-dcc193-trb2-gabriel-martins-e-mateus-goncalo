package br.ufjf.dcc193.trab02.Models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class AreaDeConhecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;

    @ManyToMany(mappedBy = "areasDeConhecimento")
    private Set<Avaliador> avaliadores;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Trabalho trabalho;

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