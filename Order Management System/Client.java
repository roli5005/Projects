package Model;

public class Client {
    private int idclient;
    private String Name;
    private String Address;

    public Client(){
        setIdclient(0);
        setName("");
        setAddress("");
    }
    Client(String Name,String Address){
        this.setName(Name);
        this.setAddress(Address);
    }

    /**
     * @return the name
     */
    public String getName() {
        return Name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        Name = name;
    }
    /**
     * @return the address
     */
    public String getAddress() {
        return Address;
    }
    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        Address = address;
    }
    /**
     * @return the idclient
     */
    public int getIdclient() {
        return idclient;
    }
    /**
     * @param idclient the idclient to set
     */
    public void setIdclient(int idclient) {
        this.idclient = idclient;
    }

}
