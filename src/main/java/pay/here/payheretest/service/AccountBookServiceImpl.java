package pay.here.payheretest.service;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pay.here.payheretest.dto.AccountBookDto;
import pay.here.payheretest.entity.AccountBook;
import pay.here.payheretest.entity.User;
import pay.here.payheretest.repository.AccountBookRepository;
import pay.here.payheretest.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountBookServiceImpl implements AccountBookService {
	
	private final AccountBookRepository accountBookRepository;
	private final UserRepository userRepository;
	
	@Override
	public void insertAccountBook(String email) {
		User user = userRepository.findByEmail(email);
		
		accountBookRepository.save(AccountBook.builder()
				                              .user(user)
				                              .date(LocalDate.now())
				                              .build());
	}


	@Override
	public Page<AccountBookDto> getAccountBook(String email, Pageable pageable) {
		User user = userRepository.findByEmail(email);
		
		Page<AccountBook> accountBooks = accountBookRepository.findByUser(user, pageable);
		
		Page<AccountBookDto> accountBooksDto = accountBooks.map(accountBook -> new AccountBookDto(accountBook.getId(), 
				                                		                                          accountBook.getDate()));
		return accountBooksDto;
	}
	
}