package org.blog.project.config.auth;

import lombok.Data;
import lombok.Getter;
import org.blog.project.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails타입의 오브젝트를  스프링 시큐리티의 고유한 세션저장소에 저장해줌
public class PrincipalDetail implements UserDetails {

    private User user;//콤포지션

    public PrincipalDetail(User user) {

        this.user = user;
    }

    /**
     *
     * @return
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정 만료 확인 false := 계정만료, true := 계정 유효
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
   // 계정 잠금 확인 false := 잠금 x , true := 잠기지 않음
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 비밀번호 만료 확인 false := 비밀번호 만료, true := 계정 유효
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 계정 활성화 확인  false := 비활성화, true := 활성화
    @Override
    public boolean isEnabled() {
        return true;
    }
   // 계정이 갖고 있는 권한 목록을 리턴 ,권한이 여러개면 for문으로 권한을 열어야함
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority>  collectors = new ArrayList<>();

        // 람다식
        collectors.add(()->{return "ROLE_"+user.getRole();});

        return collectors;
    }




}

/*

   [1] 람다식으로 대체
    collectors.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_"+user.getRole(); // 앞에 꼭 ROLE_ 붙여야한다. => ROLE_USER
            }
        });


 */