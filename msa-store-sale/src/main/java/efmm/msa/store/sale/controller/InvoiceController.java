package efmm.msa.store.sale.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import efmm.msa.store.sale.model.entity.Invoice;
import efmm.msa.store.sale.model.service.InvoiceService;
import efmm.msa.store.sale.utils.Utils;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/invoices")
public class InvoiceController {
	
	private static final String MAIN_SERVICE = "mainService";
	private static final String CB_CONFIG_A = "cbConfigA";
	
	@Autowired
    InvoiceService invoiceService;

    /**
     * Obtener todas facturas
     * @return Listado de facturas @Invoice
     */
    @GetMapping
    public ResponseEntity<List<Invoice>> listAllInvoices() {
        List<Invoice> invoices = invoiceService.findInvoiceAll();
        if (invoices.isEmpty())
            return  ResponseEntity.noContent().build();
        return  ResponseEntity.ok(invoices);
    }

    /**
     * Obtener factura por Id
     * @param id
     * @return Factura encontrada por el id
     */
    @GetMapping(value = "/{id}")
    @CircuitBreaker(name = MAIN_SERVICE, fallbackMethod="testFallBack")
    public ResponseEntity<Invoice> getInvoice(@PathVariable("id") long id) {
        log.info("Obtener factura con id {}", id);
        Invoice invoice  = invoiceService.getInvoice(id);
        if ( Objects.isNull( invoice ) ) {
            log.error("No se encontro factura con id {}", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(invoice);
    }

    /**
     * Crear factura la cual sera resivisa el cuerpo de la peticion
     * @param invoice
     * @param result
     * @return Factura creada
     */
    @PostMapping
    @CircuitBreaker(name = CB_CONFIG_A, fallbackMethod="fallBackSave")
    public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody Invoice invoice, BindingResult result) {
        if (result.hasErrors())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Utils.formatterMessage(result, "003"));
        
        Invoice invoiceDB = invoiceService.createInvoice (invoice);

        return  ResponseEntity.status( HttpStatus.CREATED).body(invoiceDB);
    }

    /**
     * Actualizacion de factura
     * @param id
     * @param invoice
     * @return 
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable("id") long id, @RequestBody Invoice invoice) {
        log.info("Actualizando factura con id {}", id);

        invoice.setId(id);
        Invoice currentInvoice=invoiceService.updateInvoice(invoice);

        if (currentInvoice == null) {
            log.error("Inposible actuzalizar, no se encontro factura con id {}.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(currentInvoice);
    }

    /**
     * Borrado logico de factura, colocando estatus en BORRADO
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Invoice> deleteInvoice(@PathVariable("id") long id) {
        log.info("Borrado logico de fcatura con id {}", id);

        Invoice invoice = invoiceService.getInvoice(id);
        if ( Objects.isNull( invoice )  ) {
            log.error("Imposible borrar, no se encontro factura con id {}.", id);
            return  ResponseEntity.notFound().build();
        }
        invoice = invoiceService.deleteInvoice(invoice);
        return ResponseEntity.ok(invoice);
    }
    
    
    @SuppressWarnings("unused")
	private ResponseEntity<String> testFallBack(Exception e){
        return new ResponseEntity<String>("In fallback method getInvoice", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @SuppressWarnings("unused")
    private ResponseEntity<String> fallBackSave(Exception e){
    	return new ResponseEntity<String>("En metodo respaldo del guardado", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
