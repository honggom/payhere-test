package pay.here.payheretest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pay.here.payheretest.entity.AccountBook;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

}
