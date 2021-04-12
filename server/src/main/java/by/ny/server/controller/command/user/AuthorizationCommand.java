package by.ny.server.controller.command.user;

import by.ny.server.controller.command.Command;
import by.ny.server.service.AuthorizationService;
import by.ny.server.util.Request;
import by.ny.server.util.Response;

import java.util.List;

public class AuthorizationCommand implements Command {
    private final AuthorizationService authorizationService = AuthorizationService.getInstance();

    @Override
    public Response execute(Request request) {
        List<Object> objects = (List) request.getData();
        return new Response(authorizationService.authenticate((String) objects.get(0), (String) objects.get(1)));
    }
}
