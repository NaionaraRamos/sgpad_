package com.sgpad.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.sgpad.modelos.Processo;
import com.sgpad.repositorios.RepositorioProcesso;

@Component
@Transactional
public class ServicoNotificacao {

	private JavaMailSender jms;
	@Autowired private RepositorioProcesso repositorioProcesso;
	
	@Autowired public void servicoNotificacao(JavaMailSender jms) { this.jms = jms; }

	public void enviarNotificacao(SimpleMailMessage email) { jms.send(email); }

	@Scheduled(cron = "00 30 11 * * *")
    public void enviarEmail() {
		
		try {
			SimpleMailMessage email = new SimpleMailMessage();
			List <Processo> processos = repositorioProcesso.findAll();
			//System.out.println("Processos: " + processos);

			for(int i = 0; i < processos.size(); i++) {

				System.out.println("Número do Processo: " + processos.get(i).getNumeroProcesso());
				System.out.println("Data Publicação Última Portaria: " + processos.get(i).getDataPublicacaoUltimaPortaria());
				System.out.println("Data Vencimento: " + processos.get(i).getDataVencimento());
				System.out.println("Dias Faltantes: " + processos.get(i).getDiasFaltantes());
				
				int diasFaltantes = processos.get(i).getDiasFaltantes();
					if(diasFaltantes == 8 && !(processos.get(i).getAndamento().matches("Finalizado"))) {
						String emailPresidente = processos.get(i).getPresidente().getEmail();
						String emailAssessorUm = processos.get(i).getAssessorUm().getEmail();
						String emailAssessorDois = processos.get(i).getAssessorDois().getEmail();
						
						email.setTo(emailPresidente, emailAssessorUm, emailAssessorDois);
						email.setSubject("Vencimento de portaria");
						email.setText("Faltam " + diasFaltantes + " dias para o vencimento da " + processos.get(i).getPortariaInstauradora()
								+ ", do processo nº " + processos.get(i).getNumeroProcesso() + ", cujo interessado é " + processos.get(i).getInteressado() + ".");
						enviarNotificacao(email);
						System.out.println(" Email Enviado!!!");
					}else {
						System.out.println("Sem emails para enviar!!!");
					}
			}
		} catch (Exception e) {System.out.println("Enviar email: " + e); }
	}
}
