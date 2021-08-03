package toby3.user.objectFactory;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import toby3.user.dao.JdbcContext;
import toby3.user.dao.UserDao;

@Configuration
public class DaoFactory {

  @Bean
  public DataSource dataSource() {
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

    dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
    dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/springbook");
    dataSource.setUsername("root");
    dataSource.setPassword("welcome1");
    return dataSource;
  }

  @Bean
  public UserDao userDao() {
    UserDao userDao = new UserDao();
    userDao.setDataSource(dataSource());
    return userDao;
  }

}
