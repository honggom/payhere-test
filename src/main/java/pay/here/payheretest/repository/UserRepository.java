package pay.here.payheretest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pay.here.payheretest.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);
	
	int countByEmail(String email);
	
	int countByEmailAndPassword(String email, String password);

}
