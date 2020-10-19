package com.sgpad.servicos;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sgpad.modelos.Arquivo;
import com.sgpad.repositorios.RepositorioArquivo;

@Service
public class ServicoArquivo {
	
	@Autowired private RepositorioArquivo repositorioArquivo;
	
	public Arquivo armazenarArquivo(MultipartFile arquivo) {
		String nomeArquivo = StringUtils.cleanPath(arquivo.getOriginalFilename());
		
		try {
			if(nomeArquivo.contains("..")) {
				throw new FileStorageException("Inválido." + nomeArquivo);
			}
			
			Arquivo arq = new Arquivo(nomeArquivo, arquivo.getContentType(), arquivo.getBytes());
			
			return repositorioArquivo.save(arq);
		
		}catch(IOException e) {
			throw new FileStorageException("Não foi possível salvar o arquivo.", e);
		}
	}
		
		public Arquivo getArquivo(String idArquivo) {
			return repositorioArquivo.findById(idArquivo)
					.orElseThrow(() -> new FileNotFoundException("Não encontrado" + idArquivo));
		}
}