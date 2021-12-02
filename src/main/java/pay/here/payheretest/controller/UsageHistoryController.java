package pay.here.payheretest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pay.here.payheretest.dto.UsageHistoryDto;
import pay.here.payheretest.service.UsageHistoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UsageHistoryController {

	private final UsageHistoryService usageHistoryService;

	/*
	 * 요구사항 3-a. 가계부에 오늘 사용한 돈의 금액과 관련된 메모를 남길 수 있습니다.
	 */
	@PostMapping("/usage-history")
	public ResponseEntity insertUsageHistory(@RequestBody UsageHistoryDto usageHistoryDto) {
		usageHistoryService.insertUsageHistory(usageHistoryDto);
		return new ResponseEntity<>("내역이 성공적으로 작성되었습니다.", HttpStatus.OK);
	}

	/*
	 * 요구사항 3-b. 가계부에서 수정을 원하는 내역은 금액과 메모를 수정 할 수 있습니다.
	 */
	@PutMapping("/usage-history")
	public ResponseEntity updateUsageHistory(@RequestBody UsageHistoryDto usageHistoryDto) {
		usageHistoryService.updateUsageHistory(usageHistoryDto);
		return new ResponseEntity<>("내역이 성공적으로 수정되었습니다.", HttpStatus.OK);
	}

	/*
	 * 요구사항 3-c. 가계부에서 삭제를 원하는 내역은 삭제 할 수 있습니다.
	 */
	@DeleteMapping("/usage-history/{id}")
	public ResponseEntity deleteUsageHistory(@PathVariable Long usageHistoryId) {
		usageHistoryService.deleteUsageHistory(usageHistoryId);
		return new ResponseEntity<>("내역이 성공적으로 삭제되었습니다.", HttpStatus.OK);
	}

	/*
	 * 요구사항 3-d. 삭제한 내역은 언제든지 다시 복구 할 수 있어야 한다.
	 */
	@PutMapping("/usage-history/restore")
	public ResponseEntity restoreUsageHistory(@RequestBody UsageHistoryDto usageHistoryDto) {
		usageHistoryService.restoreUsageHistory(usageHistoryDto);
		return new ResponseEntity<>("내역이 성공적으로 복구되었습니다.", HttpStatus.OK);
	}
	
}
