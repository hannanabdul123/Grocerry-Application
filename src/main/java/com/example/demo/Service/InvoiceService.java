package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.Model.Invoice;
import com.example.demo.Model.Order;
import com.example.demo.Repository.InvoiceRepository;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    public InvoiceService(InvoiceRepository invoiceRepository){
        this.invoiceRepository=invoiceRepository;
    }

    public Invoice generatInvoice(Order order){
        Invoice invoice=new Invoice();
        invoice.setInvoiceNumber("INV-"+UUID.randomUUID());
        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setAmount(order.getTotalAmount());
        invoice.setOrder(order);
        return invoiceRepository.save(invoice);
    }
    // Fetch invoice
    public Invoice getInvoiceByOrderId(Long orderId){
        return invoiceRepository.findByOrderId(orderId)
              .orElseThrow(()-> new RuntimeException("Invoice not found!"));
    }
}
