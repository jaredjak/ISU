package com.example.androidexample;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

//import com.example.androidexample.GameplayWebSocketListener;

import java.net.URI;

/**
 * Singleton GameplayWebSocketManager instance used for managing WebSocket connections
 * in the Android application.
 *
 * This instance ensures that there is only one GameplayWebSocketManager throughout
 * the application's lifecycle, allowing for centralized WebSocket handling.
 */
public class GameplayWebSocketManager {

    private static GameplayWebSocketManager instance;
    //was private
    private MyWebSocketClient webSocketClient;
    //was private
    private GameplayWebSocketListener gameplayWebSocketListener;
    //was private

    private GameplayWebSocketManager() {}
    //was private

    /**
     * Retrieves a synchronized instance of the GameplayWebSocketManager, ensuring that
     * only one instance of the GameplayWebSocketManager exists throughout the application.
     * Synchronization ensures thread safety when accessing or creating the instance.
     *
     * @return A synchronized instance of GameplayWebSocketManager.
     */
    public static synchronized GameplayWebSocketManager getInstance() {
        if (instance == null) {
            instance = new GameplayWebSocketManager();
        }
        return instance;
    }

    /**
     * Sets the GameplayWebSocketListener for this GameplayWebSocketManager instance. The GameplayWebSocketListener
     * is responsible for handling WebSocket events, such as received messages and errors.
     *
     * @param listener The GameplayWebSocketListener to be set for this GameplayWebSocketManager.
     */
    public void setWebSocketListener(GameplayWebSocketListener listener) {
        //this.gameplayWebSocketListener = listener;
        gameplayWebSocketListener = listener;
    }

    /**
     * Removes the currently set GameplayWebSocketListener from this GameplayWebSocketManager instance.
     * This action effectively disconnects the listener from handling WebSocket events.
     */
    public void removeWebSocketListener() {
        //this.gameplayWebSocketListener = null;
        gameplayWebSocketListener = null;
    }

    /**
     * Initiates a WebSocket connection to the specified server URL. This method
     * establishes a connection with the WebSocket server located at the given URL.
     *
     * @param serverUrl The URL of the WebSocket server to connect to.
     */
    public void connectWebSocket(String serverUrl) {
        try {
            URI serverUri = URI.create(serverUrl);
            webSocketClient = new MyWebSocketClient(serverUri);
            webSocketClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a WebSocket message to the connected WebSocket server. This method allows
     * the application to send a message to the server through the established WebSocket
     * connection.
     *
     * @param message The message to be sent to the WebSocket server.
     */
    public void sendMessage(String message) {
        if (webSocketClient != null && webSocketClient.isOpen()) {
            webSocketClient.send(message);
        }
    }

    /**
     * Disconnects the WebSocket connection, terminating the communication with the
     * WebSocket server.
     */
    public void disconnectWebSocket() {
        if (webSocketClient != null) {
            webSocketClient.close();
        }
    }


    /**
     * A private inner class that extends WebSocketClient and represents a WebSocket
     * client instance tailored for specific functionalities within the GameplayWebSocketManager.
     * This class encapsulates the WebSocketClient and provides custom behavior or
     * handling for WebSocket communication as needed by the application.
     */

    //clas was private
    private class MyWebSocketClient extends WebSocketClient {

        private MyWebSocketClient(URI serverUri) {
            super(serverUri);
        }
        //was private

        /**
         * Called when the WebSocket connection is successfully opened and a handshake
         * with the server has been completed. This method is invoked to handle the event
         * when the WebSocket connection becomes ready for sending and receiving messages.
         *
         * @param handshakedata The ServerHandshake object containing details about the
         *                      handshake with the server.
         */
        @Override
        public void onOpen(ServerHandshake handshakedata) {
            Log.d("WebSocket", "Connected");
            if (gameplayWebSocketListener != null) {
                gameplayWebSocketListener.onWebSocketOpen(handshakedata);
            }
        }

        /**
         * Called when a WebSocket message is received from the server. This method is
         * invoked to handle incoming WebSocket messages and allows the application to
         * process and respond to messages as needed.
         *
         * @param message The WebSocket message received from the server as a string.
         */
        @Override
        public void onMessage(String message) {
            Log.d("WebSocket", "Received message: " + message);
            if (gameplayWebSocketListener != null) {
                gameplayWebSocketListener.onWebSocketMessage(message);
            }
        }

        /**
         * Called when the WebSocket connection is closed, either due to a client request
         * or a server-initiated close. This method is invoked to handle the WebSocket
         * connection closure event and provides details about the closure, such as the
         * closing code, reason, and whether it was initiated remotely.
         *
         * @param code   The WebSocket closing code indicating the reason for closure.
         * @param reason A human-readable explanation for the WebSocket connection closure.
         * @param remote A boolean flag indicating whether the closure was initiated remotely.
         *               'true' if initiated remotely, 'false' if initiated by the client.
         */
        @Override
        public void onClose(int code, String reason, boolean remote) {
            Log.d("WebSocket", "Closed");
            if (gameplayWebSocketListener != null) {
                gameplayWebSocketListener.onWebSocketClose(code, reason, remote);
            }
        }

        /**
         * Called when an error occurs during WebSocket communication. This method is
         * invoked to handle WebSocket-related errors and allows the application to
         * respond to and log error details.
         *
         * @param ex The Exception representing the WebSocket communication error.
         */
        @Override
        public void onError(Exception ex) {
            Log.d("WebSocket", "Error");
            if (gameplayWebSocketListener != null) {
                gameplayWebSocketListener.onWebSocketError(ex);
            }
        }
    }
}
