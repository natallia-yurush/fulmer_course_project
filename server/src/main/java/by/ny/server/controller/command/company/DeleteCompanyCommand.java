package by.ny.server.controller.command.company;

import by.ny.server.controller.command.Command;
import by.ny.server.service.CompanyService;
import by.ny.server.util.Request;
import by.ny.server.util.Response;

public class DeleteCompanyCommand implements Command {
    private final CompanyService companyService = CompanyService.getInstance();

    @Override
    public Response execute(Request request) {
        return new Response(companyService.deleteCompany((Integer) request.getData()));
    }
}
