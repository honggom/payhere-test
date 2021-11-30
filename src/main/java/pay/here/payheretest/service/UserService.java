package pay.here.payheretest.service;

import java.security.NoSuchAlgorithmException;

import pay.here.payheretest.dto.EmailAndPassword;

public interface UserService  {
	
	public boolean alreadyExists(String email);
	
	public void signin(EmailAndPassword emailAndPassword) throws NoSuchAlgorithmException;
	
	public int countByEmailAndPassword(EmailAndPassword emailAndPassword) throws NoSuchAlgorithmException;
	
}