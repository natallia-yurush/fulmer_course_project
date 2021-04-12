package by.ny.server.service;

import by.ny.server.dao.DollarRateDao;
import by.ny.server.entity.DollarRate;

import java.util.List;

public class DollarRateService {
    private DollarRateService() {
    }

    private DollarRateDao dollarRateDao = new DollarRateDao();

    public List<DollarRate> listRates() {
        return dollarRateDao.findAll();
    }

    public DollarRate findLastDollarRate() {
        return dollarRateDao.findLastDollarRate();
    }

    public boolean saveDollarRate(DollarRate dollarRate) {
        return dollarRateDao.save(dollarRate);
    }

    private static class Holder {
        public static DollarRateService instance = new DollarRateService();
    }

    public static DollarRateService getInstance() {
        return Holder.instance;
    }
}
