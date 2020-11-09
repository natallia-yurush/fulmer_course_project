package by.ny.server.service;

import by.ny.server.dao.DollarRateDao;
import by.ny.server.entity.DollarRate;

import java.util.List;

public class DollarRateService {
    private DollarRateDao dollarRateDao;

    public DollarRateService (DollarRateDao dollarRateDao) {
        this.dollarRateDao = dollarRateDao;
    }

    public List<DollarRate> listRates() {
        return dollarRateDao.listRates();
    }

    public DollarRate findLastDollarRate() {
        return dollarRateDao.findLastDollarRate();
    }

    public boolean saveDollarRate(DollarRate dollarRate) {
        return dollarRateDao.saveDollarRate(dollarRate);
    }
}
