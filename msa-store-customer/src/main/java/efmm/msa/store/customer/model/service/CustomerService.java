package efmm.msa.store.customer.model.service;

import java.util.List;

import efmm.msa.store.customer.model.entity.Customer;
import efmm.msa.store.customer.model.entity.Region;

public interface CustomerService {
	
	public List<Customer> findCustomerAll();
    public List<Customer> findCustomersByRegion(Region region);

    public Customer createCustomer(Customer customer);
    public Customer updateCustomer(Customer customer);
    public Customer deleteCustomer(Customer customer);
    public Customer getCustomer(Long id);
	
}
