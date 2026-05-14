package promo.bot.beedle_deals.core.domain;

public class Product {
    private String name;
    private String affiliateUrl;
    private String imageUrl;
    private Integer priceInCents;
    private Integer discountPercent;

    public Product(String name, String affiliateUrl, String imageUrl, Integer priceInCents, Integer discountPercent) {
        this.name = name;
        this.affiliateUrl = affiliateUrl;
        this.imageUrl = imageUrl;
        this.priceInCents = priceInCents;
        this.discountPercent = discountPercent;
    }

    public Product() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAffiliateUrl() {
        return affiliateUrl;
    }

    public void setAffiliateUrl(String affiliateUrl) {
        this.affiliateUrl = affiliateUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPriceInCents() {
        return priceInCents;
    }

    public void setPriceInCents(Integer priceInCents) {
        this.priceInCents = priceInCents;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }
}
