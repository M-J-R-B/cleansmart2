// Generated by view binder compiler. Do not edit!
package com.example.cleansmart.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.cleansmart.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class SuccessCheckLayoutBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView successIcon;

  @NonNull
  public final LinearLayout successLayout;

  private SuccessCheckLayoutBinding(@NonNull LinearLayout rootView, @NonNull ImageView successIcon,
      @NonNull LinearLayout successLayout) {
    this.rootView = rootView;
    this.successIcon = successIcon;
    this.successLayout = successLayout;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static SuccessCheckLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static SuccessCheckLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.success_check_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static SuccessCheckLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.successIcon;
      ImageView successIcon = ViewBindings.findChildViewById(rootView, id);
      if (successIcon == null) {
        break missingId;
      }

      LinearLayout successLayout = (LinearLayout) rootView;

      return new SuccessCheckLayoutBinding((LinearLayout) rootView, successIcon, successLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
