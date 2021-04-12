package by.ny.server.entity;

import java.util.Date;

public class DollarRate extends Entity {
    private static final long serialVersionUID = 1584640582219281135L;

    private Date date;
    private Double EUR;
    private Double BYN;

    public DollarRate(Integer id, Date date, Double EUR, Double BYN) {
        super(id);
        this.date = date;
        this.EUR = EUR;
        this.BYN = BYN;
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
