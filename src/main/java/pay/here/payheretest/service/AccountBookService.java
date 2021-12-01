package pay.here.payheretest.service;

import pay.here.payheretest.dto.AccountBookDto;

public interface AccountBookService {
	
	public void insertAccountBook(String email, AccountBookDto accountBookDto);
	
}