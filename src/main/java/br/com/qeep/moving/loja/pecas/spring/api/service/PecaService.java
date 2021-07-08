package br.com.qeep.moving.loja.pecas.spring.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.qeep.moving.loja.pecas.spring.api.entity.Peca;
import br.com.qeep.moving.loja.pecas.spring.api.exception.ErroDeNegocioException;
import br.com.qeep.moving.loja.pecas.spring.api.repository.PecaRepository;


@Service
public class PecaService {

	
	@Autowired
	PecaRepository pecaRepository;
	
	public Peca cadastraPeca(Peca peca) throws ErroDeNegocioException {
		
		if (pecaRepository.existsById(peca.getCodigoDeBarras())) {
			throw new ErroDeNegocioException("A peça já existe!");
		}
		
		return pecaRepository.save(peca);
	}

	public Optional<Peca> consultaPeca(Long codBarras) {
		return pecaRepository.findById(codBarras);
	}
	
	
	public List<Peca> listaPecas() {
		return (List<Peca>) pecaRepository.findAll();
	}
	
	public boolean removePeca(Long codBarras) {
		
		if (!pecaRepository.existsById(codBarras)) {
			return false;
		}
		
		pecaRepository.deleteById(codBarras);
		return true;
	}
	
	public Peca alteraPeca(Long codBarras, Peca peca) {
		
		if (!codBarras.equals(peca.getCodigoDeBarras())) {
			return null;
		}
		
		if (!pecaRepository.existsById(codBarras)) {
			return null;
		}
		
		return pecaRepository.save(peca);
	}

	public List<Peca> listaPecasComecadasCom(String texto) {
		return pecaRepository.findAllByNomeStartingWith(texto);
	}

	public List<Peca> listaPecasPorModelo(String modeloCarro) {
		return pecaRepository.findAllByModeloCarro(modeloCarro);
	}

	public List<Peca> listaPecasPorCategoria(String categoria) {
		return pecaRepository.findAllByCategoria(categoria);
	}
	
}