package efmm.msa.store.sale.model.json;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class Product {
	
	private Long 		productId;
	private String 		productName;
	private String 		productDescription;
	private BigDecimal 	productStock;
	private BigDecimal 	productPrice;
	private String 		productStatus;
	private Date 		productReg;
	private Category 	category;
	
}
