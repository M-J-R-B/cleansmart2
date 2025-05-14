package com.example.cleansmart;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0018B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\b\u0010\u0013\u001a\u00020\fH\u0016J\b\u0010\u0014\u001a\u00020\fH\u0002J\u000e\u0010\u0015\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\tJ\b\u0010\u0016\u001a\u00020\u0017H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/example/cleansmart/DeleteAccountDialogFragment;", "Landroidx/fragment/app/DialogFragment;", "()V", "TAG", "", "confirmationInput", "Lcom/google/android/material/textfield/TextInputEditText;", "emailInput", "listener", "Lcom/example/cleansmart/DeleteAccountDialogFragment$DeleteAccountDialogListener;", "passwordInput", "deleteProfileImage", "", "context", "Landroid/content/Context;", "onCreateDialog", "Landroid/app/Dialog;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "performDeleteAccount", "setDeleteAccountDialogListener", "validateInputs", "", "DeleteAccountDialogListener", "app_debug"})
public final class DeleteAccountDialogFragment extends androidx.fragment.app.DialogFragment {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "DeleteAccountDialog";
    @org.jetbrains.annotations.Nullable()
    private com.example.cleansmart.DeleteAccountDialogFragment.DeleteAccountDialogListener listener;
    private com.google.android.material.textfield.TextInputEditText emailInput;
    private com.google.android.material.textfield.TextInputEditText passwordInput;
    private com.google.android.material.textfield.TextInputEditText confirmationInput;
    
    public DeleteAccountDialogFragment() {
        super();
    }
    
    public final void setDeleteAccountDialogListener(@org.jetbrains.annotations.NotNull()
    com.example.cleansmart.DeleteAccountDialogFragment.DeleteAccountDialogListener listener) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.app.Dialog onCreateDialog(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    private final boolean validateInputs() {
        return false;
    }
    
    private final void performDeleteAccount() {
    }
    
    private final void deleteProfileImage(android.content.Context context) {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&\u00a8\u0006\u0005"}, d2 = {"Lcom/example/cleansmart/DeleteAccountDialogFragment$DeleteAccountDialogListener;", "", "onDeleteAccountCancelled", "", "onDeleteAccountConfirmed", "app_debug"})
    public static abstract interface DeleteAccountDialogListener {
        
        public abstract void onDeleteAccountConfirmed();
        
        public abstract void onDeleteAccountCancelled();
    }
}