package br.com.qeep.moving.loja.pecas.spring.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.qeep.moving.loja.pecas.spring.api.dto.VendaDTO;
import br.com.qeep.moving.loja.pecas.spring.api.entity.Peca;
import br.com.qeep.moving.loja.pecas.spring.api.entity.Venda;
import br.com.qeep.moving.loja.pecas.spring.api.exception.ErroDeNegocioException;
import br.com.qeep.moving.loja.pecas.spring.api.repository.VendaRepository;


@Service
public class VendaService {

	@Autowired
	VendaRepository vendaRepository;

	@Autowired
	PecaService pecaService;

	public Venda realizaVenda(VendaDTO vendaDto) throws ErroDeNegocioException {

		Venda venda = vendaDto.toObject();

		Optional<Peca> pecaOpt = pecaService.consultaPeca(venda.getCodBarras());

		if (pecaOpt.isEmpty()) {
			throw new ErroDeNegocioException("A venda não pode ser realizada pois a peça não existe!");
		}

		Peca peca = pecaOpt.get();

		if (peca.getQuantidadeEmEstoque() < venda.getQuantidade()) {
			throw new ErroDeNegocioException(
					"A venda não pode ser realizada pois a quantidade em estoque é menor do que a requisita! Hoje há em estoque "
							+ peca.getQuantidadeEmEstoque() + " peças");
		}

		peca.setQuantidadeEmEstoque(peca.getQuantidadeEmEstoque() - venda.getQuantidade());
		venda.setValorVenda(venda.getQuantidade() * peca.getPrecoVenda());

		pecaService.alteraPeca(peca.getCodigoDeBarras(), peca);
		return vendaRepository.save(venda);
	}

	public List<Venda> listarVendas() {
		return (List<Venda>) vendaRepository.findAll();
	}

	public Float calculaFaturamento() {
		return vendaRepository.calculaFaturamento();
	}

	public List<Venda> listaVendasPorVendedor(String vendedor) {
		return vendaRepository.findAllByNomeVendedor(vendedor);
	}

	public List<Venda> listaVendasPorPagamento(String formaPagamento) {
		return vendaRepository.findAllByFormaPagamento(formaPagamento);
	}

	public boolean removeVenda(Long idVenda) {

		if (!vendaRepository.existsById(idVenda)) {
			return false;
		}

		vendaRepository.deleteById(idVenda);
		return true;
	}

}
