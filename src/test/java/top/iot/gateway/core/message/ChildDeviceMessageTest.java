package top.iot.gateway.core.message;

import top.iot.gateway.core.exception.DeviceOperationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChildDeviceMessageTest {


    @Test
    public void test(){
        ChildDeviceMessage message=new ChildDeviceMessage();
        message.setChildDeviceId("test");

        {
            ChildDeviceMessage child=new ChildDeviceMessage();
            child.setChildDeviceId("test2");
            {
                ChildDeviceMessage child2=new ChildDeviceMessage();
                child2.setChildDeviceId("test");
                child2.setChildDeviceMessage(child);

                child.setChildDeviceMessage(child2);
                try{
                    child.validate();
                    assertFalse(false);
                }catch (DeviceOperationException e){

                }
            }
        }


    }

}