// Generated by view binder compiler. Do not edit!
package com.example.exercisemanager.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.exercisemanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentGroupEditBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final FloatingActionButton btnAddGroupExercise;

  @NonNull
  public final FloatingActionButton btnSaveGroup;

  @NonNull
  public final ConstraintLayout editGroupLayout;

  @NonNull
  public final EditText etGroupDescription;

  @NonNull
  public final EditText etGroupName;

  @NonNull
  public final RecyclerView rvGroupExercises;

  @NonNull
  public final TextView tvGroupDescription;

  @NonNull
  public final TextView tvGroupName;

  private FragmentGroupEditBinding(@NonNull ConstraintLayout rootView,
      @NonNull FloatingActionButton btnAddGroupExercise, @NonNull FloatingActionButton btnSaveGroup,
      @NonNull ConstraintLayout editGroupLayout, @NonNull EditText etGroupDescription,
      @NonNull EditText etGroupName, @NonNull RecyclerView rvGroupExercises,
      @NonNull TextView tvGroupDescription, @NonNull TextView tvGroupName) {
    this.rootView = rootView;
    this.btnAddGroupExercise = btnAddGroupExercise;
    this.btnSaveGroup = btnSaveGroup;
    this.editGroupLayout = editGroupLayout;
    this.etGroupDescription = etGroupDescription;
    this.etGroupName = etGroupName;
    this.rvGroupExercises = rvGroupExercises;
    this.tvGroupDescription = tvGroupDescription;
    this.tvGroupName = tvGroupName;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentGroupEditBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentGroupEditBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_group_edit, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentGroupEditBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_add_group_exercise;
      FloatingActionButton btnAddGroupExercise = ViewBindings.findChildViewById(rootView, id);
      if (btnAddGroupExercise == null) {
        break missingId;
      }

      id = R.id.btn_save_group;
      FloatingActionButton btnSaveGroup = ViewBindings.findChildViewById(rootView, id);
      if (btnSaveGroup == null) {
        break missingId;
      }

      ConstraintLayout editGroupLayout = (ConstraintLayout) rootView;

      id = R.id.et_group_description;
      EditText etGroupDescription = ViewBindings.findChildViewById(rootView, id);
      if (etGroupDescription == null) {
        break missingId;
      }

      id = R.id.et_group_name;
      EditText etGroupName = ViewBindings.findChildViewById(rootView, id);
      if (etGroupName == null) {
        break missingId;
      }

      id = R.id.rv_group_exercises;
      RecyclerView rvGroupExercises = ViewBindings.findChildViewById(rootView, id);
      if (rvGroupExercises == null) {
        break missingId;
      }

      id = R.id.tv_group_description;
      TextView tvGroupDescription = ViewBindings.findChildViewById(rootView, id);
      if (tvGroupDescription == null) {
        break missingId;
      }

      id = R.id.tv_group_name;
      TextView tvGroupName = ViewBindings.findChildViewById(rootView, id);
      if (tvGroupName == null) {
        break missingId;
      }

      return new FragmentGroupEditBinding((ConstraintLayout) rootView, btnAddGroupExercise,
          btnSaveGroup, editGroupLayout, etGroupDescription, etGroupName, rvGroupExercises,
          tvGroupDescription, tvGroupName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
