package com.yanmaia12;

import com.yanmaia12.model.Categoria;
import com.yanmaia12.model.Pedido;
import com.yanmaia12.model.Produto;
import com.yanmaia12.repository.CategoriaRepository;
import com.yanmaia12.repository.PedidoRepository;
import com.yanmaia12.repository.ProdutoRepository;

import java.util.List;
import java.util.Scanner;

public class Main {
    Scanner scanner = new Scanner(System.in);
    CategoriaRepository categoriaRepository;
    PedidoRepository pedidoRepository;
    ProdutoRepository produtoRepository;
    public Main(CategoriaRepository categoriaRepository, PedidoRepository pedidoRepository, ProdutoRepository produtoRepository){
        this.categoriaRepository = categoriaRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public void exibeMenu(){
        var opcao = 100;
        while (opcao!=0){
            var menu = """
                    1 - Buscar produto pelo nome
                    2 - Buscar categoria de produtos
                    
                    0 - para sair
                    """;
            System.out.print(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:
                    buscarProdutoPeloNome();
                    break;
                case 2:
                    buscarTodosProdutosDeUmaCategoria();
                    break;
            }
        }
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

    private void buscarProdutoPeloNome(){
        System.out.print("Escreva o nome do produto que prentede buscar: ");
        String produto = scanner.nextLine();
        List<Produto> listaProdutos = produtoRepository.findByNomeEqualsIgnoreCase(produto);
        if (listaProdutos.isEmpty()){
            System.out.println("Nenhum produto encontrado com esse nome!");
        }else{
            listaProdutos.forEach(s -> System.out.print(s.toString()));
        }
    }

    private void buscarTodosProdutosDeUmaCategoria(){
        System.out.print("Escreva o nome da categoria dos produtos que prentede buscar: ");
        String categoria = scanner.nextLine();
        List<Produto> lisaProdutos = produtoRepository.findByCategoriaNomeIgnoreCase(categoria);
        if (lisaProdutos.isEmpty()){
            System.out.println("Nenhum produto encontrado com essa categoria!");
        }else{
            lisaProdutos.forEach(s -> System.out.print(s.toString()));
        }
    }
}
