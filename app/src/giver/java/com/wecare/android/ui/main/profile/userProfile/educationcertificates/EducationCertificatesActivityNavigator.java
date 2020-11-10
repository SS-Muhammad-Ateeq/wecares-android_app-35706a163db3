package com.wecare.android.ui.main.profile.userProfile.educationcertificates;

import com.wecare.android.data.model.api.models.AttachmentModel;
import com.wecare.android.data.model.api.models.EducationModel;
import com.wecare.android.data.model.api.models.ExperienceModel;
import com.wecare.android.data.model.api.models.InsuranceCompanyLookup;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.data.model.api.models.MalInsuranceModel;
import com.wecare.android.data.model.api.requests.LanguageRequest;
import com.wecare.android.ui.base.BaseNavigator;

import java.util.ArrayList;

public interface EducationCertificatesActivityNavigator extends BaseNavigator {
    void addEducation();
    void addMalInsurance();
    void addExperience();
    void addLanguage();
    void addCertificate();
    void addAttachment();
    void eductionInstructions();
    void languageAdded(LookUpModel model);
    void placeOfIssueClicked();
    void userDataUpdatedSuccessfully();
    void educationAddedSuccessfully(boolean isLastOne);
    void experienceAddedSuccessfully(boolean isLastOne);
    void malpracticeInsuranceAddedSuccessfully(boolean isLastOne);
    void userEducationFetched(ArrayList<EducationModel> educationModels);
    void userExperienceFetched(ArrayList<ExperienceModel> experienceModels);
    void malInsurancesFetched(ArrayList<MalInsuranceModel> malInsuranceModels);
    void languageDeletedSuccessfully(int position);
    void educationDeletedSuccessfully(int position);
    void experienceDeletedSuccessfully(int position);
    void certificateDeletedSuccessfully(int position);
    void malInsuranceDeletedSuccessfully(int position);
    void attachmentDeletedSuccessfully(int position);
    void languageFetched(ArrayList<LanguageRequest> models);
    void attachmentUploadedSuccessfully(AttachmentModel attachmentModel);
    void certificateUploadedSuccessfully(AttachmentModel attachmentModel);
    void attachmentsFetched(ArrayList<AttachmentModel> attachmentModels);
    void certificatesFetched(ArrayList<AttachmentModel> attachmentModels);
    void insuranceCompaniesFetched(ArrayList<InsuranceCompanyLookup> insuranceCompanies);
    void educationUpdatedSuccessfully(int position,EducationModel educationModel,boolean isLastOne);
    void experienceUpdatedSuccessfully(int position,ExperienceModel experienceModel,boolean isLastOne);
    void malInsuranceUpdatedSuccessfully(int position,MalInsuranceModel malInsuranceModel,boolean isLastOne);



}
