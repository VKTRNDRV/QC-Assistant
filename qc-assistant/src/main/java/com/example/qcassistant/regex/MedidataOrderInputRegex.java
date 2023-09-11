package com.example.qcassistant.regex;

public class MedidataOrderInputRegex {

    private static final String CLIENT = "Organization Medidata";

    private static final String STUDY = "Study\\s+(?<name>[a-zA-Z0-9]+)\\s+Site";

    private static final String SHIPPING_INSTRUCTIONS = "Shipping Instructions:(?<shippingInstructions>.+)Order Term Comments";

    private static final String ORDER_TERM_COMMENTS = "Order Term Comments:(?<orderTermComments>.+)Oline";
}
