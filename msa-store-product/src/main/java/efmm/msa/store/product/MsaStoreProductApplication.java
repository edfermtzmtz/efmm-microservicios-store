package efmm.msa.store.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsaStoreProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsaStoreProductApplication.class, args);
	}

}
