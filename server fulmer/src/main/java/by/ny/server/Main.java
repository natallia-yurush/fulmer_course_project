package by.ny.server;

import by.ny.server.controller.command.user.AuthorizationCommand;
import by.ny.server.controller.command.user.RegistrationCommand;
import by.ny.server.controller.result.user.AuthorizationResult;
import by.ny.server.controller.result.user.RegistrationResult;
import by.ny.server.dao.UserDao;
import by.ny.server.entity.RegistrationStatus;
import by.ny.server.entity.User;
import by.ny.server.service.AuthorizationService;
import by.ny.server.service.RegistrationService;
import by.ny.server.service.UserService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        final UserDao userDao = new UserDao();
        final UserService userService = new UserService(userDao);
        final RegistrationService registrationService = new RegistrationService(userDao);
        final AuthorizationService authorizationService = new AuthorizationService(userDao);

        try (ServerSocket ss = new ServerSocket(3333)) {
            while (true) {

                new Thread(() -> {
                    try (Socket s = ss.accept()) {
                        ObjectInputStream inputStream = new ObjectInputStream(s.getInputStream());
                        ObjectOutputStream outputStream = new ObjectOutputStream(s.getOutputStream());

                        Object command = inputStream.readObject();

                        if(command instanceof RegistrationCommand) {
                            RegistrationCommand registrationCommand = (RegistrationCommand) command;
                            RegistrationStatus status = registrationService.registration(registrationCommand.getLogin(), registrationCommand.getEmail());
                            if(status == RegistrationStatus.REGISTERED) {
                                User user = new User(null, registrationCommand.getSurname(), registrationCommand.getName(),
                                        registrationCommand.getLogin(), registrationCommand.getPassword(), registrationCommand.getPhoneNumber(),
                                        registrationCommand.getRole(), registrationCommand.getEmail());
                                userService.saveUser(user);
                                    RegistrationResult result = new RegistrationResult(status, user);
                                    outputStream.writeObject(result);  outputStream.writeObject(result);
                            } else {
                                RegistrationResult result = new RegistrationResult(status, null);
                                outputStream.writeObject(result);
                            }
                        } else if (command instanceof AuthorizationCommand) {
                            AuthorizationCommand authorizationCommand = (AuthorizationCommand) command;
                            User user = authorizationService.authenticate(authorizationCommand.getLogin(), authorizationCommand.getPassword());
                            AuthorizationResult result = new AuthorizationResult(user != null, user);
                            outputStream.writeObject(result);
                        }





                    } catch(Exception e) {
                        System.out.println("asgrtdhyjugi");
                    }
                }).run();
            }
        } catch (Exception e) {

        }
    }
}
