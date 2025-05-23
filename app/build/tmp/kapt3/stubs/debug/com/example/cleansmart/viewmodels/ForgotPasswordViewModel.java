package com.example.cleansmart.viewmodels;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0010"}, d2 = {"Lcom/example/cleansmart/viewmodels/ForgotPasswordViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_forgotPasswordState", "Landroidx/lifecycle/MutableLiveData;", "Lcom/example/cleansmart/viewmodels/ForgotPasswordState;", "apiService", "Lcom/example/cleansmart/network/ApiService;", "forgotPasswordState", "Landroidx/lifecycle/LiveData;", "getForgotPasswordState", "()Landroidx/lifecycle/LiveData;", "requestPasswordReset", "", "email", "", "app_debug"})
public final class ForgotPasswordViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.example.cleansmart.viewmodels.ForgotPasswordState> _forgotPasswordState = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.example.cleansmart.viewmodels.ForgotPasswordState> forgotPasswordState = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.cleansmart.network.ApiService apiService = null;
    
    public ForgotPasswordViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.example.cleansmart.viewmodels.ForgotPasswordState> getForgotPasswordState() {
        return null;
    }
    
    public final void requestPasswordReset(@org.jetbrains.annotations.NotNull()
    java.lang.String email) {
    }
}