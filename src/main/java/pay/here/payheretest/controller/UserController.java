package pay.here.payheretest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pay.here.payheretest.dto.EmailAndPasswordDto;
import pay.here.payheretest.jwt.JwtUtil;
import pay.here.payheretest.service.RefreshJwtService;
import pay.here.payheretest.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
	
	private final UserService userService;
	private final RefreshJwtService refreshJwtService;
	private final JwtUtil jwtUtil;
	
	@GetMapping("/hi")
	public String hi() {
		return "hi";
	}
	
	/*
	 * 요구사항 1.
	 * 고객은 이메일과 비밀번호 입력을 통해서 회원 가입을 할 수 있습니다.
	 */
	@PostMapping("/signin")
	public ResponseEntity signin(@RequestBody EmailAndPasswordDto emailAndPasswordDto) {
		try {
			if (userService.alreadyExists(emailAndPasswordDto.getEmail())) {
				return new ResponseEntity<>("이미 가입된 이메일입니다.", HttpStatus.BAD_REQUEST);
			} else {
				userService.signin(emailAndPasswordDto);
				return new ResponseEntity<>("회원 가입이 완료되었습니다.", HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("알 수 없는 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*
	 * 요구사항 2-1.(로그인)
	 * 고객은 회원 가입이후, 로그인과 로그아웃을 할 수 있습니다.
	 */
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody EmailAndPasswordDto emailAndPasswordDto, HttpServletResponse response) {
		try {
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
			
		} catch (Exception e) {
			return new ResponseEntity<>("알 수 없는 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*
	 * 요구사항 2-2.(로그아웃)
	 * 고객은 회원 가입이후, 로그인과 로그아웃을 할 수 있습니다.
	 */
	@DeleteMapping("/logout")
	public ResponseEntity logout(HttpServletRequest request) {
		try {
			refreshJwtService.deleteRefreshJwt(request.getHeader("email"));
			return new ResponseEntity<>("로그아웃 되었습니다.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("알 수 없는 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}