package by.ny.server.controller.command.dollarrate;

import by.ny.server.controller.command.Command;
import by.ny.server.entity.DollarRate;
import by.ny.server.service.DollarRateService;
import by.ny.server.util.Request;
import by.ny.server.util.Response;

public class SaveDollarRateCommand implements Command {
    private final DollarRateService dollarRateService = DollarRateService.getInstance();

    @Override
    public Response execute(Request request) {
        return new Response(dollarRateService.saveDollarRate((DollarRate) request.getData()));
    }
}
