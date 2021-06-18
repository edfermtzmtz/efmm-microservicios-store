package efmm.msa.store.product.model.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import efmm.msa.store.product.model.dao.ProductDAO;
import efmm.msa.store.product.model.entity.InCategory;
import efmm.msa.store.product.model.entity.InProduct;
import efmm.msa.store.product.model.service.ProductService;

@Service
//@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDAO productDAO;
	
	@Override
	public List<InProduct> listAllProduct() {
		return productDAO.findAll();
	}

	@Override
	public InProduct getProduct(Long id) {
		return productDAO.findById( id ).orElse(null);
	}

	@Override
	public InProduct createProduct(InProduct product) {
		product.setProductStatus( "ACTIVE" );
		product.setProductReg( Calendar.getInstance().getTime() );
		return productDAO.save( product );
	}

	@Override
	public InProduct updateProduct(InProduct product) {
		InProduct inProductBD = getProduct( product.getProductId() );
		if ( Objects.isNull( inProductBD ) )
			return null;
		
		inProductBD.setProductName( product.getProductName() );
		inProductBD.setProductReg( Calendar.getInstance().getTime() );
		inProductBD.setProductDescription( product.getProductDescription() );
		inProductBD.setProductStock( product.getProductStock() );
		inProductBD.setProductStatus( product.getProductStatus() );
		inProductBD.setProductPrice( product.getProductPrice() );
		inProductBD.setInCategory( product.getInCategory() );
		
		return productDAO.save( inProductBD );
	}

	@Override
	public InProduct deleteProduct(Long id) {
		InProduct inProductBD = getProduct( id );
		if ( Objects.isNull( inProductBD ) )
			return null;
		
		inProductBD.setProductReg( Calendar.getInstance().getTime() );
		inProductBD.setProductStatus( "BORRADO" );
		
		return productDAO.save( inProductBD );
	}

	@Override
	public List<InProduct> findByCategory(InCategory category) {
		return productDAO.findByInCategory( category );
	}

	@Override
	public InProduct updateStock(Long id, BigDecimal quantity) {
		InProduct inProductBD = getProduct( id );
		if ( Objects.isNull( inProductBD ) )
			return null;
		BigDecimal stock = inProductBD.getProductStock().add( quantity );
		inProductBD.setProductReg( Calendar.getInstance().getTime() );
		inProductBD.setProductStock( stock );
		
		return productDAO.save( inProductBD );
	}

}
