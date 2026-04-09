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
                    
                    1 - Criar Produto
                    2 - Criar Categoria
                    3 - Buscar produto pelo nome exato
                    4 - Buscar categoria de produtos
                    5 - Buscar produtos por um preço mínimo
                    6 - Buscar produtos até um preço máximo
                    7 - Buscar produto por nome
                    8 - Buscar produtos de uma categoria ordenado por preço (crescente)
                    9 - Buscar produtos de uma categoria ordenado por preço (decrescente)
                    10 - Número de produtos registrados em uma categoria
                                   
                    0 - para sair
                    
                    Opção:  """;
            System.out.print(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (opcao){
                case 1:
                    createProduto();
                    break;
                case 2:
                    createCategoria();
                    break;
                case 3:
                    buscarProdutoPeloNome();
                    break;
                case 4:
                    buscarTodosProdutosDeUmaCategoria();
                    break;
                case 5:
                    buscarProdutoPrecoMinimo();
                    break;
                case 6:
                    buscarProdutoPrecoMaximo();
                    break;
                case 7:
                    buscarProdutoPorNomeParecido();
                    break;
                case 8:
                    buscarPorCategoriaPrecoCresc();
                    break;
                case 9:
                    buscarPorCategoriaPrecoDesc();
                    break;
                case 10:
                    contagemPorCategoria();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcção inválida!");
            }
        }
    }

    public void createProduto(){
        List<Categoria> listaCategorias = categoriaRepository.findAll();
        if (listaCategorias.isEmpty()){
            System.out.println("Nenhuma categoria criada, crie uma para poder criar um produto!");
            return;
        }
        System.out.println("\n--- Categorias Disponíveis ---");
        listaCategorias.forEach(c -> System.out.println("- " + c.getNome()));
        System.out.print("\nDigite o nome da categoria para este novo produto: ");
        String nomeCategoria = scanner.nextLine();
        var categoria = categoriaRepository.findByNomeIgnoreCase(nomeCategoria);
        if (categoria.isEmpty()) {
            System.out.println("Categoria não encontrada!");
            return;
        }
        System.out.print("Qual o nome do produto? ");
        var nome = scanner.nextLine();
        System.out.print("Qual o preço do produto? ");
        var preco = scanner.nextDouble();
        scanner.nextLine();
        Produto produto = new Produto(nome, preco, categoria.get());
        produtoRepository.save(produto);
        System.out.println("Novo produto '%s' salvo com sucesso!".formatted(produto.getNome()));
    }

    public void createCategoria(){
        System.out.print("Qual o nome da categoria: ");
        var nome = scanner.nextLine();
        Categoria categoria = new Categoria(nome);
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
        List<Categoria> listaCategorias = categoriaRepository.findAll();
        System.out.println("\n--- Categorias Disponíveis ---");
        listaCategorias.forEach(c -> System.out.println("- " + c.getNome()));
        System.out.print("Escreva o nome da categoria dos produtos que prentede buscar: ");
        String categoria = scanner.nextLine();
        List<Produto> lisaProdutos = produtoRepository.findByCategoriaNomeIgnoreCase(categoria);
        if (lisaProdutos.isEmpty()){
            System.out.println("Nenhum produto encontrado com essa categoria!");
        }else{
            lisaProdutos.forEach(s -> System.out.println("%s - %.2f€.".formatted(s.getNome(), s.getPreco())));
        }
    }

    private void buscarProdutoPrecoMinimo(){
        System.out.print("Defina um preço minímo para os produtos que deseja buscar: ");
        Double preco = scanner.nextDouble();
        scanner.nextLine();
        List<Produto> listaProdutos = produtoRepository.findByPrecoGreaterThanEqual(preco);
        if (listaProdutos.isEmpty()){
            System.out.println("Nenhum produto encontrado a partir desse valor!");
        }else{
            listaProdutos.forEach(s -> System.out.print(s.toString()));
        }
    }

    private void buscarProdutoPrecoMaximo(){
        System.out.print("Defina um preço máximo para os produtos que deseja buscar: ");
        Double preco = scanner.nextDouble();
        scanner.nextLine();
        List<Produto> listaProdutos = produtoRepository.findByPrecoLessThanEqual(preco);
        if (listaProdutos.isEmpty()){
            System.out.println("Nenhum produto encontrado até esse valor!");
        }else{
            listaProdutos.forEach(s -> System.out.print(s.toString()));
        }
    }

    private void buscarProdutoPorNomeParecido(){
        System.out.print("Escreva o nome do produto que prentede buscar: ");
        String produto = scanner.nextLine();
        List<Produto> listaProdutos = produtoRepository.findByNomeContainsIgnoreCase(produto);
        if (listaProdutos.isEmpty()){
            System.out.println("Nenhum produto encontrado com esse nome!");
        }else{
            listaProdutos.forEach(s -> System.out.print(s.toString()));
        }
    }

    private void buscarPorCategoriaPrecoCresc(){
        List<Categoria> listaCategorias = categoriaRepository.findAll();
        System.out.println("\n--- Categorias Disponíveis ---");
        listaCategorias.forEach(c -> System.out.println("- " + c.getNome()));
        System.out.print("Escreva o nome da categoria dos produtos que prentede buscar: ");
        String categoria = scanner.nextLine();
        List<Produto> listaProdutos = produtoRepository.findByCategoriaNomeIgnoreCaseOrderByPreco(categoria);
        if (listaProdutos.isEmpty()){
            System.out.println("Nenhum produto encontrado com essa categoria!");
        }else{
            listaProdutos.forEach(s -> System.out.println("%s - %.2f€.".formatted(s.getNome(), s.getPreco())));
        }

    }

    private void buscarPorCategoriaPrecoDesc(){
        List<Categoria> listaCategorias = categoriaRepository.findAll();
        System.out.println("\n--- Categorias Disponíveis ---");
        listaCategorias.forEach(c -> System.out.println("- " + c.getNome()));
        System.out.print("Escreva o nome da categoria dos produtos que prentede buscar: ");
        String categoria = scanner.nextLine();
        List<Produto> listaProdutos = produtoRepository.findByCategoriaNomeIgnoreCaseOrderByPrecoDesc(categoria);
        if (listaProdutos.isEmpty()){
            System.out.println("Nenhum produto encontrado com essa categoria!");
        }else{
            listaProdutos.forEach(s -> System.out.println("%s - %.2f€.".formatted(s.getNome(), s.getPreco())));
        }
    }

    private void contagemPorCategoria(){
        List<Categoria> listaCategorias = categoriaRepository.findAll();
        System.out.println("\n--- Categorias Disponíveis ---");
        listaCategorias.forEach(c -> System.out.println("- " + c.getNome()));
        System.out.print("Escreva o nome da categoria dos produtos que prentede buscar: ");
        String categoria = scanner.nextLine();
        int contagem = produtoRepository.countByCategoriaNomeIgnoreCase(categoria);
        if (contagem==0){
            System.out.println("Nenhum produto nessa categoria!");
        }else{
            System.out.println("Foram encontrados %d produtos na categoria %s!".formatted(contagem, categoria));
        }
    }

}
