package ru.demis.springplalyground.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.demis.springplalyground.domain.User;

import java.util.List;

@Component
@Transactional
public class UsersDao {
    @Autowired
    JdbcTemplate jdbcTpl;

    private static final User.Mapper USER_MAPPER = new User.Mapper();

    public List<User> getAll() {
        return jdbcTpl.query("SELECT username, password FROM test;", USER_MAPPER);
    }

    public void insert(User user) {
        jdbcTpl.update("INSERT INTO test(username, password) VALUES (?, ?)",
                user.getUsername(), user.getPassword());
    }
}
