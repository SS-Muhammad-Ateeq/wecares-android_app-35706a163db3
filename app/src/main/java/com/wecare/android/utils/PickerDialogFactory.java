package com.wecare.android.utils;

import android.content.Context;

import com.wecare.android.R;
import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.CityModel;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.data.model.api.models.InsuranceCompanyModel;
import com.wecare.android.data.model.api.models.LookUpModel;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

/**
 * Created by hussam on 5/21/18.
 */

public class PickerDialogFactory {
    private PickerDialogFactory() {
    }


    public static void showSortingType(Context context, ArrayList<LookUpModel> sortList, SearchResultListener<LookUpModel> sortResultListener) {

        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(context,
                context.getString(R.string.sorting_type),
                context.getString(R.string.general_search),
                null, sortList, sortResultListener);

        simpleSearchDialogCompat.show();
    }

    public static void showCountryOfService(Context context, ArrayList<CountryModel> countries,
                                            SearchResultListener<CountryModel> countriesResultListener) {

        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(context,
                context.getString(R.string.country_of_service),
                context.getString(R.string.general_search),
                null, countries, countriesResultListener);

        simpleSearchDialogCompat.show();
    }

    public static void showNationality(Context context, ArrayList<CountryModel> countries,
                                       SearchResultListener<CountryModel> nationalityResultListener) {

        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(context,
                context.getString(R.string.nationality),
                context.getString(R.string.general_search),
                null, countries, nationalityResultListener);

        simpleSearchDialogCompat.show();
    }

    public static void showCities(Context context, DataManagerFlavour dataManagerFlavour, ArrayList<CityModel> cities,
                                  SearchResultListener<CityModel> citiesResultListener) {
        if (cities == null)
            return;
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(context,
                context.getString(R.string.city),
                context.getString(R.string.general_search),
                null, cities, citiesResultListener);

        simpleSearchDialogCompat.show();
    }

    public static void showLanguages(Context context, DataManagerFlavour dataManagerFlavour, SearchResultListener<LookUpModel> languageResultListener) {
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(context,
                context.getString(R.string.language),
                context.getString(R.string.general_search),
                null, dataManagerFlavour.getLookupsModel().getLanguageArrayList(), languageResultListener);

        simpleSearchDialogCompat.show();
    }

    public static void showGiverAge(Context context, DataManagerFlavour dataManagerFlavour, SearchResultListener<LookUpModel> genderResultListener) {
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(context,
                context.getString(R.string.age),
                context.getString(R.string.general_search),
                null, dataManagerFlavour.getLookupsModel().getCaregiverAgeArrayList(), genderResultListener);

        simpleSearchDialogCompat.show();
    }

    public static void showGender(Context context, DataManagerFlavour dataManagerFlavour, SearchResultListener<LookUpModel> genderResultListener) {
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(context,
                context.getString(R.string.gender),
                context.getString(R.string.general_search),
                null, dataManagerFlavour.getLookupsModel().getGenderArrayList(), genderResultListener);

        simpleSearchDialogCompat.show();
    }

    public static void showProviderGender(Context context, DataManagerFlavour dataManagerFlavour, SearchResultListener<LookUpModel> genderResultListener) {
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(context,
                context.getString(R.string.gender),
                context.getString(R.string.general_search),
                null, dataManagerFlavour.getLookupsModel().getProviderServiceGendersArrayList(), genderResultListener);

        simpleSearchDialogCompat.show();
    }

    public static void showRelationship(Context context, ArrayList<LookUpModel> relationArrayList, SearchResultListener<LookUpModel> relationResultListener) {
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(context,
                context.getString(R.string.relationship),
                context.getString(R.string.general_search),
                null, relationArrayList, relationResultListener);

        simpleSearchDialogCompat.show();
    }


    public static void showInsuranceCompany(Context context, ArrayList<InsuranceCompanyModel> typeOfInsuranceArrayList, SearchResultListener<InsuranceCompanyModel> typeOfInsuranceResultListener) {
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(context,
                context.getString(R.string.insurance_company_name),
                context.getString(R.string.general_search),
                null, typeOfInsuranceArrayList, typeOfInsuranceResultListener);

        simpleSearchDialogCompat.show();
    }

    public static void showTypeOfInsurance(Context context, ArrayList<LookUpModel> typeOfInsuranceArrayList, SearchResultListener<LookUpModel> typeOfInsuranceResultListener) {
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(context,
                context.getString(R.string.type_of_insurance),
                context.getString(R.string.general_search),
                null, typeOfInsuranceArrayList, typeOfInsuranceResultListener);

        simpleSearchDialogCompat.show();
    }

    public static void showBloodTypeGender(Context context, ArrayList<LookUpModel> bloodTypesArrayList, SearchResultListener<LookUpModel> genderResultListener) {
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(context,
                context.getString(R.string.blood_type),
                context.getString(R.string.general_search),
                null, bloodTypesArrayList, genderResultListener);

        simpleSearchDialogCompat.show();
    }

    public static void showYesNoSelection(Context context, SearchResultListener<LookUpModel> yesNoResultListener) {
        ArrayList<LookUpModel> yesNoArrayList = new ArrayList<>();
        yesNoArrayList.add(new LookUpModel().setArabicName(context.getString(R.string.yes)).setEnglishName(context.getString(R.string.yes)).setId("1"));
        yesNoArrayList.add(new LookUpModel().setArabicName(context.getString(R.string.no)).setEnglishName(context.getString(R.string.no)).setId("0"));

        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(context,
                "",
                context.getString(R.string.general_search),
                null, yesNoArrayList, yesNoResultListener);

        simpleSearchDialogCompat.show();
    }

}