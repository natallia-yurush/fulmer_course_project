package by.ny.server;

import by.ny.server.controller.command.StatisticCommand;
import by.ny.server.controller.command.company.DeleteCompanyCommand;
import by.ny.server.controller.command.company.ListCompaniesCommand;
import by.ny.server.controller.command.company.SaveCompanyCommand;
import by.ny.server.controller.command.dollarrate.ListDollarRatesCommand;
import by.ny.server.controller.command.dollarrate.SaveDollarRateCommand;
import by.ny.server.controller.command.report.ListReportsCommand;
import by.ny.server.controller.command.report.SavePdfReportCommand;
import by.ny.server.controller.command.report.SaveReportCommand;
import by.ny.server.controller.command.report.SaveTxtReportCommand;
import by.ny.server.controller.command.user.*;
import by.ny.server.controller.result.StatisticResult;
import by.ny.server.controller.result.company.ListCompaniesResult;
import by.ny.server.controller.result.company.UpdateCompanyStatusResult;
import by.ny.server.controller.result.dollarrate.ListDollarRateResult;
import by.ny.server.controller.result.dollarrate.SaveDollarRateResult;
import by.ny.server.controller.result.report.ListReportsResult;
import by.ny.server.controller.result.report.SaveReportResult;
import by.ny.server.controller.result.report.SaveTxtPdfReportResult;
import by.ny.server.controller.result.user.AuthorizationResult;
import by.ny.server.controller.result.user.ListUsersResult;
import by.ny.server.controller.result.user.RegistrationResult;
import by.ny.server.controller.result.user.UpdateUserStatusResult;
import by.ny.server.entity.DollarRate;
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
        final UserService userService = UserService.getInstance();
        final RegistrationService registrationService = RegistrationService.getInstance();
        final AuthorizationService authorizationService = AuthorizationService.getInstance();
        final DollarRateService dollarRateService = DollarRateService.getInstance();
        final CompanyService companyService = CompanyService.getInstance();
        final ReportService reportService = ReportService.getInstance();
        final StatisticService statisticService = StatisticService.getInstance();

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
                        } else if (command instanceof SaveDollarRateCommand) {
                            SaveDollarRateCommand saveDollarRateCommand = (SaveDollarRateCommand) command;
                            DollarRate dollarRate = saveDollarRateCommand.getDollarRate();
                            SaveDollarRateResult result = new SaveDollarRateResult(dollarRateService.saveDollarRate(dollarRate));
                            outputStream.writeObject(result);
                        } else if (command instanceof SaveUserCommand) {
                            SaveUserCommand saveUserCommand = (SaveUserCommand) command;
                            outputStream.writeObject(registrationService.registration(saveUserCommand.getUser().getLogin(), saveUserCommand.getUser().getEmail()));
                            boolean success = userService.saveUser(saveUserCommand.getUser());
                            UpdateUserStatusResult result = new UpdateUserStatusResult(success);
                            outputStream.writeObject(result);
                        } else if (command instanceof SaveCompanyCommand) {
                            SaveCompanyCommand saveCompanyCommand = (SaveCompanyCommand) command;
                            UpdateCompanyStatusResult updateCompanyStatusResult = new UpdateCompanyStatusResult(companyService.
                                    saveCompany(saveCompanyCommand.getCompany()));
                            outputStream.writeObject(updateCompanyStatusResult);
                        } else if (command instanceof DeleteCompanyCommand) {
                            DeleteCompanyCommand deleteCompanyCommand = (DeleteCompanyCommand) command;
                            boolean success = companyService.deleteCompany(deleteCompanyCommand.getCompanyId());
                            UpdateCompanyStatusResult result = new UpdateCompanyStatusResult(success);
                            outputStream.writeObject(result);
                        } else if (command instanceof SaveReportCommand) {
                            SaveReportCommand saveReportCommand = (SaveReportCommand) command;
                            SaveReportResult saveReportResult = new SaveReportResult(reportService.saveReport(saveReportCommand.getReport()));
                            outputStream.writeObject(saveReportResult);
                        }
                        /*
                        else if (command instanceof SaveTxtReportCommand) {
                            SaveTxtReportCommand saveTxtReportCommand = (SaveTxtReportCommand) command;
                            SaveTxtPdfReportResult result = new SaveTxtPdfReportResult(reportService.saveTxtReport(saveTxtReportCommand.getReport()));
                            outputStream.writeObject(result);
                        } else if (command instanceof SavePdfReportCommand) {
                            SavePdfReportCommand saveTxtReportCommand = (SavePdfReportCommand) command;
                            SaveTxtPdfReportResult result = new SaveTxtPdfReportResult(reportService.savePdfReport(saveTxtReportCommand.getReport()));
                            outputStream.writeObject(result);
                        }
                        */
                        else if (command instanceof StatisticCommand) {
                            StatisticResult statisticResult = new StatisticResult(statisticService.numberOfUsersResult(), statisticService.numberOfCompaniesResult(), statisticService.numberOfReportsResult());
                            outputStream.writeObject(statisticResult);
                        } else if (command instanceof ListReportsCommand) {
                            ListReportsCommand listReportsCommand = (ListReportsCommand) command;
                            ListReportsResult listReportsResult = new ListReportsResult(reportService.listCompaniesReports(listReportsCommand.getCompanyId()));
                            outputStream.writeObject(listReportsResult);
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
