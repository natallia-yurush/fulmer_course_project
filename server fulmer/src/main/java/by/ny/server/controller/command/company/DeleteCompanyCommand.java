package by.ny.server.controller.command.company;

import java.io.Serializable;

public class DeleteCompanyCommand implements Serializable {
    private static final long serialVersionUID = -2974314414414103659L;

    private Integer companyId;

    public DeleteCompanyCommand(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
