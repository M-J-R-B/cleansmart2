package com.example.cleansmart.dialogs;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0014J\b\u0010\r\u001a\u00020\nH\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/example/cleansmart/dialogs/TaskDetailsDialog;", "Landroid/app/Dialog;", "context", "Landroid/content/Context;", "taskData", "Lcom/example/cleansmart/models/TaskDataTransfer;", "(Landroid/content/Context;Lcom/example/cleansmart/models/TaskDataTransfer;)V", "binding", "Lcom/example/cleansmart/databinding/DialogTaskDetailsBinding;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "setupViews", "app_debug"})
public final class TaskDetailsDialog extends android.app.Dialog {
    @org.jetbrains.annotations.NotNull()
    private final com.example.cleansmart.models.TaskDataTransfer taskData = null;
    private com.example.cleansmart.databinding.DialogTaskDetailsBinding binding;
    
    public TaskDetailsDialog(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.example.cleansmart.models.TaskDataTransfer taskData) {
        super(null);
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupViews() {
    }
}