package efmm.msa.store.customer.model.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import efmm.msa.store.customer.model.dao.CustomerDAO;
import efmm.msa.store.customer.model.entity.Customer;
import efmm.msa.store.customer.model.entity.Region;
import efmm.msa.store.customer.model.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
    private CustomerDAO customerDAO;

    @Override
    public List<Customer> findCustomerAll() {
        return customerDAO.findAll();
    }

    @Override
    public List<Customer> findCustomersByRegion(Region region) {
        return customerDAO.findByRegion(region);
    }

    @Override
    public Customer createCustomer(Customer customer) {

        Customer customerDB = customerDAO.findByNumberId( customer.getNumberId() );
        if ( Objects.nonNull( customerDB ) )
            return  customerDB;

        customer.setCustomerState("ACTIVO");
        customerDB = customerDAO.save ( customer );
        return customerDB;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer customerDB = getCustomer(customer.getCustomerId());
        if ( Objects.isNull( customerDB ) )
            return  null;
        
        customerDB.setCustomerFirstName(customer.getCustomerFirstName());
        customerDB.setCustomerLastName(customer.getCustomerLastName());
        customerDB.setCustomerEmail(customer.getCustomerEmail());
        customerDB.setCustomerPhotoUrl(customer.getCustomerPhotoUrl());

        return  customerDAO.save(customerDB);
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        Customer customerDB = getCustomer(customer.getCustomerId());
        if ( Objects.isNull( customerDB ) )
        	return  null;
        
        customer.setCustomerState("BORRADO");
        return customerDAO.save(customer);
    }

    @Override
    public Customer getCustomer(Long id) {
        return  customerDAO.findById(id).orElse(null);
    }
	
	
	
}
