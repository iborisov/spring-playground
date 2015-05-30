package ru.demis.springplalyground.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.demis.springplalyground.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class UsersDao {
    private static final Logger LOG = LoggerFactory.getLogger(UsersDao.class);

    @Autowired
    JdbcTemplate jdbcTpl;

    private static final UserMapper USER_MAPPER = new UserMapper();

    public List<User> getAll() {
        return jdbcTpl.query("SELECT username, password FROM users;", USER_MAPPER);
    }

    public void insert(User user) {
        LOG.info("Inserting user {}", user);
        jdbcTpl.update("INSERT INTO users(username, password) VALUES (?, ?)",
                user.getUsername(), user.getPassword());
    }

    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            return new User(rs.getString("username"), rs.getString("password"));
        }
    }
}
