package model.builder;

import model.Client;

public class ClientBuilder {
    private Client client;
    public ClientBuilder(){
        client = new Client();
    }

    public ClientBuilder setName(String name){
        client.setName(name);
        return this;
    }
    public ClientBuilder setId(Long id){
        client.setId(id);
        return this;
    }
    public ClientBuilder setCardNumber(String cardNumber){
        client.setIdentity_card_number(cardNumber);
        return this;
    }
    public ClientBuilder setPersonalNumber(String personalNumber){
        client.setPersonal_nr_code(personalNumber);
        return this;
    }
    public ClientBuilder setAddress(String address){
        client.setAddress(address);
        return this;
    }
    public ClientBuilder setPhoneNumber(String phoneNumber){
        client.setPhone_number(phoneNumber);
        return this;
    }
    public Client build(){return client;}
}
