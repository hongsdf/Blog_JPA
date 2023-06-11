package org.blog.project.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.blog.project.config.auth.PrincipalDetail;
import org.blog.project.model.KakaoProfile;
import org.blog.project.model.OAuthToken;
import org.blog.project.model.User;
import org.blog.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;


// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static이하에 있는 /js/**, /css/**, /image/**

@Controller
public class UserController {

	@Value("${cos.key}")
	private String cosKey;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private BCryptPasswordEncoder Encoder;




	/**
	 *  인증이 안된 사용자들이 출입할수 있는 경로 /auth/~ 이하
	 *  주소가 / 이면 index.jsp 허용
	 * static하위 폴더 있는 js,css,img 범위 허용
	 *
	 * @return
	 */
	// 회원 가입
	// /auth 가 붙은 url은 인증이 필요없다.
	@GetMapping("/auth/joinForm")
	public String signUpForm(){
		return "user/joinForm";

	}


	// 로그인
	// /auth 가 붙은 url은 인증이 필요없다.
	@GetMapping("/auth/loginForm")
	public String LoginForm(){
		return "user/loginForm";

	}



	@GetMapping("/user/updateForm")
	public String updateForm(@AuthenticationPrincipal PrincipalDetail principal){
		return "user/updateForm";
	}

	@GetMapping("/auth/kakao/callback")
	public String kakaoCallBackLogin(String code) { // @ResponseBody를 리턴 타입으로 받으면 data를 받는다.

		// 순서1. post방법 key-value 데이터를  카카오 쪽으로 요청해야함
		RestTemplate rt = new RestTemplate(); // 기존에는 HttpURLConntection을 사용했음,OkHttp,Retrofit2

		HttpHeaders headers = new HttpHeaders(); // 헤더 생성
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // 의미 내가 전송할 데이터의 형태를 알려줌

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); // body정보를 담을 객체 생성
		params.add("grant_type", "authorization_code");
		params.add("client_id", "de8912d3a644d419b09bf7a6596ab3ce");
		params.add("redirect_uri", "http://localhost:8001/auth/kakao/callback");
		params.add("code", code); //code값은 동적

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> KakaoTokenRequest = new HttpEntity<>(params, headers); // 바디값(param을 담고 있는)과 헤더값을 가지고 있는 객체가 됨

		// Http 요청하기 - post방식
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				KakaoTokenRequest,
				String.class
		);

		// Gson : json형태 파싱 라이브러리 ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oAuthToken = null;
		try {
			oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		System.out.println(oAuthToken.getAccess_token());

		/**
		 *  getBody := reponse값을 벗기는 메서드
		 *
		 */

		RestTemplate rt2 = new RestTemplate();

		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
				new HttpEntity<>(headers2);

		// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest2,
				String.class
		);
		System.out.println(response2.getBody());

		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// User 오브젝트 : username, password, email
		System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
		System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());

		System.out.println("블로그서버 유저네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
		System.out.println("블로그서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		// UUID란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘
		UUID uuid = UUID.randomUUID();
//		System.out.println("블로그서버 패스워드 : " + cosKey);

		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();

		System.out.println("카카오 회원 아이디:"+kakaoUser.getUsername());
		System.out.println("카카오 패스워드:"+kakaoUser.getPassword());
		int result = 0;
		// 가입자 혹은 비가입자 체크 해서 처리
		User originUser = userService.findUser(kakaoUser.getUsername());

		if (originUser.getUsername() == null) {
			System.out.println("회원가입 시작 입니다");
			 userService.joinUser(kakaoUser);
		}
		System.out.println("자동 로그인 진행");
//		System.out.println(result);
//
//		System.out.println("카카오 패스워드:"+kakaoUser.getPassword());
//
//		String encPassword = Encoder.encode(kakaoUser.getPassword());
//		kakaoUser.setPassword(encPassword);
//		System.out.println("암호화된 패스워드 :" +encPassword);

		// 로그인 처리
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);


		return "redirect:/";
	}






}
