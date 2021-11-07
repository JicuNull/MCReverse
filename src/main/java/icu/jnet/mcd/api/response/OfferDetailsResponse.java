package icu.jnet.mcd.api.response;

import java.util.List;

public class OfferDetailsResponse extends Response {

    private Response response;

    public Response getResponse() {
        return response;
    }

    public static class Response {

        private int colorCodingInfo, offerPropositionId, offerType, orderDiscountType, redemptionMode;
        private boolean isDynamicExpiration, isExpired, isLocked, isSLPOffer, isvalidTotalOrder;
        private String imageBaseLanguage, imageBaseName, localValidFrom, localValidTo, longDescription, name, offerBucket;
        private Conditions conditions;
        private List<ProductSet> productSets;
        private List<String> restaurants;

        public int getColorCodingInfo() {
            return colorCodingInfo;
        }

        public int getOfferPropositionId() {
            return offerPropositionId;
        }

        public int getOfferType() {
            return offerType;
        }

        public int getRedemptionMode() {
            return redemptionMode;
        }

        public int getOrderDiscountType() {
            return orderDiscountType;
        }

        public boolean isSLPOffer() {
            return isSLPOffer;
        }

        public boolean isLocked() {
            return isLocked;
        }

        public boolean isDynamicExpiration() {
            return isDynamicExpiration;
        }

        public boolean isExpired() {
            return isExpired;
        }

        public boolean isValidTotalOrder() {
            return isvalidTotalOrder;
        }

        public String getImageBaseLanguage() {
            return imageBaseLanguage;
        }

        public String getImageBaseName() {
            return imageBaseName;
        }

        public String getOfferBucket() {
            return offerBucket;
        }

        public String getName() {
            return name;
        }

        public String getLongDescription() {
            return longDescription;
        }

        public String getLocalValidTo() {
            return localValidTo;
        }

        public String getLocalValidFrom() {
            return localValidFrom;
        }

        public Conditions getConditions() {
            return conditions;
        }

        public List<ProductSet> getProductSets() {
            return productSets;
        }

        public List<String> getRestaurants() {
            return restaurants;
        }
    }

    public static class Conditions {

        private List<String> dateConditions, dayOfWeekConditions, saleAmountConditions;

        public List<String> getDateConditions() {
            return dateConditions;
        }

        public List<String> getDayOfWeekConditions() {
            return dayOfWeekConditions;
        }

        public List<String> getSaleAmountConditions() {
            return saleAmountConditions;
        }
    }

    public static class ProductSet {
        private int minQuantity, quantity;
        private String alias;
        private Action action;
        private List<String> products;

        public Action getAction() {
            return action;
        }

        public String getAlias() {
            return alias;
        }

        public int getMinQuantity() {
            return minQuantity;
        }

        public List<String> getProducts() {
            return products;
        }

        public int getQuantity() {
            return quantity;
        }
    }

    public static class Action {
        private int discountType, type;
        private double value;

        public int getDiscountType() {
            return discountType;
        }

        public int getType() {
            return type;
        }

        public double getValue() {
            return value;
        }
    }
}