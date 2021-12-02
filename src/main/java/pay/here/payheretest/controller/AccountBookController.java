package pay.here.payheretest.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pay.here.payheretest.dto.AccountBookDto;
import pay.here.payheretest.dto.UsageHistoryDto;
import pay.here.payheretest.jwt.JwtUtil;
import pay.here.payheretest.service.AccountBookService;
import pay.here.payheretest.service.UsageHistoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountBookController {

	private final JwtUtil jwtUtil;
	
	private final AccountBookService accountBookService;
	private final UsageHistoryService usageHistoryService;

	@PostMapping("/account-book")
	public ResponseEntity insertAccountBook(@RequestHeader(value = "jwt-auth-token") String jwt) {
		accountBookService.insertAccountBook(jwtUtil.getEmail(jwt));
		return new ResponseEntity<>("가계부가 생성되었습니다.", HttpStatus.OK);
	}

	/*
	 * 요구사항 3-e. 가계부에서 이제까지 기록한 가계부 리스트를 볼 수 있습니다.
	 */
	@GetMapping("/account-books")
	public ResponseEntity getAccountBook(@RequestHeader(value = "jwt-auth-token") String jwt, 
			                             Pageable pageable) {
		return new ResponseEntity<Page<AccountBookDto>>(accountBookService.getAccountBook(jwtUtil.getEmail(jwt), pageable), HttpStatus.OK);
	}
	
	/*
	 * 요구사항 3-f. 가계부에서 상세한 세부 내역을 볼 수 있습니다. 
	 */
	@GetMapping("/account-book/{accountBookId}/usage-history-list")
	public ResponseEntity getUsageHistory(@PathVariable Long accountBookId) {
		return new ResponseEntity<List<UsageHistoryDto>>(usageHistoryService.getUsageHistory(accountBookId), HttpStatus.OK);
	}

}
