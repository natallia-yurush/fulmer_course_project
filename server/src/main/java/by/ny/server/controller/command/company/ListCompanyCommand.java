package by.ny.server.controller.command.company;

import by.ny.server.controller.command.Command;
import by.ny.server.service.CompanyService;
import by.ny.server.util.Request;
import by.ny.server.util.Response;

public class ListCompanyCommand implements Command {
    private final CompanyService companyService = CompanyService.getInstance();

    @Override
    public Response execute(Request request) {
        Integer userId = (Integer) request.getData();
        return new Response(companyService.listUsersCompanies(userId));
    }
}
