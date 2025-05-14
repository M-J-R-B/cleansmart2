package com.example.cleansmart;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001f\u001a\u00020 H\u0002J\b\u0010!\u001a\u00020\"H\u0002J\u0012\u0010#\u001a\u00020 2\b\u0010$\u001a\u0004\u0018\u00010%H\u0014J\b\u0010&\u001a\u00020 H\u0002J\b\u0010\'\u001a\u00020 H\u0002J\b\u0010(\u001a\u00020 H\u0002J\b\u0010)\u001a\u00020\"H\u0002J\b\u0010*\u001a\u00020\"H\u0002J\b\u0010+\u001a\u00020\"H\u0002J\b\u0010,\u001a\u00020\"H\u0002J\b\u0010-\u001a\u00020\"H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006."}, d2 = {"Lcom/example/cleansmart/SignUpActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "TAG", "", "backButton", "Landroid/widget/ImageButton;", "binding", "Lcom/example/cleansmart/databinding/ActivitySignupBinding;", "btnSignUp", "Lcom/google/android/material/button/MaterialButton;", "confirmPasswordInputLayout", "Lcom/google/android/material/textfield/TextInputLayout;", "emailInputLayout", "etConfirmPassword", "Lcom/google/android/material/textfield/TextInputEditText;", "etEmail", "etName", "etPassword", "gson", "Lcom/google/gson/Gson;", "nameInputLayout", "passwordInputLayout", "progressBar", "Lcom/google/android/material/progressindicator/CircularProgressIndicator;", "secureStorage", "Lcom/example/cleansmart/utils/SecureStorageManager;", "sessionManager", "Lcom/example/cleansmart/utils/SessionManager;", "signInLink", "Landroid/widget/TextView;", "initializeViews", "", "isNetworkAvailable", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "runNetworkDiagnostics", "setupClickListeners", "setupTextWatchers", "validateConfirmPassword", "validateEmail", "validateInputs", "validateName", "validatePassword", "app_debug"})
public final class SignUpActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.cleansmart.databinding.ActivitySignupBinding binding;
    private com.example.cleansmart.utils.SessionManager sessionManager;
    private com.example.cleansmart.utils.SecureStorageManager secureStorage;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "SignUpActivity";
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    private com.google.android.material.textfield.TextInputLayout nameInputLayout;
    private com.google.android.material.textfield.TextInputLayout emailInputLayout;
    private com.google.android.material.textfield.TextInputLayout passwordInputLayout;
    private com.google.android.material.textfield.TextInputLayout confirmPasswordInputLayout;
    private com.google.android.material.textfield.TextInputEditText etName;
    private com.google.android.material.textfield.TextInputEditText etEmail;
    private com.google.android.material.textfield.TextInputEditText etPassword;
    private com.google.android.material.textfield.TextInputEditText etConfirmPassword;
    private com.google.android.material.button.MaterialButton btnSignUp;
    private android.widget.TextView signInLink;
    private android.widget.ImageButton backButton;
    private com.google.android.material.progressindicator.CircularProgressIndicator progressBar;
    
    public SignUpActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initializeViews() {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void setupTextWatchers() {
    }
    
    private final boolean validateInputs() {
        return false;
    }
    
    private final boolean validateName() {
        return false;
    }
    
    private final boolean validateEmail() {
        return false;
    }
    
    private final boolean validatePassword() {
        return false;
    }
    
    private final boolean validateConfirmPassword() {
        return false;
    }
    
    private final boolean isNetworkAvailable() {
        return false;
    }
    
    private final void runNetworkDiagnostics() {
    }
}