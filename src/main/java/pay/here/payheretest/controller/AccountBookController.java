package pay.here.payheretest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pay.here.payheretest.dto.AccountBookDto;
import pay.here.payheretest.service.AccountBookService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account-book")
public class AccountBookController {
	
	private final AccountBookService accountBookService;
	
	@PostMapping("")
	public ResponseEntity insertAccountBook(@RequestBody AccountBookDto accountBookDto, HttpServletRequest request) {
		try {
			String email = request.getHeader("jwt-auth-token");
			accountBookService.insertAccountBook(email, accountBookDto);
			return new ResponseEntity<>("가계부가 성공적으로 생성되었습니다.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("알 수 없는 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
