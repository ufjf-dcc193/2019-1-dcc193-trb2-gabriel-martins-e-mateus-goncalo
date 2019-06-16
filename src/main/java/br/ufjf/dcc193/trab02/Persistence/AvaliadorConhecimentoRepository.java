package br.ufjf.dcc193.trab02.Persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufjf.dcc193.trab02.Models.AvaliadorConhecimento;

public interface AvaliadorConhecimentoRepository extends JpaRepository<AvaliadorConhecimento, Long>{

    List<AvaliadorConhecimento> findAllByAvaliadorId (Long avaliadorId);
}