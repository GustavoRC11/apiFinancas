package br.com.cotiinformatica.infrastucture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.domain.models.entities.Categoria;
import br.com.cotiinformatica.domain.models.entities.Conta;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	
	@Query("SELECT COUNT(c) > 0 FROM Categoria c WHERE c.nome = :nome")
	boolean verifyExists(@Param("nome")String nome);

	void save(Conta conta);

}
