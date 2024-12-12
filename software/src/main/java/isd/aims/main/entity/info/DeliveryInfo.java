package isd.aims.main.entity.info;

public class DeliveryInfo {
    private int id;
    private String name;
    private String address;
    private String province;
    private String instruction;
    private String phoneNumber;
    private String email;

    public DeliveryInfo(String name, String address, String province, String instruction, String phoneNumber, String email) {
        this.name = name;
        this.address = address;
        this.province = province;
        this.instruction = instruction;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public DeliveryInfo getDeliveryInfo() {
        return this;
    }


}
