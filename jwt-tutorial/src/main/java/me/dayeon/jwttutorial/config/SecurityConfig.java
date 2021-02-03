package me.dayeon.jwttutorial.config;

import me.dayeon.jwttutorial.jwt.JwtSecurityConfig;
import me.dayeon.jwttutorial.jwt.JwtAccessDeniedHandler;
import me.dayeon.jwttutorial.jwt.JwtAuthenticationEntryPoint;
import me.dayeon.jwttutorial.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * EnableGlobalMethodSecurity
 * @PreAuthorize을 메소드 단위로 추가하기 위해 적용
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
            CorsFilter corsFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    /**
     * h2-console과 favicon.ico는 무효
     *
     */
    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers(
                        "/h2-console/**"
                        ,"/favicon.ico"
                        ,"/error"
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * csrf.disable
     * 토큰 사용하기 위해 csft를 disable
     *
     * exceptionHandling
     * exception 핸들링할 때
     * jwtAuthenticationEntryPoint클래스와 jwtAccessDeniedHandler 추가
     *
     * and ~ sameOrigin
     * h2-console위한 설정 추가
     *
     * and ~ session
     * 세션사용하지 않기 때문에 session을 stateless로 설정
     *
     * antMatchers.permitAll
     * 토큰을 받기 위한 로그인 , 회원가입 api는 토큰이 없는 상태에서 요청이 들어오기
     * 때문에 permitall 설정
     *
     * authorizeRequest
     * HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다
     *
     * antMatchers.permitAll
     * 인증없이 접근 허용하겠다
     *
     * anyRequest.authenticated
     * 그리고 나머지 요청들은 모두 인증받아야한다
     *
     * apply
     * jwtFilter를 addFilterBefore로 등록했 jwtSecurityConfig 적용
     *
     */

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .csrf().disable()

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // enable h2-console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/api/hello").permitAll()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/signup").permitAll()

                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
    }


}
