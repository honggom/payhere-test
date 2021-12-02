package pay.here.payheretest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pay.here.payheretest.entity.AccountBook;
import pay.here.payheretest.entity.UsageHistory;

public interface UsageHistoryRepository extends JpaRepository<UsageHistory, Long> {
	
	List<UsageHistory> findByAccountBookAndDeletedFalse(AccountBook accountBook);

}
