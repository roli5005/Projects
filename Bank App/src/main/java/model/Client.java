package model;

public class Client {
    private Long id;
    private String name;
    private String identity_card_number;
    private String personal_nr_code;
    private String address;
    private String phone_number;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity_card_number() {
        return identity_card_number;
    }

    public void setIdentity_card_number(String identity_card_number) {
        this.identity_card_number = identity_card_number;
    }

    public String getPersonal_nr_code() {
        return personal_nr_code;
    }

    public void setPersonal_nr_code(String personal_nr_code) {
        this.personal_nr_code = personal_nr_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }


}
