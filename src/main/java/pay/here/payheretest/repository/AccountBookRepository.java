package pay.here.payheretest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import pay.here.payheretest.entity.AccountBook;
import pay.here.payheretest.entity.User;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {
	
	Page<AccountBook> findByUser(User user, Pageable pageable);

}
