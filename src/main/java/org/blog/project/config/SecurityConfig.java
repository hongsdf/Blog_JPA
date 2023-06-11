package org.blog.project.config;


import org.blog.project.config.auth.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것

@Configuration // 빈등록 (IoC관리)
@EnableWebSecurity // 시큐리티 필터가 등록이 된다.
//Controller에서 특정 권한이 있는 유저만 접근을 허용하려면 @PreAuthorize 어노테이션을 사용하는데, 해당 어노테이션을 활성화 시키는 어노테이션이다.
@EnableGlobalMethodSecurity(prePostEnabled = true) 
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalService principalService;

	@Bean // 어디서든 빈 객체를 사용이 가능해짐
	@Override
	public AuthenticationManager authenticationManager() throws Exception{
		return super.authenticationManager();
	}

	@Bean // bean등록 IOC관리영역
	public BCryptPasswordEncoder encodePWD() {

		return new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 대신 로그인 하면 password를 가로채간다.
    //	해당 password가 어떤 방식으로 해쉬 되었는지 알아야한다.
    //	해쉬방법을 알아야 db정보와 비교가 가능하다.


	/**
	 * 스프링 시큐리티가 패스워드를 비교하는 로직
	 * @param auth
	 * @throws Exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalService).passwordEncoder(encodePWD()); // 스프링 시큐리티가 패스워드를 비교한다.
	}

	/**
	 * "/","/auth/**","/js/**","/css/**","/image/** 는 인증 없이 접근이 가능
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()  // csrf 토큰 비활성화 (테스트시 걸어두는 게 좋음)  요청시 CSRF가 없을 경우 요청이 안됨
				.authorizeRequests()// 페이지별 권한을 부여하기 위해 사용되는 메서드
				.antMatchers("/","/auth/**","/js/**","/css/**","/image/**","/dummy/**").permitAll() // 특정 url접근 시 인가가 필요한 url 설정
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.loginPage("/auth/loginForm") // 커스텀 로그인 페이지
		        .loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소를 가로챔 => 로그인을 실행해줌
				.defaultSuccessUrl("/");




	}
}
