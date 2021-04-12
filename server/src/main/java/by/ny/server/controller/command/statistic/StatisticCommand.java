package by.ny.server.controller.command.statistic;

import by.ny.server.controller.command.Command;
import by.ny.server.service.StatisticService;
import by.ny.server.util.Request;
import by.ny.server.util.Response;

import java.util.Arrays;

public class StatisticCommand implements Command {
    private final StatisticService statisticService = StatisticService.getInstance();

    @Override
    public Response execute(Request request) {
        return new Response(Arrays.asList(statisticService.numberOfCompaniesResult(),
                statisticService.numberOfReportsResult(), statisticService.numberOfUsersResult()));
    }
}
