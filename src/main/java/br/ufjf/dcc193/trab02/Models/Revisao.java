package br.ufjf.dcc193.trab02.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Revisao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne
    @JoinColumn
    private Avaliador revisor;

    @ManyToOne
    @JoinColumn
    private Trabalho trabalhoRevisao;

    private Integer nota;
    private String descricao;
    private Integer status;
    private String statusNome;

    public Revisao(Avaliador avaliador, Trabalho trabalho, Integer nota, String descricao, Integer status) {
        this.revisor = avaliador;
        this.trabalhoRevisao = trabalho;
        this.nota = nota;
        this.descricao = descricao;
        this.status = status;
    }

    public Revisao() {
    }

    public Avaliador getAvaliador() {
        return revisor;
    }

    public void setAvaliador(Avaliador avaliador) {
        this.revisor = avaliador;
    }

    public Trabalho getTrabalho() {
        return trabalhoRevisao;
    }

    public void setTrabalho(Trabalho trabalho) {
        this.trabalhoRevisao = trabalho;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Avaliador getRevisor() {
        return revisor;
    }

    public void setRevisor(Avaliador revisor) {
        this.revisor = revisor;
    }

    public Trabalho getTrabalhoRevisao() {
        return trabalhoRevisao;
    }

    public void setTrabalhoRevisao(Trabalho trabalhoRevisao) {
        this.trabalhoRevisao = trabalhoRevisao;
    }

    public String getStatusNome() {
        return statusNome;
    }

    public void setStatusNome(String statusNome) {
        this.statusNome = statusNome;
    }
    
    
    
}
