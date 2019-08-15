package de.netview.data;

import de.netview.model.Contract;
import de.netview.model.Handy;
import de.netview.model.MobileUser;

import java.io.Serializable;

public class MobileUserData implements Serializable {

    private Long mid;
    private String name;
    private String number;
    private String sim;
    private String pin;
    private String puk;
    private String ultraOne;
    private String ultraTwo;
    private HandyData handy;
    private ContractData contract;

    public MobileUserData(){

    }

    public MobileUserData(MobileUser mobileUser){
        this.setMid(mobileUser.getMid());
        this.setName(mobileUser.getName());
        this.setNumber(mobileUser.getNumber());
        this.setSim(mobileUser.getSim());
        this.setPin(mobileUser.getPin());
        this.setPuk(mobileUser.getPuk());
        this.setUltraOne(mobileUser.getUltraOne());
        this.setUltraTwo(mobileUser.getUltraTwo());
        this.setHandy(new HandyData(mobileUser.getHandy()));
        this.setContract(new ContractData(mobileUser.getContract()));
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPuk() {
        return puk;
    }

    public void setPuk(String puk) {
        this.puk = puk;
    }

    public String getUltraOne() {
        return ultraOne;
    }

    public void setUltraOne(String ultraOne) {
        this.ultraOne = ultraOne;
    }

    public String getUltraTwo() {
        return ultraTwo;
    }

    public void setUltraTwo(String ultraTwo) {
        this.ultraTwo = ultraTwo;
    }

    public HandyData getHandy() {
        return handy;
    }

    public void setHandy(HandyData handy) {
        this.handy = handy;
    }

    public ContractData getContract() {
        return contract;
    }

    public void setContract(ContractData contract) {
        this.contract = contract;
    }
}
