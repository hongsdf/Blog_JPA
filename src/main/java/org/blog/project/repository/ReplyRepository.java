package org.blog.project.repository;

import org.blog.project.dto.ReplyDto;
import org.blog.project.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

// jpa 영속성 컨택스트 <테이블,pk값>
public interface ReplyRepository extends JpaRepository<Reply,Integer> {

    @Modifying
    @Query(value="INSERT INTO reply(userId, boardId,content,createDate) values(?1,?2,?3,now())", nativeQuery = true)

    int MySave(int userId, int boardId,String content); //  업데이트 된 행의 개수를 리턴

}
