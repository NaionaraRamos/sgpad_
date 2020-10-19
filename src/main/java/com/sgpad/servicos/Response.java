package com.sgpad.servicos;

public class Response {
	
	private String nomeArquivo;
	private String arquivoDownloadUri;
	private String tipoArquivo;
	private long size;
	
	public Response(String nomeArquivo, String arquivoDownloadUri, String tipoArquivo, long size) {
		this.nomeArquivo = nomeArquivo;
		this.arquivoDownloadUri = arquivoDownloadUri;
		this.tipoArquivo = tipoArquivo;
		this.size  =size;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getArquivoDownloadUri() {
		return arquivoDownloadUri;
	}

	public void setArquivoDownloadUri(String arquivoDownloadUri) {
		this.arquivoDownloadUri = arquivoDownloadUri;
	}

	public String getTipoArquivo() {
		return tipoArquivo;
	}

	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}	
}