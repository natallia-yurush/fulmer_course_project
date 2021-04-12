package by.ny.server.controller.command.dollarrate;

import by.ny.server.controller.command.Command;
import by.ny.server.service.DollarRateService;
import by.ny.server.util.Request;
import by.ny.server.util.Response;

public class ListDollarRateCommand implements Command {
    private final DollarRateService dollarRateService = DollarRateService.getInstance();

    @Override
    public Response execute(Request request) {
        return new Response(dollarRateService.listRates());
    }
}
