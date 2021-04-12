package by.ny.server.controller.command.user;

import by.ny.server.controller.command.Command;
import by.ny.server.entity.RegistrationStatus;
import by.ny.server.entity.User;
import by.ny.server.entity.UserRole;
import by.ny.server.service.RegistrationService;
import by.ny.server.service.UserService;
import by.ny.server.util.Request;
import by.ny.server.util.Response;

import java.util.ArrayList;
import java.util.Arrays;

public class RegistrationCommand implements Command {
    private final RegistrationService registrationService = RegistrationService.getInstance();
    private final UserService userService = UserService.getInstance();

    @Override
    public Response execute(Request request) {
        ArrayList<Object> objects = (ArrayList<Object>) request.getData();
        RegistrationStatus status = registrationService.registration((String) objects.get(2), (String) objects.get(3));
        if (status == RegistrationStatus.REGISTERED) {
            User user = new User(null, (String) objects.get(0), (String) objects.get(2),
                    (String) objects.get(3), (String) objects.get(4), (String) objects.get(5),
                    UserRole.valueOf((String) objects.get(6)), (String) objects.get(7));
            userService.saveUser(user);
            return new Response(Arrays.asList(status, user));
        } else {
            return new Response(Arrays.asList(status, null));
        }
    }


}
