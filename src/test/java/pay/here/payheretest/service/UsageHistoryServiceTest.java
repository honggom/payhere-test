package pay.here.payheretest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import pay.here.payheretest.dto.UsageHistoryDto;
import pay.here.payheretest.entity.AccountBook;
import pay.here.payheretest.entity.UsageHistory;
import pay.here.payheretest.entity.User;
import pay.here.payheretest.repository.AccountBookRepository;
import pay.here.payheretest.repository.UsageHistoryRepository;
import pay.here.payheretest.repository.UserRepository;

@SpringBootTest
@Transactional
@Rollback(true)
class UsageHistoryServiceTest {
	
	@Autowired
	UsageHistoryService usageHistoryService;
	
	@Autowired
	UsageHistoryRepository usageHistoryRepository;
	
	@Autowired
	AccountBookRepository accountBookRepository;
	
	@Autowired
	UserRepository userRepository;

	@Test
	void 가계부를_foreign_key로_사용하여_사용내역을_추가한다() {
		// given 
		User savedUser = userRepository.save(User.builder()
                                                 .email("cavok699")
                                                 .password("1234")
                                                 .build());
		
		AccountBook savedAccountBook = accountBookRepository.save(AccountBook.builder()
                                                            .user(savedUser)
                                                            .date(LocalDate.now())
                                                            .build());
		
		UsageHistoryDto dto = new UsageHistoryDto();
		dto.setAccountBookId(savedAccountBook.getId());
		dto.setMemo("abc");
		dto.setExpense(1000L);
		
		// when
		usageHistoryService.insertUsageHistory(dto);
		
		// then
		assertEquals(1, usageHistoryRepository.count());
	}
	
	@Test
	void 사용내역의_금액과_메모를_수정한다() {
		// given 
		User savedUser = userRepository.save(User.builder()
                                                 .email("cavok699")
                                                 .password("1234")
                                                 .build());
		
		AccountBook savedAccountBook = accountBookRepository.save(AccountBook.builder()
                                                            .user(savedUser)
                                                            .date(LocalDate.now())
                                                            .build());
		
		UsageHistory savedUsageHistory = usageHistoryRepository.save(UsageHistory.builder()
				                                               .accountBook(savedAccountBook)
				                                               .memo("old")
				                                               .expense(1L)
				                                               .build());
		UsageHistoryDto dto = new UsageHistoryDto();
		dto.setId(savedUsageHistory.getId());
		dto.setMemo("new");
		dto.setExpense(1000L);
		
		// when
		usageHistoryService.updateUsageHistory(dto);
		
		UsageHistory updatedUageHistory = usageHistoryRepository.findById(savedUsageHistory.getId()).get();
		String updatedMemo = updatedUageHistory.getMemo();
		Long updatedExpense = updatedUageHistory.getExpense();
		
		// then
		assertEquals("new", updatedMemo);
		assertEquals(1000L, updatedExpense);
	}
	
	@Test
	void 사용내역을_삭제한다() {
		// given 
		User savedUser = userRepository.save(User.builder()
                                                 .email("cavok699")
                                                 .password("1234")
                                                 .build());
		
		AccountBook savedAccountBook = accountBookRepository.save(AccountBook.builder()
                                                            .user(savedUser)
                                                            .date(LocalDate.now())
                                                            .build());
		
		UsageHistory savedUsageHistory = usageHistoryRepository.save(UsageHistory.builder()
				                                               .accountBook(savedAccountBook)
				                                               .memo("old")
				                                               .expense(1L)
				                                               .build());
		
		// when
		usageHistoryService.deleteUsageHistory(savedUsageHistory.getId());
		
		// then
		UsageHistory deletedUsageHistory = usageHistoryRepository.findById(savedUsageHistory.getId()).get();
		assertEquals(true, deletedUsageHistory.isDeleted());
	}
	
	@Test
	void 삭제된_사용내역을_복구한다() {
		// given 
		User savedUser = userRepository.save(User.builder()
                                                 .email("cavok699")
                                                 .password("1234")
                                                 .build());
		
		AccountBook savedAccountBook = accountBookRepository.save(AccountBook.builder()
                                                            .user(savedUser)
                                                            .date(LocalDate.now())
                                                            .build());
		
		UsageHistory savedUsageHistory = usageHistoryRepository.save(UsageHistory.builder()
				                                               .accountBook(savedAccountBook)
				                                               .memo("old")
				                                               .expense(1L)
				                                               .build());
		
		Long usageHistoryId = savedUsageHistory.getId();
		
		usageHistoryService.deleteUsageHistory(usageHistoryId);
		
		UsageHistoryDto dto = new UsageHistoryDto();
		dto.setId(usageHistoryId);
		
		// when
		usageHistoryService.restoreUsageHistory(dto);
		
		// then
		assertEquals(false, usageHistoryRepository.findById(usageHistoryId).get().isDeleted());
	}
	
	@Test
	void 가계부의_사용내역_리스트를_조회한다() {
		// given 
		User savedUser = userRepository.save(User.builder()
                                                 .email("cavok699")
                                                 .password("1234")
                                                 .build());
		
		AccountBook savedAccountBook = accountBookRepository.save(AccountBook.builder()
                                                            .user(savedUser)
                                                            .date(LocalDate.now())
                                                            .build());
		
		for (int i = 0; i < 30; i++) {
			usageHistoryRepository.save(UsageHistory.builder()
					                                .accountBook(savedAccountBook)
					                                .memo("hong")
					                                .expense(100L)
					                                .build());
		}
		
		// when
		List<UsageHistoryDto> usageHistoryList = usageHistoryService.getUsageHistory(savedAccountBook.getId());
		
		// then
		assertEquals(30, usageHistoryList.size());
	}

}
