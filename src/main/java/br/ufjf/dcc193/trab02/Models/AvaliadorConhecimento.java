package br.ufjf.dcc193.trab02.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AvaliadorConhecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    private Long avaliadorId;
    private Long conhecimentoId;

    public AvaliadorConhecimento() {
    }

    public AvaliadorConhecimento(Long id, Long avaliador, Long conhecimento) {
        this.id = id;
        this.avaliadorId = avaliador;
        this.conhecimentoId = conhecimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAvaliador() {
        return avaliadorId;
    }

    public void setAvaliador(Long avaliador) {
        this.avaliadorId = avaliador;
    }

    public Long getConhecimento() {
        return conhecimentoId;
    }

    public void setConhecimento(Long conhecimento) {
        this.conhecimentoId = conhecimento;
    }
    
}