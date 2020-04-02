package aboutjava.annotion.combat;

import java.util.*;

/**
 * @program: javac-source-code-reading
 * @description:
 * @author: hu_pf
 * @create: 2020-03-25 16:06
 **/
public class TestDelete {

    public static void main(String[] args) {
        String code = "account_already_exists\n" +
                "account_country_invalid_address\n" +
                "account_invalid\n" +
                "account_number_invalid\n" +
                "alipay_upgrade_required\n" +
                "amount_too_large\n" +
                "amount_too_small\n" +
                "api_key_expired\n" +
                "authentication_required\n" +
                "balance_insufficient\n" +
                "bank_account_declined\n" +
                "bank_account_exists\n" +
                "bank_account_unusable\n" +
                "bank_account_unverified\n" +
                "bank_account_verification_failed\n" +
                "bitcoin_upgrade_required\n" +
                "card_decline_rate_limit_exceeded\n" +
                "card_declined\n" +
                "charge_already_captured\n" +
                "charge_already_refunded\n" +
                "charge_disputed\n" +
                "charge_exceeds_source_limit\n" +
                "charge_expired_for_capture\n" +
                "charge_invalid_parameter\n" +
                "country_unsupported\n" +
                "coupon_expired\n" +
                "customer_max_subscriptions\n" +
                "email_invalid\n" +
                "expired_card\n" +
                "idempotency_key_in_use\n" +
                "incorrect_address\n" +
                "incorrect_cvc\n" +
                "incorrect_number\n" +
                "incorrect_zip\n" +
                "instant_payouts_unsupported\n" +
                "invalid_card_type\n" +
                "invalid_charge_amount\n" +
                "invalid_cvc\n" +
                "invalid_expiry_month\n" +
                "invalid_expiry_year\n" +
                "invalid_number\n" +
                "invalid_source_usage\n" +
                "invoice_no_customer_line_items\n" +
                "invoice_no_subscription_line_items\n" +
                "invoice_not_editable\n" +
                "invoice_payment_intent_requires_action\n" +
                "invoice_upcoming_none\n" +
                "livemode_mismatch\n" +
                "lock_timeout\n" +
                "missing\n" +
                "not_allowed_on_standard_account\n" +
                "order_creation_failed\n" +
                "order_required_settings\n" +
                "order_status_invalid\n" +
                "order_upstream_timeout\n" +
                "out_of_inventory\n" +
                "parameter_invalid_empty\n" +
                "parameter_invalid_integer\n" +
                "parameter_invalid_string_blank\n" +
                "parameter_invalid_string_empty\n" +
                "parameter_missing\n" +
                "parameter_unknown\n" +
                "parameters_exclusive\n" +
                "payment_intent_action_required\n" +
                "payment_intent_authentication_failure\n" +
                "payment_intent_incompatible_payment_method\n" +
                "payment_intent_invalid_parameter\n" +
                "payment_intent_payment_attempt_failed\n" +
                "payment_intent_unexpected_state\n" +
                "payment_method_unactivated\n" +
                "payment_method_unexpected_state\n" +
                "payouts_not_allowed\n" +
                "platform_api_key_expired\n" +
                "postal_code_invalid\n" +
                "processing_error\n" +
                "product_inactive\n" +
                "rate_limit\n" +
                "resource_already_exists\n" +
                "resource_missing\n" +
                "routing_number_invalid\n" +
                "secret_key_required\n" +
                "sepa_unsupported_account\n" +
                "setup_attempt_failed\n" +
                "setup_intent_authentication_failure\n" +
                "setup_intent_invalid_parameter\n" +
                "setup_intent_unexpected_state\n" +
                "shipping_calculation_failed\n" +
                "sku_inactive\n" +
                "state_unsupported\n" +
                "tax_id_invalid\n" +
                "taxes_calculation_failed\n" +
                "testmode_charges_only\n" +
                "tls_version_unsupported\n" +
                "token_already_used\n" +
                "token_in_use\n" +
                "transfers_not_allowed\n" +
                "upstream_order_creation_failed\n" +
                "url_invalid";

        String msg = "The email address provided for the creation of a deferred account already has an account associated with it. Use the OAuth flow to connect the existing account to your platform.     \n" +
                "The country of the business address provided does not match the country of the account. Businesses must be located in the same country as the account.     \n" +
                "The account ID provided as a value for the Stripe-Account header is invalid. Check that your requests are specifying a valid account ID.     \n" +
                "The bank account number provided is invalid (e.g., missing digits). Bank account information varies from country to country. We recommend creating validations in your entry forms based on the bank account formats we provide.     \n" +
                "This method for creating Alipay payments is not supported anymore. Please upgrade your integration to use Sources instead.     \n" +
                "The specified amount is greater than the maximum amount allowed. Use a lower amount and try again.     \n" +
                "The specified amount is less than the minimum amount allowed. Use a higher amount and try again.     \n" +
                "The API key provided has expired. Obtain your current API keys from the Dashboard and update your integration to use them.     \n" +
                "The payment requires authentication to proceed. If your customer is off session, notify your customer to return to your application and complete the payment. If you provided the error_on_requires_action parameter, then your customer should try another card that does not require authentication.     \n" +
                "The transfer or payout could not be completed because the associated account does not have a sufficient balance available. Create a new transfer or payout using an amount less than or equal to the account’s available balance.     \n" +
                "The bank account provided can not be used to charge, either because it is not verified yet or it is not supported.     \n" +
                "The bank account provided already exists on the specified Customer object. If the bank account should also be attached to a different customer, include the correct customer ID when making the request again.     \n" +
                "The bank account provided cannot be used for payouts. A different bank account must be used.     \n" +
                "Your Connect platform is attempting to share an unverified bank account with a connected account.     \n" +
                "The bank account cannot be verified, either because the microdeposit amounts provided do not match the actual amounts, or because verification has failed too many times.     \n" +
                "This method for creating Bitcoin payments is not supported anymore. Please upgrade your integration to use Sources instead.     \n" +
                "This card has been declined too many times. You can try to charge this card again after 24 hours. We suggest reaching out to your customer to make sure they have entered all of their information correctly and that there are no issues with their card.     \n" +
                "The card has been declined. When a card is declined, the error returned also includes the decline_code attribute with the reason why the card was declined. Refer to our decline codes documentation to learn more.     \n" +
                "The charge you’re attempting to capture has already been captured. Update the request with an uncaptured charge ID.     \n" +
                "The charge you’re attempting to refund has already been refunded. Update the request to use the ID of a charge that has not been refunded.     \n" +
                "The charge you’re attempting to refund has been charged back. Check the disputes documentation to learn how to respond to the dispute.     \n" +
                "This charge would cause you to exceed your rolling-window processing limit for this source type. Please retry the charge later, or contact us to request a higher processing limit.     \n" +
                "The charge cannot be captured as the authorization has expired. Auth and capture charges must be captured within seven days.     \n" +
                "One or more provided parameters was not allowed for the given operation on the Charge. Check our API reference or the returned error message to see which values were not correct for that Charge.     \n" +
                "Your platform attempted to create a custom account in a country that is not yet supported. Make sure that users can only sign up in countries supported by custom accounts.     \n" +
                "The coupon provided for a subscription or order has expired. Either create a new coupon, or use an existing one that is valid.     \n" +
                "The maximum number of subscriptions for a customer has been reached. Contact us if you are receiving this error.     \n" +
                "The email address is invalid (e.g., not properly formatted). Check that the email address is properly formatted and only includes allowed characters.     \n" +
                "The card has expired. Check the expiration date or use a different card.     \n" +
                "The idempotency key provided is currently being used in another request. This occurs if your integration is making duplicate requests simultaneously.     \n" +
                "The card’s address is incorrect. Check the card’s address or use a different card.     \n" +
                "The card’s security code is incorrect. Check the card’s security code or use a different card.     \n" +
                "The card number is incorrect. Check the card’s number or use a different card.     \n" +
                "The card’s ZIP code is incorrect. Check the card’s ZIP code or use a different card.     \n" +
                "This card is not eligible for Instant Payouts. Try a debit card from a supported bank.     \n" +
                "The card provided as an external account is not supported for payouts. Provide a non-prepaid debit card instead.     \n" +
                "The specified amount is invalid. The charge amount must be a positive integer in the smallest currency unit, and not exceed the minimum or maximum amount.     \n" +
                "The card’s security code is invalid. Check the card’s security code or use a different card.     \n" +
                "The card’s expiration month is incorrect. Check the expiration date or use a different card.     \n" +
                "The card’s expiration year is incorrect. Check the expiration date or use a different card.     \n" +
                "The card number is invalid. Check the card details or use a different card.     \n" +
                "The source cannot be used because it is not in the correct state (e.g., a charge request is trying to use a source with a pending, failed, or consumed source). Check the status of the source you are attempting to use.     \n" +
                "An invoice cannot be generated for the specified customer as there are no pending invoice items. Check that the correct customer is being specified or create any necessary invoice items first.     \n" +
                "An invoice cannot be generated for the specified subscription as there are no pending invoice items. Check that the correct subscription is being specified or create any necessary invoice items first.     \n" +
                "The specified invoice can no longer be edited. Instead, consider creating additional invoice items that will be applied to the next invoice. You can either manually generate the next invoice or wait for it to be automatically generated at the end of the billing cycle.     \n" +
                "This payment requires additional user action before it can be completed successfully. Payment can be completed using the PaymentIntent associated with the invoice. See this page for more details.     \n" +
                "There is no upcoming invoice on the specified customer to preview. Only customers with active subscriptions or pending invoice items have invoices that can be previewed.     \n" +
                "Test and live mode API keys, requests, and objects are only available within the mode they are in.     \n" +
                "This object cannot be accessed right now because another API request or Stripe process is currently accessing it. If you see this error intermittently, retry the request. If you see this error frequently and are making multiple concurrent requests to a single object, make your requests serially or at a lower rate.     \n" +
                "Both a customer and source ID have been provided, but the source has not been saved to the customer. To create a charge for a customer with a specified source, you must first save the card details.     \n" +
                "Transfers and payouts on behalf of a Standard connected account are not allowed.     \n" +
                "The order could not be created. Check the order details and then try again.     \n" +
                "The order could not be processed as it is missing required information. Check the information provided and try again.     \n" +
                "The order cannot be updated because the status provided is either invalid or does not follow the order lifecycle (e.g., an order cannot transition from created to fulfilled without first transitioning to paid).     \n" +
                "The request timed out. Try again later.     \n" +
                "The SKU is out of stock. If more stock is available, update the SKU’s inventory quantity and try again.     \n" +
                "One or more required values were not provided. Make sure requests include all required parameters.     \n" +
                "One or more of the parameters requires an integer, but the values provided were a different type. Make sure that only supported values are provided for each attribute. Refer to our API documentation to look up the type of data each attribute supports.     \n" +
                "One or more values provided only included whitespace. Check the values in your request and update any that contain only whitespace.     \n" +
                "One or more required string values is empty. Make sure that string values contain at least one character.     \n" +
                "One or more required values are missing. Check our API documentation to see which values are required to create or modify the specified resource.     \n" +
                "The request contains one or more unexpected parameters. Remove these and try again.     \n" +
                "Two or more mutually exclusive parameters were provided. Check our API documentation or the returned error message to see which values are permitted when creating or modifying the specified resource.     \n" +
                "The provided payment method requires customer actions to complete, but error_on_requires_action was set. If you’d like to add this payment method to your integration, we recommend that you first upgrade your integration to handle actions.     \n" +
                "The provided payment method has failed authentication. Provide a new payment method to attempt to fulfill this PaymentIntent again.     \n" +
                "The PaymentIntent expected a payment method with different properties than what was provided.     \n" +
                "One or more provided parameters was not allowed for the given operation on the PaymentIntent. Check our API reference or the returned error message to see which values were not correct for that PaymentIntent.     \n" +
                "The latest payment attempt for the PaymentIntent has failed. Check the last_payment_error property on the PaymentIntent for more details, and provide a new payment method to attempt to fulfill this PaymentIntent again.     \n" +
                "The PaymentIntent’s state was incompatible with the operation you were trying to perform.     \n" +
                "The charge cannot be created as the payment method used has not been activated. Activate the payment method in the Dashboard, then try again.     \n" +
                "The provided payment method’s state was incompatible with the operation you were trying to perform. Confirm that the payment method is in an allowed state for the given operation before attempting to perform it.     \n" +
                "Payouts have been disabled on the connected account. Check the connected account’s status to see if any additional information needs to be provided, or if payouts have been disabled for another reason.     \n" +
                "The API key provided by your Connect platform has expired. This occurs if your platform has either generated a new key or the connected account has been disconnected from the platform. Obtain your current API keys from the Dashboard and update your integration, or reach out to the user and reconnect the account.     \n" +
                "The ZIP code provided was incorrect.     \n" +
                "An error occurred while processing the card. Try again later or with a different payment method.     \n" +
                "The product this SKU belongs to is no longer available for purchase.     \n" +
                "Too many requests hit the API too quickly. We recommend an exponential backoff of your requests.     \n" +
                "A resource with a user-specified ID (e.g., plan or coupon) already exists. Use a different, unique value for id and try again.     \n" +
                "The ID provided is not valid. Either the resource does not exist, or an ID for a different resource has been provided.     \n" +
                "The bank routing number provided is invalid.     \n" +
                "The API key provided is a publishable key, but a secret key is required. Obtain your current API keys from the Dashboard and update your integration to use them.     \n" +
                "Your account does not support SEPA payments.     \n" +
                "The latest setup attempt for the SetupIntent has failed. Check the last_setup_error property on the SetupIntent for more details, and provide a new payment method to attempt to set it up again.     \n" +
                "The provided payment method has failed authentication. Provide a new payment method to attempt to fulfill this SetupIntent again.     \n" +
                "One or more provided parameters was not allowed for the given operation on the SetupIntent. Check our API reference or the returned error message to see which values were not correct for that SetupIntent.     \n" +
                "The SetupIntent’s state was incompatible with the operation you were trying to perform.     \n" +
                "Shipping calculation failed as the information provided was either incorrect or could not be verified.     \n" +
                "The SKU is inactive and no longer available for purchase. Use a different SKU, or make the current SKU active again.     \n" +
                "Occurs when providing the legal_entity information for a U.S. custom account, if the provided state is not supported. (This is mostly associated states and territories.)     \n" +
                "The tax ID number provided is invalid (e.g., missing digits). Tax ID information varies from country to country, but must be at least nine digits.     \n" +
                "Tax calculation for the order failed.     \n" +
                "Your account has not been activated and can only make test charges. Activate your account in the Dashboard to begin processing live charges.     \n" +
                "Your integration is using an older version of TLS that is unsupported. You must be using TLS 1.2 or above.     \n" +
                "The token provided has already been used. You must create a new token before you can retry this request.     \n" +
                "The token provided is currently being used in another request. This occurs if your integration is making duplicate requests simultaneously.     \n" +
                "The requested transfer cannot be created. Contact us if you are receiving this error.     \n" +
                "The order could not be created. Check the order details and then try again.     \n" +
                "The URL provided is invalid.  ";

        String newMsg = "The email address provided for the creation of a deferred account already has an account associated with it. Use the OAuth flow to connect the existing account to your platform.\n" +
                "The country of the business address provided does not match the country of the account. Businesses must be located in the same country as the account.\n" +
                "The account ID provided as a value for the Stripe-Account header is invalid. Check that your requests are specifying a valid account ID.\n" +
                "The bank account number provided is invalid (e.g., missing digits). Bank account information varies from country to country. We recommend creating validations in your entry forms based on the bank account formats we provide.\n" +
                "The specified amount is greater than the maximum amount allowed. Use a lower amount and try again.\n" +
                "The specified amount is less than the minimum amount allowed. Use a higher amount and try again.\n" +
                "The API key provided has expired. Obtain your current API keys from the Dashboard and update your integration to use them.\n" +
                "The payment requires authentication to proceed. If your customer is off session, notify your customer to return to your application and complete the payment. If you provided the error_on_requires_action parameter, then your customer should try another card that does not require authentication.\n" +
                "The transfer or payout could not be completed because the associated account does not have a sufficient balance available. Create a new transfer or payout using an amount less than or equal to the account’s available balance.\n" +
                "The bank account provided can not be used to charge, either because it is not verified yet or it is not supported.\n" +
                "The bank account provided already exists on the specified Customer object. If the bank account should also be attached to a different customer, include the correct customer ID when making the request again.\n" +
                "The bank account provided cannot be used for payouts. A different bank account must be used.\n" +
                "Your Connect platform is attempting to share an unverified bank account with a connected account.\n" +
                "The bank account cannot be verified, either because the microdeposit amounts provided do not match the actual amounts, or because verification has failed too many times.\n" +
                "This card has been declined too many times. You can try to charge this card again after 24 hours. We suggest reaching out to your customer to make sure they have entered all of their information correctly and that there are no issues with their card.\n" +
                "The card has been declined. When a card is declined, the error returned also includes the decline_code attribute with the reason why the card was declined. Refer to our decline codes documentation to learn more.\n" +
                "Your platform attempted to create a custom account in a country that is not yet supported. Make sure that users can only sign up in countries supported by custom accounts.\n" +
                "The coupon provided for a subscription or order has expired. Either create a new coupon, or use an existing one that is valid.\n" +
                "The maximum number of subscriptions for a customer has been reached. Contact us if you are receiving this error.\n" +
                "The email address is invalid (e.g., not properly formatted). Check that the email address is properly formatted and only includes allowed characters.\n" +
                "The card has expired. Check the expiration date or use a different card.\n" +
                "The idempotency key provided is currently being used in another request. This occurs if your integration is making duplicate requests simultaneously.\n" +
                "The card’s address is incorrect. Check the card’s address or use a different card.\n" +
                "The card’s security code is incorrect. Check the card’s security code or use a different card.\n" +
                "The card number is incorrect. Check the card’s number or use a different card.\n" +
                "The card’s ZIP code is incorrect. Check the card’s ZIP code or use a different card.\n" +
                "This card is not eligible for Instant Payouts. Try a debit card from a supported bank.\n" +
                "The card provided as an external account is not supported for payouts. Provide a non-prepaid debit card instead.\n" +
                "The specified amount is invalid. The charge amount must be a positive integer in the smallest currency unit, and not exceed the minimum or maximum amount.\n" +
                "The card’s security code is invalid. Check the card’s security code or use a different card.\n" +
                "The card’s expiration month is incorrect. Check the expiration date or use a different card.\n" +
                "The card’s expiration year is incorrect. Check the expiration date or use a different card.\n" +
                "The card number is invalid. Check the card details or use a different card.\n" +
                "The source cannot be used because it is not in the correct state (e.g., a charge request is trying to use a source with a pending, failed, or consumed source). Check the status of the source you are attempting to use.\n" +
                "Test and live mode API keys, requests, and objects are only available within the mode they are in.\n" +
                "This object cannot be accessed right now because another API request or Stripe process is currently accessing it. If you see this error intermittently, retry the request. If you see this error frequently and are making multiple concurrent requests to a single object, make your requests serially or at a lower rate.\n" +
                "Both a customer and source ID have been provided, but the source has not been saved to the customer. To create a charge for a customer with a specified source, you must first save the card details.\n" +
                "Transfers and payouts on behalf of a Standard connected account are not allowed.\n" +
                "One or more required values were not provided. Make sure requests include all required parameters.\n" +
                "One or more of the parameters requires an integer, but the values provided were a different type. Make sure that only supported values are provided for each attribute. Refer to our API documentation to look up the type of data each attribute supports.\n" +
                "One or more values provided only included whitespace. Check the values in your request and update any that contain only whitespace.\n" +
                "One or more required string values is empty. Make sure that string values contain at least one character.\n" +
                "One or more required values are missing. Check our API documentation to see which values are required to create or modify the specified resource.\n" +
                "The request contains one or more unexpected parameters. Remove these and try again.\n" +
                "Two or more mutually exclusive parameters were provided. Check our API documentation or the returned error message to see which values are permitted when creating or modifying the specified resource.\n" +
                "The provided payment method requires customer actions to complete, but error_on_requires_action was set. If you’d like to add this payment method to your integration, we recommend that you first upgrade your integration to handle actions.\n" +
                "The provided payment method has failed authentication. Provide a new payment method to attempt to fulfill this PaymentIntent again.\n" +
                "The PaymentIntent expected a payment method with different properties than what was provided.\n" +
                "One or more provided parameters was not allowed for the given operation on the PaymentIntent. Check our API reference or the returned error message to see which values were not correct for that PaymentIntent.\n" +
                "The latest payment attempt for the PaymentIntent has failed. Check the last_payment_error property on the PaymentIntent for more details, and provide a new payment method to attempt to fulfill this PaymentIntent again.\n" +
                "The PaymentIntent’s state was incompatible with the operation you were trying to perform.\n" +
                "The charge cannot be created as the payment method used has not been activated. Activate the payment method in the Dashboard, then try again.\n" +
                "The provided payment method’s state was incompatible with the operation you were trying to perform. Confirm that the payment method is in an allowed state for the given operation before attempting to perform it.\n" +
                "Payouts have been disabled on the connected account. Check the connected account’s status to see if any additional information needs to be provided, or if payouts have been disabled for another reason.\n" +
                "The API key provided by your Connect platform has expired. This occurs if your platform has either generated a new key or the connected account has been disconnected from the platform. Obtain your current API keys from the Dashboard and update your integration, or reach out to the user and reconnect the account.\n" +
                "The ZIP code provided was incorrect.\n" +
                "An error occurred while processing the card. Try again later or with a different payment method.\n" +
                "Too many requests hit the API too quickly. We recommend an exponential backoff of your requests.\n" +
                "A resource with a user-specified ID (e.g., plan or coupon) already exists. Use a different, unique value for id and try again.\n" +
                "The ID provided is not valid. Either the resource does not exist, or an ID for a different resource has been provided.\n" +
                "The bank routing number provided is invalid.\n" +
                "The API key provided is a publishable key, but a secret key is required. Obtain your current API keys from the Dashboard and update your integration to use them.\n" +
                "Your account does not support SEPA payments.\n" +
                "The latest setup attempt for the SetupIntent has failed. Check the last_setup_error property on the SetupIntent for more details, and provide a new payment method to attempt to set it up again.\n" +
                "The provided payment method has failed authentication. Provide a new payment method to attempt to fulfill this SetupIntent again.\n" +
                "One or more provided parameters was not allowed for the given operation on the SetupIntent. Check our API reference or the returned error message to see which values were not correct for that SetupIntent.\n" +
                "The SetupIntent’s state was incompatible with the operation you were trying to perform.\n" +
                "Occurs when providing the legal_entity information for a U.S. custom account, if the provided state is not supported. (This is mostly associated states and territories.)\n" +
                "Your account has not been activated and can only make test charges. Activate your account in the Dashboard to begin processing live charges.\n" +
                "Your integration is using an older version of TLS that is unsupported. You must be using TLS 1.2 or above.\n" +
                "The requested transfer cannot be created. Contact us if you are receiving this error.\n" +
                "The order could not be created. Check the order details and then try again.\n" +
                "The URL provided is invalid.";
        List<String> codeList = Arrays.asList(code.split("\n"));
        List<String> msgList = Arrays.asList(msg.split("\n"));
        List<String> newMsgList = Arrays.asList(newMsg.split("\n"));
        Map<String,String> newMsgList2 = new HashMap<>();

        newMsgList.forEach(s -> {
            String s1 = s.replaceAll(" ","").replaceAll(" ","");
            newMsgList2.put(s1,s1);
        });



//        int i = 0;
//        for (int j = 0; j < msgList.size(); j++) {
//            String s = msgList.get(j).replaceAll(" ","");
//            if (newMsgList2.containsKey(s)){
//                i++;
//            }
//        }

        Map<String,String> codeAndMsgMap = new LinkedHashMap<>();

        for (int i = 0; i < msgList.size(); i++) {
            String s = msgList.get(i).replaceAll(" ","").replaceAll(" ","");
            codeAndMsgMap.put(s,codeList.get(i));
        }
        List<String> removeKey = new ArrayList<>();

        int j = 0;
        for (String key : codeAndMsgMap.keySet()){
            String s = key.replaceAll(" ","").replaceAll(" ","");
            if (newMsgList2.containsKey(s)){
                j++;
            }else {
//                System.out.println(key);
                removeKey.add(key);
            }
        }

        removeKey.forEach(s -> {
            codeAndMsgMap.remove(s);
        });


        Map<String,String> resultMap = new LinkedHashMap<>();

        newMsgList.forEach(s->{

            resultMap.put(s,codeAndMsgMap.get(getSubString(s)));
        });

        for (String value : resultMap.keySet()){

            System.out.println(resultMap.get(value));
        }


        System.out.println("1");

    }

    private static String getSubString(String s){

        return s.replaceAll(" ","").replaceAll(" ","");
    }
}
