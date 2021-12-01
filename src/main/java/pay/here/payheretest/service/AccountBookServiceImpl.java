package pay.here.payheretest.service;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
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
	
	private final ModelMapper modelMapper;
	private final AccountBookRepository accountBookRepository;
	private final UserRepository userRepository;
	
	@Override
	public void insertAccountBook(String email, AccountBookDto accountBookDto) {
		User user = userRepository.findByEmail(email);
		
		if (accountBookDto.getDate() == null) {
			accountBookDto.setDate(LocalDate.now());
		}
		
		accountBookRepository.save(AccountBook.builder()
				                              .user(user)
				                              .date(accountBookDto.getDate())
				                              .build());
	}
	
}
