package efmm.msa.store.sale.client;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import efmm.msa.store.sale.model.json.Product;

@FeignClient(name = "product-microservice")
@RequestMapping( value = "/products" )
public interface ProductClient {
	
	@GetMapping( value = "/{id}" )
	public ResponseEntity<Product> getProduct(@PathVariable("id") Long id );
	
	@GetMapping("/{id}/stock")
	public ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id, @RequestParam( name = "quantity", required = true) BigDecimal quantity );
}
