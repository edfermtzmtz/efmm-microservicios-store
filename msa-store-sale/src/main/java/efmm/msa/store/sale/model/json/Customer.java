package efmm.msa.store.sale.model.json;

import lombok.Data;

@Data
public class Customer {
	
	private Long 	customerId;
	private String 	numberId;
	private String 	customerFirstName;
	private String 	customerLastName;
	private String 	customerEmail;
	private String 	customerPhotoUrl;
	private Region 	region;
	private String 	customerState;
}
