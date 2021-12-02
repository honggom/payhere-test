package pay.here.payheretest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import pay.here.payheretest.dto.AccountBookDto;
import pay.here.payheretest.entity.AccountBook;
import pay.here.payheretest.entity.User;
import pay.here.payheretest.repository.AccountBookRepository;
import pay.here.payheretest.repository.UserRepository;

@SpringBootTest
@Transactional
@Rollback(true)
class AccountBookServiceTest {

	@Autowired
	AccountBookService accountBookService;
	
	@Autowired
	AccountBookRepository accountBookRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	void 고객을_foreign_key로_사용하여_가계부를_추가한다() {
		// given 
		String email = "cavok699";
		
		userRepository.save(User.builder()
                                .email(email)
				                .password("1234")
				                .build());
		
		// when 
		accountBookService.insertAccountBook(email);
		
		// then
		assertEquals(1L, accountBookRepository.count());
	}
	
	@Test
	void 가계부_리스트를_조회한다() {
		// given 
		String email = "cavok699";
		
		User savedUser = userRepository.save(User.builder()
                                       .email(email)
				                       .password("1234")
				                       .build());

		for (int i = 0; i < 30; i++) {
			accountBookRepository.save(AccountBook.builder()
	                             .user(savedUser)
	                             .date(LocalDate.now())
	                             .build());
		}
		
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
		
		// when
		Page<AccountBookDto> dtoList = accountBookService.getAccountBook(email, pageRequest);
		
		// then
		assertEquals(30, dtoList.getTotalElements());
	}

}
