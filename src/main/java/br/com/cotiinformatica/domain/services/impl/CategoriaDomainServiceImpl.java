package br.com.cotiinformatica.domain.services.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.domain.models.dtos.CategoriaRequestDto;
import br.com.cotiinformatica.domain.models.dtos.CategoriaResponseDto;
import br.com.cotiinformatica.domain.models.entities.Categoria;
import br.com.cotiinformatica.domain.services.interfaces.CategoriaDomainService;
import br.com.cotiinformatica.infrastucture.repository.CategoriaRepository;


@Service
public class CategoriaDomainServiceImpl implements CategoriaDomainService{

	
	@Autowired CategoriaRepository categoriaRepository;
	@Autowired ModelMapper modelMapper;
	
	@Override
	public CategoriaResponseDto adicionar(CategoriaRequestDto request) throws Exception {
		
		if(categoriaRepository.verifyExists(request.getNome())) {
			throw new IllegalArgumentException("Já existe uma categoria cadastrada com esse nome.Tente outro");
		}
		
		var categoria = modelMapper.map(request, Categoria.class);
		categoria.setNome(request.getNome());
		
		categoriaRepository.save(categoria);
		
		var response = new CategoriaResponseDto();
		response.setId(categoria.getId());
		response.setNome(categoria.getNome());
		
		return response;
	}

	@Override
	public CategoriaResponseDto aditar(Integer id, CategoriaRequestDto request) throws Exception {
		
		var categoria = categoriaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
		categoria.setNome(request.getNome());
		categoriaRepository.save(categoria);
		return modelMapper.map(categoria, CategoriaResponseDto.class);
	}

	@Override
	public CategoriaResponseDto excluir(Integer id) throws Exception {
		
		var categoria = categoriaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
		categoriaRepository.delete(categoria);
		
		return modelMapper.map(categoria, CategoriaResponseDto.class);
	}

	@Override
	public List<CategoriaResponseDto> consultar() throws Exception {
		var response = categoriaRepository.findAll()
				.stream()
				.map((categoria) -> modelMapper.map(categoria, CategoriaResponseDto.class))
				.collect(Collectors.toList());
		
		return response;


	}

	@Override
	public CategoriaResponseDto obterPorId(Integer id) throws Exception {
		
		var categoria = categoriaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
		return modelMapper.map(categoria,CategoriaResponseDto. class);
	}

}
