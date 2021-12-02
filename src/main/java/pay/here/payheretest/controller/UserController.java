package pay.here.payheretest.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pay.here.payheretest.dto.EmailAndPasswordDto;
import pay.here.payheretest.jwt.JwtUtil;
import pay.here.payheretest.service.RefreshJwtService;
import pay.here.payheretest.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

	private final JwtUtil jwtUtil;

	private final UserService userService;
	private final RefreshJwtService refreshJwtService;

	/*
	 * 요구사항 1. 고객은 이메일과 비밀번호 입력을 통해서 회원 가입을 할 수 있습니다.
	 */
	@PostMapping("/user/signin")
	public ResponseEntity signin(@RequestBody EmailAndPasswordDto emailAndPasswordDto) {
			if (userService.alreadyExists(emailAndPasswordDto.getEmail())) {
				return new ResponseEntity<>("이미 가입된 이메일입니다.", HttpStatus.BAD_REQUEST);
			} else {
				userService.signin(emailAndPasswordDto);
				return new ResponseEntity<>("회원 가입이 완료되었습니다.", HttpStatus.CREATED);
			}
	}

	/*
	 * 요구사항 2-1.(로그인) 고객은 회원 가입이후, 로그인과 로그아웃을 할 수 있습니다.
	 */
	@PostMapping("/user/login")
	public ResponseEntity login(@RequestBody EmailAndPasswordDto emailAndPasswordDto, HttpServletResponse response) {
			int count = userService.countByEmailAndPassword(emailAndPasswordDto);

			if (count == 1) { // 로그인 성공
				String email = emailAndPasswordDto.getEmail();

				response.setHeader("jwt-auth-token", jwtUtil.generateJwt(email));
				response.setHeader("email", email);

				jwtUtil.saveRefreshJwt(email);
				return new ResponseEntity<>("로그인이 완료되었습니다.", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("아이디 또는 비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST);
			}
	}

	/*
	 * 요구사항 2-2.(로그아웃) 고객은 회원 가입이후, 로그인과 로그아웃을 할 수 있습니다.
	 */
	@DeleteMapping("/user/logout")
	public ResponseEntity logout(@RequestHeader(value = "email") String email) {
		refreshJwtService.deleteRefreshJwt(email);
		return new ResponseEntity<>("로그아웃 되었습니다.", HttpStatus.OK);
	}

}