package org.blog.project.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.blog.project.dto.ReplyDto;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reply {
	@Id //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; // 시퀀스, auto_increment

	@Column(nullable = false, length = 200)
	private String content;

	@ManyToOne // 여러개의 답변/ 하나의 게시글
	@JoinColumn(name = "boardId") // 연관 관계
	private Board board; // Board객체안에서 @JsonIgnoreProperties({"board"})어노테이션으로 인해 이 칼럼은 호출 하지 않는다.

	@ManyToOne
	@JoinColumn(name="userId") // 댓글 작성자
	private User user;

	@CreationTimestamp
	private LocalDateTime createDate;


}








