package efmm.msa.store.sale.model.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import efmm.msa.store.sale.client.CustomerClient;
import efmm.msa.store.sale.client.ProductClient;
import efmm.msa.store.sale.model.dao.InvoiceDAO;
import efmm.msa.store.sale.model.dao.InvoiceItemsDAO;
import efmm.msa.store.sale.model.entity.Invoice;
import efmm.msa.store.sale.model.entity.InvoiceItem;
import efmm.msa.store.sale.model.json.Customer;
import efmm.msa.store.sale.model.json.Product;
import efmm.msa.store.sale.model.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService{
	
	@Value("${server.port}")
	private int serverPort;
	
	@Autowired
    private InvoiceDAO 		invoiceDAO;
	@Autowired
	private CustomerClient 	customerClient;
	@Autowired
	private ProductClient 	productClient;
    @Autowired
    		InvoiceItemsDAO invoiceItemsDAO;
    
    
    @Override
    public List<Invoice> findInvoiceAll() {
        return  invoiceDAO.findAll();
    }


    @Override
    public Invoice createInvoice(Invoice invoice) {
        log.info("Peticion 'createInvoice' en el puerto: {}", serverPort);
    	
    	Invoice invoiceDB = invoiceDAO.findByNumberInvoice ( invoice.getNumberInvoice () );
        if ( Objects.nonNull( invoiceDB ) )
            return  invoiceDB;
        
        invoice.setState("ACTIVO");
        invoiceDB = invoiceDAO.save(invoice);
        
        invoiceDB.getItems().forEach( inv -> {
        	productClient.updateStockProduct(inv.getProductId(), BigDecimal.valueOf(inv.getQuantity()).negate() );
        	inv.getProduct();	
        });
        
        return invoiceDB;
    }


    @Override
    public Invoice updateInvoice(Invoice invoice) {
    	log.info("Peticion 'updateInvoice' en el puerto: {}", serverPort);
        Invoice invoiceDB = getInvoice(invoice.getId());
        if ( Objects.isNull( invoiceDB ) )
            return  null;
        
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());
        return invoiceDAO.save(invoiceDB);
    }


    @Override
    public Invoice deleteInvoice(Invoice invoice) {
    	log.info("Peticion 'deleteInvoice' en el puerto: {}", serverPort);
        Invoice invoiceDB = getInvoice(invoice.getId());
        if ( Objects.isNull( invoiceDB ) )
        	return  null;
        
        invoiceDB.setState("BORRADO");
        return invoiceDAO.save(invoiceDB);
    }

    @Override
    public Invoice getInvoice(Long id) {
    	log.info("Peticion 'getInvoice' en el puerto: {}", serverPort);
    	Invoice invoice = invoiceDAO.findById(id).orElse(null);
    	if ( Objects.nonNull( invoice ) ) {
    		Customer customer = customerClient.getCustomer( invoice.getCustomerId() ).getBody();
    		invoice.setCustomer( customer );
    		
    		List<InvoiceItem> lstInvoiceItem = invoice.getItems().stream().map( item -> {
    			Product product = productClient.getProduct( item.getProductId() ).getBody(); 
    			item.setProduct(product);
    			return item;
    		} ).collect( Collectors.toList() );
    		
    		invoice.setItems( lstInvoiceItem );
			
		}
        return invoice;
    }
}
