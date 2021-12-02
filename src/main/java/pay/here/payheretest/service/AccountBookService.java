package pay.here.payheretest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pay.here.payheretest.dto.AccountBookDto;

public interface AccountBookService {
	
	public void insertAccountBook(String email);
	
	public Page<AccountBookDto> getAccountBook(String email, Pageable pageable);
	
}