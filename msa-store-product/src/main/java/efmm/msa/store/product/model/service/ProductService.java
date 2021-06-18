package efmm.msa.store.product.model.service;

import java.math.BigDecimal;
import java.util.List;

import efmm.msa.store.product.model.entity.InCategory;
import efmm.msa.store.product.model.entity.InProduct;

public interface ProductService {
	
	public List<InProduct> 	listAllProduct();
    public InProduct 		getProduct(Long id);
    public InProduct 		createProduct(InProduct product);
    public InProduct 		updateProduct(InProduct product);
    public InProduct 		deleteProduct(Long id);
    public List<InProduct> 	findByCategory(InCategory category);
    public InProduct 		updateStock(Long id, BigDecimal quantity);

}
