package efmm.msa.store.product.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "IN_PRODUCT")
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class InProduct {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "PRODUCT_ID")
	private Long 		productId;
	
	@NotEmpty( message = "El nombre del producto no puede ser vacio" )
	@Column( name = "PRODUCT_NAME")
	private String 		productName;

	@Column( name = "PRODUCT_DESCRIPTION")
	private String 		productDescription;
	
	@Column( name = "PRODUCT_STOCK")
	private BigDecimal 	productStock;

	@Positive( message = "El precio del producto no puede ser negativo" )
	@Column( name = "PRODUCT_PRICE")
	private BigDecimal 	productPrice;

	@Column( name = "PRODUCT_STATUS")
	private String 		productStatus;

	@Column( name = "PRODUCT_REG")
	private Date 		productReg;
	
	@NotNull( message = "La categoria no puede ser vacio" )
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "CATEGORY_ID" )
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private InCategory 	inCategory;
	
}
