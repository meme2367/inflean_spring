package toby3.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import toby3.user.domain.User;

public class UserDao {

  private JdbcContext jdbcContext;
  private DataSource dataSource;

  public void setDataSource(DataSource dataSource) {
    this.jdbcContext = new JdbcContext();
    this.jdbcContext.setDataSource(dataSource);
    this.dataSource = dataSource;
  }

  public void add(User user) throws SQLException {
    this.jdbcContext.workWithStatementStrategy(c -> {
      PreparedStatement ps = c.prepareStatement("INSERT INTO users VALUES(?,?,?)");
      ps.setString(1, user.getId());
      ps.setString(2, user.getName());
      ps.setString(3, user.getPassword());
      return ps;
    });
  }

  public User get(String id) throws SQLException {
    Connection c = dataSource.getConnection();
    PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE id = ?");
    ps.setString(1, id);

    ResultSet rs = ps.executeQuery();
    User user = null;
    if (rs.next()) {
      user = new User(rs.getString("id"),
          rs.getString("name"),
          rs.getString("password"));
    }

    rs.close();
    ps.close();
    c.close();

    if (user == null) {
      throw new EmptyResultDataAccessException(1);
    }

    return user;

  }


  public void deleteAll() throws SQLException {
    executeSql("DELETE FROM users");
  }

  public int getCount() throws SQLException {

    Connection c = dataSource.getConnection();
    PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM users");

    ResultSet rs = ps.executeQuery();
    rs.next();

    int cnt = rs.getInt(1);

    ps.close();
    c.close();

    return cnt;

  }

  private void executeSql(final String query) throws SQLException {
    this.jdbcContext.workWithStatementStrategy(c -> c.prepareStatement(query));
  }

}
