package com.wecare.android.ui.search_giver.blocked;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.local.FilterObject;
import com.wecare.android.data.model.api.responses.SearchGiverResponse;
import com.wecare.android.databinding.FragmentSuggestedGiverBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.ui.profile.CaregiverShowProfileActivity;
import com.wecare.android.ui.search_giver.SearchGiverActivity;
import com.wecare.android.ui.search_giver.SearchGiverNavigator;
import com.wecare.android.ui.search_giver.SearchGiverViewModel;
import com.wecare.android.ui.search_giver.common.KeyProvider;
import com.wecare.android.ui.search_giver.common.SelectorItemDetailsLookup;
import com.wecare.android.ui.search_giver.common.SuggestedGiverAdapter;
import com.wecare.android.utils.AppConstants;

import java.util.List;

import javax.inject.Inject;


public class BlockedGiverFragment extends BaseFragment<FragmentSuggestedGiverBinding, SearchGiverViewModel>
        implements SearchGiverNavigator, SuggestedGiverAdapter.SearchGiverAdapterListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = BlockedGiverFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    @Inject
    SuggestedGiverAdapter suggestedGiverAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    private SearchGiverViewModel viewModel;
    private FragmentSuggestedGiverBinding binding;

    /**/
    public static BlockedGiverFragment newInstance(Bundle args) {
        if (args == null) {
            args = new Bundle();
        }

        BlockedGiverFragment fragment = new BlockedGiverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_suggested_giver;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        viewModel.setNavigator(this);

        //get args
        Bundle bundle = getArguments();
//        if (bundle != null && bundle.containsKey(AppConstants.ARGS_KEY_FILTER_GIVER_OBJECT)) {
//            filterObject = bundle.getParcelable(AppConstants.ARGS_KEY_FILTER_GIVER_OBJECT);
//        } else {
        getViewModel().isFromProfileView = true;
        suggestedGiverAdapter.setSummaryView(true);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();

        binding.swipeLayout.setOnRefreshListener(this);

//        suggestedGiverAdapter.setFavouriteView(true);

        setUp(savedInstanceState);
        subscribeToLiveData();
    }

    @Override
    public SearchGiverViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(SearchGiverViewModel.class);
        return viewModel;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_filter, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            validator.toValidate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUp(Bundle savedInstanceState) {
        suggestedGiverAdapter.setListener(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.suggestedRecycler.setLayoutManager(mLayoutManager);
        binding.suggestedRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.suggestedRecycler.setAdapter(suggestedGiverAdapter);
    }

    private void subscribeToLiveData() {
        viewModel.getSearchGiverListLiveData().observe(this, new Observer<List<SearchGiverResponse>>() {
            @Override
            public void onChanged(@Nullable List<SearchGiverResponse> searchGiverResponseList) {
                viewModel.addSearchGiverItemsToList(searchGiverResponseList);
            }
        });
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onGiverPicked() {
    }

    @Override
    public void toggleSwipeToRefresh(boolean b) {
        binding.swipeLayout.setRefreshing(b);
    }

    @Override
    public void onRefresh() {
        getViewModel().fetchAllBlockedGiverResponse();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void OnUpdateView() {
        super.OnUpdateView();
        getViewModel().fetchAllBlockedGiverResponse();

//        getBaseActivity().getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
//        getBaseActivity().setTitle(getString(R.string.));
    }

    @Override
    public void onItemClicked(SearchGiverResponse searchGiverResponse) {
        startActivity(CaregiverShowProfileActivity.newIntent(getActivity()).putExtra(AppConstants.ARGS_KEY_CAREGIVER_ID, searchGiverResponse.getCaregiver().getId()));
    }

    @Override
    public void onLoadMore() {

    }
}
