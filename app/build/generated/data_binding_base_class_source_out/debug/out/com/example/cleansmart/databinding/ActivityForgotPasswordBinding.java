// Generated by view binder compiler. Do not edit!
package com.example.cleansmart.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.cleansmart.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityForgotPasswordBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton backButton;

  @NonNull
  public final EditText codeEditText;

  @NonNull
  public final EditText emailPhoneEditText;

  @NonNull
  public final LinearLayout logoContainer;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final Button resendCodeButton;

  @NonNull
  public final Button signInButton;

  private ActivityForgotPasswordBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageButton backButton, @NonNull EditText codeEditText,
      @NonNull EditText emailPhoneEditText, @NonNull LinearLayout logoContainer,
      @NonNull ProgressBar progressBar, @NonNull Button resendCodeButton,
      @NonNull Button signInButton) {
    this.rootView = rootView;
    this.backButton = backButton;
    this.codeEditText = codeEditText;
    this.emailPhoneEditText = emailPhoneEditText;
    this.logoContainer = logoContainer;
    this.progressBar = progressBar;
    this.resendCodeButton = resendCodeButton;
    this.signInButton = signInButton;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityForgotPasswordBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityForgotPasswordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_forgot_password, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityForgotPasswordBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backButton;
      ImageButton backButton = ViewBindings.findChildViewById(rootView, id);
      if (backButton == null) {
        break missingId;
      }

      id = R.id.codeEditText;
      EditText codeEditText = ViewBindings.findChildViewById(rootView, id);
      if (codeEditText == null) {
        break missingId;
      }

      id = R.id.emailPhoneEditText;
      EditText emailPhoneEditText = ViewBindings.findChildViewById(rootView, id);
      if (emailPhoneEditText == null) {
        break missingId;
      }

      id = R.id.logoContainer;
      LinearLayout logoContainer = ViewBindings.findChildViewById(rootView, id);
      if (logoContainer == null) {
        break missingId;
      }

      id = R.id.progressBar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.resendCodeButton;
      Button resendCodeButton = ViewBindings.findChildViewById(rootView, id);
      if (resendCodeButton == null) {
        break missingId;
      }

      id = R.id.signInButton;
      Button signInButton = ViewBindings.findChildViewById(rootView, id);
      if (signInButton == null) {
        break missingId;
      }

      return new ActivityForgotPasswordBinding((ConstraintLayout) rootView, backButton,
          codeEditText, emailPhoneEditText, logoContainer, progressBar, resendCodeButton,
          signInButton);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
