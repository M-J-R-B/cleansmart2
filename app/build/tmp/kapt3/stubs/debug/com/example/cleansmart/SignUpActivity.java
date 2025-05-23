package com.example.cleansmart;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014J\b\u0010\u0013\u001a\u00020\u0010H\u0002J\b\u0010\u0014\u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/example/cleansmart/SignUpActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "TAG", "", "binding", "Lcom/example/cleansmart/databinding/ActivitySignupBinding;", "gson", "Lcom/google/gson/Gson;", "secureStorage", "Lcom/example/cleansmart/utils/SecureStorageManager;", "sessionManager", "Lcom/example/cleansmart/utils/SessionManager;", "isNetworkAvailable", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "setupClickListeners", "validateInputs", "app_debug"})
public final class SignUpActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.cleansmart.databinding.ActivitySignupBinding binding;
    private com.example.cleansmart.utils.SessionManager sessionManager;
    private com.example.cleansmart.utils.SecureStorageManager secureStorage;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "SignUpActivity";
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    
    public SignUpActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final boolean isNetworkAvailable() {
        return false;
    }
    
    private final void setupClickListeners() {
    }
    
    private final boolean validateInputs() {
        return false;
    }
}