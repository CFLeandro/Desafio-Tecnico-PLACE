package com.placeti.avaliacao.repository;

import com.placeti.avaliacao.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//----------------------------------------------
/** Repositório para entidade Cidade */
//----------------------------------------------
@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}
