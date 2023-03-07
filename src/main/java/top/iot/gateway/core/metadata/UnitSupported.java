package top.iot.gateway.core.metadata;

import top.iot.gateway.core.metadata.unit.ValueUnit;

public interface UnitSupported {
    ValueUnit getUnit();

    void setUnit(ValueUnit unit);
}
