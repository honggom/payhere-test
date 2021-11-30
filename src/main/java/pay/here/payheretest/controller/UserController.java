package pay.here.payheretest.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pay.here.payheretest.dto.EmailAndPassword;
import pay.here.payheretest.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
	
	private final UserService userService;
	
	/*
	 * 요구사항 1.
	 * 고객은 이메일과 비밀번호 입력을 통해서 회원 가입을 할 수 있습니다.
	 */
	@PostMapping("/signin")
	public ResponseEntity signin(@RequestBody EmailAndPassword emailAndPassword) {
		try {
			if (userService.alreadyExists(emailAndPassword.getEmail())) {
				return new ResponseEntity<>("이미 가입된 이메일입니다.", HttpStatus.BAD_REQUEST);
			} else {
				userService.signin(emailAndPassword);
				return new ResponseEntity<>("회원 가입이 완료되었습니다.", HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("서버 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*
	 * 요구사항 2-1.(로그인)
	 * 고객은 회원 가입이후, 로그인과 로그아웃을 할 수 있습니다.
	 */
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody EmailAndPassword emailAndPassword, HttpServletResponse response) {
		try {
			int count = userService.countByEmailAndPassword(emailAndPassword);
			
			if (count == 1) { // 로그인 성공
				// TODO jwt 로직
				return null;
			} else {
				return new ResponseEntity<>("아이디 또는 비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>("서버 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}