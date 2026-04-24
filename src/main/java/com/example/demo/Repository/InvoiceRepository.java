package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Invoice;
import com.example.demo.Model.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
public interface InvoiceRepository extends JpaRepository<Invoice,Long>{
    Optional<Invoice> findByOrderId(Long order_id);
    @Modifying
@Transactional
void deleteByOrder(Order order);
}
