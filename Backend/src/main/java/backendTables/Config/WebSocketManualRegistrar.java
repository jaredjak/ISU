package backendTables.Config;

import backendTables.Game.GameSocket;
import backendTables.Game.SpectatorSocket;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketManualRegistrar {

    @Autowired
    private ServletContext servletContext;

    @PostConstruct
    public void registerEndpoints() {
        try {
            ServerContainer container = (ServerContainer) servletContext.getAttribute("javax.websocket.server.ServerContainer");

            if (container == null) {
                System.err.println("❌ ServerContainer is null — WebSockets will not be registered.");
                return;
            }

            container.addEndpoint(ServerEndpointConfig.Builder
                    .create(GameSocket.class, "/gameSocket/{lobbyId}/{username}")
                    .configurator(new backendTables.Game.CustomSpringConfigurator())
                    .build());

            container.addEndpoint(ServerEndpointConfig.Builder
                    .create(SpectatorSocket.class, "/spectatorSocket/{lobbyId}/{username}")
                    .configurator(new backendTables.Game.CustomSpringConfigurator())
                    .build());

            System.out.println("✅ WebSocket endpoints registered manually via ServerContainer.");

        } catch (Exception e) {
            System.err.println("❌ Error registering WebSocket endpoints:");
            e.printStackTrace();
        }
    }
}
