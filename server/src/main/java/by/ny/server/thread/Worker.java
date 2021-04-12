package by.ny.server.thread;

import by.ny.server.controller.CommandController;
import by.ny.server.controller.command.Command;
import by.ny.server.util.Request;
import by.ny.server.util.Response;

import java.io.*;
import java.net.Socket;

public class Worker implements Runnable {
    protected Socket clientSocket = null;

    public Worker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            while (true) {
                try {
                    Request request = (Request) in.readObject();
                    Response response = null;

                    Command command = new CommandController().getCommand(request.getType());
                    if (command != null) {
                        response = command.execute(request);
                    }
                    out.writeObject(response);
                    out.flush();
                } catch (ClassNotFoundException e) {
                    System.out.println("Error deserialize " + e);
                }
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
}
