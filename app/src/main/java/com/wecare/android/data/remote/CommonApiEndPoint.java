package com.wecare.android.data.remote;

import com.wecare.android.BuildConfig;


public class CommonApiEndPoint {

    public static final String ENDPOINT_COUNTRIES = BuildConfig.BASE_WECARE_URL
            + "/countries";
    public static final String ENDPOINT_CITIES = BuildConfig.BASE_WECARE_URL
            + "/city";

    public static final String ENDPOINT_GENDERS = BuildConfig.BASE_WECARE_URL
            + "/genders/index";

    public static final String ENDPOINT_LOOKUPS = BuildConfig.BASE_WECARE_URL
            + "/common/lookups";

    public static final String ENDPOINT_MAIN_SERVICES = BuildConfig.BASE_WECARE_URL
            + "/main-services/services";

    public static final String ENDPOINT_STATISTICS = BuildConfig.BASE_WECARE_URL
            + "/common/statistics";
    public static final String ENDPOINT_SET_CAREGIVER_FAVORITE = BuildConfig.BASE_SEEKER_URL
            + "/favourites/create";
    public static final String ENDPOINT_UNFAVORITE_CAREGIVER_ = BuildConfig.BASE_SEEKER_URL
            + "/favourites/delete";

    public static final String ENDPOINT_BLOCK_GIVER = BuildConfig.BASE_SEEKER_URL
            + "/block-caregiver/block";
    public static final String ENDPOINT_UNBLOCK_GIVER = BuildConfig.BASE_SEEKER_URL
            + "/block-caregiver/unblock";

    public static final String ENDPOINT_BLOCK_SEEKER = BuildConfig.BASE_GIVER_URL
            + "/block-careseeker/block";
    public static final String ENDPOINT_UNBLOCK_SEEKER = BuildConfig.BASE_GIVER_URL
            + "/block-careseeker/unblock";
    public static final String ENDPOINT_GET_CAREGIVER_PROFILE = BuildConfig.BASE_SEEKER_URL
            + "/user/caregiver-profile";

    public static final String ENDPOINT_PAGES = BuildConfig.BASE_WECARE_URL
            + "/pages/index";

    public static final String ENDPOINT_GET_NATIONALITY = BuildConfig.BASE_WECARE_URL
            + "/countries/nationalities";

    public static final String ENDPOINT_MEMBERSHIP_TYPE = BuildConfig.BASE_GIVER_URL
            + "/membership-type/index";

}
