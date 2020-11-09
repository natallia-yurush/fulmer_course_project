package by.ny.server;

import by.ny.server.controller.command.company.ListCompaniesCommand;
import by.ny.server.controller.command.dollarrate.ListDollarRatesCommand;
import by.ny.server.controller.command.user.AuthorizationCommand;
import by.ny.server.controller.command.user.DeleteUserCommand;
import by.ny.server.controller.command.user.ListUsersCommand;
import by.ny.server.controller.command.user.RegistrationCommand;
import by.ny.server.controller.result.company.ListCompaniesResult;
import by.ny.server.controller.result.dollarrate.ListDollarRateResult;
import by.ny.server.controller.result.user.AuthorizationResult;
import by.ny.server.controller.result.user.ListUsersResult;
import by.ny.server.controller.result.user.RegistrationResult;
import by.ny.server.controller.result.user.UpdateUserStatusResult;
import by.ny.server.dao.CompanyDao;
import by.ny.server.dao.DollarRateDao;
import by.ny.server.dao.UserDao;
import by.ny.server.entity.RegistrationStatus;
import by.ny.server.entity.User;
import by.ny.server.service.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        final UserDao userDao = new UserDao(); //TODO singleton
        final DollarRateDao dollarRateDao = new DollarRateDao();
        final CompanyDao companyDao = new CompanyDao();
        final UserService userService = new UserService(userDao);
        final RegistrationService registrationService = new RegistrationService(userDao);
        final AuthorizationService authorizationService = new AuthorizationService(userDao);
        final DollarRateService dollarRateService = new DollarRateService(dollarRateDao);
        final CompanyService companyService = new CompanyService(companyDao);

        try (ServerSocket ss = new ServerSocket(3333)) {
            while (true) {

                new Thread(() -> {
                    try (Socket s = ss.accept()) {
                        ObjectInputStream inputStream = new ObjectInputStream(s.getInputStream());
                        ObjectOutputStream outputStream = new ObjectOutputStream(s.getOutputStream());

                        Object command = inputStream.readObject();

                        if (command instanceof RegistrationCommand) {
                            RegistrationCommand registrationCommand = (RegistrationCommand) command;
                            RegistrationStatus status = registrationService.registration(registrationCommand.getLogin(), registrationCommand.getEmail());
                            if (status == RegistrationStatus.REGISTERED) {
                                User user = new User(null, registrationCommand.getSurname(), registrationCommand.getName(),
                                        registrationCommand.getLogin(), registrationCommand.getPassword(), registrationCommand.getPhoneNumber(),
                                        registrationCommand.getRole(), registrationCommand.getEmail());
                                userService.saveUser(user);
                                RegistrationResult result = new RegistrationResult(status, user);
                                outputStream.writeObject(result);
                                outputStream.writeObject(result);
                            } else {
                                RegistrationResult result = new RegistrationResult(status, null);
                                outputStream.writeObject(result);
                            }
                        } else if (command instanceof AuthorizationCommand) {
                            AuthorizationCommand authorizationCommand = (AuthorizationCommand) command;
                            User user = authorizationService.authenticate(authorizationCommand.getLogin(), authorizationCommand.getPassword());
                            AuthorizationResult result = new AuthorizationResult(user != null, user);
                            outputStream.writeObject(result);
                        } else if (command instanceof ListUsersCommand) {
                            ListUsersResult listUsersResult = new ListUsersResult(userService.listUsers());
                            outputStream.writeObject(listUsersResult);
                        } else if (command instanceof ListDollarRatesCommand) {
                            ListDollarRateResult result = new ListDollarRateResult(dollarRateService.listRates());
                            outputStream.writeObject(result);
                        } else if (command instanceof DeleteUserCommand) {
                            DeleteUserCommand deleteUserCommand = (DeleteUserCommand) command;
                            boolean success = userService.deleteUser(deleteUserCommand.getUserId());
                            UpdateUserStatusResult result = new UpdateUserStatusResult(success);
                            outputStream.writeObject(result);
                        } else if (command instanceof ListCompaniesCommand) {
                            ListCompaniesResult result = new ListCompaniesResult(companyService
                                    .listUsersCompanies(((ListCompaniesCommand) command).getUserId()));
                            outputStream.writeObject(result);
                        }


                    } catch (Exception e) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
                    }
                }).run();
            }
        } catch (Exception e) {

        }
    }
}
