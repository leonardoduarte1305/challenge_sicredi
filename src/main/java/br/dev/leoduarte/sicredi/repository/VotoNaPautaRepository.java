package br.dev.leoduarte.sicredi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.dev.leoduarte.sicredi.model.VotoNaPauta;
import br.dev.leoduarte.sicredi.model.VotoNaPautaPK;

@Repository
public interface VotoNaPautaRepository extends JpaRepository<VotoNaPauta, VotoNaPautaPK> {

}
