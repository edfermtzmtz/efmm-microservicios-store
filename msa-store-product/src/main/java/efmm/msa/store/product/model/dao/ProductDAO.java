package efmm.msa.store.product.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import efmm.msa.store.product.model.entity.InCategory;
import efmm.msa.store.product.model.entity.InProduct;

public interface ProductDAO extends JpaRepository<InProduct, Long>{
	
	public List<InProduct> findAll();
	public List<InProduct> findByInCategory( InCategory inCategory );
	
}
