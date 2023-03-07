package top.iot.gateway.core.metadata.types;

import org.junit.Assert;
import org.junit.Test;

public class DataTypesTest {

    @Test
    public void testLookup(){
        Assert.assertNotNull(DataTypes.lookup("int"));
        Assert.assertNotNull(DataTypes.lookup("long"));
        Assert.assertNotNull(DataTypes.lookup("double"));
        Assert.assertNotNull(DataTypes.lookup("float"));
        Assert.assertNotNull(DataTypes.lookup("date"));
        Assert.assertNotNull(DataTypes.lookup("object"));
        Assert.assertNotNull(DataTypes.lookup("array"));
        Assert.assertNotNull(DataTypes.lookup("enum"));

    }
}