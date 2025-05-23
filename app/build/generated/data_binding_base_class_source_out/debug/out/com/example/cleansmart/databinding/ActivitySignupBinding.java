// Generated by view binder compiler. Do not edit!
package com.example.cleansmart.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.cleansmart.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySignupBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final ImageButton backButton;

  @NonNull
  public final MaterialButton btnSignUp;

  @NonNull
  public final TextInputLayout confirmPasswordInputLayout;

  @NonNull
  public final TextInputLayout emailInputLayout;

  @NonNull
  public final TextInputEditText etConfirmPassword;

  @NonNull
  public final TextInputEditText etEmail;

  @NonNull
  public final TextInputEditText etName;

  @NonNull
  public final TextInputEditText etPassword;

  @NonNull
  public final LinearLayout headerLayout;

  @NonNull
  public final TextInputLayout nameInputLayout;

  @NonNull
  public final TextInputLayout passwordInputLayout;

  @NonNull
  public final CircularProgressIndicator progressBar;

  @NonNull
  public final TextView signInLink;

  private ActivitySignupBinding(@NonNull FrameLayout rootView, @NonNull ImageButton backButton,
      @NonNull MaterialButton btnSignUp, @NonNull TextInputLayout confirmPasswordInputLayout,
      @NonNull TextInputLayout emailInputLayout, @NonNull TextInputEditText etConfirmPassword,
      @NonNull TextInputEditText etEmail, @NonNull TextInputEditText etName,
      @NonNull TextInputEditText etPassword, @NonNull LinearLayout headerLayout,
      @NonNull TextInputLayout nameInputLayout, @NonNull TextInputLayout passwordInputLayout,
      @NonNull CircularProgressIndicator progressBar, @NonNull TextView signInLink) {
    this.rootView = rootView;
    this.backButton = backButton;
    this.btnSignUp = btnSignUp;
    this.confirmPasswordInputLayout = confirmPasswordInputLayout;
    this.emailInputLayout = emailInputLayout;
    this.etConfirmPassword = etConfirmPassword;
    this.etEmail = etEmail;
    this.etName = etName;
    this.etPassword = etPassword;
    this.headerLayout = headerLayout;
    this.nameInputLayout = nameInputLayout;
    this.passwordInputLayout = passwordInputLayout;
    this.progressBar = progressBar;
    this.signInLink = signInLink;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_signup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backButton;
      ImageButton backButton = ViewBindings.findChildViewById(rootView, id);
      if (backButton == null) {
        break missingId;
      }

      id = R.id.btnSignUp;
      MaterialButton btnSignUp = ViewBindings.findChildViewById(rootView, id);
      if (btnSignUp == null) {
        break missingId;
      }

      id = R.id.confirmPasswordInputLayout;
      TextInputLayout confirmPasswordInputLayout = ViewBindings.findChildViewById(rootView, id);
      if (confirmPasswordInputLayout == null) {
        break missingId;
      }

      id = R.id.emailInputLayout;
      TextInputLayout emailInputLayout = ViewBindings.findChildViewById(rootView, id);
      if (emailInputLayout == null) {
        break missingId;
      }

      id = R.id.etConfirmPassword;
      TextInputEditText etConfirmPassword = ViewBindings.findChildViewById(rootView, id);
      if (etConfirmPassword == null) {
        break missingId;
      }

      id = R.id.etEmail;
      TextInputEditText etEmail = ViewBindings.findChildViewById(rootView, id);
      if (etEmail == null) {
        break missingId;
      }

      id = R.id.etName;
      TextInputEditText etName = ViewBindings.findChildViewById(rootView, id);
      if (etName == null) {
        break missingId;
      }

      id = R.id.etPassword;
      TextInputEditText etPassword = ViewBindings.findChildViewById(rootView, id);
      if (etPassword == null) {
        break missingId;
      }

      id = R.id.header_layout;
      LinearLayout headerLayout = ViewBindings.findChildViewById(rootView, id);
      if (headerLayout == null) {
        break missingId;
      }

      id = R.id.nameInputLayout;
      TextInputLayout nameInputLayout = ViewBindings.findChildViewById(rootView, id);
      if (nameInputLayout == null) {
        break missingId;
      }

      id = R.id.passwordInputLayout;
      TextInputLayout passwordInputLayout = ViewBindings.findChildViewById(rootView, id);
      if (passwordInputLayout == null) {
        break missingId;
      }

      id = R.id.progressBar;
      CircularProgressIndicator progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.signInLink;
      TextView signInLink = ViewBindings.findChildViewById(rootView, id);
      if (signInLink == null) {
        break missingId;
      }

      return new ActivitySignupBinding((FrameLayout) rootView, backButton, btnSignUp,
          confirmPasswordInputLayout, emailInputLayout, etConfirmPassword, etEmail, etName,
          etPassword, headerLayout, nameInputLayout, passwordInputLayout, progressBar, signInLink);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
