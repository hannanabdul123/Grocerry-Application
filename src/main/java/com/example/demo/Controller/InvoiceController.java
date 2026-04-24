package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Invoice;
import com.example.demo.Repository.InvoiceRepository;
import com.example.demo.Service.InvoiceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceRepository invoiceRepository;
    public InvoiceController(InvoiceRepository invoiceRepository, InvoiceService invoiceService){
        this.invoiceRepository=invoiceRepository;
        this.invoiceService = invoiceService;
    }
    @GetMapping("/{orderid}")
    public Invoice getInvoice(@PathVariable Long orderid) {
      return invoiceService.getInvoiceByOrderId(orderid);
    }
    
}
