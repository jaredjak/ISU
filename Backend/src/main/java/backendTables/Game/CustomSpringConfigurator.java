package backendTables.Game;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpointConfig;

@Component
public class CustomSpringConfigurator extends ServerEndpointConfig.Configurator implements ApplicationContextAware {

    private static volatile ApplicationContext context;

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        if (context == null) {
            throw new InstantiationException("ApplicationContext is not initialized yet!");
        }

        // Create the WebSocket endpoint and inject Spring beans into it
        T endpoint = null;
        try {
            endpoint = clazz.getDeclaredConstructor().newInstance();
            context.getAutowireCapableBeanFactory().autowireBean(endpoint);
        } catch (Exception e) {
            throw new InstantiationException("Failed to instantiate endpoint: " + e.getMessage());
        }
        return endpoint;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CustomSpringConfigurator.context = applicationContext;
    }
}
