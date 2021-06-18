package efmm.msa.store.product;

import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import efmm.msa.store.product.model.dao.ProductDAO;
import efmm.msa.store.product.model.entity.InCategory;
import efmm.msa.store.product.model.entity.InProduct;
import efmm.msa.store.product.model.service.ProductService;

@SpringBootTest
public class ProductServiceMockTest {
	
	@Mock
	private ProductDAO productDAO;
	
	@Autowired
	private ProductService productService;
	
	private static final String NAME_PROD = "Hojas Color";
	
	@BeforeEach
	public void setup() {
		 	mock(this.getClass());
	        InProduct colorSheets =  InProduct.builder()
	                .productId(1L)
	                .productName( NAME_PROD )
	                .inCategory( InCategory.builder().categoryId(3L).build())
	                .productPrice(new BigDecimal("12.5"))
	                .productStock(new BigDecimal("5"))
	                .build();

	        Mockito.when(productDAO.findById(1L))
	                .thenReturn(Optional.of(colorSheets));
	        Mockito.when(productDAO.save(colorSheets)).thenReturn(colorSheets);
	}
	
	@Test
	public void whenValidGetID_ThenReturnProduct(){
        InProduct found = productService.getProduct(1L);
       Assertions.assertThat(found.getProductName()).isEqualTo( NAME_PROD );
	}

	@Test
	public void whenValidUpdateStock_ThenReturnNewStock(){
        InProduct newStock = productService.updateStock(1L, BigDecimal.TEN );
        Assertions.assertThat(newStock.getProductStock()).isEqualTo( new BigDecimal( 15 ) );
	}
	
	
	

}
