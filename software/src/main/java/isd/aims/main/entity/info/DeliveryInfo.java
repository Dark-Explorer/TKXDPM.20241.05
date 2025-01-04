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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProvince() {
        return province;
    }


}
