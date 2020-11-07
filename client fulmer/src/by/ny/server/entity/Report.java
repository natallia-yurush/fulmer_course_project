package by.ny.server.entity;

import java.io.Serializable;
import java.util.Date;

public class Report implements Serializable {
    private static final long serialVersionUID = -8372756804772618469L;

    private Date date;
    private int id;
    private double x1, x2, x3, x4, x5, x6, x7, x8, x9;
    private double result;
    private int idUSD;
    private int idCompany;

    public Report(Date date, int id, double x1, double x2, double x3, double x4, double x5, double x6, double x7,
                  double x8, double x9, double result, int idUSD, int idCompany) {
        this.date = date;
        this.id = id;
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
        this.idUSD = idUSD;
        this.idCompany = idCompany;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getX3() {
        return x3;
    }

    public void setX3(double x3) {
        this.x3 = x3;
    }

    public double getX4() {
        return x4;
    }

    public void setX4(double x4) {
        this.x4 = x4;
    }

    public double getX5() {
        return x5;
    }

    public void setX5(double x5) {
        this.x5 = x5;
    }

    public double getX6() {
        return x6;
    }

    public void setX6(double x6) {
        this.x6 = x6;
    }

    public double getX7() {
        return x7;
    }

    public void setX7(double x7) {
        this.x7 = x7;
    }

    public double getX8() {
        return x8;
    }

    public void setX8(double x8) {
        this.x8 = x8;
    }

    public double getX9() {
        return x9;
    }

    public void setX9(double x9) {
        this.x9 = x9;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public int getIdUSD() {
        return idUSD;
    }

    public void setIdUSD(int idUSD) {
        this.idUSD = idUSD;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }
}
