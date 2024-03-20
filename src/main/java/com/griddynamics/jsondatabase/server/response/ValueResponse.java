package com.griddynamics.jsondatabase.server.response;



import lombok.Getter;

import java.util.Objects;
@Getter
public class ValueResponse extends Response {
    private final String value;
    public ValueResponse(String response, String value) {
        super(response);
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValueResponse that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}
