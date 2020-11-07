package by.ny.server.entity;

import java.io.Serializable;
import java.util.Date;

public class DollarRate implements Serializable {
    private static final long serialVersionUID = 1584640582219281135L;

    private Integer id;
    private Date date;
    private Double EUR;
    private Double BYN;

    public DollarRate(Integer id, Date date, Double EUR, Double BYN) {
        this.id = id;
        this.date = date;
        this.EUR = EUR;
        this.BYN = BYN;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getEUR() {
        return EUR;
    }

    public void setEUR(Double EUR) {
        this.EUR = EUR;
    }

    public Double getBYN() {
        return BYN;
    }

    public void setBYN(Double BYN) {
        this.BYN = BYN;
    }
}
