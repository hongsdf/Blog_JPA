package org.blog.project.service;

import org.blog.project.model.RoleType;
import org.blog.project.model.User;
import org.blog.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder Encoder;


	/**
	 * 회원 가입
	 * @param user
	 */
	@Transactional
	public int joinUser(User user) {
		String rawPassword = user.getPassword(); // 1234 원문
		String encPassword = Encoder.encode(rawPassword); // 해쉬
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		try {
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 *  회원 수정
	 * 영속성 컨텍스트에 먼저 user 오브젝트를 영속화 시키고,
	 * 영속화된 user 오브젝트를 수정
	 */
	@Transactional
	public void  updateUser(User user){
		User persistanceUser = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("해당 아이디는 없습니다.");
		});

		// OAuth 사용자들은 패스워드를 수정할수 없다.
		// OAuth 칼럼 null인 사용자만 수정할수 있다.
		if(persistanceUser.getOauth() == null || persistanceUser.getOauth().equals("")){
			String password = user.getPassword(); // 사용자로 부터 받은 패스워드
			String encodingPassword = Encoder.encode(password); // 해쉬값을 취한 후
			persistanceUser.setPassword(encodingPassword);
			persistanceUser.setEmail(user.getEmail());
		}





		// 트랜잭션 종료 -> commit
		// 영속컨택스트안의 객체가 변화되면 '더티체킹' (Update 된 것에 대해 자동으로 db에 반영)
	}

	/**
	 * 회원 찾기
	 * @param username
	 * @return
	 */
	@Transactional(readOnly = true)
	public User findUser(String username){
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
		return user;

	}




}
