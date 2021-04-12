package by.ny.server.controller.command;

import by.ny.server.util.Request;
import by.ny.server.util.Response;

public interface Command {
    Response execute(Request request);
}
