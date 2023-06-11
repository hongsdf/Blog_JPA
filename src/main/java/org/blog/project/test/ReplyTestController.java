package org.blog.project.test;

import org.blog.project.model.Board;
import org.blog.project.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 무한 참조 테스트
 */
@RestController
public class ReplyTestController {

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/test/board/{id}")
    public Board getBoard(@PathVariable int id){
        return boardRepository.findById(id).get(); // jaskson라이브러리에서 해당 id와 관련 된 model을 찾고, model의 getter메서드를 수행한다.
    }


    @GetMapping("/test/reply")
    public List<Board> getReply(@PathVariable int id){
        return boardRepository.findAll(); // jaskson라이브러리에서 해당 id와 관련 된 model을 찾고, model의 getter메서드를 수행한다.
    }




}
