package pay.here.payheretest.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pay.here.payheretest.repository.RefreshJwtRepository;
import pay.here.payheretest.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshJwtServiceImpl implements RefreshJwtService {
	
	private final RefreshJwtRepository refreshJwtRepository;
	private final UserRepository userRepository;

	@Override
	public void deleteRefreshJwt(String email) {
		refreshJwtRepository.delete(refreshJwtRepository.findByUser(userRepository.findByEmail(email)).get());
	}

}
