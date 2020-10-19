package com.sgpad.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.sgpad.modelos.Arquivo;
import com.sgpad.servicos.Response;
import com.sgpad.servicos.ServicoArquivo;

@RestController
public class ControllerUploadArquivo {
	
	@Autowired ServicoArquivo servicoArquivo;
	
	@PostMapping("/uploadFile")
	public Response uploadFile(@RequestParam("arquivo") MultipartFile arquivo) {
		Arquivo arq= servicoArquivo.armazenarArquivo(arquivo);
		
		String arquivoDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/")
				.path(arq.getNome())
				.toUriString();
		
		return new Response(arq.getNome(), arquivoDownloadUri, arquivo.getContentType(), arquivo.getSize());
	}
	
	@PostMapping("/uploadMultipartFiles")
	public List <Response> uploadMultipleFiles(@RequestParam("arquivos") MultipartFile[] arquivos){
		return Arrays.asList(arquivos).stream().map(arquivo -> uploadFile(arquivo)).collect(Collectors.toList());
	}
}