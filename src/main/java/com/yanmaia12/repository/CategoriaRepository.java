package com.yanmaia12.repository;

import com.yanmaia12.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    Optional<Categoria> findByNomeIgnoreCase(String nome);
}
