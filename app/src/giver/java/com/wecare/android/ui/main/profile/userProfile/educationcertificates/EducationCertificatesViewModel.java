package com.wecare.android.ui.main.profile.userProfile.educationcertificates;

import android.util.Log;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.AttachmentModel;
import com.wecare.android.data.model.api.models.EduCerUserModel;
import com.wecare.android.data.model.api.models.EducationModel;
import com.wecare.android.data.model.api.models.ExperienceModel;
import com.wecare.android.data.model.api.models.InsuranceCompanyLookup;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.data.model.api.models.MalInsuranceModel;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.data.model.api.requests.LanguageRequest;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.functions.Consumer;

public class EducationCertificatesViewModel extends BaseViewModel<EducationCertificatesActivityNavigator> {

    EduCerUserModel userModel = new EduCerUserModel();

    public EduCerUserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(EduCerUserModel userModel) {
        this.userModel = userModel;
    }

    public EducationCertificatesViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void eductionInstructions(){
        getNavigator().eductionInstructions();
    }
    public void addExperience() {
        getNavigator().addExperience();
    }

    public void addEducation() {
        getNavigator().addEducation();
    }
    public void addMalInsurance() {
        getNavigator().addMalInsurance();
    }

    public void addLanguage() {
        getNavigator().addLanguage();
    }

    public void addCertificate () {
        getNavigator().addCertificate();
    }

    public void addAttachment() {
        getNavigator().addAttachment();
    }
    public void placeOfIssueClicked() {
        getNavigator().placeOfIssueClicked();
    }

    public void addLanguageRemote(LookUpModel model){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().addLanguage(JSONBuilderFlavour.getAddLanguageParams(new LanguageRequest(model.getId(),"1")))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().languageAdded(model);
                        else
                            getNavigator().handleError(response.getError().getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        // incase anything went wrong
                    }
                }));
    }

    public void updateUser(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().updateUser(JSONBuilderFlavour.getUpdateEduCerUserParams(userModel))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserModel>() {
                    @Override
                    public void accept(UserModel response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().userDataUpdatedSuccessfully();
                        else
                            getNavigator().handleError(response.getError().getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        // incase anything went wrong
                    }
                }));
    }
    public void addExperienceRemote(ExperienceModel experienceModel,boolean isLastOne){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().addExperience(JSONBuilderFlavour.getAddExperienceParams(experienceModel))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().experienceAddedSuccessfully(isLastOne);
                        else
                            getNavigator().handleError(response.getError().getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        // incase anything went wrong
                    }
                }));
    }

    public void addMalInsuranceRemote(MalInsuranceModel malInsuranceModel, boolean isLastOne){
        setIsLoading(true);
        malInsuranceModel.setCaregiverId(getDataManager().getCurrentUserModel().getId());
        getCompositeDisposable().add(getDataManager().addMalInsurance(JSONBuilderFlavour.getCommonRequestParams(malInsuranceModel))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().malpracticeInsuranceAddedSuccessfully(isLastOne);
                        else
                            getNavigator().handleError(response.getError().getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        // incase anything went wrong
                    }
                }));
    }

    public void addEducationRemote(EducationModel educationModel,boolean isLastOne){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().addEducation(JSONBuilderFlavour.getAddEducationParams(educationModel))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().educationAddedSuccessfully(isLastOne);
                        else
                            getNavigator().handleError(response.getError().getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        // incase anything went wrong
                    }
                }));
    }

    public void fetchUserEducation(){
            setIsLoading(true);
            getCompositeDisposable().add(getDataManager()
                    .getUserEducation()
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<List<EducationModel>>() {
                        @Override
                        public void accept(@NonNull List<EducationModel> modelList) throws Exception {
                            setIsLoading(false);
                            getNavigator().userEducationFetched(new ArrayList<EducationModel>(modelList));
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            setIsLoading(false);
                            //getNavigator().showCommonError();
                            Log.e("error","fetchUserEducation");
                        }
                    }));

    }
    public void fetchUserMalInsurance(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getUserMalInsurance()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<MalInsuranceModel>>() {
                    @Override
                    public void accept(@NonNull List<MalInsuranceModel> modelList) throws Exception {
                        setIsLoading(false);
                        getNavigator().malInsurancesFetched(new ArrayList<MalInsuranceModel>(modelList));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        //getNavigator().showCommonError();
                        Log.e("error","fetchUserMalInsurance");
                    }
                }));

    }
    public void fetchUserExperience(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getUserExperience()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<ExperienceModel>>() {
                    @Override
                    public void accept(@NonNull List<ExperienceModel> modelList) throws Exception {
                        setIsLoading(false);
                        getNavigator().userExperienceFetched(new ArrayList<ExperienceModel>(modelList));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        //getNavigator().showCommonError();
                        Log.e("error","fetchUserExperience");
                    }
                }));
    }
    public void fetchّInsuranceCompanies(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getInsuranceCompanies(getDataManager().getCurrentUserModel().getCountry().getId())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(modelList -> {
                    setIsLoading(false);
                    getNavigator().insuranceCompaniesFetched(new ArrayList<InsuranceCompanyLookup>(modelList));
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
//                        //getNavigator().showCommonError();
                        Log.e("error","fetchّInsuranceCompanies");
                    }
                }));
    }
    public void deleteLanguage(String id, int position){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .deleteLanguage(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                        getNavigator().languageDeletedSuccessfully(position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
//                        //getNavigator().showCommonError();
                        Log.e("error","deleteLanguage");

                    }
                }));
    }
    public void deleteAttachment(String id, int position){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .deleteAttachment(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().attachmentDeletedSuccessfully(position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
//                        //getNavigator().showCommonError();
                        Log.e("error","deleteAttachment");
                    }
                }));
    }
    public void deleteCertificate(String id, int position){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .deleteCertificate(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().certificateDeletedSuccessfully(position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
//                        //getNavigator().showCommonError();
                        Log.e("error","deleteCertificate");
                    }
                }));
    }

    public void deleteMalInsurance(String id, int position){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .deleteMalInsurance(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().malInsuranceDeletedSuccessfully(position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        //getNavigator().showCommonError();
                        Log.e("error","deleteMalInsurance");
                    }
                }));
    }

    public void deleteEducation(String id, int position){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .deleteEducation(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().educationDeletedSuccessfully(position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        //getNavigator().showCommonError();
                        Log.e("error","deleteEducation");
                    }
                }));
    }
    public void deleteExperience(String id, int position){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .deleteExperience(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().experienceDeletedSuccessfully(position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        //getNavigator().showCommonError();
                    }
                }));
    }
    public void fetchUserLanguages(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getLanguage()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<LanguageRequest>>() {
                    @Override
                    public void accept(@NonNull List<LanguageRequest> modelList) throws Exception {
                        setIsLoading(false);
                        getNavigator().languageFetched(new ArrayList<LanguageRequest>(modelList));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        //getNavigator().showCommonError();
                    }
                }));
    }

    public void uploadCertificates(String name, File file){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().uploadCaregiverCertificate(name,file)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<AttachmentModel>() {
                    @Override
                    public void accept(AttachmentModel model) throws Exception {
                        setIsLoading(false);
                        if (model.isSuccess())
                            getNavigator().certificateUploadedSuccessfully(model);
                        else
                            getNavigator().handleError(model.getError().getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        // incase anything went wrong
                    }
                }));
    }
    public void uploadAttachment(String name,String categoryID, File file){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().uploadCaregiverAttachment(name,categoryID,file)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<AttachmentModel>() {
                    @Override
                    public void accept(AttachmentModel model) throws Exception {
                        setIsLoading(false);
                        if (model.isSuccess())
                            getNavigator().attachmentUploadedSuccessfully(model);
                        else
                            getNavigator().handleError(model.getError().getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        // incase anything went wrong
                    }
                }));
    }

    public void fetchUserAttachments(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getUserAttachments()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<AttachmentModel>>() {
                    @Override
                    public void accept(@NonNull List<AttachmentModel> modelList) throws Exception {
                        setIsLoading(false);
                        getNavigator().attachmentsFetched(new ArrayList<AttachmentModel>(modelList));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        //getNavigator().showCommonError();
                    }
                }));
    }
    public void fetchUserCertificates(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getUserCertificates()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<AttachmentModel>>() {
                    @Override
                    public void accept(@NonNull List<AttachmentModel> modelList) throws Exception {
                        setIsLoading(false);
                        getNavigator().certificatesFetched(new ArrayList<AttachmentModel>(modelList));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        //getNavigator().showCommonError();
                    }
                }));
    }

    public void updateEducation(int position, EducationModel educationModel,boolean isLastOne){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .updateEducation(educationModel.getId()+"",JSONBuilderFlavour.getAddEducationParams(educationModel))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<EducationModel>() {
                    @Override
                    public void accept(@NonNull EducationModel model) throws Exception {
                        setIsLoading(false);
                        getNavigator().educationUpdatedSuccessfully(position,educationModel,isLastOne);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        //getNavigator().showCommonError();
                    }
                }));
    }
    public void updateMalInsurance(int position, MalInsuranceModel malInsuranceModel,boolean isLastOne){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .updateMalInsurance(malInsuranceModel.getId()+"",JSONBuilderFlavour.getCommonRequestParams(malInsuranceModel))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<MalInsuranceModel>() {
                    @Override
                    public void accept(@NonNull MalInsuranceModel model) throws Exception {
                        setIsLoading(false);
                        getNavigator().malInsuranceUpdatedSuccessfully(position,malInsuranceModel,isLastOne);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        //getNavigator().showCommonError();
                    }
                }));
    }
    public void updateExperience(int position, ExperienceModel experienceModel,boolean isLastOne){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .updateExperience(experienceModel.getId()+"",JSONBuilderFlavour.getAddExperienceParams(experienceModel))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ExperienceModel>() {
                    @Override
                    public void accept(@NonNull ExperienceModel model) throws Exception {
                        setIsLoading(false);
                        getNavigator().experienceUpdatedSuccessfully(position,model,isLastOne);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        //getNavigator().showCommonError();
                    }
                }));
    }


}
