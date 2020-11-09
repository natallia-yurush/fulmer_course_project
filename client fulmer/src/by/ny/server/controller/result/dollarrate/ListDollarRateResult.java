package by.ny.server.controller.result.dollarrate;

import by.ny.server.entity.DollarRate;

import java.io.Serializable;
import java.util.List;

public class ListDollarRateResult implements Serializable {
    private static final long serialVersionUID = -4023178159209646409L;

    private List<DollarRate> dollarRates;

    public ListDollarRateResult(List<DollarRate> dollarRates) {
        this.dollarRates = dollarRates;
    }

    public List<DollarRate> getDollarRates() {
        return dollarRates;
    }

    public void setDollarRates(List<DollarRate> dollarRates) {
        this.dollarRates = dollarRates;
    }
}
