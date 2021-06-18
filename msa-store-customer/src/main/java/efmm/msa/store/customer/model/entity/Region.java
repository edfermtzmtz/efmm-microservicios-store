package efmm.msa.store.customer.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table( name = "MG_REGIONS" )
public class Region {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "REGION_ID" )
	private Long regionId;
	
	@Column( name = "REGION_NAME" )
	private String regionName;
	
}
