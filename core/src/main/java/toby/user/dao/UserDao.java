package toby.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import toby.user.domain.User;

public class UserDao {

  private JdbcTemplate jdbcTemplate;

  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public void add(User user) throws SQLException {
    this.jdbcTemplate.update("INSERT INTO users VALUES(?,?,?)", user.getId(), user.getName(),
        user.getPassword());
  }

  public User get(String id) throws SQLException {
    return this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?",
        new Object[]{id},
        new RowMapper<User>() {
          @Override
          public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getString("id"),
                rs.getString("name"),
                rs.getString("password"));
            return user;
          }
        }
    );
  }


  public void deleteAll() throws SQLException {
    this.jdbcTemplate.update("DELETE FROM users");
  }

  public int getCount() throws SQLException {
    return this.jdbcTemplate.query(con -> con.prepareStatement("SELECT COUNT(*) FROM users"),
        rs -> {
          rs.next();
          return rs.getInt(1);
        });
  }


  public List<User> getAll() {
  }
}
