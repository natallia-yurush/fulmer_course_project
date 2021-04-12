package by.ny.server.controller.command.report;

import by.ny.server.controller.command.Command;
import by.ny.server.entity.Report;
import by.ny.server.service.ReportService;
import by.ny.server.util.Request;
import by.ny.server.util.Response;

public class SaveReportCommand implements Command {
    private final ReportService reportService = ReportService.getInstance();

    @Override
    public Response execute(Request request) {
        return new Response(reportService.saveReport((Report) request.getData()));
    }
}
