package toby3.user.dao;

import java.sql.SQLException;
import javax.sql.DataSource;
import toby3.user.domain.User;

public class UserDao {

  private DataSource dataSource;

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void add(User user) throws SQLException {

  }

  public User get(String id) {
    return null;
  }


  public void deleteAll() {
  }

  public int getCount() {
    return 0;
  }
}
