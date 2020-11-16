package by.ny.server.service;

import by.ny.server.dao.DollarRateDao;
import by.ny.server.entity.DollarRate;

import java.util.List;

public class DollarRateService {
    private DollarRateService() {
    }

    private DollarRateDao dollarRateDao = DollarRateDao.getInstance();

    public List<DollarRate> listRates() {
        return dollarRateDao.listRates();
    }

    public DollarRate findLastDollarRate() {
        return dollarRateDao.findLastDollarRate();
    }

    public boolean saveDollarRate(DollarRate dollarRate) {
        return dollarRateDao.saveDollarRate(dollarRate);
    }

    private static class Holder {
        public static DollarRateService instance = new DollarRateService();
    }

    public static DollarRateService getInstance() {
        return DollarRateService.Holder.instance;
    }
}
