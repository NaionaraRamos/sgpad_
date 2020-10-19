package com.sgpad.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sgpad.modelos.Processo;

@Repository
public interface RepositorioProcesso extends JpaRepository<Processo, Long> {

	Processo findByNumeroProcesso(String numeroProcesso);
	
	@Query("SELECT p FROM Processo p WHERE presidente_matricula=:matricula")
	List<Processo> carregarProcessosComoPresidente(@Param("matricula") String matricula);
	
	@Query("SELECT p FROM Processo p WHERE assessor_um_matricula=:matricula")
	List<Processo> carregarProcessosComoAssessorUm(@Param("matricula") String matricula);
	
	@Query("SELECT p FROM Processo p WHERE assessor_dois_matricula=:matricula")
	List<Processo> carregarProcessosComoAssessorDois(@Param("matricula") String matricula);
	
	@Query("SELECT p FROM Processo p WHERE secretario_matricula=:matricula")
	List<Processo> carregarProcessosComoSecretário(@Param("matricula") String matricula);
	
	@Query("SELECT p FROM Processo p WHERE assessor_um_matricula=:matricula OR assessor_dois_matricula=:matricula OR presidente_matricula=:matricula OR secretario_matricula=:matricula")
	List<Processo> carregarTodosOsProcessos(@Param("matricula") String matricula);
	
	@Query(value = "SELECT numero_processo FROM processo WHERE numero_processo!=:numeroProcesso", nativeQuery = true)
	List<String> carregarTodosOsProcessosMenosUm(@Param("numeroProcesso") String numeroProcesso);
}
