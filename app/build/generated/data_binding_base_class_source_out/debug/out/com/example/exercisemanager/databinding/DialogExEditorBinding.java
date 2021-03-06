// Generated by view binder compiler. Do not edit!
package com.example.exercisemanager.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.exercisemanager.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DialogExEditorBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final ImageButton btnCloseCreateDialog;

  @NonNull
  public final Button btnExEditorAddMuscle;

  @NonNull
  public final ImageButton btnSaveItem;

  @NonNull
  public final ScrollView clExc;

  @NonNull
  public final ConstraintLayout constraintLayout;

  @NonNull
  public final EditText etEditEdescription;

  @NonNull
  public final EditText etEditEname;

  @NonNull
  public final RecyclerView rvPickMusclesEdit;

  @NonNull
  public final TextView tvEditEdescription;

  @NonNull
  public final TextView tvEditEname;

  @NonNull
  public final TextView tvPageName;

  private DialogExEditorBinding(@NonNull ScrollView rootView,
      @NonNull ImageButton btnCloseCreateDialog, @NonNull Button btnExEditorAddMuscle,
      @NonNull ImageButton btnSaveItem, @NonNull ScrollView clExc,
      @NonNull ConstraintLayout constraintLayout, @NonNull EditText etEditEdescription,
      @NonNull EditText etEditEname, @NonNull RecyclerView rvPickMusclesEdit,
      @NonNull TextView tvEditEdescription, @NonNull TextView tvEditEname,
      @NonNull TextView tvPageName) {
    this.rootView = rootView;
    this.btnCloseCreateDialog = btnCloseCreateDialog;
    this.btnExEditorAddMuscle = btnExEditorAddMuscle;
    this.btnSaveItem = btnSaveItem;
    this.clExc = clExc;
    this.constraintLayout = constraintLayout;
    this.etEditEdescription = etEditEdescription;
    this.etEditEname = etEditEname;
    this.rvPickMusclesEdit = rvPickMusclesEdit;
    this.tvEditEdescription = tvEditEdescription;
    this.tvEditEname = tvEditEname;
    this.tvPageName = tvPageName;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogExEditorBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogExEditorBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_ex_editor, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogExEditorBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_close_create_dialog;
      ImageButton btnCloseCreateDialog = ViewBindings.findChildViewById(rootView, id);
      if (btnCloseCreateDialog == null) {
        break missingId;
      }

      id = R.id.btn_ex_editor_add_muscle;
      Button btnExEditorAddMuscle = ViewBindings.findChildViewById(rootView, id);
      if (btnExEditorAddMuscle == null) {
        break missingId;
      }

      id = R.id.btn_save_item;
      ImageButton btnSaveItem = ViewBindings.findChildViewById(rootView, id);
      if (btnSaveItem == null) {
        break missingId;
      }

      ScrollView clExc = (ScrollView) rootView;

      id = R.id.constraintLayout;
      ConstraintLayout constraintLayout = ViewBindings.findChildViewById(rootView, id);
      if (constraintLayout == null) {
        break missingId;
      }

      id = R.id.et_edit_edescription;
      EditText etEditEdescription = ViewBindings.findChildViewById(rootView, id);
      if (etEditEdescription == null) {
        break missingId;
      }

      id = R.id.et_edit_ename;
      EditText etEditEname = ViewBindings.findChildViewById(rootView, id);
      if (etEditEname == null) {
        break missingId;
      }

      id = R.id.rv_pick_muscles_edit;
      RecyclerView rvPickMusclesEdit = ViewBindings.findChildViewById(rootView, id);
      if (rvPickMusclesEdit == null) {
        break missingId;
      }

      id = R.id.tv_edit_edescription;
      TextView tvEditEdescription = ViewBindings.findChildViewById(rootView, id);
      if (tvEditEdescription == null) {
        break missingId;
      }

      id = R.id.tv_edit_ename;
      TextView tvEditEname = ViewBindings.findChildViewById(rootView, id);
      if (tvEditEname == null) {
        break missingId;
      }

      id = R.id.tv_page_name;
      TextView tvPageName = ViewBindings.findChildViewById(rootView, id);
      if (tvPageName == null) {
        break missingId;
      }

      return new DialogExEditorBinding((ScrollView) rootView, btnCloseCreateDialog,
          btnExEditorAddMuscle, btnSaveItem, clExc, constraintLayout, etEditEdescription,
          etEditEname, rvPickMusclesEdit, tvEditEdescription, tvEditEname, tvPageName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
