package org.blog.project.repository;

import java.util.Optional;

import org.blog.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


// DAO
// 자동으로 bean등록이 된다.
// @Repository // 생략 가능하다.
public interface UserRepository extends JpaRepository<User, Integer>{
    /**
     * 유저내임을 찾는 쿼리 문 생성
     * 실제 쿼리
     * select * from user where username = ?
     */

    Optional<User> findByUsername(String username);

}


// JPA Naming 쿼리
// SELECT * FROM user WHERE username = ?1 AND password = ?2;
//User findByUsernameAndPassword(String username, String password);

//	@Query(value="SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//	User login(String username, String password);
