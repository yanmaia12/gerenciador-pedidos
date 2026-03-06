package com.yanmaia12;

import com.yanmaia12.model.Categoria;
import com.yanmaia12.model.Pedido;
import com.yanmaia12.model.Produto;
import com.yanmaia12.repository.CategoriaRepository;
import com.yanmaia12.repository.PedidoRepository;
import com.yanmaia12.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class GerenciadorPedidosApplication implements CommandLineRunner{

    @Autowired
    CategoriaRepository categoriaRepository;
    PedidoRepository pedidoRepository;
    ProdutoRepository produtoRepository;

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorPedidosApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Main main = new Main(categoriaRepository, pedidoRepository, produtoRepository);

        Produto produto = new Produto("Notebook", 3500.0);
        Categoria categoria = new Categoria("Eletrônicos");
        Pedido pedido = new Pedido(LocalDate.now());

        main.createProduto(produto);
        main.createCategoria(categoria);
        main.createPedido(pedido);
    }
}
