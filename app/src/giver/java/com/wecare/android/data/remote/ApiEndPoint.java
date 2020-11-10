package com.wecare.android.data.remote;

import com.wecare.android.BuildConfig;

public class ApiEndPoint extends CommonApiEndPoint {

    public static final String ENDPOINT_SERVER_LOGIN = BuildConfig.BASE_GIVER_URL
            + "/user/login";
    public static final String ENDPOINT_LOGOUT = BuildConfig.BASE_GIVER_URL
            + "/user/logout";
    public static final String ENDPOINT_REGISTER_CAREGIVER = BuildConfig.BASE_GIVER_URL
            + "/user/register";
    public static final String ENDPOINT_SUBMIT_VERIFICATION_CODE = BuildConfig.BASE_GIVER_URL
            + "/user/code-verification";
    public static final String ENDPOINT_RESEND_VERIFICATION_CODE = BuildConfig.BASE_GIVER_URL
            + "/user/send-verification-code";
    public static final String ENDPOINT_RESET_PASS_EMAIL = BuildConfig.BASE_GIVER_URL
            + "/user/resetpassword-by-email";
    public static final String ENDPOINT_RESET_PASS_PHONE = BuildConfig.BASE_GIVER_URL
            + "/user/resetpassword-by-phone-number";
    public static final String ENDPOINT_GOOGLE_LOGIN = BuildConfig.BASE_GIVER_URL
            + "/user/google";
    public static final String ENDPOINT_FACEBOOK_LOGIN = BuildConfig.BASE_GIVER_URL
            + "/user/facebook";
    public static final String ENDPOINT_GET_USER = BuildConfig.BASE_GIVER_URL
            + "/user/get";
    public static final String ENDPOINT_CURRENT_ORDER = BuildConfig.BASE_GIVER_URL
            + "/orders/current";
    public static final String ENDPOINT_PREVIOUS_ORDER = BuildConfig.BASE_GIVER_URL
            + "/orders/previous";
    public static final String ENDPOINT_CREATE_SCHEDULE = BuildConfig.BASE_GIVER_URL
            + "/schedule/create";
    public static final String ENDPOINT_GET_USER_SCHEDULE = BuildConfig.BASE_GIVER_URL
            + "/schedule/index";
    public static final String ENDPOINT_GET_ALL_USER_SERVICES = BuildConfig.BASE_GIVER_URL
            + "/user-service/all-services";
    public static final String ENDPOINT_ADD_SERVICE = BuildConfig.BASE_GIVER_URL
            + "/user-service/add";
    public static final String ENDPOINT_DELETE_SERVICE = BuildConfig.BASE_GIVER_URL
            + "/user-service/delete";
    public static final String ENDPOINT_EDIT_SERVICE = BuildConfig.BASE_GIVER_URL
            + "/user-service/edit";
    public static final String ENDPOINT_ADD_LANGUAGE = BuildConfig.BASE_GIVER_URL
            + "/languages/create";
    public static final String ENDPOINT_DELETE_LANGUAGE = BuildConfig.BASE_GIVER_URL
            + "/languages/delete";
    public static final String ENDPOINT_ADD_EDUCATION = BuildConfig.BASE_GIVER_URL
            + "/education/create";
    public static final String ENDPOINT_UPDATE_EDUCATION = BuildConfig.BASE_GIVER_URL
            + "/education/update";
    public static final String ENDPOINT_GET_EDUCATION = BuildConfig.BASE_GIVER_URL
            + "/education/index";
    public static final String ENDPOINT_DELETE_EDUCATION = BuildConfig.BASE_GIVER_URL
            + "/education/delete";
    public static final String ENDPOINT_ADD_EXPERIENCE = BuildConfig.BASE_GIVER_URL
            + "/experience/create";
    public static final String ENDPOINT_UPDATE_EXPERIENCE = BuildConfig.BASE_GIVER_URL
            + "/experience/update";
    public static final String ENDPOINT_GET_EXPERIENCE = BuildConfig.BASE_GIVER_URL
            + "/experience/index";
    public static final String ENDPOINT_DELETE_EXPERIENCE = BuildConfig.BASE_GIVER_URL
            + "/experience/delete";
    public static final String ENDPOINT_ADD_MAL_INSURANCE = BuildConfig.BASE_GIVER_URL
            + "/malpractice-insurance/create";
    public static final String ENDPOINT_UPDATE_MAL_INSURANCE = BuildConfig.BASE_GIVER_URL
            + "/malpractice-insurance/update";
    public static final String ENDPOINT_GET_MAL_INSURANCE = BuildConfig.BASE_GIVER_URL
            + "/malpractice-insurance/index";
    public static final String ENDPOINT_DELETE_MAL_INSURANCE = BuildConfig.BASE_GIVER_URL
            + "/malpractice-insurance/delete";
    public static final String ENDPOINT_GET_INSURANCE_COMPANIES = BuildConfig.BASE_GIVER_URL
            + "/insurance-companies/view";
    public static final String ENDPOINT_UPDATE_USER = BuildConfig.BASE_GIVER_URL
            + "/user/set";
    public static final String ENDPOINT_GET_LANGUAGES = BuildConfig.BASE_GIVER_URL
            + "/languages/index";
    public static final String ENDPOINT_UPLOAD_ATTACHMENT = BuildConfig.BASE_GIVER_URL
            + "/attachment/create";
    public static final String ENDPOINT_GET_ATTACHMENT = BuildConfig.BASE_GIVER_URL
            + "/attachment/get";
    public static final String ENDPOINT_GET_ATTACHMENTS = BuildConfig.BASE_GIVER_URL
            + "/attachment/index";
    public static final String ENDPOINT_DELETE_ATTACHMENT = BuildConfig.BASE_GIVER_URL
            + "/attachment/delete";
    public static final String ENDPOINT_UPLOAD_CERTIFICATE = BuildConfig.BASE_GIVER_URL
            + "/certificate/create";
    public static final String ENDPOINT_GET_CERTIFICATES = BuildConfig.BASE_GIVER_URL
            + "/certificate/index";
    public static final String ENDPOINT_GET_CERTIFICATE = BuildConfig.BASE_GIVER_URL
            + "/certificate/get";
    public static final String ENDPOINT_DELETE_CERTIFICATE = BuildConfig.BASE_GIVER_URL
            + "/certificate/delete";
    public static final String ENDPOINT_GET_BANK_INFO = BuildConfig.BASE_GIVER_URL
            + "/bank-info/index";
    public static final String ENDPOINT_SET_BANK_INFO = BuildConfig.BASE_GIVER_URL
            + "/bank-info/set";
    public static final String ENDPOINT_DELETE_BANK_INFO = BuildConfig.BASE_GIVER_URL
            + "/bank-info/delete";
    public static final String ENDPOINT_USER_PROFILE_PICTURE = BuildConfig.BASE_GIVER_URL
            + "/user/avatars";


    public static final String ENDPOINT_GET_LOCATION = BuildConfig.BASE_GIVER_URL
            + "/location/get";
    public static final String ENDPOINT_SET_LOCATION = BuildConfig.BASE_GIVER_URL
            + "/location/set";


    //TODO
    //invoke the following API's
    public static final String ENDPOINT_GET_SETTINGS = BuildConfig.BASE_GIVER_URL
            + "/settings/index";
    public static final String ENDPOINT_SETTINGS_HOLD_SERVICE = BuildConfig.BASE_GIVER_URL
            + "/settings/hold-service";
    public static final String ENDPOINT_SETTINGS_NOTIFICATIONS = BuildConfig.BASE_GIVER_URL
            + "/settings/notifications";
    public static final String ENDPOINT_SETTINGS_LANGUAGE = BuildConfig.BASE_GIVER_URL
            + "/settings/language";
    public static final String ENDPOINT_SETTINGS_CHANGE_PASSWORD = BuildConfig.BASE_GIVER_URL
            + "/user/changepassword";
    public static final String ENDPOINT_REQUEST_TO_ACTIVATE = BuildConfig.BASE_GIVER_URL
            + "/user/request-to-activate";

    public static final String ENDPOINT_GET_ORDERS = BuildConfig.BASE_GIVER_URL
            + "/orders/get-orders";
    public static final String ENDPOINT_GET_ORDER_DETAILS = BuildConfig.BASE_GIVER_URL
            + "/orders/get-order-details";

    public static final String ENDPOINT_GET_ORDER_SERVICES_FOR_CAREGIVER = BuildConfig.BASE_GIVER_URL
            + "/orders/get-sub-services";
    public static final String ENDPOINT_CHANGE_ORDER_STATUS = BuildConfig.BASE_GIVER_URL
            + "/orders/change-order-status";
    public static final String ENDPOINT_CANCEL_ORDER = BuildConfig.BASE_GIVER_URL
            + "/orders/cancel-order";
    public static final String ENDPOINT_REJECT_ORDER = BuildConfig.BASE_GIVER_URL
            + "/orders/reject-order";
    public static final String ENDPOINT_FINISH_ORDER = BuildConfig.BASE_GIVER_URL
            + "/orders/finish-order";

    public static final String ENDPOINT_RATE_ORDER = BuildConfig.BASE_GIVER_URL
            + "/rating/create";

    public static final String ENDPOINT_WALLET = BuildConfig.BASE_GIVER_URL
            + "/wallet/index";
    public static final String ENDPOINT_TRANSACTIONS = BuildConfig.BASE_GIVER_URL
            + "/transactions/index";



    public static final String ENDPOINT_GET_NOTIFICATION = BuildConfig.BASE_GIVER_URL
            + "/notifications/index";
    public static final String ENDPOINT_DELETE_NOTIFICATION = BuildConfig.BASE_GIVER_URL
            + "/notifications/delete";
    public static final String ENDPOINT_NOTIFICATION_COUNT = BuildConfig.BASE_GIVER_URL
            + "/notifications/get-unread-count";
    public static final String ENDPOINT_MARK_NOTIFICATION_READ = BuildConfig.BASE_GIVER_URL
            + "/notifications/mark-all-as-read";

}
