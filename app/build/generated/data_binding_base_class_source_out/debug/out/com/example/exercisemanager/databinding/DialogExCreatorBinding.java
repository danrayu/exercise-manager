// Generated by view binder compiler. Do not edit!
package com.example.exercisemanager.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.example.exercisemanager.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DialogExCreatorBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnExCreatorAddMuscle;

  @NonNull
  public final ConstraintLayout clExc;

  @NonNull
  public final TextView createExerciseDialogName;

  @NonNull
  public final EditText etEnterEdescription;

  @NonNull
  public final EditText etEnterEname;

  @NonNull
  public final RecyclerView rvPickMuscles;

  @NonNull
  public final TextView tvEnterEdescription;

  @NonNull
  public final TextView tvEnterEname;

  private DialogExCreatorBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button btnExCreatorAddMuscle, @NonNull ConstraintLayout clExc,
      @NonNull TextView createExerciseDialogName, @NonNull EditText etEnterEdescription,
      @NonNull EditText etEnterEname, @NonNull RecyclerView rvPickMuscles,
      @NonNull TextView tvEnterEdescription, @NonNull TextView tvEnterEname) {
    this.rootView = rootView;
    this.btnExCreatorAddMuscle = btnExCreatorAddMuscle;
    this.clExc = clExc;
    this.createExerciseDialogName = createExerciseDialogName;
    this.etEnterEdescription = etEnterEdescription;
    this.etEnterEname = etEnterEname;
    this.rvPickMuscles = rvPickMuscles;
    this.tvEnterEdescription = tvEnterEdescription;
    this.tvEnterEname = tvEnterEname;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogExCreatorBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogExCreatorBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_ex_creator, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogExCreatorBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_ex_creator_add_muscle;
      Button btnExCreatorAddMuscle = rootView.findViewById(id);
      if (btnExCreatorAddMuscle == null) {
        break missingId;
      }

      ConstraintLayout clExc = (ConstraintLayout) rootView;

      id = R.id.create_exercise_dialog_name;
      TextView createExerciseDialogName = rootView.findViewById(id);
      if (createExerciseDialogName == null) {
        break missingId;
      }

      id = R.id.et_enter_edescription;
      EditText etEnterEdescription = rootView.findViewById(id);
      if (etEnterEdescription == null) {
        break missingId;
      }

      id = R.id.et_enter_ename;
      EditText etEnterEname = rootView.findViewById(id);
      if (etEnterEname == null) {
        break missingId;
      }

      id = R.id.rv_pick_muscles;
      RecyclerView rvPickMuscles = rootView.findViewById(id);
      if (rvPickMuscles == null) {
        break missingId;
      }

      id = R.id.tv_enter_edescription;
      TextView tvEnterEdescription = rootView.findViewById(id);
      if (tvEnterEdescription == null) {
        break missingId;
      }

      id = R.id.tv_enter_ename;
      TextView tvEnterEname = rootView.findViewById(id);
      if (tvEnterEname == null) {
        break missingId;
      }

      return new DialogExCreatorBinding((ConstraintLayout) rootView, btnExCreatorAddMuscle, clExc,
          createExerciseDialogName, etEnterEdescription, etEnterEname, rvPickMuscles,
          tvEnterEdescription, tvEnterEname);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
