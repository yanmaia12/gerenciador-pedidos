package com.yanmaia12.repository;

import com.yanmaia12.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    List<Pedido> findByDataGreaterThanEqual(LocalDate data);
}
