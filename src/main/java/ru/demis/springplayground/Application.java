package ru.demis.springplayground;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.demis.springplayground.repositories.UsersDao;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    @Autowired
    AppConf appConf;

    @Autowired
    UsersDao usersDao;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.run(args);
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
        System.out.println("========================");
        System.out.println(appConf);
        System.out.println("========================");
    }
}
