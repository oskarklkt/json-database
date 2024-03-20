package com.griddynamics.jsondatabase.client.input;

import com.beust.jcommander.JCommander;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientArgsTest {

    @Test
    void testArgumentParsing() {
        String[] args = {"--type", "set", "--key", "myKey", "--value", "myValue"};
        ClientArgs clientArgs = new ClientArgs();
        JCommander.newBuilder()
                .addObject(clientArgs)
                .build()
                .parse(args);

        assertEquals("set", clientArgs.getType());
        assertEquals("myKey", clientArgs.getKey());
        assertEquals("myValue", clientArgs.getValue());
    }
}