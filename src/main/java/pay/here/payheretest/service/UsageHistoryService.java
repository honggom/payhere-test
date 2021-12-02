package pay.here.payheretest.service;

import java.util.List;

import pay.here.payheretest.dto.UsageHistoryDto;

public interface UsageHistoryService {
	
	public void insertUsageHistory(UsageHistoryDto usageHistoryDto);
	
	public void updateUsageHistory(UsageHistoryDto usageHistoryDto);
	
	public void deleteUsageHistory(Long usageHistoryId);
	
	public void restoreUsageHistory(UsageHistoryDto usageHistoryDto);
	
	public List<UsageHistoryDto> getUsageHistory(Long accountBookId);
	
}
