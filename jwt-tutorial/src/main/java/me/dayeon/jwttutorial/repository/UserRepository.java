package me.dayeon.jwttutorial.repository;

import me.dayeon.jwttutorial.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Member;
import java.util.Optional;

/**
 * JPARepository
 * findAll, save와 같은 메서드 사용가능
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * @EntityGraph
     * 쿼리 수행될 때 Lazy 조회(지연로딩)이 아니고 Eager 조회로
     * 조인된 authorities정보를 같이 가져옴
     *
     * findOneWithAuthoritiesByUsername
     * username을 기준으로 user정보를 가져올 때 권한 정보도 같이 가져옴
     */
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);


}