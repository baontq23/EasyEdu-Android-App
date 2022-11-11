package com.btcdteam.easyedu.fragments.teacher;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.adapter.ViewPagerAdapter;


public class ClassInfoFragment extends Fragment {
    private View bgStudent, bgParent;
    private FrameLayout layoutStudent, layoutParent;
    private ViewPager2 viewPager2;

    Toolbar toolbar, searchToolbar;
    Menu search_menu;
    MenuItem item_search;
    ImageView icSearch, icSetting;

    ScaleAnimation scaleUpAnimation = new ScaleAnimation(0f, 1.0f, 1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
    ScaleAnimation scaleDownAnimation = new ScaleAnimation(1f, 0f, 1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bgStudent = view.findViewById(R.id.bg_student);
        bgParent = view.findViewById(R.id.bg_parent);

        layoutParent = view.findViewById(R.id.parent_layout);
        layoutStudent = view.findViewById(R.id.student_layout);
        viewPager2 = view.findViewById(R.id.view_pager);

        toolbar = view.findViewById(R.id.toolbar);
        icSearch = view.findViewById(R.id.ic_toolbar_search);
        icSetting = view.findViewById(R.id.ic_toolbar_setting);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setSearchToolbar(view);
        setMenuItemSelected();

        setViewPager();
        setBottomNav();
    }

    private void setBottomNav() {
        layoutStudent.setOnClickListener(view -> {
            viewPager2.setCurrentItem(0);
        });

        layoutParent.setOnClickListener(view -> {
            viewPager2.setCurrentItem(1);
        });
    }

    private void setViewPager() {
        scaleDownAnimation.setDuration(200);
        scaleUpAnimation.setDuration(200);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity());
        viewPager2.setAdapter(adapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bgParent.startAnimation(scaleDownAnimation);
                        bgParent.setVisibility(View.GONE);

                        bgStudent.setVisibility(View.VISIBLE);
                        bgStudent.startAnimation(scaleUpAnimation);
                        break;
                    case 1:
                        bgParent.setVisibility(View.VISIBLE);
                        bgParent.startAnimation(scaleUpAnimation);

                        bgStudent.startAnimation(scaleDownAnimation);
                        bgStudent.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    private void setMenuItemSelected() {

        icSearch.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                circleReveal(R.id.searchtoolbar, 1, true, true);
            else
                searchToolbar.setVisibility(View.VISIBLE);

            item_search.expandActionView();
        });

        icSetting.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Home Settings Click", Toast.LENGTH_SHORT).show();
        });
    }

    private void setSearchToolbar(@NonNull View view) {
        searchToolbar = view.findViewById(R.id.searchtoolbar);
        if (searchToolbar != null) {
            searchToolbar.inflateMenu(R.menu.menu_search);
            search_menu = searchToolbar.getMenu();

            searchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        circleReveal(R.id.searchtoolbar, 1, true, false);
                    else
                        searchToolbar.setVisibility(View.GONE);
                }
            });

            item_search = search_menu.findItem(R.id.action_filter_search);

            MenuItemCompat.setOnActionExpandListener(item_search, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    // Do something when collapsed
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        circleReveal(R.id.searchtoolbar, 1, true, false);
                    } else
                        searchToolbar.setVisibility(View.GONE);
                    return true;
                }

                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    // Do something when expanded
                    return true;
                }
            });

            initSearchView();

        } else
            Log.d("toolbar", "setSearchtollbar: NULL");
    }

    // bat su kien search view o day
    private void initSearchView() {
        final SearchView searchView =
                (SearchView) search_menu.findItem(R.id.action_filter_search).getActionView();

        // Enable/Disable Submit button in the keyboard

        searchView.setSubmitButtonEnabled(false);

        // Change search close button image

        ImageView closeButton = (ImageView) searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeButton.setImageResource(R.drawable.ic_round_close_24);


        // set hint and the text colors

        EditText txtSearch = ((EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text));
        txtSearch.setHint("Search..");
        txtSearch.setHintTextColor(Color.DKGRAY);
        txtSearch.setTextColor(getResources().getColor(R.color.blue_primary));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                return true;
            }

            public void callSearch(String query) {
                //Do searching
                Log.i("query", "" + query);

            }

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow) {
        final View myView = requireView().findViewById(viewID);

        int width = myView.getWidth();

        if (posFromRight > 0)
            width -= (posFromRight * getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)) - (getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) / 2);
        if (containsOverflow)
            width -= getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);

        int cx = width;
        int cy = myView.getHeight() / 2;

        Animator anim;
        if (isShow)
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, (float) width);
        else
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float) width, 0);

        anim.setDuration((long) 220);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isShow) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            }
        });

        // make the view visible and start the animation
        if (isShow)
            myView.setVisibility(View.VISIBLE);

        // start the animation
        anim.start();
    }
}