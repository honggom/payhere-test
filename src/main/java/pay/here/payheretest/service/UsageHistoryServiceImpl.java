package pay.here.payheretest.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pay.here.payheretest.dto.UsageHistoryDto;
import pay.here.payheretest.entity.AccountBook;
import pay.here.payheretest.entity.UsageHistory;
import pay.here.payheretest.repository.AccountBookRepository;
import pay.here.payheretest.repository.UsageHistoryRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UsageHistoryServiceImpl implements UsageHistoryService {
	
	private final ModelMapper modelMapper;
	
	private final AccountBookRepository accountBookRepository;
	private final UsageHistoryRepository usageHistoryRepository;
	
	@Override
	public void insertUsageHistory(UsageHistoryDto usageHistoryDto) {
		AccountBook accountBook = accountBookRepository
                                      .findById(usageHistoryDto.getAccountBookId()).get();
		
		usageHistoryRepository.save(UsageHistory.builder()
				                                .accountBook(accountBook)
				                                .memo(usageHistoryDto.getMemo())
				                                .expense(usageHistoryDto.getExpense())
				                                .build());
	}
	
	@Override
	public void updateUsageHistory(UsageHistoryDto usageHistoryDto) {
		UsageHistory usageHistory = usageHistoryRepository
				                        .findById(usageHistoryDto.getId()).get();
		
		usageHistory.setExpense(usageHistoryDto.getExpense());
		usageHistory.setMemo(usageHistoryDto.getMemo());
		
		usageHistoryRepository.save(usageHistory);
	}

	@Override
	public void deleteUsageHistory(Long usageHistoryId) {
		UsageHistory usageHistory = usageHistoryRepository
                                        .findById(usageHistoryId).get();
		
		usageHistory.setDeleted(true);
		
		usageHistoryRepository.save(usageHistory);
	}

	@Override
	public void restoreUsageHistory(UsageHistoryDto usageHistoryDto) {
		UsageHistory usageHistory = usageHistoryRepository
                                        .findById(usageHistoryDto.getId()).get();
		
		usageHistory.setDeleted(false);
		
		usageHistoryRepository.save(usageHistory);
	}

	@Override
	public List<UsageHistoryDto> getUsageHistory(Long accountBookId) {
		AccountBook accountBook = accountBookRepository.findById(accountBookId).get();
		
		List<UsageHistory> usageHistoryList = usageHistoryRepository.findByAccountBookAndDeletedFalse(accountBook);
		
		return usageHistoryList.stream()
				               .map(usageHistory -> modelMapper.map(usageHistory, UsageHistoryDto.class))
				               .collect(Collectors.toList());
	}
	
}
