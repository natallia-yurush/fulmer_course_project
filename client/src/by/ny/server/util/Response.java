package by.ny.server.util;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = -3227238585358255766L;

    private Object data;

    public Response(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
