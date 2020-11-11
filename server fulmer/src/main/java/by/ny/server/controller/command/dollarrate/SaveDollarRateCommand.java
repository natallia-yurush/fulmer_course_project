package by.ny.server.controller.command.dollarrate;

import by.ny.server.entity.DollarRate;

import java.io.Serializable;

public class SaveDollarRateCommand implements Serializable {
    private static final long serialVersionUID = -4334465564831184470L;

    private DollarRate dollarRate;

    public SaveDollarRateCommand(DollarRate dollarRate) {
        this.dollarRate = dollarRate;
    }

    public DollarRate getDollarRate() {
        return dollarRate;
    }

    public void setDollarRate(DollarRate dollarRate) {
        this.dollarRate = dollarRate;
    }
}
