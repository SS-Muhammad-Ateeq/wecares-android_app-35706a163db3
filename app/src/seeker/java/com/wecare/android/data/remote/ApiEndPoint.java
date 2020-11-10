package com.wecare.android.data.remote;

import com.wecare.android.BuildConfig;

public class ApiEndPoint extends CommonApiEndPoint {

    public static final String ENDPOINT_SERVER_LOGIN = BuildConfig.BASE_SEEKER_URL
            + "/user/login";

    public static final String ENDPOINT_LOGOUT = BuildConfig.BASE_SEEKER_URL
            + "/user/logout";
    public static final String ENDPOINT_REGISTER_CAREGIVER = BuildConfig.BASE_SEEKER_URL
            + "/user/register";
    public static final String ENDPOINT_SUBMIT_VERIFICATION_CODE = BuildConfig.BASE_SEEKER_URL
            + "/user/code-verification";
    public static final String ENDPOINT_RESEND_VERIFICATION_CODE = BuildConfig.BASE_SEEKER_URL
            + "/user/send-verification-code";

    public static final String ENDPOINT_GET_USER = BuildConfig.BASE_SEEKER_URL
            + "/user/get";

    public static final String ENDPOINT_UPLOAD_USER_AVATARS = BuildConfig.BASE_SEEKER_URL
            + "/user/avatars";

    public static final String ENDPOINT_RESET_PASS_EMAIL = BuildConfig.BASE_SEEKER_URL
            + "/user/resetpassword-by-email";

    public static final String ENDPOINT_RESET_PASS_PHONE = BuildConfig.BASE_SEEKER_URL
            + "/user/resetpassword-by-phone-number";

    public static final String ENDPOINT_GOOGLE_LOGIN = BuildConfig.BASE_SEEKER_URL
            + "/user/google";

    public static final String ENDPOINT_FACEBOOK_LOGIN = BuildConfig.BASE_SEEKER_URL
            + "/user/facebook";

    public static final String ENDPOINT_TWITTER_LOGIN = BuildConfig.BASE_SEEKER_URL
            + "/user/twitter";

    public static final String ENDPOINT_GET_ORDERS = BuildConfig.BASE_SEEKER_URL
            + "/orders/get-orders";

    public static final String ENDPOINT_GET_ORDER_DETAILS = BuildConfig.BASE_SEEKER_URL
            + "/orders/get-order-details";


    public static final String ENDPOINT_RELATIVE_PROFILE = BuildConfig.BASE_SEEKER_URL
            + "/profile/index";

    public static final String ENDPOINT_CREATE_RELATIVE_PROFILE = BuildConfig.BASE_SEEKER_URL
            + "/profile/create";

    public static final String ENDPOINT_UPDATE_RELATIVE_PROFILE = BuildConfig.BASE_SEEKER_URL
            + "/profile/update";

    public static final String ENDPOINT_DELETE_RELATIVE_PROFILE = BuildConfig.BASE_SEEKER_URL
            + "/profile/delete";

    public static final String ENDPOINT_GET_SPECIFIC_RELATIVE_PROFILE = BuildConfig.BASE_SEEKER_URL
            + "/profile/view";

    public static final String ENDPOINT_GET_MY_SEEKER_PROFILE = BuildConfig.BASE_SEEKER_URL
            + "/user/get";

    public static final String ENDPOINT_SET_MY_SEEKER_PROFILE = BuildConfig.BASE_SEEKER_URL
            + "/user/set";

    public static final String ENDPOINT_GET_RECOMMENDED_CAREGIVERS = BuildConfig.BASE_SEEKER_URL
            + "/orders/get-recommended-caregivers";

    public static final String ENDPOINT_GET_FAVOURITE_CAREGIVERS = BuildConfig.BASE_SEEKER_URL
            + "/orders/get-favorite-caregivers";

    public static final String ENDPOINT_GET_SEARCH_FOR_CAREGIVERS = BuildConfig.BASE_SEEKER_URL
            + "/orders/search-for-caregiver";

    public static final String ENDPOINT_GET_ALL_USER_FAVOURITE_CAREGIVERS = BuildConfig.BASE_SEEKER_URL
            + "/favourites/index";

    public static final String ENDPOINT_GET_ALL_USER_BLOCKED_CAREGIVERS = BuildConfig.BASE_SEEKER_URL
            + "/block-caregiver/index";

    public static final String ENDPOINT_GET_ALL_USER_SEARCH_FOR_CAREGIVERS = BuildConfig.BASE_SEEKER_URL
            + "/user/search-for-caregiver";

    public static final String ENDPOINT_GET_CAREGIVER_PROFILE = BuildConfig.BASE_SEEKER_URL
            + "/user/caregiver-profile";

    public static final String ENDPOINT_GET_LOCATION = BuildConfig.BASE_SEEKER_URL
            + "/location/index";

    public static final String ENDPOINT_CREATE_LOCATION = BuildConfig.BASE_SEEKER_URL
            + "/location/create";

    public static final String ENDPOINT_UPDATE_LOCATION = BuildConfig.BASE_SEEKER_URL
            + "/location/update";

    public static final String ENDPOINT_DELETE_LOCATION = BuildConfig.BASE_SEEKER_URL
            + "/location/delete";

    public static final String ENDPOINT_CREATE_ORDER = BuildConfig.BASE_SEEKER_URL
            + "/orders/create";

    public static final String ENDPOINT_UPLOAD_ORDER_IMAGES = BuildConfig.BASE_SEEKER_URL
            + "/order-images/create";

    public static final String ENDPOINT_GET_NOTIFICATION = BuildConfig.BASE_SEEKER_URL
            + "/notifications/index";

    public static final String ENDPOINT_DELETE_NOTIFICATION = BuildConfig.BASE_SEEKER_URL
            + "/notifications/delete";

    public static final String ENDPOINT_NOTIFICATION_COUNT = BuildConfig.BASE_SEEKER_URL
            + "/notifications/get-unread-count";

    public static final String ENDPOINT_MARK_NOTIFICATION_READ = BuildConfig.BASE_SEEKER_URL
            + "/notifications/mark-all-as-read";

    public static final String ENDPOINT_SETTINGS_CHANGE_PASSWORD = BuildConfig.BASE_SEEKER_URL
            + "/user/changepassword";

    public static final String ENDPOINT_SETTINGS_NOTIFICATIONS = BuildConfig.BASE_SEEKER_URL
            + "/settings/notifications";

    public static final String ENDPOINT_CANCEL_ORDER = BuildConfig.BASE_SEEKER_URL
            + "/orders/cancel-order";

    public static final String ENDPOINT_RATE_ORDER = BuildConfig.BASE_SEEKER_URL
            + "/rating/create";

    public static final String ENDPOINT_WALLET = BuildConfig.BASE_SEEKER_URL
            + "/wallet/index";

    public static final String ENDPOINT_GET_INSURANCE_COMPANY = BuildConfig.BASE_SEEKER_URL
            + "/insurance/index";

    public static final String ENDPOINT_TRANSACTIONS = BuildConfig.BASE_SEEKER_URL
            + "/transactions/index";

}
