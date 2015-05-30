package ru.demis.springplalyground;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.demis.springplalyground.domain.User;
import ru.demis.springplalyground.repositories.UsersDao;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    AppConf appConf;

    @Autowired
    UsersDao usersDao;

    @Value("#{'${test2}'.split(',')}")
    public final Set<String> test2 = new HashSet<>();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Controller
    public static class IndexController {
        @RequestMapping("/")
        public ModelAndView index() {
            return new ModelAndView("index");
        }

        @RequestMapping("/login")
        public ModelAndView login() {
            return new ModelAndView("login");
        }
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(appConf.test);
        System.out.println("test2:");
        for (String s : test2) {
            System.out.println(s);
        }

        for (User user : usersDao.getAll()) {
            System.out.println(user);
        }
    }
}
