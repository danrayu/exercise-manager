// Generated by view binder compiler. Do not edit!
package com.example.exercisemanager.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager2.widget.ViewPager2;
import com.example.exercisemanager.R;
import com.google.android.material.tabs.TabLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentHomeBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final ViewPager2 pagerHome;

  @NonNull
  public final TabLayout tabLayoutHome;

  private FragmentHomeBinding(@NonNull FrameLayout rootView, @NonNull ViewPager2 pagerHome,
      @NonNull TabLayout tabLayoutHome) {
    this.rootView = rootView;
    this.pagerHome = pagerHome;
    this.tabLayoutHome = tabLayoutHome;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.pager_home;
      ViewPager2 pagerHome = ViewBindings.findChildViewById(rootView, id);
      if (pagerHome == null) {
        break missingId;
      }

      id = R.id.tab_layout_home;
      TabLayout tabLayoutHome = ViewBindings.findChildViewById(rootView, id);
      if (tabLayoutHome == null) {
        break missingId;
      }

      return new FragmentHomeBinding((FrameLayout) rootView, pagerHome, tabLayoutHome);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
