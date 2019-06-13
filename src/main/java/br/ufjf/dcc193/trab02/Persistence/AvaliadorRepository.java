package br.ufjf.dcc193.trab02.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufjf.dcc193.trab02.Models.Avaliador;

public interface AvaliadorRepository extends JpaRepository<Avaliador, Long>{

    
}