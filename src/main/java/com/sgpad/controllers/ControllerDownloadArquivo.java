package com.sgpad.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.sgpad.modelos.Arquivo;
import com.sgpad.servicos.ServicoArquivo;

@RestController
public class ControllerDownloadArquivo {
	
	@Autowired ServicoArquivo servicoArquivo;
	
	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String nomeArquivo, HttpServletRequest request){
		Arquivo arquivo = servicoArquivo.getArquivo(nomeArquivo);
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(arquivo.getTipo()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo.getNome() + "\"")
				.body(new ByteArrayResource(arquivo.getDados()));
	}
}