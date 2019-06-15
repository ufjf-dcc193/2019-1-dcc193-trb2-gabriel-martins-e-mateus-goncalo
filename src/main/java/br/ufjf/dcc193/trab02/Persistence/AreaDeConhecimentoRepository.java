package br.ufjf.dcc193.trab02.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufjf.dcc193.trab02.Models.AreaDeConhecimento;

public interface AreaDeConhecimentoRepository extends JpaRepository<AreaDeConhecimento, Long>{

	AreaDeConhecimento findByNome(String nome);
}