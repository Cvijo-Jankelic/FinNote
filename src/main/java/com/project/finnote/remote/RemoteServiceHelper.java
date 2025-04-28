package com.project.finnote.remote;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import com.project.finnote.utils.JsonUtils;

public class RemoteServiceHelper {
    private static final String HOST = "localhost";
    private static final int PORT = 5555;

    /**
     * Sends a single command to the server and returns the JSON response.
     */
    public String sendCommand(String command) throws Exception {
        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            writer.println(command);
            String response = reader.readLine();  // server sends a single JSON line per command
            writer.println("QUIT"); // close connection politely
            return response;
        }
    }

    /**
     * Provides shared ObjectMapper with JavaTime support.
     */
    public com.fasterxml.jackson.databind.ObjectMapper mapper() {
        return JsonUtils.MAPPER;
    }

}