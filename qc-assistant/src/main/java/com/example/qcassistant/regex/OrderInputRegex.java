package com.example.qcassistant.regex;

public class OrderInputRegex {

    public static final String BASIC_INFO_REGEX = "(?<basicInfo>.+)Shipping Instructions:";
    public static final String BASIC_INFO_GROUP = "basicInfo";

    public static final String SHIPPING_INSTRUCTIONS_REGEX = "Shipping Instructions:(?<shippingInstructions>.+)Order Term Comments";
    public static final String SHIPPING_INSTRUCTIONS_GROUP = "shippingInstructions";

    public static final String ORDER_TERM_COMMENTS_REGEX = "Order Term Comments:(?<orderTermComments>.+)OLine";
    public static final String ORDER_TERM_COMMENTS_GROUP = "orderTermComments";

    public static final String ITEMS_LIST_REGEX = "OLine(?<itemsList>.+)";
    public static final String ITEMS_LIST_GROUP = "itemsList";
}
