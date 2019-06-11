package app.com.loyaltyapp.bounty.models;

import java.io.Serializable;
import java.util.Random;

public class UserDetails implements Serializable {

    private String name;
    private String cardNumber;
    private String phoneNumber;
    private String email;
    private String points;

    private static Random rnd;

    public UserDetails(){
        rnd = new Random();
    }



    public UserDetails(String name, String phoneNumber, String email,String points,String cardNumber) {
        rnd = new Random();
        this.name = name;
        this.cardNumber = generateCardNumber();
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.points = "2000";
    }

    private String generateCardNumber(){
        StringBuilder sb = new StringBuilder(16);
        for(int i=0; i < 16; i++)
            sb.append((char)('0' + rnd.nextInt(10)));
        return sb.toString();
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
