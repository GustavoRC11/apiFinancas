package br.com.cotiinformatica.domain.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.domain.models.dtos.ContaRequestDto;
import br.com.cotiinformatica.domain.models.dtos.ContaResponseDto;
import br.com.cotiinformatica.domain.models.entities.Conta;
import br.com.cotiinformatica.domain.models.enums.TipoConta;
import br.com.cotiinformatica.domain.services.interfaces.ContaDomainService;
import br.com.cotiinformatica.infrastucture.repository.CategoriaRepository;
import br.com.cotiinformatica.infrastucture.repository.ContaRepository;

@Service
public class ContaDomainServiceImpl implements ContaDomainService {
	
	@Autowired ContaRepository contaRepository;
	@Autowired CategoriaRepository categoriaRepository;
	@Autowired ModelMapper modelMapper;
	
	

	@Override
	public ContaResponseDto adicionar(ContaRequestDto request) {
		
		var categoria = categoriaRepository.findById(request.getCategoriaId())
				.orElseThrow(() ->new IllegalArgumentException("Categoria não encontrada."));
		
		var conta = modelMapper.map(request, Conta.class);
		
		conta.setCategoria(categoria);
		
		categoriaRepository.save(conta);
		
		
		return modelMapper.map(conta, ContaResponseDto.class);
	}

	@Override
	public ContaResponseDto editar(Integer id, ContaRequestDto request) {

		var conta = contaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));
		
		var categoria = categoriaRepository.findById(request.getCategoriaId())
				.orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada."));
		
		conta.setNome(request.getNome());
		conta.setData(request.getData());
		conta.setValor(BigDecimal.valueOf(request.getValor()));
		conta.setTipo(TipoConta.valueOf(request.getTipo()));
		conta.setObservacoes(request.getObservacoes());
		conta.setCategoria(categoria);
		
		contaRepository.save(conta);
						
		return modelMapper.map(conta, ContaResponseDto.class);
	}

	@Override
	public ContaResponseDto excluir(Integer id) throws Exception {
		
		var conta = contaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));
		
		contaRepository.delete(conta);
				
		
		return null;
	}

	@Override
	public List<ContaResponseDto> consultar() throws Exception {
		
		var response = contaRepository.findAll()
				.stream()
				.map((conta) -> modelMapper.map(conta, ContaResponseDto.class))
				.collect(Collectors.toList());
		
		return response;
	}

	@Override
	public ContaResponseDto obterPorId(Integer id) throws Exception {
		
		var conta = contaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));
		
		return modelMapper.map(conta, ContaResponseDto.class);
	}

}
