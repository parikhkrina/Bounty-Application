package app.com.loyaltyapp.bounty.models;

public class OffersModel {
    String offerId;
    String offerDesc;
    String offerImage;
    String offerName;
    String offerPoints;

    public OffersModel() {
    }

    public OffersModel(String offerId, String offerDesc, String offerImage, String offerName, String offerPoints) {
        this.offerId = offerId;
        this.offerDesc = offerDesc;
        this.offerImage = offerImage;
        this.offerName = offerName;
        this.offerPoints = offerPoints;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferDesc() {
        return offerDesc;
    }

    public void setOfferDesc(String offerDesc) {
        this.offerDesc = offerDesc;
    }

    public String getOfferImage() {
        return offerImage;
    }

    public void setOfferImage(String offerImage) {
        this.offerImage = offerImage;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getOfferPoints() {
        return offerPoints;
    }

    public void setOfferPoints(String offerPoints) {
        this.offerPoints = offerPoints;
    }

}
