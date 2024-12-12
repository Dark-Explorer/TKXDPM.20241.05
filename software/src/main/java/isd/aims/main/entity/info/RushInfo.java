package isd.aims.main.entity.info;

import java.time.LocalDateTime;

public class RushInfo {
    private int id;
    private String instruction;
    private LocalDateTime timeDelivery;

    public RushInfo(String instruction, LocalDateTime timeDelivery) {
        this.instruction = instruction;
        this.timeDelivery = timeDelivery;
    }

    public RushInfo getRushInfo() {
        return this;
    }
}
