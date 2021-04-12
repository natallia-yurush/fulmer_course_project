package by.ny.server.util;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 3069251170288574205L;

    private RequestType type;
    private Object data;

    public Request(RequestType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
