package br.com.qeep.moving.loja.pecas.spring.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.qeep.moving.loja.pecas.spring.api.entity.Peca;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PecaRepository extends CrudRepository<Peca, Long>{

	List<Peca> findAllByNomeStartingWith(String texto);
	
	List<Peca> findAllByModeloCarro(String modeloCarro);
	
	List<Peca> findAllByCategoria(String categoria);

}