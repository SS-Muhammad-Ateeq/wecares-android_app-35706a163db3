package com.wecare.android.utils;

import com.google.gson.Gson;
import com.wecare.android.data.local.FilterObject;
import com.wecare.android.data.local.InformationAttachmentObj;
import com.wecare.android.data.model.api.models.CaregiverUser;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.responses.RelativeProfileResponse;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.data.model.api.responses.UserLocationResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONBuilderFlavour extends JSONBuilder {

    public static JSONObject getCommonRequestParams(Object model) {
        Gson gson = new Gson();
        JSONObject jsonObject;
        String jsonInString = gson.toJson(model);
        try {
            jsonObject = new JSONObject(jsonInString);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getCreateRelativeProfileParams(RelativeProfileResponse model, String profileID) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_NAME, model.getName());
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_MOBILE_NUMBER, model.getMobile_number());
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_COUNTRY_CODE, model.getCountry_code());
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_GENDER, model.getGender().getId());
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_AGE, model.getAge());
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_BLOOD_TYPE, model.getBlood_type());
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_WEIGHT, model.getWeight());
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_LENGTH, model.getLength());
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_BLOOD_PRESSURE, model.getBlood_pressure());
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_CHRONIC_DISEASES, model.getChronic_diseases());
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_RELATIONSHIP, model.getSelectedNewRelationShip());

            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_HEALTH_INSURANCE, model.getHealth_insurance());
            if (!model.getHealth_insurance().equals("0")) {
                jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_INSURANCE_COMPANY, model.getSelectedInsuranceCompany());
                jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_INSURANCE_NUMBER, model.getInsurance_number());
                jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_TYPE_OF_INSURANCE, model.getSelectedTypeOfInsurance());
                jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_INSURANCE_EXPIRATION_DATE, model.getInsurance_expiration_date());
            }
            if (!WeCareUtils.isNullOrEmpty(profileID)) {
                jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_ID, profileID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getUpdateUserProfileParams(RelativeProfileResponse model, String profileID) {
        JSONObject jsonObject = new JSONObject();
        JSONObject userProfileJsonObject = new JSONObject();
        try {
//            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_NAME, model.getName());
//            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_MOBILE_NUMBER, model.getMobile_number());
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_COUNTRY_CODE, model.getCountry_code());
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_GENDER, model.getGender().getId());
//            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_AGE, model.getAge()); // no age on relative profile
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_BIRTH_DATE, model.getBirthDate());

            userProfileJsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_BLOOD_TYPE, model.getBlood_type());
            userProfileJsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_WEIGHT, model.getWeight());
            userProfileJsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_LENGTH, model.getLength());
            userProfileJsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_BLOOD_PRESSURE, model.getBlood_pressure());
            userProfileJsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_CHRONIC_DISEASES, model.getChronic_diseases());
            userProfileJsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_RELATIONSHIP, model.getSelectedNewRelationShip());

            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_PHONE_NUMBER, model.getMobile_number());

            userProfileJsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_HEALTH_INSURANCE, model.getHealth_insurance());
            if (model.getHealth_insurance() != null && !model.getHealth_insurance().equals("0")) {
                userProfileJsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_INSURANCE_COMPANY, model.getSelectedInsuranceCompany());
                userProfileJsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_INSURANCE_NUMBER, model.getInsurance_number());
                userProfileJsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_TYPE_OF_INSURANCE, model.getSelectedTypeOfInsurance());
                userProfileJsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_INSURANCE_EXPIRATION_DATE, model.getInsurance_expiration_date());
            }

//            if (!WeCareUtils.isNullOrEmpty(model.getCountryOfServiceID()) && !WeCareUtils.isNullOrEmpty(model.getNationalityID())) {
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_FIRST_NAME, model.getFirstName());
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_MIDDLE_NAME, model.getMiddleName());
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_LAST_NAME, model.getLastName());
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_NATIONALITY, model.getNationalityID());
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_NATIONAL_ID, model.getNationalityNumberID());
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_COUNTRY_ID_OF_SERVICE, model.getCountryOfServiceID());
//            }
            jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_USER_PROFILE_OBJECT, userProfileJsonObject);

            if (!WeCareUtils.isNullOrEmpty(profileID)) {
                jsonObject.put(ServerKeys.CreateRelativeProfileParams.PARAM_ID, profileID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getRecommendedCaregiversParams(FilterObject filterObject) {
        JSONObject jsonObject = new JSONObject();
        try {

            if (!WeCareUtils.isNullOrEmpty(filterObject.getCaregiver_age())) {
                jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_AGE, filterObject.getCaregiver_age());//"1"
            }
            if (!WeCareUtils.isNullOrEmpty(filterObject.getCaregiver_gender_id())) {
                jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_GENDER_ID, filterObject.getCaregiver_gender_id());//"1"
            }
            if (!WeCareUtils.isNullOrEmpty(filterObject.getCaregiver_language_id())) {
                jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_CAREGIVER_LANGUAGE_ID, filterObject.getCaregiver_language_id());//"1"
            }
            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_DATE, filterObject.getDate());//"2019-04-27"
            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_START_TIME, filterObject.getStart_time());//"11:30:00"
            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_PAYMENT_METHOD, filterObject.getPayment_method());//"1"
            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_LOCATION_ID, filterObject.getLocation_id());//"1"
            if (filterObject.getProfile_id() != null)
                jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_PROFILE_ID, filterObject.getProfile_id());//"1"

            //sorting currently for suggested giver tab.
            if (!WeCareUtils.isNullOrEmpty(filterObject.getSortYearsOfExperience()))
                jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_SORT_YEARS_OF_EXPERIENCE, filterObject.getSortYearsOfExperience());

            if (!WeCareUtils.isNullOrEmpty(filterObject.getSortRating()))
                jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_SORT_RATING, filterObject.getSortRating());

            if (!WeCareUtils.isNullOrEmpty(filterObject.getSortPrice()))
                jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_SORT_PRICE, filterObject.getSortPrice());

            if (!WeCareUtils.isNullOrEmpty(filterObject.getSortAge()))
                jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_SORT_AGE, filterObject.getSortAge());

            if (!WeCareUtils.isNullOrEmpty(filterObject.getSortGender()))
                jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_SORT_GENDER, filterObject.getSortGender());


            JSONArray jsonArray = new JSONArray();
            for (SubServiceResponse subServiceResponse : filterObject.getServices()) {
                JSONObject jsonArrayObject = new JSONObject();
                jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_MAIN_SERVICE_ID, subServiceResponse.getMainServiceId());//"3"
                jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_SUB_SERVICE_ID, subServiceResponse.getId());//"3"
                jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_SUB_SERVICE_TYPE, subServiceResponse.getService_type());//"1"
                jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_CAREGIVER_SERVICE_ID, subServiceResponse.getCaregiverServiceID());//"1"

                //service_type : quantity = 1, hours = 2
                if (subServiceResponse.getService_type().equals("1")) {
                    jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_QUANTITY, "1");//"1"
                } else {
                    jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_QUANTITY, subServiceResponse.getHourlyDuration());
                }
                jsonArray.put(jsonArrayObject);
            }

            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_SERVICES, jsonArray);

//            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_AGE, "1");
//            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_GENDER_ID, "1");
//            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_CAREGIVER_LANGUAGE_ID, "1");
//            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_DATE, "2019-04-27");
//            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_START_TIME, "11:30:00");
//            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_DESCRIPTION, "test");
//            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_PAYMENT_METHOD, "1");
//            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_PROFILE_ID, "1");
//            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_LOCATION_ID, "1");
//
//            JSONArray jsonArray = new JSONArray();
//            JSONObject jsonArrayObject = new JSONObject();
//            jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_MAIN_SERVICE_ID, "3");
//            jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_SUB_SERVICE_ID, "3");
//            jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_SUB_SERVICE_TYPE, "1");
//            jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_QUANTITY, "1");
//            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_SERVICES, jsonArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getFavouriteCaregiversParams(FilterObject filterObject) {
        JSONObject jsonObject = new JSONObject();
        try {

            if (!WeCareUtils.isNullOrEmpty(filterObject.getWecare_id())) { //for search.
                jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_WECARE_ID, filterObject.getWecare_id());
            }
            //testing filter
//            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_DATE, "2019-04-27");
//            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_START_TIME, "11:30:00");
//            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_LOCATION_ID, "1");
//            //jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_AGE, "1");
//            //jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_GENDER_ID,"1");
//            //jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_CAREGIVER_LANGUAGE_ID, "1");
//            //jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_DESCRIPTION, "test");
//            //jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_PAYMENT_METHOD, "1");
//            //jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_PROFILE_ID, "1");
//
//            JSONObject jsonArrayObject = new JSONObject();
////            jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_MAIN_SERVICE_ID, "3");
//            jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_SUB_SERVICE_ID, "3");
//            jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_SUB_SERVICE_TYPE, "1");
//            jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_QUANTITY, "1");
//
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put(jsonArrayObject);
//            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_SERVICES, jsonArray);

            //real filter.
            if (!WeCareUtils.isNullOrEmpty(filterObject.getCaregiver_age())) {
                jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_AGE, filterObject.getCaregiver_age());//"1"
            }
            if (!WeCareUtils.isNullOrEmpty(filterObject.getCaregiver_gender_id())) {
                jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_GENDER_ID, filterObject.getCaregiver_gender_id());//"1"
            }
            if (!WeCareUtils.isNullOrEmpty(filterObject.getCaregiver_language_id())) {
                jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_CAREGIVER_LANGUAGE_ID, filterObject.getCaregiver_language_id());//"1"
            }
            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_DATE, filterObject.getDate());//"2019-04-27"
            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_START_TIME, filterObject.getStart_time());//"11:30:00"
            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_PAYMENT_METHOD, filterObject.getPayment_method());//"1"
            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_LOCATION_ID, filterObject.getLocation_id());//"1"
            if (filterObject.getProfile_id() != null)
                jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_PROFILE_ID, filterObject.getProfile_id());//"1"

            JSONArray jsonArray = new JSONArray();
            for (SubServiceResponse subServiceResponse : filterObject.getServices()) {
                JSONObject jsonArrayObject = new JSONObject();
                jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_MAIN_SERVICE_ID, subServiceResponse.getMainServiceId());//"3"
                jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_SUB_SERVICE_ID, subServiceResponse.getId());//"3"
                jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_SUB_SERVICE_TYPE, subServiceResponse.getService_type());//"1"
                jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_CAREGIVER_SERVICE_ID, subServiceResponse.getCaregiverServiceID());//"1"

                //service_type : quantity = 1, hours = 2
                if (subServiceResponse.getService_type().equals("1")) {
                    jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_QUANTITY, "1");//"1"
                } else {
                    jsonArrayObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_QUANTITY, subServiceResponse.getHourlyDuration());
                }
                jsonArray.put(jsonArrayObject);
            }
            jsonObject.put(ServerKeys.RecommendedCaregiversFilters.PARAM_SERVICES, jsonArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public static JSONObject getUserLocationParams(UserLocationResponse model) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ServerKeys.UserLocationParams.PARAM_NAME, model.getName());
            jsonObject.put(ServerKeys.UserLocationParams.PARAM_CITY, model.getCity().getId());//id
            jsonObject.put(ServerKeys.UserLocationParams.PARAM_COUNTRY_ID, model.getCountry().getId());//id
            jsonObject.put(ServerKeys.UserLocationParams.PARAM_BUILDING_NUMBER, model.getBuilding_number());
            jsonObject.put(ServerKeys.UserLocationParams.PARAM_FLOOR_NUMBER, model.getFloor_number());
            jsonObject.put(ServerKeys.UserLocationParams.PARAM_AREA, model.getArea());
            jsonObject.put(ServerKeys.UserLocationParams.PARAM_STREET_NAME, model.getStreet_name());
            jsonObject.put(ServerKeys.UserLocationParams.PARAM_LATITUDE, model.getLatitude());
            jsonObject.put(ServerKeys.UserLocationParams.PARAM_LONGITUDE, model.getLongitude());

            if (!WeCareUtils.isNullOrEmpty(model.getId())) {// is edit mode.
                jsonObject.put(ServerKeys.UserLocationParams.PARAM_ID, model.getId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getCreateOrderParams(CaregiverUser selectedCaregiverBean,
                                                  String date, String start_time, String description, String payment_method,
                                                  String location_id, RelativeProfileResponse relativeProfileResponse,
                                                  ArrayList<Integer> imagesIDList,
                                                  ArrayList<SubServiceResponse> selectedSubServiceResponseList, OrderModel reOrderModel, FilterObject filterObject) {
        JSONObject jsonObject = new JSONObject();
        try {

            if (reOrderModel != null) {//if reorder flow
                jsonObject.put(ServerKeys.PARAM_ORDER_ID, reOrderModel.getId());//getRef_order_id()
                jsonObject.put(ServerKeys.CreateOrderParams.PARAM_LOCATION_ID, reOrderModel.getRef_location_id());
                jsonObject.put(ServerKeys.CreateOrderParams.PARAM_PROFILE_ID, reOrderModel.getRef_profile_id());

                if (imagesIDList.isEmpty()) {
                    for (InformationAttachmentObj informationAttachmentObj : reOrderModel.getImages()) {
                        imagesIDList.add(informationAttachmentObj.getId());
                    }
                }
            } else {
                jsonObject.put(ServerKeys.CreateOrderParams.PARAM_LOCATION_ID, location_id);
                jsonObject.put(ServerKeys.CreateOrderParams.PARAM_PROFILE_ID, relativeProfileResponse.getId());
            }
            //special request
            jsonObject.put(ServerKeys.CreateOrderParams.PARAM_CAREGIVER_ID, selectedCaregiverBean.getId());

//            if (!WeCareUtils.isNullOrEmpty(filterObject.getCaregiver_age()))
            jsonObject.put(ServerKeys.CreateOrderParams.PARAM_CAREGIVER_AGE, filterObject.getCaregiver_age());//default 0
//            if (!WeCareUtils.isNullOrEmpty(filterObject.getCaregiver_gender_id()))
            jsonObject.put(ServerKeys.CreateOrderParams.PARAM_CAREGIVER_GENDER_ID, filterObject.getCaregiver_gender_id());//default 0
//            if (!WeCareUtils.isNullOrEmpty(filterObject.getCaregiver_language_id()))
            jsonObject.put(ServerKeys.CreateOrderParams.PARAM_CAREGIVER_LANGUAGE_ID, filterObject.getCaregiver_language_id());//default 0

            jsonObject.put(ServerKeys.CreateOrderParams.PARAM_DATE, date);
            jsonObject.put(ServerKeys.CreateOrderParams.PARAM_START_TIME, start_time);
            jsonObject.put(ServerKeys.CreateOrderParams.PARAM_DESCRIPTION, description);
            jsonObject.put(ServerKeys.CreateOrderParams.PARAM_PAYMENT_METHOD, payment_method);
            jsonObject.put(ServerKeys.CreateOrderParams.PARAM_PHONE_NUMBER, relativeProfileResponse.getMobile_number());
            jsonObject.put(ServerKeys.CreateOrderParams.PARAM_COUNTRY_CODE, relativeProfileResponse.getCountry_code());
            jsonObject.put(ServerKeys.CreateOrderParams.PARAM_NEED_MATERIALS, relativeProfileResponse.getNeedSomeMaterial());
            jsonObject.put(ServerKeys.CreateOrderParams.PARAM_MATERIALS_NEEDED, relativeProfileResponse.getNeededMaterial());
            jsonObject.put(ServerKeys.CreateOrderParams.PARAM_DESCRIPTION, relativeProfileResponse.getMoreDescription());

            JSONArray imagesArray = new JSONArray();
            for (Integer imageID : imagesIDList) {
                imagesArray.put(imageID);
            }
//            imagesArray.put(imagesIDList.toArray());

//            JSONObject result = new JSONObject();
            jsonObject.put(ServerKeys.CreateOrderParams.PARAM_IMAGES, imagesArray);

            JSONArray jsonArray = new JSONArray();
            for (SubServiceResponse subServiceResponse : selectedSubServiceResponseList) {
                JSONObject jsonArrayObject = new JSONObject();
                jsonArrayObject.put(ServerKeys.CreateOrderParams.PARAM_MAIN_SERVICE_ID, subServiceResponse.getMainServiceId());//"3"
                jsonArrayObject.put(ServerKeys.CreateOrderParams.PARAM_SUB_SERVICE_ID, subServiceResponse.getId());//"3"
                jsonArrayObject.put(ServerKeys.CreateOrderParams.PARAM_SUB_SERVICE_TYPE, subServiceResponse.getService_type());//"1"

                //service_type : quantity = 1, hours = 2
                if (subServiceResponse.getService_type().equals("1")) {
                    jsonArrayObject.put(ServerKeys.CreateOrderParams.PARAM_QUANTITY, "1");//"1"
                } else {
                    jsonArrayObject.put(ServerKeys.CreateOrderParams.PARAM_HOURS, subServiceResponse.getHourlyDuration());
                }
                jsonArray.put(jsonArrayObject);
            }
            jsonObject.put(ServerKeys.CreateOrderParams.PARAM_SERVICES, jsonArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


}
