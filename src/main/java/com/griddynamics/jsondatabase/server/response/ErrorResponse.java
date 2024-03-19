package com.griddynamics.jsondatabase.server.response;

import lombok.Getter;

import java.util.Objects;

@Getter
public class ErrorResponse extends Response {
    private final String reason;
    public ErrorResponse(String response, String reason) {
        super(response);
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorResponse that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), reason);
    }
}
