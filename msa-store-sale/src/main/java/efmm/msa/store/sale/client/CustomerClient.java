package efmm.msa.store.sale.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import efmm.msa.store.sale.model.json.Customer;

@FeignClient("customer-microservice")
@RequestMapping("/customers")
public interface CustomerClient {
	
	@GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id);
	
}
