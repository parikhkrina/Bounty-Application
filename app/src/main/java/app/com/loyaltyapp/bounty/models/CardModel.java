package app.com.loyaltyapp.bounty.models;

public class CardModel {
    String offerId;
    String offerDesc;
    int offerImgId;

    public CardModel(String id, String desc, int imgId)
    {
        this.offerId = id;
        this.offerDesc = desc;
        this.offerImgId = imgId;
    }

    public String getOfferId() {
        return offerId;
    }

    public String getOfferDesc() {
        return offerDesc;
    }

    public int getOfferImgId() {
        return offerImgId;
    }
}
