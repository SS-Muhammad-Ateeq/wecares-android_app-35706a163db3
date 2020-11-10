package com.wecare.android.ui.search_giver.suggested;

import android.content.Intent;
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
import com.wecare.android.ui.create_order.CreateOrderActivity;
import com.wecare.android.ui.profile.CaregiverShowProfileActivity;
import com.wecare.android.ui.search_giver.SearchGiverActivity;
import com.wecare.android.ui.search_giver.SearchGiverNavigator;
import com.wecare.android.ui.search_giver.SearchGiverViewModel;
import com.wecare.android.ui.search_giver.common.KeyProvider;
import com.wecare.android.ui.search_giver.common.SelectorItemDetailsLookup;
import com.wecare.android.ui.search_giver.common.SuggestedGiverAdapter;
import com.wecare.android.ui.search_giver.filter.FilterGiverActivity;
import com.wecare.android.utils.AppConstants;

import javax.inject.Inject;

import java.util.List;


public class SuggestedGiverFragment extends BaseFragment<FragmentSuggestedGiverBinding, SearchGiverViewModel>
        implements SearchGiverNavigator, SuggestedGiverAdapter.SearchGiverAdapterListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = SuggestedGiverFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    @Inject
    SuggestedGiverAdapter suggestedGiverAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    private SearchGiverViewModel viewModel;
    private FragmentSuggestedGiverBinding binding;

    private int selectedPosition = -1;
    private SelectionTracker<Long> selectionTracker;

    //filter request.
    private FilterObject filterObject;

    /**/
    public static SuggestedGiverFragment newInstance(Bundle args) {
        if (args == null) {
            args = new Bundle();
        }
        SuggestedGiverFragment fragment = new SuggestedGiverFragment();
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
        setHasOptionsMenu(true);
        viewModel.setNavigator(this);

        filterObject = new FilterObject();
        //get args
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(AppConstants.ARGS_KEY_FILTER_GIVER_OBJECT)) {
            filterObject = bundle.getParcelable(AppConstants.ARGS_KEY_FILTER_GIVER_OBJECT);
        }

        //get givers
        getViewModel().fetchSuggestedGiverResponse(filterObject);
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
        if (item.getItemId() == R.id.action_filter) {
            Intent i = FilterGiverActivity.getStartIntent(getBaseActivity());
            i.putExtra(AppConstants.ARGS_KEY_FILTER_GIVER_OBJECT, filterObject);
            startActivityForResult(i, AppConstants.REQ_CODE_EDITED_FILTER_GIVER_OBJECT_LIST);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.REQ_CODE_EDITED_FILTER_GIVER_OBJECT_LIST && data != null && data.hasExtra(AppConstants.ARGS_KEY_EDITED_FILTER_GIVER_OBJECT)) {
            filterObject = data.getParcelableExtra(AppConstants.ARGS_KEY_EDITED_FILTER_GIVER_OBJECT);
            onRefresh();
        }
    }

    private void setUp(Bundle savedInstanceState) {
        suggestedGiverAdapter.setListener(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.suggestedRecycler.setLayoutManager(mLayoutManager);
        binding.suggestedRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.suggestedRecycler.setAdapter(suggestedGiverAdapter);

        selectionTracker = new SelectionTracker.Builder<>("mySelectionPosition",
                binding.suggestedRecycler,
                new KeyProvider(binding.suggestedRecycler.getAdapter()),
                new SelectorItemDetailsLookup(binding.suggestedRecycler),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(SelectionPredicates.createSelectSingleAnything()).build();
//                .withSelectionPredicate(new SuggestedGiverAdapter.DetailsPredicate()).build();

        suggestedGiverAdapter.setSelectionTracker(selectionTracker);

        selectionTracker.addObserver(new SelectionTracker.SelectionObserver() {
            @Override
            public void onItemStateChanged(@NonNull Object key, boolean selected) {
                super.onItemStateChanged(key, selected);
                if (selected) {
                    selectedPosition = ((Long) key).intValue();
                    getViewModel().isListItemSelected.set(true);
                } else {
                    getViewModel().isListItemSelected.set(false);
                    selectedPosition = -1;
                }
            }
        });

        if (savedInstanceState != null) {
            selectionTracker.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        selectionTracker.onSaveInstanceState(outState);
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
        SearchGiverResponse selectedSearchGiverResponse = getSelectedGiver();
        ((SearchGiverActivity) getBaseActivity()).onGiverPickedWithGiverSelection(selectedSearchGiverResponse, filterObject);
    }

    @Override
    public void toggleSwipeToRefresh(boolean b) {
        binding.swipeLayout.setRefreshing(b);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void OnUpdateView() {
        super.OnUpdateView();

//        getBaseActivity().getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
//        getBaseActivity().setTitle(getString(R.string.));
    }

    public SearchGiverResponse getSelectedGiver() {
        SearchGiverResponse searchGiverResponse = null;
        if (selectionTracker != null && selectedPosition != -1) {
            searchGiverResponse = suggestedGiverAdapter.getItemAtPosition(selectedPosition);
        }
        return searchGiverResponse;
    }

    @Override
    public void onItemClicked(SearchGiverResponse searchGiverResponse) {
        startActivity(CaregiverShowProfileActivity.newIntent(getActivity()).putExtra(AppConstants.ARGS_KEY_CAREGIVER_ID,
                searchGiverResponse.getCaregiver().getId()));
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {
        getViewModel().fetchSuggestedGiverResponse(filterObject);
    }
}
