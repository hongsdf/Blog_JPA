package org.blog.project.controller.api;

import org.blog.project.config.auth.PrincipalDetail;
import org.blog.project.config.auth.PrincipalService;
import org.blog.project.dto.ResponseDto;
import org.blog.project.model.User;
import org.blog.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
public class UserApiController {

	@Autowired
	private UserService userService;


	// 전통적인 session방식
    /*@Autowired
    private HttpSession httpSession;*/


	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 *  415에러
	 *  404에러
	 *
	 * @param
	 * @return
	 */
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user){
		System.out.println("ApiController: save함수 호출");
		userService.joinUser(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}

		/**
		 * /auth/loginProc 요청을 스프링 시큐리티가 가로채게 할것
		 */

		@PutMapping("/user")
		public ResponseDto<Integer> update(@RequestBody User user){
			userService.updateUser(user);
			System.out.println("ApiController: update 호출");

			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			return new ResponseDto<Integer>(HttpStatus.OK.value(),1);

		}
}
