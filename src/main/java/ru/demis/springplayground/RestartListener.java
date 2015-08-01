package ru.demis.springplayground;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SmartApplicationListener;

public class RestartListener implements SmartApplicationListener {
    private ConfigurableApplicationContext context;

    private ApplicationPreparedEvent event;

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return ApplicationPreparedEvent.class.isAssignableFrom(eventType)
                || ContextRefreshedEvent.class.isAssignableFrom(eventType)
                || ContextClosedEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return true;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent input) {
        if (input instanceof ApplicationPreparedEvent) {
            event = (ApplicationPreparedEvent) input;
            if (context == null) {
                context = event.getApplicationContext();
            }
        } else if (input instanceof ContextRefreshedEvent) {
            if (context != null && input.getSource().equals(context) && event != null) {
                context.publishEvent(event);
            }
        } else {
            if (context != null && input.getSource().equals(context)) {
                context = null;
            }
        }
    }
}
