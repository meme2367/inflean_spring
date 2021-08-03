package toby3.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import toby3.user.domain.User;
import toby3.user.statementStrategy.StatementStrategy;

public class UserDao {

  private DataSource dataSource;

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void add(User user) throws SQLException {

    jdbcContextWithStatementStrategy(c -> {
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

    jdbcContextWithStatementStrategy(c -> c.prepareStatement("DELETE FROM users"));

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

  private void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
    Connection c = null;
    PreparedStatement ps = null;

    try {
      c = dataSource.getConnection();
      ps = stmt.makePreparedStatement(c);

      ps.executeUpdate();
    } catch (SQLException e) {
      throw e;
    } finally {
      if (ps != null) {
        try {
          ps.close();
        } catch (SQLException e) {
        }
      }
      if (c != null) {
        try {
          c.close();
        } catch (SQLException e) {
        }
      }
    }
  }
}
