package toby3.user.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import toby3.user.domain.User;


public class UserDaoTest {

  UserDao dao;
  User user1;
  User user2;
  User user3;


  @BeforeEach
  public void setUp() {
    dao = new UserDao();
    DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost:3306/springbook",
        "root", "welcome1", true);
    dao.setDataSource(dataSource);
    user1 = new User("guu", "명다연1", "1234");
    user2 = new User("nuu", "명다연2", "5678");
    user3 = new User("buu", "명다연3", "0000");
  }

  @Test
  @DisplayName("회원 등록과 조회")
  public void addAndGet() throws SQLException {
    dao.deleteAll();
    assertThat(dao.getCount()).isEqualTo(0);

    dao.add(user1);
    assertThat(dao.getCount()).isEqualTo(1);

    User getUser = dao.get(user1.getId());
    assertThat(user1).isEqualTo(getUser);
  }

  @Test
  @DisplayName("회원 테이블 레코드 수 확인")
  public void count() throws SQLException {
    dao.deleteAll();
    assertThat(dao.getCount()).isEqualTo(0);

    dao.add(user1);
    assertThat(dao.getCount()).isEqualTo(1);

    dao.add(user2);
    assertThat(dao.getCount()).isEqualTo(2);

    dao.add(user3);
    assertThat(dao.getCount()).isEqualTo(3);
  }

  @Test
  @DisplayName("유저 조회 실패")
  public void getUserFailure() throws SQLException {
    dao.deleteAll();
    assertThrows(EmptyResultDataAccessException.class, () ->
        dao.get("unkown_id"));
  }

}
