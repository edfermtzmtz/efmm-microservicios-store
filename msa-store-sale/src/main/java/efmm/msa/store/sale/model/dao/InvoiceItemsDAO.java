package efmm.msa.store.sale.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import efmm.msa.store.sale.model.entity.InvoiceItem;

public interface InvoiceItemsDAO extends JpaRepository<InvoiceItem, Long>{
	
}
