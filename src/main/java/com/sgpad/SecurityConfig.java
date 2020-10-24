package com.sgpad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DetalhesUsuario userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.csrf()
			.disable()
			.authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/recuperar_senha").permitAll()
			.antMatchers("/").hasRole("USER")
			.antMatchers("/perfil/{matricula}").hasRole("USER")
			.antMatchers("/alterar_dados/{matricula}").hasRole("USER")
			.antMatchers("/area_admin").hasRole("ADMIN")
			.antMatchers("/listar_usuarios").hasRole("ADMIN")
			.antMatchers("/listar_todos_os_processos").hasRole("ADMIN")
			.antMatchers("/adicionar_usuario").hasRole("ADMIN")
			.antMatchers("/alterar_dados_usuario/{matricula}").hasRole("ADMIN")
			.antMatchers("/excluir_usuario/{matricula}").hasRole("ADMIN")
			.antMatchers("/excluir_processo/{numero_processo}").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and().formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/listar_processos").usernameParameter("email").passwordParameter("senha")
			.failureUrl("/login?error=true")
			.permitAll()
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.and().exceptionHandling().accessDeniedPage("/403");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/js/**", "/css/**", "/scss/*", "/vendor/**");
	}
}
