package org.blog.project.config.auth;

import org.blog.project.model.User;
import org.blog.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    /**
     *  스프링이 로그인 요청을 가로 채서 , username,password 를
     *  password부분은 알아서 해줌
     *  username이 db안에 있는 지만 확인 하면 됨(이부분은 프로그래머가 수행)
     * @param
     * @return
     * @throws
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username).orElseThrow(()->{
            return new IllegalArgumentException("해당 유저를 찾을 수 없습니다."+username);
        });

        // 이때 시큐리티 세션 공간에 유저 정보가 저장
        return new PrincipalDetail(principal);

    }
}
