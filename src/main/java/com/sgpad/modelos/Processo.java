package com.sgpad.modelos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
//import com.sgpad.modelos.Usuario;
import org.hibernate.annotations.Type;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity @Table(name = "processo") public class Processo {

	@Id @GeneratedValue(strategy = GenerationType.AUTO) private Long id;
	@Column(nullable = false) @NotNull private String numeroProcesso;
   // @Column(nullable = false) @NotNull private String unidadeOrigem;
    @Column(nullable = false) @NotNull private String naturezaProcesso;
    @Column(nullable = false) @NotNull private String portariaInstauradora;
    @OneToOne private Usuario presidente;
    @OneToOne private Usuario secretario;
    @OneToOne private Usuario assessorUm;
    @OneToOne private Usuario assessorDois;
    @Transient private int diasCorridos;
    @Column(nullable = false) private String andamento;
    @Column(nullable = false) private String interessado;
    //@Column(nullable = false) private String fatos;
    private String ultimaPortariaExpedida;
    //= portariaInstauradora;
    @Transient private int diasFaltantes;
    
    //private Arquivo arquivo;
////    @Column(nullable = true) private String sugestao_julgamento;
////    @Column(nullable = true) private String decisao;
    
    @NotNull @DateTimeFormat(pattern = "yyyy-M" + "M-dd")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate dataInicioProcesso;
    
    @Column(nullable = false) @NotNull @DateTimeFormat(pattern = "yyyy-M" + "M-dd")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate dataPublicacaoPortariaDesignacao;
    
////    @DateTimeFormat(pattern = "yyyy-M" + "M-dd")
////    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
////    private LocalDate dataProrrogacao;
////    
////    @DateTimeFormat(pattern = "yyyy-M" + "M-dd")
////    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
////    private LocalDate dataReconducao;
//    
    @DateTimeFormat(pattern = "yyyy-M" + "M-dd")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate dataPublicacaoUltimaPortaria;
    //= dataPublicacaoPortariaDesignacao;
    
    @Transient @DateTimeFormat(pattern = "yyyy-M" + "M-dd")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate dataVencimento;
    
//    @DateTimeFormat(pattern = "yyyy-M" + "M-dd")
//    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
//    private LocalDate dataRelatorioFinal;
    
    public Long getId() { return id; }

   	public void setId(Long id) { this.id = id; }
   	
    public String getNumeroProcesso() { return numeroProcesso; }

	public void setDiasFaltantes(int diasFaltantes) { this.diasFaltantes = diasFaltantes; }

	public void setNumeroProcesso(String numeroProcesso) { this.numeroProcesso = numeroProcesso; }

//    public String getUnidadeOrigem() { return unidadeOrigem; }

    //    public void setUnidadeOrigem(String unidadeOrigem) { this.unidadeOrigem = unidadeOrigem; }

    public String getNaturezaProcesso() { return naturezaProcesso; }
    
    public void setNaturezaProcesso(String naturezaProcesso) { this.naturezaProcesso = naturezaProcesso; }

    public String getPortariaInstauradora() { return portariaInstauradora; }
    
    public void setPortariaInstauradora(String portariaInstauradora) { this.portariaInstauradora = portariaInstauradora; }

    public LocalDate getDataInicioProcesso() { return dataInicioProcesso; }
    
    public void setDataInicioProcesso(LocalDate dataInicioProcesso) { this.dataInicioProcesso = dataInicioProcesso; }
    
    public LocalDate getDataPublicacaoPortariaDesignacao() { return dataPublicacaoPortariaDesignacao; }
	
    public void setDataPublicacaoPortariaDesignacao(LocalDate dataPublicacaoPortariaDesignacao) { this.dataPublicacaoPortariaDesignacao = dataPublicacaoPortariaDesignacao; }

	public Usuario getPresidente() { return presidente; }
    
	public void setPresidente(Usuario presidente) { this.presidente = presidente; }

    public Usuario getAssessorUm() { return assessorUm; }
	
    public void setAssessorUm(Usuario assessorUm) { this.assessorUm = assessorUm; }

    public Usuario getAssessorDois() { return assessorDois; }
    
    public void setAssessorDois(Usuario assessorDois) { this.assessorDois = assessorDois; }

    public Usuario getSecretario() { return secretario; }
    
    public void setSecretario(Usuario secretario) { this.secretario = secretario; }
	
    public int getDiasCorridos() {
    	LocalDate hoje = LocalDate.now();
    	diasCorridos = Days.daysBetween(dataInicioProcesso, hoje).getDays();
        return diasCorridos;
    }

    public void setDiasCorridos(int diasCorridos) { this.diasCorridos = diasCorridos; }

	public String getAndamento() { return andamento; }
	
	public void setAndamento(String andamento) { this.andamento = andamento; }

	public String getInteressado() { return interessado; }
	
	public void setInteressado(String interessado) { this.interessado = interessado; }

//	public String getFatos() { return fatos; }

	//	public void setFatos(String fatos) { this.fatos = fatos; }
	
//	public LocalDate getDataProrrogacao() { return dataProrrogacao; }

	//	public void setDataProrrogacao(LocalDate dataProrrogacao) { this.dataProrrogacao = dataProrrogacao; }
	
//	public LocalDate getDataReconducao() { return dataReconducao; }

	//	public void setDataReconducao(LocalDate dataReconducao) { this.dataReconducao = dataReconducao; }

	public String getUltimaPortariaExpedida() {
		if(ultimaPortariaExpedida == "") {
			setUltimaPortariaExpedida(portariaInstauradora);
		}else {
			setUltimaPortariaExpedida(ultimaPortariaExpedida);
		}
		return ultimaPortariaExpedida;
	}

	public void setUltimaPortariaExpedida(String ultimaPortariaExpedida) { this.ultimaPortariaExpedida = ultimaPortariaExpedida; }

	public LocalDate getDataPublicacaoUltimaPortaria() {
		if(dataPublicacaoUltimaPortaria == null) {
			setDataPublicacaoUltimaPortaria(dataPublicacaoPortariaDesignacao);
		} else {
			setDataPublicacaoUltimaPortaria(dataPublicacaoUltimaPortaria);
		}
		return dataPublicacaoUltimaPortaria;
	}

	public void setDataPublicacaoUltimaPortaria(LocalDate dataPublicacaoUltimaPortaria) { this.dataPublicacaoUltimaPortaria = dataPublicacaoUltimaPortaria; }

	public LocalDate getDataVencimento() {
		
//		if(dataVencimento == null) {
//			if(naturezaProcesso.matches("Sindicancia")) {
//				dataVencimento = dataPublicacaoPortariaDesignacao.plusDays(30);
//			}else if(naturezaProcesso.matches("PAD")){
//				dataVencimento = dataPublicacaoPortariaDesignacao.plusDays(60);
//			}
//		}
		
		//if(dataVencimento != null) {
			if(naturezaProcesso.matches("Sindicancia")) {
				dataVencimento = dataPublicacaoUltimaPortaria.plusDays(30);
			}else				
				if(naturezaProcesso.matches("PAD")){
					dataVencimento = dataPublicacaoUltimaPortaria.plusDays(60);
			}
		//}
		
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
	
	public int getDiasFaltantes() { 
		LocalDate hoje = LocalDate.now();
		diasFaltantes = Days.daysBetween(hoje, dataVencimento).getDays();
		return diasFaltantes;
	}
	
//	public void setDiasFaltantes(int diasFaltantes) { this.diasFaltantes = diasFaltantes; }
//
//	public Arquivo getArquivo() {
//		return arquivo;
//	}
//
//	public void setArquivo(Arquivo arquivo) {
//		this.arquivo = arquivo;
//	}
	
//	public LocalDate getDataRelatorioFinal() { return dataRelatorioFinal; }
//	public void setDataRelatorioFinal(LocalDate dataRelatorioFinal) { this.dataRelatorioFinal = dataRelatorioFinal; }
	
//	public String getSugestao_julgamento() { return sugestao_julgamento; }
//	public void setSugestao_julgamento(String sugestao_julgamento) { this.sugestao_julgamento = sugestao_julgamento; }
	
//	public String getDecisao() { return decisao; }
//	public void setDecisao(String decisao) { this.decisao = decisao; }
	
//	public LocalDate getDataRelatorioFinal() { return dataRelatorioFinal; }

	//	public void setDataRelatorioFinal(LocalDate dataRelatorioFinal) { this.dataRelatorioFinal = dataRelatorioFinal; }
	
//	public String getSugestao_julgamento() { return sugestao_julgamento; }

	//	public void setSugestao_julgamento(String sugestao_julgamento) { this.sugestao_julgamento = sugestao_julgamento; }
	
//	public String getDecisao() { return decisao; }

	//	public void setDecisao(String decisao) { this.decisao = decisao; }
}
