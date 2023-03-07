package top.iot.gateway.core.metadata.types;

import org.junit.Assert;
import org.junit.Test;

public class EnumTypeTest {

    @Test
    public void test(){

        EnumType type=new EnumType();

        Assert.assertFalse(type.validate("1").isSuccess());
        Assert.assertEquals(type.format("1"),"1");

        type.addElement(EnumType.Element.of("1","男"));
        type.addElement(EnumType.Element.of("2","女"));

        Assert.assertTrue(type.validate("1").isSuccess());
        Assert.assertEquals(type.format("1"),"男");


    }

}