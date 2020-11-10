package com.wecare.android.utils;

public class ServerKeys {

    public static String AUTH_USER = "weCareMaster";
    public static String AUTH_PASS = "M@$teR-2o!9";

    public static final String KEY_TERMS_CONDITIONS_URL = "http://new.api.wecares.info/page/terms-of-service";
    public static final String KEY_PRIVACY_POLICY_URL = "http://new.api.wecares.info/page/privacy-policy";
    public static final String KEY_ABOUT_US_URL = "http://new.api.wecares.info/page/about-us";


    //json parsing constants
    public static final String SUCCESS = "success";
    public static final String DATA = "data";
    public static final String MESSAGE = "message";
    public static final String ERRORS = "errors";
    public static final String ERROR_CODE = "code";

    public static final String PARAM_ACCEPT = "Accept";
    public static final String PARM_CONTENT_TYPE = "Content-Type";
    public static final String PARM_CONTENT_TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded";
    public static final String PARM_CONTENT_TYPE_IMAGE = "image/png";
    public static final String PARM_CONTENT_TYPE_JSON = "application/json";
    public static final String PARM_CONTENT_TYPE_JSON_UTF = "Application/json; charset=UTF-8";
    public static final String PARM_CONTENT_TYPE_ALL = "application/json, text/plain, */*";
    public static final String PARM_CONTENT_LENGTH = "Content-Length";


    public static final String PARAM_PASSWORD_CODE = "reset_password_code";
    public static final String PARAM_CODE = "code";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_PASSWORD_REPEAT = "password_repeat";
    public static final String PARAM_PASSWORD_NEW = "password_new";

    //social login
    public static final String PARAM_SECRET_KEY = "secret_key";
    public static final String PARAM_ACCESS_TOKEN = "access_token";

    public static final String PARAM_EMAIL = "email";

    public static final String PARAM_STATUS = "status";
    public static final String PARAM_OFFSET = "offset";
    public static final String PARAM_LIMIT = "limit";
    public static final String PARAM_EXPAND = "expand";
    public static final String PARAM_ORDER_ID = "order_id";


    public static final String PARAM_CERTIFICATE = "certificate";
    public static final String PARAM_TITLE = "title";
    public static final String PARAM_FILE = "file";
    public static final String PARAM_CATEGORY_ID = "category_id";
    public static final String PARAM_ATTACHMENT = "attachment";
    public static final String PARAM_AVATAR_IMAGE = "avatar_image";


    public static final String PARAM_PHONE_NUMBER = "phone_number";
    public static final String PARAM_COUNTRY_CODE = "country_code";
    public static final String PARAM_COUNTRY_ID = "country_id";
    public static final String PARAM_ID = "id";
    public static final String PARAM_ACTIVE = "active";

    public static final String PARAM_LANGUAGE = "Language";


    public static final String PARAM_VERIFICATION_CODE = "verification_code";

    public interface CreateRelativeProfileParams {
        String PARAM_ID = "id";
        String PARAM_NAME = "name";
        String PARAM_RELATIONSHIP = "relationship";
        String PARAM_MOBILE_NUMBER = "mobile_number";
        String PARAM_COUNTRY_CODE = "country_code";
        String PARAM_GENDER = "gender";
        String PARAM_AGE = "age";
        String PARAM_BLOOD_TYPE = "blood_type";
        String PARAM_WEIGHT = "weight";
        String PARAM_LENGTH = "length";
        String PARAM_HEALTH_INSURANCE = "health_insurance";
        String PARAM_BLOOD_PRESSURE = "blood_pressure";
        String PARAM_CHRONIC_DISEASES = "chronic_diseases";

        String PARAM_USER_PROFILE_OBJECT = "user_profile";
        String PARAM_PAYMENT_METHOD = "payment_method";///////why
        String PARAM_INSURANCE_COMPANY = "insurance_company";
        String PARAM_INSURANCE_NUMBER = "insurance_number";
        String PARAM_TYPE_OF_INSURANCE = "type_of_insurance";
        String PARAM_INSURANCE_EXPIRATION_DATE = "insurance_expiration_date";

        String PARAM_CITY = "city";
        String PARAM_PHONE_NUMBER = "phone_number";
        String PARAM_FIRST_NAME = "first_name";
        String PARAM_MIDDLE_NAME = "middle_name";
        String PARAM_LAST_NAME = "last_name";
        String PARAM_NATIONALITY = "nationality";
        String PARAM_NATIONAL_ID = "national_id";
        String PARAM_COUNTRY_ID_OF_SERVICE = "country_id";
        String PARAM_BIRTH_DATE = "birth_date";
    }

    public interface RecommendedCaregiversFilters {
        String PARAM_WECARE_ID = "wecare_id";
        String PARAM_AGE = "caregiver_age";
        String PARAM_GENDER_ID = "caregiver_gender_id";
        String PARAM_CAREGIVER_LANGUAGE_ID = "caregiver_language_id";
        String PARAM_DATE = "date";
        String PARAM_START_TIME = "start_time";
        String PARAM_DESCRIPTION = "description";
        String PARAM_PAYMENT_METHOD = "payment_method";
        String PARAM_PROFILE_ID = "profile_id";
        String PARAM_LOCATION_ID = "location_id";
        String PARAM_SERVICES = "services";
        String PARAM_MAIN_SERVICE_ID = "main_service_id";
        String PARAM_SUB_SERVICE_ID = "sub_service_id";
        String PARAM_SUB_SERVICE_TYPE = "sub_service_type";
        String PARAM_CAREGIVER_SERVICE_ID = "caregiver_service_id";
        String PARAM_QUANTITY = "quantity";
        String PARAM_HOURS = "hours";

        String PARAM_SORT_YEARS_OF_EXPERIENCE = "sort_years_of_experience";
        String PARAM_SORT_RATING = "sort_rating";
        String PARAM_SORT_PRICE = "sort_price";
        String PARAM_SORT_AGE = "sort_age";
        String PARAM_SORT_GENDER = "sort_gender";

    }

    public interface CreateOrderParams {
        String PARAM_CAREGIVER_ID = "caregiver_id";
        String PARAM_CAREGIVER_AGE = "caregiver_age";
        String PARAM_CAREGIVER_GENDER_ID = "caregiver_gender_id";
        String PARAM_CAREGIVER_LANGUAGE_ID = "caregiver_language_id";
        String PARAM_DATE = "date";
        String PARAM_START_TIME = "start_time";
        String PARAM_DESCRIPTION = "description";
        String PARAM_PAYMENT_METHOD = "payment_method";
        String PARAM_PROFILE_ID = "profile_id";
        String PARAM_LOCATION_ID = "location_id";
        String PARAM_PHONE_NUMBER = "phone_number";
        String PARAM_COUNTRY_CODE = "country_code";
        String PARAM_NEED_MATERIALS = "need_materials";
        String PARAM_MATERIALS_NEEDED = "materials_description";
        String PARAM_IMAGES = "images";
        String PARAM_SERVICES = "services";
        String PARAM_MAIN_SERVICE_ID = "main_service_id";
        String PARAM_SUB_SERVICE_ID = "sub_service_id";
        String PARAM_SUB_SERVICE_TYPE = "sub_service_type";
        String PARAM_QUANTITY = "quantity";
        String PARAM_HOURS = "hours";
    }

    public interface UserLocationParams {
        /*
                {
                    "name":"address 1",
                        "city":"41521",
                        "country":"1",
                        "building_number":"322",
                        "floor_number":"1",
                        "area":"Tla Al Ali",
                        "street_name":"2332444",
                        "latitude":"25.413509",
                        "longitude":"45.029665"
                }
        */
        String PARAM_ID = "id";
        String PARAM_NAME = "name";
        String PARAM_CITY = "city";
        String PARAM_COUNTRY_ID = "country_id";
        String PARAM_BUILDING_NUMBER = "building_number";
        String PARAM_FLOOR_NUMBER = "floor_number";
        String PARAM_AREA = "area";
        String PARAM_STREET_NAME = "street_name";
        String PARAM_LATITUDE = "latitude";
        String PARAM_LONGITUDE = "longitude";
    }

}
