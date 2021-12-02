package pay.here.payheretest.service;

import pay.here.payheretest.dto.EmailAndPasswordDto;

public interface UserService  {
	
	public boolean alreadyExists(String email);
	
	public void signin(EmailAndPasswordDto emailAndPassword);
	
	public int countByEmailAndPassword(EmailAndPasswordDto emailAndPassword);
	
}