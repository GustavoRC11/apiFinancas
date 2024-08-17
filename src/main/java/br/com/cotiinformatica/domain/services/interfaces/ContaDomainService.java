package br.com.cotiinformatica.domain.services.interfaces;

import java.util.List;

import br.com.cotiinformatica.domain.models.dtos.ContaRequestDto;
import br.com.cotiinformatica.domain.models.dtos.ContaResponseDto;

public interface ContaDomainService {
	
	ContaResponseDto adicionar (ContaRequestDto request);
	
	ContaResponseDto editar (Integer id, ContaRequestDto request);
	
	ContaResponseDto excluir (Integer id) throws Exception;
	
	List<ContaResponseDto> consultar() throws Exception;
	
	ContaResponseDto obterPorId(Integer id) throws Exception;
 
}
