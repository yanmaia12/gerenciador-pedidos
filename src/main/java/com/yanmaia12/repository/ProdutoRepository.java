package com.yanmaia12.repository;

import com.yanmaia12.model.Categoria;
import com.yanmaia12.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    List<Produto> findByNomeEqualsIgnoreCase(String nome);
    List<Produto> findByCategoriaNomeIgnoreCase(String categoria);
    List<Produto> findByPrecoGreaterThanEqual(Double preco);
    List<Produto> findByPrecoLessThanEqual(Double preco);
    List<Produto> findByNomeContainsIgnoreCase(String nome);
    List<Produto> findByCategoriaNomeIgnoreCaseOrderByPreco(String categoria);
    List<Produto> findByCategoriaNomeIgnoreCaseOrderByPrecoDesc(String categoria);
    Long countByCategoriaNomeIgnoreCase(String categoria);
    Long countByPrecoGreaterThanEqual(Double preco);
    List<Produto> findByPrecoLessThanEqualOrNomeContainsIgnoreCase(Double preco, String nome);

}
