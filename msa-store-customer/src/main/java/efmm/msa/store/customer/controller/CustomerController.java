package efmm.msa.store.customer.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import efmm.msa.store.customer.model.entity.Customer;
import efmm.msa.store.customer.model.entity.Region;
import efmm.msa.store.customer.model.service.CustomerService;
import efmm.msa.store.customer.utils.Utils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
    private CustomerService customerService;

 
	/**
	 * Obtener todos los clientes
	 * @param regionId
	 * @return Listado de clientes
	 */
    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomers(@RequestParam(name = "regionId" , required = false) Long regionId ) {
        List<Customer> customers =  new ArrayList<>();
        if ( Objects.isNull( regionId ) ) {
            customers = customerService.findCustomerAll();
            if (customers.isEmpty())
                return ResponseEntity.noContent().build();
            
        }else{
            Region Region= new Region();
            Region.setRegionId(regionId);
            customers = customerService.findCustomersByRegion(Region);
            if ( Objects.isNull( customers ) ) {
                log.error("No se encontro cliente con la region : {}", regionId);
                return  ResponseEntity.notFound().build();
            }
        }
        return  ResponseEntity.ok(customers);
    }

    
    /**
     * Obtener cliente por id
     * @param id
     * @return Cliente encontrado
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id) {
        log.info("Fetching Customer with id {}", id);
        Customer customer = customerService.getCustomer(id);
        if (  Objects.isNull( customer ) ) {
            log.error("Cliente con id {} no encontrado.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(customer);
    }

    /**
     * Insertar cliente en base de datos
     * @param customer
     * @param result
     * @return cliente guadado correctamente
     */
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result) {
        log.info("Creando Cliente: {}", customer);
        if (result.hasErrors())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Utils.formatterMessage(result, "002") );

       Customer customerDB = customerService.createCustomer (customer);

        return  ResponseEntity.status( HttpStatus.CREATED).body(customerDB);
    }

    /**
     * Actualizacion de cliente
     * @param id
     * @param customer
     * @return Cliente actualizado
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
        log.info("Actualizando Cliente con id {}", id);

        Customer currentCustomer = customerService.getCustomer(id);

        if ( Objects.isNull( currentCustomer ) ) {
            log.error("No se puede actualizar cliente {}, no se encontro", id);
            return  ResponseEntity.notFound().build();
        }
        customer.setCustomerId(id);
        currentCustomer=customerService.updateCustomer(customer);
        return  ResponseEntity.ok(currentCustomer);
    }

    /**
     * Borrado logico del cliente
     * @param id
     * @return Cliente actualizado con estatus BORRADO
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") long id) {
        log.info("Borrando cliente con id {}", id);

        Customer customer = customerService.getCustomer(id);
        if ( Objects.isNull( customer ) ) {
            log.error("Imposible eliminar cliente con id {}", id);
            return  ResponseEntity.notFound().build();
        }
        customer = customerService.deleteCustomer(customer);
        return  ResponseEntity.ok(customer);
    }
}
