package org.blog.project.controller.api;

import org.blog.project.config.auth.PrincipalDetail;
import org.blog.project.dto.ReplyDto;
import org.blog.project.dto.ResponseDto;
import org.blog.project.model.Board;
import org.blog.project.model.Reply;
import org.blog.project.service.BoardService;
import org.blog.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
public class BoardApiController {

	@Autowired
	private BoardService boardService;



	@PostMapping("/api/writting")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal){
		boardService.writting(board,principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}

	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id){
		boardService.boardDelete(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}

	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board){
		boardService.boardUpdate(id,board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	// 클라이언트로 부터 데이터를 받을 때 => [컨트롤러에서 dto]를 만들어 주는 것이 좋다.


//	@PostMapping("/api/board/{id}/reply")
//	public ResponseDto<Integer> save(@PathVariable int id, @RequestBody Reply reply,@AuthenticationPrincipal PrincipalDetail principal){
//		boardService.replyWrite(principal.getUser(),id,reply);
//		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
//	}

	@PostMapping("/api/board/{id}/reply")
	public ResponseDto<Integer> save(@RequestBody ReplyDto replyDto){
		boardService.replyWrite(replyDto);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}

	@DeleteMapping("/api/reply/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> ReplyDelete(@PathVariable int replyId){
		boardService.replyDelete(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}


}
