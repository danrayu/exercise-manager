// Generated by view binder compiler. Do not edit!
package com.example.exercisemanager.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.exercisemanager.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DialogMuscleCreatorBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton btnSaveItem;

  @NonNull
  public final ConstraintLayout clMuscle;

  @NonNull
  public final ConstraintLayout constraintLayout;

  @NonNull
  public final EditText etEnterMname;

  @NonNull
  public final TextView tvEnterMname;

  @NonNull
  public final TextView tvPageName;

  private DialogMuscleCreatorBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageButton btnSaveItem, @NonNull ConstraintLayout clMuscle,
      @NonNull ConstraintLayout constraintLayout, @NonNull EditText etEnterMname,
      @NonNull TextView tvEnterMname, @NonNull TextView tvPageName) {
    this.rootView = rootView;
    this.btnSaveItem = btnSaveItem;
    this.clMuscle = clMuscle;
    this.constraintLayout = constraintLayout;
    this.etEnterMname = etEnterMname;
    this.tvEnterMname = tvEnterMname;
    this.tvPageName = tvPageName;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogMuscleCreatorBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogMuscleCreatorBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_muscle_creator, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogMuscleCreatorBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_save_item;
      ImageButton btnSaveItem = ViewBindings.findChildViewById(rootView, id);
      if (btnSaveItem == null) {
        break missingId;
      }

      ConstraintLayout clMuscle = (ConstraintLayout) rootView;

      id = R.id.constraintLayout;
      ConstraintLayout constraintLayout = ViewBindings.findChildViewById(rootView, id);
      if (constraintLayout == null) {
        break missingId;
      }

      id = R.id.et_enter_mname;
      EditText etEnterMname = ViewBindings.findChildViewById(rootView, id);
      if (etEnterMname == null) {
        break missingId;
      }

      id = R.id.tv_enter_mname;
      TextView tvEnterMname = ViewBindings.findChildViewById(rootView, id);
      if (tvEnterMname == null) {
        break missingId;
      }

      id = R.id.tv_page_name;
      TextView tvPageName = ViewBindings.findChildViewById(rootView, id);
      if (tvPageName == null) {
        break missingId;
      }

      return new DialogMuscleCreatorBinding((ConstraintLayout) rootView, btnSaveItem, clMuscle,
          constraintLayout, etEnterMname, tvEnterMname, tvPageName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
