package by.ny.server.controller;

import by.ny.server.controller.command.Command;
import by.ny.server.controller.command.company.DeleteCompanyCommand;
import by.ny.server.controller.command.company.ListCompanyCommand;
import by.ny.server.controller.command.company.SaveCompanyCommand;
import by.ny.server.controller.command.dollarrate.ListDollarRateCommand;
import by.ny.server.controller.command.dollarrate.SaveDollarRateCommand;
import by.ny.server.controller.command.report.ListReportCommand;
import by.ny.server.controller.command.report.SaveReportCommand;
import by.ny.server.controller.command.statistic.StatisticCommand;
import by.ny.server.controller.command.user.*;
import by.ny.server.util.RequestType;

public class CommandController {
    public Command getCommand(RequestType type) {
        switch(type) {
            case AUTHORIZATION:
                return new AuthorizationCommand();
            case SAVE_USER:
                return new SaveUserCommand();
            case USERS_LIST:
                return new ListUserCommand();
            case DELETE_USER:
                return new DeleteUserCommand();
            case COMPANIES_LIST:
                return new ListCompanyCommand();
            case DOLLAR_RATES_LIST:
                return new ListDollarRateCommand();
            case SAVE_DOLLAR_RATE:
                return new SaveDollarRateCommand();
            case STATISTIC:
                return new StatisticCommand();
            case SAVE_COMPANY:
                return new SaveCompanyCommand();
            case DELETE_COMPANY:
                return new DeleteCompanyCommand();
            case REPORTS_LIST:
                return new ListReportCommand();
            case REGISTRATION:
                return new RegistrationCommand();
            case SAVE_REPORT:
                return new SaveReportCommand();
        }
        return null;
    }
}
