package efmm.msa.store.sale.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import efmm.msa.store.sale.model.entity.Invoice;

public interface InvoiceDAO extends JpaRepository<Invoice, Long> {
	public List<Invoice> findByCustomerId(Long customerId );
    public Invoice findByNumberInvoice(String numberInvoice);
}
