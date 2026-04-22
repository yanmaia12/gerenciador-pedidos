package com.yanmaia12.repository;

import com.yanmaia12.model.Categoria;
import com.yanmaia12.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    List<Produto> findByNomeEqualsIgnoreCase(String nome);
    Produto findByNomeIgnoreCase(String nome);
    List<Produto> findByCategoriaNomeIgnoreCase(String categoria);
    List<Produto> findByPrecoGreaterThanEqual(Double preco);
    List<Produto> findByPrecoLessThanEqual(Double preco);
    List<Produto> findByNomeContainsIgnoreCase(String nome);
    List<Produto> findByCategoriaNomeIgnoreCaseOrderByPreco(String categoria);
    List<Produto> findByCategoriaNomeIgnoreCaseOrderByPrecoDesc(String categoria);
    Long countByCategoriaNomeIgnoreCase(String categoria);
    Long countByPrecoGreaterThanEqual(Double preco);
    List<Produto> findByPrecoLessThanEqualOrNomeContainsIgnoreCase(Double preco, String nome);
    @Query("SELECT p FROM Produto p ORDER BY p.preco ASC")
    List<Produto> listaProdutosOrdemCrescente();
    @Query("SELECT p FROM Produto p ORDER BY p.preco DESC")
    List<Produto> listaProdutosOrdemDescrescente();
    @Query("SELECT p FROM Produto p WHERE LOWER(p.nome) LIKE LOWER(:letra)")
    List<Produto> buscarProdutosQueComecemPor(String letra);
    @Query("SELECT AVG(p.preco) FROM Produto p")
    Double mediaPreco();
    @Query("SELECT MAX(p.preco) FROM Produto p WHERE p.categoria = :categoria")
    Double maiorPrecoPorCategoria(Categoria categoria);


}
