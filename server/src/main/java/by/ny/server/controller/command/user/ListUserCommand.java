package by.ny.server.controller.command.user;

import by.ny.server.controller.command.Command;
import by.ny.server.service.UserService;
import by.ny.server.util.Request;
import by.ny.server.util.Response;

public class ListUserCommand implements Command {
    private final UserService userService = UserService.getInstance();
    @Override
    public Response execute(Request request) {
        return new Response(userService.listUsers());
    }
}
