package com.sgpad.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgpad.modelos.Arquivo;

@Repository
public interface RepositorioArquivo extends JpaRepository<Arquivo, String>{}