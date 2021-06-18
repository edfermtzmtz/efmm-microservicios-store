package efmm.msa.store.customer.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
	
	public static String formatterMessage( BindingResult result, String code ) {
		List<Map<String, String>> errors = result.getFieldErrors().stream()
						.map( err -> {
							Map<String, String> map = new HashMap<>();
							map.put( err.getField(), err.getDefaultMessage() );
							return map;
						} ).collect( Collectors.toList() );
		
		ErrorMessage errorMessage = ErrorMessage.builder()
				.code( code )
				.message(errors).build();
		
		ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
	}
	
}
