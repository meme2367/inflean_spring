package jpabook.jpashop.jwt;

import jpabook.jpashop.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    public static final String AUTHORIZATION_HEADER = "Authorization";


    public JwtTokenFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * doFilter
     * GenericFilterBean의 doFilter 메서드 오버라이딩
     * 실제 필터링 로직은 doFilter에서 진행
     * jwt 토큰의 인증정보를 현재 실행중인 security context에 저장하는 역할 수행
     *
     * 1. resolveToken : request에서  토큰을 받아옴
     * 2. validateToken : 유효성 검증 통과하면
     * 3. getAuthentication : 토큰에서 Authentication 객체를 받아와
     * 4. setAuthentication : securityContext에 set해줌
     */
    @Override
    public void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        String jwt = resolveToken(httpServletRequest);

        String requestURI = httpServletRequest.getRequestURI();
        String username = jwtUtil.getUsername(jwt);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails
                    = this.customUserDetailsService.loadUserByUsername(username);

            if (StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt,userDetails)) {
                Authentication authentication = jwtUtil.getAuthentication(jwt,userDetails);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * resolveToken
     * Request Header에서 토큰 정보를 꺼내옴
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
