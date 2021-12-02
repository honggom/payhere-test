package pay.here.payheretest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UsageHistoryDto {
		
	private Long id;
	
	private Long accountBookId;
	
	private String memo;
	
	private Long expense;

}
