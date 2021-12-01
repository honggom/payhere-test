package pay.here.payheretest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pay.here.payheretest.entity.RefreshJwt;
import pay.here.payheretest.entity.User;

public interface RefreshJwtRepository extends JpaRepository<RefreshJwt, Long> {
	
	Optional<RefreshJwt> findByUser(User user);

}
