package efmm.msa.store.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsaStoreCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsaStoreCustomerApplication.class, args);
	}

}
