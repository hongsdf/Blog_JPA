package org.blog.project.service;

import org.blog.project.dto.ReplyDto;
import org.blog.project.model.Board;
import org.blog.project.model.Reply;
import org.blog.project.model.RoleType;
import org.blog.project.model.User;
import org.blog.project.repository.BoardRepository;
import org.blog.project.repository.ReplyRepository;
import org.blog.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
public class BoardService {


	@Autowired
	private ReplyRepository replyRepository;


	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public void writting(Board board, User user) { // title, content
		// 유저 정보 등록
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board); // 글 등록
	}

	/**
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<Board> boardList(Pageable pageable) {

		return boardRepository.findAll(pageable); // 페이지징 되서 페이지 호출
	}


	/**
	 * 글 상세 보기
	 */

	@Transactional(readOnly = true)
	public Board boardInfo(int id) {
		return boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 상세보기에 대한 아이디를 찾을 수 없습니다.:" + id);
		});
	}

	/**
	 * 글 삭제 하기
	 */

	@Transactional
	public void boardDelete(int id) {
		boardRepository.deleteById(id);
	}


	/**
	 * 글 삭제
	 */

	@Transactional
	public void boardUpdate(int id, Board board) {
		Board findBoard = boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 찾기 실패");
		}); // 1단계 select 으로 영속화 시키기
		findBoard.setTitle(board.getTitle());
		findBoard.setContent(board.getContent());
		// 메서드 종료시 트랜잭션 (service)종료 될때. => 이때 '더티 체킹'이 일어나 자동 업데이트 db flush
	}

//	// 댓글 작성하기
//	@Transactional
//	public void replyWrite(User user, int boardId, Reply reply){
//		Board board = boardRepository.findById(boardId).orElseThrow(()->{
//			return new IllegalArgumentException("댓글 쓰기 실패 게시글 아이디를 찾을 수 없습니다." );
//		});
//
//		reply.setUser(user);
//		reply.setBoard(board);
//		replyRepository.save(reply);
//	}

	// 댓글 작성하기
//	@Transactional
//	public void replyWrite( ReplyDto replyDto){
//		User user = userRepository.findById(replyDto.getUserId()).orElseThrow(()->{
//			return new IllegalArgumentException("댓글 쓰기 실패 유저 아이디를 찾을 수 없습니다." );
//		}); // 영속화
//
//		Board board = boardRepository.findById(replyDto.getBoardId()).orElseThrow(()->{
//			return new IllegalArgumentException("댓글 쓰기 실패 게시글 아이디를 찾을 수 없습니다." );
//		}); // 영속화
//
//		Reply reply = Reply.builder()
//						.user(user)
//								.board(board)
//										.content(replyDto.getContent())
//												.build();
//
//
//		replyRepository.save(reply);
//	}

	// 댓글 작성하기
	@Transactional
	public void replyWrite( ReplyDto replyDto){
		replyRepository.MySave(replyDto.getUserId(), replyDto.getBoardId(), replyDto.getContent());

	}

	/**
	 * 댓글 삭제
	 *
	 */

	public void replyDelete(int replyId){
		replyRepository.deleteById(replyId);
	}
}
