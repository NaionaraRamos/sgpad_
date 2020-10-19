package com.sgpad.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.sgpad.modelos.Usuario;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, String> {
	
	Usuario findByEmail(String email);
	Usuario findByMatricula(String matricula);
	
	@Query("SELECT u FROM Usuario u WHERE deleted=false AND admin=false")
	List<Usuario> carregarUsuariosNaoExcluidos();
	
	@Query(value = "SELECT email FROM usuario", nativeQuery = true)
	List<String> carregarEmails();
	
	@Query(value = "SELECT email FROM usuario WHERE email!=:email", nativeQuery = true)
	List<String> carregarEmailsMenosUm(@Param("email") String email);
	
	@Query(value = "SELECT EXISTS(SELECT * FROM usuario_role WHERE usuario_matricula=:matricula AND role_id=2)", nativeQuery = true)
	//Integer checarSeEhAdmin(@Param("matricula") String matricula);
	String checarSeEhAdmin(@Param("matricula") String matricula);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE usuario SET deleted=true WHERE matricula=:matricula", nativeQuery = true)
	public void deletarUsuario(@Param("matricula") String matricula);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM usuario_role WHERE usuario_matricula=:matricula", nativeQuery = true)
	public void deletarRoleUsuario(@Param("matricula") String matricula);
		
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO usuario_role (usuario_matricula, role_id) VALUES (:usuario_matricula, 1)", nativeQuery = true)
	public void adicionarRoleUsuario(@Param("usuario_matricula") String usuario_matricula);	
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO usuario_role (usuario_matricula, role_id) VALUES (:usuario_matricula, 2)", nativeQuery = true)
	public void permitirAcessoDeAdministrador(@Param("usuario_matricula") String matricula_usuario);
	
	@Query(value = "SELECT senha FROM usuario WHERE matricula=:matricula", nativeQuery = true)
	public String senha(@Param("matricula") String matricula);
	
	@Query(value = "SELECT email FROM usuario WHERE matricula=:matricula", nativeQuery = true)
	public String email(@Param("matricula") String matricula);
}
