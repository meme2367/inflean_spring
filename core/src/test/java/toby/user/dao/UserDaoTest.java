package toby.user.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import toby.user.domain.User;


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
    user1 = new User("auu", "명다연1", "1234");
    user2 = new User("buu", "명다연2", "5678");
    user3 = new User("cuu", "명다연3", "0000");
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

  @Test
  @DisplayName("모든 사용자 조회")
  public void getAll() throws SQLException {
    dao.deleteAll();

    List<User> users0 = dao.getAll();
    assertThat(users0.size()).isEqualTo(0);

    dao.add(user1);
    List<User> users1 =  dao.getAll();
    assertThat(users1.size()).isEqualTo(1);
    assertThat(user1).isEqualTo(users1.get(0));

    dao.add(user2);
    List<User> users2 = dao.getAll();
    assertThat(users2.size()).isEqualTo(2);
    assertThat(user2).isEqualTo(users2.get(1));

    dao.add(user3);
    List<User> users3 = dao.getAll();
    assertThat(users3.size()).isEqualTo(3);
    assertThat(user3).isEqualTo(users3.get(2));//알파벳
  }
}
