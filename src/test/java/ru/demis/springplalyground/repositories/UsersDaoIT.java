package ru.demis.springplalyground.repositories;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ru.demis.springplalyground.Application;
import ru.demis.springplalyground.domain.User;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class
})
public class UsersDaoIT {
    private static final Logger LOG = LoggerFactory.getLogger(UsersDaoIT.class);

    @Autowired
    private UsersDao usersDao;

    @Test
    @DatabaseSetup(value = "/sampleData.xml")
    @ExpectedDatabase(value = "/expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void test() {
        LOG.info("Users before: {}", usersDao.getAll());
        usersDao.insert(new User("user2", "p2"));
        LOG.info("Users after: {}", usersDao.getAll());
    }
}
