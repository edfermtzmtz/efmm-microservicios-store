package efmm.msa.store.customer.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table( name = "MG_CUSTOMERS" )
public class Customer{
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long customerId;
	
	@NotEmpty( message = "El n�mero de documento no puede ser vac�o" )
	@Size( min = 8, max = 8, message = "El tama�o del n�mero de documento es 8" )
	@Column( name="NUMBER_ID", unique = true, length = 8, nullable = false )
	private String numberId;
	
	@NotEmpty( message = "El nombre no puede ser vac�o" )
	@Column( name = "CUSTOMER_FIRST_NAME", 	nullable = false )
	private String customerFirstName;
	
	@NotEmpty( message = "El apellido no puede ser vac�o" )
	@Column( name = "CUSTOMER_LAST_NAME", nullable = false )
	private String customerLastName;
	
	@NotEmpty( message = "El correo no puede ser vac�o" )
	@Email( message = "No es un correo valido" )
	@Column( name = "CUSTOMER_EMAIL", nullable = false )
	private String customerEmail;
	
	@Column( name = "CUSTOMER_PHOTO_URL" )
	private String customerPhotoUrl;
	
	@NotNull( message = "La region no puede ser vac�a" )
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "REGION_ID" )
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Region region;
	
	private String customerState;
}
