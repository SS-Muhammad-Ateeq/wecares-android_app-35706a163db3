
package com.wecare.android.utils;

import androidx.annotation.IntDef;

/**
 * Created by amitshekhar on 07/07/17.
 */

public final class AppConstants {


    private AppConstants() {
        // This utility class is not publicly instantiable
    }

    public static final String STATUS_CODE_SUCCESS = "success";
    public static final String STATUS_CODE_FAILED = "failed";

    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final String DB_NAME = "mindorks_mvvm.db";
    public static final String PREF_NAME = "mindorks_pref";

    public static final long NULL_INDEX = -1L;

    public static final String LANGUAGE = "Language";
    public static final String LANGUAGE_LOCALE_ENGLISH = "en";
    public static final String LANGUAGE_LOCALE_ARABIC = "ar";

    public static final String SEED_DATABASE_OPTIONS = "seed/options.json";
    public static final String SEED_DATABASE_QUESTIONS = "seed/questions.json";

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";

    public static final String DATE_FORMAT_DISPLAY = "yyyy-MM-dd";


    public static final String ARGS_IS_EMAIL = "isEmail";


    public static final String PHP_TRUE = "1";
    public static final String PHP_FALSE = "0";


    public static final String SERVICE_STATUS_NOT_VERIFIED = "1";
    public static final String SERVICE_STATUS_UNDER_REVIEW = "2";
    public static final String SERVICE_STATUS_VERIFIED  = "3";
    public static final String SERVICE_STATUS_REJECTED  = "4";
    public static final String SERVICE_STATUS_INACTIVE  = "5";



    public static final int PHP_TRUE_RAW = 1;
    public static final int PHP_FALSE_RAW = 0;


    public static final int SERVICE_TYPE_QUANTITY = 1;
    public static final int SERVICE_TYPE_HOURS = 2;


    public static final int ATTACHMENT_CAMERA = 100;
    public static final int ATTACHMENT_GALLERY = 200;
    public static final int CERTIFICATE_CAMERA = 300;
    public static final int CERTIFICATE_GALLERY = 400;


    public static final int PROFILE_IMAGE = 500;
    public static final int IDENTITY_IMAGE = 600;



    public static final int ORDERS_PAGINATION_LIMIT = 10;


    public static final int PERMISSION_ACCESS_GO_TO_MY_LOCATION = 10;
    public static String CURRENT_LOCALE = "";

    //Fragment arguments keys
    public static final String ARGS_KEY_HTML_PAGG_URI = "REQ_ACC_HTML_PAGG_URI";
    public static final String ARGS_KEY_MODEL_CLASS = "CLASS_MODEL_FRG_ARGS";
    public static final String ARGS_KEY_MODEL_LIST = "LIST_MODEL_FRG_ARGS";
    public static final String ARGS_KEY_NO_DATA_MSG = "NO_DATA_MSG_FRG_ARGS";
    public static final String ARGS_KEY_TITLE = "title";
    public static final String ARGS_KEY_MUST_ACCEPT = "ARGS_KEY_MUST_ACCEPT";

    public static final String ARGS_KEY_URL = "url";
    public static final String ARGS_KEY_TYPE = "TYPE_KEY_ARGS";
    public static final String ARGS_KEY_CONTENT = "CONTENT_ARGS_KEY";
    public static final String ARGS_KEY_PROFILE_ID = "ARGS_KEY_PROFILE_ID";
    public static final String ARGS_KEY_CAREGIVER_ID = "ARGS_KEY_CAREGIVER_ID";

    //social account flow
    public static final String ARGS_KEY_IS_REGISTRATION_USING_SOCIAL = "IS_REGISTRATION_USING_SOCIAL_ARGS_KEY";
    public static final String ARGS_KEY_SOCIAL_FACEBOOK_HASH = "SOCIAL_FACEBOOK_HASH_ARGS_KEY";
    public static final String ARGS_KEY_SOCIAL_TWITTER_HASH = "SOCIAL_TWITTER_HASH_KEY";
    public static final String ARGS_KEY_SOCIAL_GOOGLE_HASH = "SOCIAL_GOOGLE_HASH_ARGS_KEY";
    //services
    public static final String ARGS_KEY_SERVICES = "SERVICES_ARGS_KEY";
    public static final String ARGS_KEY_SUB_SERVICES = "SUB_SERVICES_ARGS_KEY";

    public static final String ARGS_KEY_SERVICES_PICKING_TYPE = "ARGS_KEY_SERVICES_PICKING_TYPE";
    public static final String ARGS_KEY_CAREGIVER_SERVICES = "ARGS_KEY_CAREGIVER_SERVICES";
    public static final String ARGS_KEY_ORDER_SERVICES = "ARGS_KEY_ORDER_SERVICES";

    public static final String ARGS_KEY_SUB_SERVICES_PICKING_TYPE = "ARGS_KEY_SERVICES_PICKING_TYPE";
    public static final String ARGS_KEY_SUB_SERVICES_EDIT = "ARGS_KEY_SUB_SERVICES_EDIT";
    public static final String ARGS_KEY_SUB_SERVICES_CREATE = "ARGS_KEY_SUB_SERVICES_CREATE";

    public static final String ARGS_KEY_LOCATION_TO_BE_EDITED_OBJECT = "LOCATION_TO_BE_EDITED_OBJECT";
    public static final String ARGS_KEY_LOCATION_OBJECT = "LOCATION_OBJECT";
    public static final String ARGS_KEY_ORDER_ID = "ORDER_ID";

    public static final String ARGS_USER_SERVICES_ACTIVE = "active";
    public static final String ARGS_USER_SERVICES_INACTIVE = "Inactive";

    public static final String ACTIVE_COUNTRIES = "1";
    public static final String NON_ACTIVE_COUNTRIES = "0";


    public static final String ARGS_SELECTED_RELATIVE_PROFILE = "SELECTED_RELATIVE_PROFILE";
    public static final String ARGS_SELECTED_ORDER_SUB_SERVICE_LIST = "SELECTED_ORDER_SUB_SERVICE_LIST";

    public static final String ARGS_KEY_FILTER_GIVER_OBJECT = "FILTER_GIVER_OBJECT";
    public static final String ARGS_KEY_EDITED_FILTER_GIVER_OBJECT = "EDITED_FILTER_GIVER_OBJECT";
    public static final String ARGS_SELECTED_GIVER_PROFILE = "SELECTED_GIVER_PROFILE";

    //schudle services
    public static final String SAME_TIME_CARES_ALL_DAYS = "SAME_TIME_CARES_ALL_DAYS";
    public static final String DIFFERENT_TIME_CARES_ALL_DAYS = "DIFFERENT_TIME_CARES_ALL_DAYS";

    //request code
    public static final int REQUEST_LOCATION_SETTING_RESULT = 12097;
    public static final int REQUEST_LOCATION_PERMISSION = 12098;
    public static final int REQUEST_LOCATION_PERMISSION_BUTTON = 12099;
    public static final int REQ_CODE_RELATIVE_PROFILE = 12101;
    public static final int REQ_CODE_ORDER_INFORMATION_PIN_IMAGE = 12102;
    public static final int REQ_CODE_ORDER_SUB_SERVICE_LIST = 12103;
    public static final int REQ_CODE_LOCATION_PLACE_PICKER = 12104;
    public static final int REQ_CODE_SEARCH_GIVER_ACTIVITY_PICK = 12105;
    public static final int REQ_CODE_EDITED_FILTER_GIVER_OBJECT_LIST = 12106;


    //orders status
    public static final String KEY_ORDERS_STATUS = "KEY_ORDERS_STATUS";
    public static final String KEY_ORDER_OBJECT = "KEY_ORDER_OBJECT";
    public static final String KEY_ORDER_REPORT = "KEY_ORDER_REPORT";

    public static final String KEY_WALLET_OBJECT = "KEY_WALLET_OBJECT";


    public static final String KEY_USER_OBJECT = "KEY_USER_OBJECT";

    public static final String KEY_USER_TYPE = "KEY_USER_TYPE";
    public static final String KEY_USER_CAREGIVER = "KEY_USER_CAREGIVER";
    public static final String KEY_USER_CARE_SEEKER = "KEY_USER_CARE_SEEKER";

    //caregiver status
    public static final int CAREGIVER_STATUS_INACTIVE = 1;
    public static final int CAREGIVER_STATUS_ACTIVE = 2;
    public static final int CAREGIVER_STATUS_UNDER_REVIEW = 3;
    public static final int CAREGIVER_STATUS_REJECTED = 4;

    public static final String KEY_BASE_RATING = "KEY_BASE_RATING";


    public static final String ORDERS_STATUS_PENDING = "pending";
    public static final String ORDERS_STATUS_ACCEPTED = "accepted";
    public static final String ORDERS_STATUS_PREVIOUS = "previous";
    public static final String ORDERS_STATUS_SCHEDULING = "scheduling";
    public static final String ORDERS_STATUS_CURRENT = "current";

    public static final String FIREBASE_EVENT_REGISTRATION = "successful_registration";
    public static final String FIREBASE_EVENT_VERIFICATION = "successful_code_verification";
    public static final String FACEBOOK_EVENT_REGISTRATION = "successful_registration";


    public static final int ORDER_STATUS_PENDING = 1;
    public static final int ORDER_STATUS_SEEKER_CANCELED = 2;

    public static final int ORDER_STATUS_REJECTED = 3;
    public static final int ORDER_STATUS_ACCEPTED = 4;
    public static final int ORDER_STATUS_CANCELED = 5;
    public static final int ORDER_STATUS_CARING = 6;
    public static final int ORDER_STATUS_COMPLETED = 7;
    public static final int ORDER_STATUS_FULFILLED = 8;
    public static final int ORDER_STATUS_COMPLETED_UNPAID = 9;

    public static final int PAYMENT_METHOD_CASH = 1;
    public static final int PAYMENT_METHOD_CREDIT = 2;

    public static final int PAYMENT_STATUS_WAITING_PAYMENT = 1;
    public static final int PAYMENT_STATUS_PAID = 2;
    public static final int PAYMENT_STATUS_PAYMENT_REJECTED = 3;
    public static final int PAYMENT_STATUS_PAYMENT_CANCELED = 4;



    //pages Id's
    public static final int PAGE_ABOUT_US_ENGLISH = 1;
    public static final int PAGE_ABOUT_US_ARABIC = 2;
    public static final int PAGE_PRIVACY_POLICY_ENGLISH = 4;
    public static final int PAGE_PRIVACY_POLICY_ARABIC = 6;
    public static final int SEEKER_TERMS_SERVICE_ARABIC = 7;
    public static final int SEEKER_TERMS_SERVICE_ENGLISH = 8;
    public static final int GIVER_TERMS_SERVICE_ARABIC = 9;
    public static final int GIVER_TERMS_SERVICE_ENGLISH = 10;




    public static final String NOTIFICATION_TYPE_ORDER_DETAILS = "order";
    public static final String NOTIFICATION_TYPE_SELECTED_SERVICES = "service_status";
    public static final String NOTIFICATION_TYPE_OPEN_PROFILE = "account_status";


    public static final String KEY_REGISTRATION_TYPE = "KEY_REGISTRATION_TYPE";
    public static final int REGISTRATION_TYPE_GIVER = 1;
    public static final int REGISTRATION_TYPE_SEEKER = 2;

    public static final String ARGS_VERIFY_USER = "ARGS_VERIFY_USER";



    // Enums Definations
    public static final int SLIDING_ANIMATION = 1;
    public static final int POPING_ANIMATION = 2;

    public static final int FACEBOOK = 1;
    public static final int TWITTER = 2;
    public static final int GOOGLE = 3;

    public static final int INTRO_FIRST = 1;
    public static final int INTRO_SECOND = 2;
    public static final int INTRO_THIRD = 3;
    public static final int INTRO_FOURTH = 4;

    public static final int EXIT_STEPPER = 10;
    public static final int BACK_STEPPER = 11;

    public static final int RATING_FIRST = 1;
    public static final int RATING_SECOND = 2;
    public static final int FINISH_RATING = 3;

    public static final int REGISTRATION_FIRST_STEP = 0;
    public static final int REGISTRATION_SECOND_STEP = 1;
    public static final int REGISTRATION_THIRD_STEP = 2;
    public static final int REGISTRATION_FORTH_STEP = 3;

    public static final int REGISTRATION_TERMS_CONDITIONS = 4;
    public static final int REGISTRATION_PRIVACY_POLICY = 5;


    public static final int ACCOUNT_TYPE_INDIVIDUAL = 101;
    public static final int ACCOUNT_TYPE_ORGANIZATION = 102;

    public static final int MY_LAST_LOCATION = 1;
    public static final int SPECIFIC_LOCATION_LOCATION = 2;


    public static final int MEMBERSHIP_FEES_PER_ORDER = 0;
    public static final int MEMBERSHIP_MID_TERM = 1;
    public static final int MEMBERSHIP_QUARTERLY = 2;
    public static final int MEMBERSHIP_YEARLY = 3;




    public static final int SUNDAY_INDEX = 1;
    public static final int SUNDAY_TIMESLOT_1_INDEX = 11;
    public static final int SUNDAY_TIMESLOT_2_INDEX = 12;
    public static final int MONDAY_INDEX = 2;
    public static final int MONDAY_TIMESLOT_1_INDEX = 21;
    public static final int MONDAY_TIMESLOT_2_INDEX = 22;
    public static final int TUESDAY_INDEX = 3;
    public static final int TUESDAY_TIMESLOT_1_INDEX = 31;
    public static final int TUESDAY_TIMESLOT_2_INDEX = 32;
    public static final int WEDNSDAY_INDEX = 4;
    public static final int WEDNSDAY_TIMESLOT_1_INDEX = 41;
    public static final int WEDNSDAY_TIMESLOT_2_INDEX = 42;
    public static final int THURSDAY_INDEX = 5;
    public static final int THURSDAY_TIMESLOT_1_INDEX = 51;
    public static final int THURSDAY_TIMESLOT_2_INDEX = 52;
    public static final int FRIDAY_INDEX = 6;
    public static final int FRIDAY_TIMESLOT_1_INDEX = 61;
    public static final int FRIDAY_TIMESLOT_2_INDEX = 62;
    public static final int SATURDAY_INDEX = 7;
    public static final int SATURDAY_TIMESLOT_1_INDEX = 71;
    public static final int SATURDAY_TIMESLOT_2_INDEX = 72;

    @IntDef({FACEBOOK, TWITTER, GOOGLE})
    public @interface SocialLoginType {
    }


    @IntDef({SLIDING_ANIMATION, POPING_ANIMATION})
    public @interface animationType {
    }

    @IntDef({INTRO_FIRST, INTRO_SECOND, INTRO_THIRD, INTRO_FOURTH, EXIT_STEPPER, BACK_STEPPER})
    public @interface IntroStepper {
    }

    @IntDef({REGISTRATION_FIRST_STEP, REGISTRATION_SECOND_STEP, EXIT_STEPPER})
    public @interface RegistrationStepper {
    }
}
