package br.ufjf.dcc193.trab02.Models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Avaliador {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    private String email;
    private String chave;

    @OneToMany(mappedBy = "revisor", cascade = CascadeType.ALL)
    private Set<Revisao> revisoes;

    public Avaliador(Long id, String nome, String email, String chave) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.chave = chave;
    }

    public Avaliador(){

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

	public Set<Revisao> getRevisoes() {
		return revisoes;
	}

	public void setRevisoes(Set<Revisao> revisoes) {
		this.revisoes = revisoes;
	}
}