package by.ny.server.controller.command.report;

import java.io.Serializable;

public class ListReportsCommand implements Serializable {
    private static final long serialVersionUID = -759667215382937265L;

    private Integer companyId;

    public ListReportsCommand(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer userId) {
        this.companyId = companyId;
    }
}
