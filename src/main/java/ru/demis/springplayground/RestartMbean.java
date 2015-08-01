package ru.demis.springplayground;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

@Component
@ManagedResource
public class RestartMbean implements ApplicationListener<ApplicationPreparedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(RestartMbean.class);

    private ConfigurableApplicationContext context;
    private String[] args;
    private SpringApplication application;

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        LOG.info("!!!: {}", event);
        if (this.context == null) {
            LOG.info("Setting context and others");
            this.context = event.getApplicationContext();
            this.args = event.getArgs();
            this.application = event.getSpringApplication();
        }
    }

    @ManagedOperation
    public synchronized String restart() {
        LOG.info("Begin restart");
        if (this.context != null) {
            LOG.info("Context != null");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    application.setEnvironment(context.getEnvironment());
                    context.close();
                    // If running in a webapp then the context classloader is probably going to
                    // die so we need to revert to a safe place before starting again
                    LOG.info("Overriding classloader");
                    overrideClassLoaderForRestart();
                    context = application.run(args);
                }
            });
            thread.setDaemon(false);
            thread.start();
        }
        LOG.info("Finish restart");
        return this.context.toString();
    }

    private void overrideClassLoaderForRestart() {
        ClassUtils.overrideThreadContextClassLoader(this.application.getClass().getClassLoader());
    }
}
