package com.placeti.avaliacao.repository;

import com.placeti.avaliacao.model.Comercio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//----------------------------------------------
/** Repositório para entidade Comercio */
//----------------------------------------------
@Repository
public interface ComercioRepository extends JpaRepository<Comercio, Long> {

    // Spring Data monta a query pelo nome do método
    List<Comercio> findByCidadeId(Long idCidade);
}
