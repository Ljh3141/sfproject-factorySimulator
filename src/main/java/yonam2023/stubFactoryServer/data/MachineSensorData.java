package yonam2023.stubFactoryServer.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MachineSensorData {
    int machineId;
    int value;
    int used;
    int stock;
    boolean output;

    String productType;
}
