package pay.here.payheretest.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountBookDto {
	
	private Long id;
	
	private UserDto user;
	
	private LocalDate date;
}
