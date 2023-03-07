package top.iot.gateway.core.things;

public interface ThingsRegistrySupport extends ThingsRegistry {

    boolean isSupported(String thingType);

   default boolean isSupported(ThingType thingType){
       return isSupported(thingType.getId());
   }

}
