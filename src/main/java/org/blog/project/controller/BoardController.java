package org.blog.project.controller;


import org.blog.project.dto.ResponseDto;
import org.blog.project.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    // Home 화면
    // 두가지 uri 기입 공백 , "/"
    // @AuthenticationPrincipal PrincipalDetail principal
    @GetMapping({"","/"})
    public String index(Model model,@PageableDefault(size=3, sort="id", direction = Sort.Direction.DESC) Pageable pageable){ // model에 글목록을 담는다. viewResolver가 필요 = >해당 model의 데이터를 가지 index페이지로 이동
         model.addAttribute("boards",boardService.boardList(pageable));
        return "index";
    }





   // user들만 게시글을 작성할수 있다.
    @GetMapping("/board/writeForm")
    public String boardWrite(){
        return "board/writeForm";
    }


    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id,Model model){
        model.addAttribute("board",boardService.boardInfo(id));
        return "board/detail";
    }

    @GetMapping( "/board/{id}/updateForm")
    public String updateForm(@PathVariable int id,Model model){
        model.addAttribute("board",boardService.boardInfo(id));
        return "board/updateForm";
    }




}
