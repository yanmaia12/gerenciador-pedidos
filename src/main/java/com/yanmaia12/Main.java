package com.yanmaia12;

import com.yanmaia12.model.Categoria;
import com.yanmaia12.model.Pedido;
import com.yanmaia12.model.Produto;
import com.yanmaia12.repository.CategoriaRepository;
import com.yanmaia12.repository.PedidoRepository;
import com.yanmaia12.repository.ProdutoRepository;
import com.yanmaia12.util.TratamentoErros;

import java.time.LocalDate;
import java.util.ArrayList;
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
                    11 - Número de produtos a partir de um preço
                    12 - Criar Pedido
                    13 - Listar pedidos pós data
                    14 - Listar todos os produtos por ordem crescente de valor
                    15 - Listar todos os produtos por ordem decrescente de valor
                    16 - Buscar por letra inicial
                    17 - Calcular média de preço
                    
                                   
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
                case 11:
                    contagemPorPreco();
                    break;
                case 12:
                    criarPedido();
                    break;
                case 13:
                    listarPedidosPosData();
                    break;
                case 14:
                    listarProdutoOrdemCrescente();
                    break;
                case 15:
                    listarProdutoOrdemDecrescente();
                    break;
                case 16:
                    buscarProdutosPorLetraInicial();
                    break;
                case 17:
                    calcularMediaDePreco();
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
        Long contagem = produtoRepository.countByCategoriaNomeIgnoreCase(categoria);
        if (contagem==0){
            System.out.println("Nenhum produto nessa categoria!");
        }else{
            System.out.println("Foram encontrados %d produtos na categoria %s!".formatted(contagem, categoria));
        }
    }

    private void contagemPorPreco(){
        System.out.print("Valor mínimo dos produtos: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();

        Long contagem = produtoRepository.countByPrecoGreaterThanEqual(preco);
        if (contagem==0){
            System.out.println("Nenhum produto igual ou superior a esse preço!");
        }else{
            System.out.println("Foram encontrados %d produtos a partir de %.2f€!".formatted(contagem, preco));
        }
    }

    private void criarPedido(){
        System.out.print("Qual a data do pedido (yyyy-mm-dd): ");
        String data = scanner.nextLine();
        LocalDate dataBusca;

        try{
             dataBusca = LocalDate.parse(data);
        }catch (Exception e){
            System.out.println("Data inválida. Usando a data de hoje");
             dataBusca = LocalDate.now();
        }

        System.out.print("Pedido criado, adicione produtos a lista!");
        List<Produto> listaProdutos = produtoRepository.findAll();
        listaProdutos.forEach(s -> System.out.println("%s - %.2f€".formatted(s.getNome(), s.getPreco())));
        String res = "";
        List<Produto> carrinho = new ArrayList<>();
        while (!res.equalsIgnoreCase("n")){
            System.out.print("Escreva o nome do produto: ");
            String produto = scanner.nextLine();
            Produto produtoAchado = produtoRepository.findByNomeIgnoreCase(produto);
            if (produtoAchado != null){
                System.out.print("Quantos unidades de %s pretende adicionar ao carrinho? ".formatted(produtoAchado.getNome()));
                int quantidade = scanner.nextInt();
                scanner.nextLine();

                for (int i = 0; i < quantidade; i++) {
                    carrinho.add(produtoAchado);
                }
                System.out.println("%s adicionado ao carrinho!".formatted(produtoAchado.getNome()));
            }else {
                System.out.println("Produto não encontrado!");
            }

            System.out.println("Deseja adicionar mais produtos? (s/n) ");
            res = scanner.nextLine();
        }

        Pedido pedido = new Pedido(dataBusca, carrinho);
        pedidoRepository.save(pedido);
        System.out.println("Pedido criado!");
    }

    private void listarPedidosPosData(){
        System.out.print("Qual a data do pedido (yyyy-mm-dd): ");
        String data = scanner.nextLine();
        LocalDate dataBusca;

        try{
            dataBusca = LocalDate.parse(data);
        }catch (Exception e){
            System.out.println("Data inválida. Usando a data de hoje");
            dataBusca = LocalDate.now();
        }

        List<Pedido> listaPedidos = pedidoRepository.findByDataGreaterThanEqual(dataBusca);

        if (listaPedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado após esta data.");
        } else {
            listaPedidos.forEach(p -> {
                System.out.println("📦 PEDIDO ID: " + p.getId());
                System.out.println("📅 DATA: " + p.getData());
                System.out.println("🛒 ITENS DO PEDIDO:");

                p.getProdutos().forEach(prod -> {
                    System.out.print("   - " + prod.toString());
                });

                double total = p.getProdutos().stream()
                        .mapToDouble(Produto::getPreco)
                        .sum();
                System.out.println("💰 TOTAL: %.2f€".formatted(total));
            });
            System.out.println("\n");
        }

    }

    private void listarProdutoOrdemCrescente(){
        List<Produto> listaProdutos = produtoRepository.listaProdutosOrdemCrescente();
        listaProdutos.forEach(p -> System.out.println("%s - %.2f€".formatted(p.getNome(), p.getPreco())));
    }

    private void listarProdutoOrdemDecrescente(){
        List<Produto> listaProdutos = produtoRepository.listaProdutosOrdemDescrescente();
        listaProdutos.forEach(p -> System.out.println("%s - %.2f€".formatted(p.getNome(), p.getPreco())));
    }

    private void buscarProdutosPorLetraInicial(){
        String letra = TratamentoErros.tratamentoString("Indique a letra inicial: ").substring(0, 1) + "%";
        List<Produto> listaProduto = produtoRepository.buscarProdutosQueComecemPor(letra);
        listaProduto.forEach(p -> System.out.println("%s - %.2f€".formatted(p.getNome(), p.getPreco())));
    }

    private void calcularMediaDePreco(){
        System.out.println("A média de preço dos produtos registrados é de %.2f€".formatted(produtoRepository.mediaPreco()));
    }
}
