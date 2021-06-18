package efmm.msa.store.product;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import efmm.msa.store.product.model.dao.ProductDAO;
import efmm.msa.store.product.model.entity.InCategory;
import efmm.msa.store.product.model.entity.InProduct;

@DataJpaTest
public class ProductDAOMockTest {
	
	@Autowired
	private ProductDAO productDAO;
	
	@Test
	public void whenFindByCategory_thenResultListProducts() {
		
		InProduct product01 = InProduct.builder()
				.productName("Lapiz Bic")
				.inCategory( InCategory.builder()
								.categoryId(1L)
								.categoryName("Plumas y lapices").build() )
				.productDescription("Lapicero Bic punta media")
				.productPrice( new BigDecimal("5.7") )
				.productStock( BigDecimal.TEN )
				.productStatus("ACTIVO")
				.productReg( Calendar.getInstance().getTime() )
				.build();
		
		List<InProduct> lstProducts = productDAO.findByInCategory( product01.getInCategory() );
		
		Assertions.assertThat( lstProducts.size() ).isEqualTo(2);
	}
	
}
