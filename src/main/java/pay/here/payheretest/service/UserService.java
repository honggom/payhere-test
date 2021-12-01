package pay.here.payheretest.service;

import java.security.NoSuchAlgorithmException;

import pay.here.payheretest.dto.EmailAndPasswordDto;

public interface UserService  {
	
	public boolean alreadyExists(String email);
	
	public void signin(EmailAndPasswordDto emailAndPassword) throws NoSuchAlgorithmException;
	
	public int countByEmailAndPassword(EmailAndPasswordDto emailAndPassword) throws NoSuchAlgorithmException;
	
}