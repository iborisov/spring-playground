package ru.demis.springplalyground;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConf {
    public final int test;

    @Autowired
    public AppConf(@Value("${test}") int test) {
        this.test = test;
    }
}
