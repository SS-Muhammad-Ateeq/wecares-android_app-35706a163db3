
package com.wecare.android.ui.search_giver.suggested;

import androidx.databinding.ObservableField;
import androidx.recyclerview.selection.SelectionTracker;
import com.wecare.android.data.model.api.requests.LanguageRequest;
import com.wecare.android.data.model.api.responses.SearchGiverResponse;
import com.wecare.android.ui.search_giver.common.SelectorItemDetail;
import com.wecare.android.utils.WeCareUtils;


public class SuggestedGiverItemViewModel {

    private SearchGiverResponse searchGiverResponse;

    public ObservableField<Integer> giverRating;
    public ObservableField<String> giverNameTitle;
    public ObservableField<String> giverPrice;
    public ObservableField<String> giverGender;
    public ObservableField<String> giverYears;
    public ObservableField<String> giverLanguage;
    public ObservableField<String> giverImageUrl;
    public ObservableField<Boolean> giverCheckedStatusCheckbox;

    private ItemViewModelListener mListener;

    public SuggestedGiverItemViewModel(SearchGiverResponse searchGiverResponse, SelectionTracker<Long> selectionTracker,
                                       SelectorItemDetail details, ItemViewModelListener listener) {
        this.searchGiverResponse = searchGiverResponse;
        this.mListener = listener;

        String name = String.format("%s %s", searchGiverResponse.getCaregiver().getFirstName(),
                searchGiverResponse.getCaregiver().getLastName());

        giverNameTitle = new ObservableField<>(name);
        giverPrice = new ObservableField<>(searchGiverResponse.getEstimated_total_price());
        giverYears = new ObservableField<>(searchGiverResponse.getCaregiver().getYearsOfExperience());
        giverImageUrl = new ObservableField<>(searchGiverResponse.getCaregiver().getAvatar());
        giverGender = new ObservableField<>(searchGiverResponse.getCaregiver().getGender().getTitle());

        if (searchGiverResponse.getCaregiver().getLanguageModels() == null || searchGiverResponse.getCaregiver().getLanguageModels().size() > 0) {
            String lang = "";

            for (LanguageRequest languageRequest : searchGiverResponse.getCaregiver().getLanguageModels()) {
                lang = String.format("%s %s", lang, languageRequest.toString());
            }
            giverLanguage = new ObservableField<>(lang);
        }

        int rate = (int) searchGiverResponse.getCaregiver().getRating();
        giverRating = new ObservableField<>(rate);
//        if (!WeCareUtils.isNullOrEmpty(searchGiverResponse.getCaregiver().getRating())) {
//            giverRating = new ObservableField<>(Integer.valueOf(searchGiverResponse.getCaregiver().getRating()));
//        } else {
//            giverRating = new ObservableField<>(0);
//        }

        if (selectionTracker != null) {
            giverCheckedStatusCheckbox = new ObservableField<>(selectionTracker.isSelected(details.getSelectionKey()));
        } else {
            giverCheckedStatusCheckbox = new ObservableField<>(false);
        }
    }

    public void onCheckedClick() {
        mListener.onCheckedClick(searchGiverResponse);
    }

    public void onProfileImageClick() {
        mListener.onProfileImageClick(searchGiverResponse);
    }

    public interface ItemViewModelListener {
        void onCheckedClick(SearchGiverResponse searchGiverResponse);

        void onProfileImageClick(SearchGiverResponse searchGiverResponse);
    }
}
