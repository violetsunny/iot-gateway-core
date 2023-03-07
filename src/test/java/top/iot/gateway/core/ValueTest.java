package top.iot.gateway.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValueTest {


    @Test
    public void test(){
        assertEquals(1,Value.simple(1).asInt());
        assertEquals(1L,Value.simple(1).asLong());
        assertTrue(Value.simple(true).asBoolean());
        assertTrue(Value.simple("true").asBoolean());


    }
}