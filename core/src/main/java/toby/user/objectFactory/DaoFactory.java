package toby.user.objectFactory;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import toby.user.dao.UserDao;

@Configuration
public class DaoFactory {

  @Autowired
  DataSource dataSource;

  @Bean
  public UserDao userDao() {
    UserDao userDao = new UserDao();
    userDao.setDataSource(dataSource);
    return userDao;
  }

}
