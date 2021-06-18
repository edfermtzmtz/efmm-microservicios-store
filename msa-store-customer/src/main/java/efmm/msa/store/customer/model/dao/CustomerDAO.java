package efmm.msa.store.customer.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import efmm.msa.store.customer.model.entity.Customer;
import efmm.msa.store.customer.model.entity.Region;

public interface CustomerDAO extends JpaRepository<Customer, Long>{
	
	public Customer 		findByNumberId(String numberId);
    public List<Customer> 	findByCustomerLastName(String lastName);
    public List<Customer> 	findByRegion(Region region);
	
}
