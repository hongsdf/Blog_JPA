package org.blog.project.repository;

import org.blog.project.model.Board;
import org.blog.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


// DAO
// 자동으로 bean등록이 된다.
// @Repository // 생략 가능하다.
public interface BoardRepository extends JpaRepository<Board, Integer>{


}


