package efmm.msa.store.product.controller;

import java.math.BigDecimal;
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

import efmm.msa.store.product.model.entity.InCategory;
import efmm.msa.store.product.model.entity.InProduct;
import efmm.msa.store.product.model.service.ProductService;
import efmm.msa.store.product.utils.Utils;

@RestController
@RequestMapping( value = "/products" )
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	// http://localhost:8003/products?categoryId=1
	/**
	 * Listado de todos los productos, con la posibilidad de filtrar por categoria
	 * @param categoryId
	 * @return Listado de productos
	 */
	@GetMapping
	public ResponseEntity<List<InProduct>> lstProducts(@RequestParam(name = "categoryId", required = false) Long categoryId ){
		List<InProduct> products = null;
		
		if ( Objects.isNull( categoryId ) ) {
			products = productService.listAllProduct();
				if ( products.isEmpty())
					return ResponseEntity.noContent().build();
		} else {
			products = productService.findByCategory( InCategory.builder().categoryId( categoryId ).build() );
				if ( products.isEmpty())
					return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok( products );
	}
	
	// http://localhost:8003/products/3
	/**
	 * Obtener producto filtrando por id
	 * @param id
	 * @return Producto encontrado
	 */
	@GetMapping( value = "/{id}" )
	public ResponseEntity<InProduct> getProduct(@PathVariable("id") Long id ){
		InProduct product = productService.getProduct( id );
		if ( Objects.isNull( id ) ) { 
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok( product );
	}
	
	// http://localhost:8003/products
	/**
	 * Guardar un nuevo producto en base de datos
	 * @param product
	 * @param result
	 * @return Producto guardado correctamente
	 */
	@PostMapping
	public ResponseEntity<InProduct> insertProduct(@Valid @RequestBody InProduct product, BindingResult result){
		if ( result.hasErrors() )
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, Utils.formatterMessage(result, "001") );
		
		InProduct newProduct = productService.createProduct(product);
		
		return ResponseEntity.status( HttpStatus.CREATED ).body( newProduct );
	}
	
	//	@PutMapping( value = "/{id}" )
	/**
	 * Actualizacion de producto
	 * @param product
	 * @return producto actualizado
	 */
	@PutMapping
	public ResponseEntity<InProduct> updateProduct(@RequestBody InProduct product ){
		InProduct productDB = productService.updateProduct( product );
		if ( Objects.isNull( productDB ) )
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok( productDB );
	}
	
	/**
	 * Borrado logico del producto
	 * @param id
	 * @return Producto actualizado con estatus BORRADO
	 */
	@DeleteMapping( "/{id}" )
	public ResponseEntity<InProduct> deleteProduct(@PathVariable("id") Long id ){
		InProduct product = productService.deleteProduct(id);
		if ( Objects.isNull( product ) )
			return ResponseEntity.notFound().build();
			
		return ResponseEntity.ok( product );
		
	}
	
	/**
	 * Actualizar la cantidad en almacen de un producto
	 * @param id
	 * @param quantity
	 * @return Producto actuzalizado
	 */
	@GetMapping("/{id}/stock")
	public ResponseEntity<InProduct> updateStockProduct(@PathVariable("id") Long id, @RequestParam( name = "quantity", required = true) BigDecimal quantity ){
		InProduct product = productService.updateStock(id, quantity);
		if ( Objects.isNull( product ) )
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok( product );
		
	}
	
}
