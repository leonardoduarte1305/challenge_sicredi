package br.dev.leoduarte.sicredi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.dev.leoduarte.sicredi.model.Associado;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

}
