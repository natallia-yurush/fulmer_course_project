package by.ny.server.entity;

import java.util.Date;

public class Report extends Entity {
    private static final long serialVersionUID = -8372756804772618469L;

    private Date date;
    private Double x1, x2, x3, x4, x5, x6, x7, x8, x9;
    private Double result;
    private DollarRate dollarRate;
    private Company company;
    private Currency currency;

    public Report(Integer id, Company company, DollarRate dollarRate, Date date, Double x1, Double x2, Double x3, Double x4, Double x5, Double x6, Double x7, Double x8, Double x9, Double result, Currency currency) {
        super(id);
        this.date = date;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
        this.x5 = x5;
        this.x6 = x6;
        this.x7 = x7;
        this.x8 = x8;
        this.x9 = x9;
        this.result = result;
        this.dollarRate = dollarRate;
        this.company = company;
        this.currency = currency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getX1() {
        return x1;
    }

    public void setX1(Double x1) {
        this.x1 = x1;
    }

    public Double getX2() {
        return x2;
    }

    public void setX2(Double x2) {
        this.x2 = x2;
    }

    public Double getX3() {
        return x3;
    }

    public void setX3(Double x3) {
        this.x3 = x3;
    }

    public Double getX4() {
        return x4;
    }

    public void setX4(Double x4) {
        this.x4 = x4;
    }

    public Double getX5() {
        return x5;
    }

    public void setX5(Double x5) {
        this.x5 = x5;
    }

    public Double getX6() {
        return x6;
    }

    public void setX6(Double x6) {
        this.x6 = x6;
    }

    public Double getX7() {
        return x7;
    }

    public void setX7(Double x7) {
        this.x7 = x7;
    }

    public Double getX8() {
        return x8;
    }

    public void setX8(Double x8) {
        this.x8 = x8;
    }

    public Double getX9() {
        return x9;
    }

    public void setX9(Double x9) {
        this.x9 = x9;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public DollarRate getDollarRate() {
        return dollarRate;
    }

    public void setDollarRate(DollarRate dollarRate) {
        this.dollarRate = dollarRate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
