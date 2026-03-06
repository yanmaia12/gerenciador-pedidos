package com.yanmaia12;

import com.yanmaia12.model.Categoria;
import com.yanmaia12.model.Pedido;
import com.yanmaia12.model.Produto;
import com.yanmaia12.repository.CategoriaRepository;
import com.yanmaia12.repository.PedidoRepository;
import com.yanmaia12.repository.ProdutoRepository;

public class Main {
    CategoriaRepository categoriaRepository;
    PedidoRepository pedidoRepository;
    ProdutoRepository produtoRepository;
    public Main(CategoriaRepository categoriaRepository, PedidoRepository pedidoRepository, ProdutoRepository produtoRepository){
        this.categoriaRepository = categoriaRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public void createProduto(Produto produto){
        produtoRepository.save(produto);
        System.out.println("Novo produto '%s' salvo com sucesso!".formatted(produto.getNome()));
    }

    public void createCategoria(Categoria categoria){
        categoriaRepository.save(categoria);
        System.out.println("Nova categoria '%s' salvo com sucesso!".formatted(categoria.getNome()));
    }

    public void createPedido(Pedido pedido){
        pedidoRepository.save(pedido);
        System.out.println("Novo pedido feito as " + pedido.getData() + "!");
    }
}
