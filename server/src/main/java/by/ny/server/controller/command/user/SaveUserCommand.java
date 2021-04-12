package by.ny.server.controller.command.user;

import by.ny.server.controller.command.Command;
import by.ny.server.entity.User;
import by.ny.server.service.RegistrationService;
import by.ny.server.service.UserService;
import by.ny.server.util.Request;
import by.ny.server.util.Response;

import java.util.ArrayList;
import java.util.List;

public class SaveUserCommand implements Command {
    private final UserService userService = UserService.getInstance();
    private final RegistrationService registrationService = RegistrationService.getInstance();

    @Override
    public Response execute(Request request) {
        List<Object> objects = new ArrayList<>();
        User user = (User) request.getData();
        objects.add(userService.saveUser(user));
        objects.add(registrationService.registration(user.getLogin(), user.getEmail()));

        return new Response(objects);
    }
}
