package by.ny.client;

import by.ny.server.entity.User;

public class CurrentUserUtil {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
