package pay.here.payheretest.service;

import java.security.NoSuchAlgorithmException;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pay.here.payheretest.dto.EmailAndPassword;
import pay.here.payheretest.entity.User;
import pay.here.payheretest.repository.UserRepository;
import pay.here.payheretest.util.SHA256;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final SHA256 sha256;
	
	@Override
	public boolean alreadyExists(String email) {
		int count = userRepository.countByEmail(email);
		
		if (count >= 1) {
            return true;
		} else {
            return false;
		}
	}
	
	@Override
	public void signin(EmailAndPassword emailAndPassword) throws NoSuchAlgorithmException {
		userRepository.save(User.builder()
				                .email(emailAndPassword.getEmail())
				                .password(sha256.encrypt(emailAndPassword.getPassword()))
				                .build());
	}

	@Override
	public int countByEmailAndPassword(EmailAndPassword emailAndPassword) throws NoSuchAlgorithmException {
		int count = userRepository
				      .countByEmailAndPassword(emailAndPassword.getEmail(), sha256.encrypt(emailAndPassword.getPassword()));
		
		return count;
	}
	
}