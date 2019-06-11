package app.com.loyaltyapp.bounty.models;

public class PointsHistory {

    String cardNumber;
    String email;
    String pointsUsed;
    String transactionDate;
    String otherUser;

    public PointsHistory() {
    }

    public PointsHistory(String cardNumber, String email, String pointsUsed, String transactionDate, String user) {
        this.cardNumber = cardNumber;
        this.email = email;
        this.pointsUsed = pointsUsed;
        this.transactionDate = transactionDate;
        this.otherUser = user;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPointsUsed() {
        return pointsUsed;
    }

    public void setPointsUsed(String pointsUsed) {
        this.pointsUsed = pointsUsed;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(String user) {
        this.otherUser = user;
    }
}
