package com.example.cleansmart.activities;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 -2\u00020\u0001:\u0001-B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\b\u0010\u001b\u001a\u00020\u001aH\u0002J\u0010\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\b\u0010\u001f\u001a\u00020\u001aH\u0002J\u0012\u0010 \u001a\u00020\u001a2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0014J\u0010\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020%H\u0016J\b\u0010&\u001a\u00020\u001aH\u0002J\b\u0010\'\u001a\u00020\u001aH\u0002J\b\u0010(\u001a\u00020\u001aH\u0002J\b\u0010)\u001a\u00020\u001aH\u0002J\u0016\u0010*\u001a\u00020\u001a2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002J\b\u0010,\u001a\u00020\u001aH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\f\u001a\u0010\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u00040\u00040\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u000f\u001a\u0010\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u00100\u00100\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0013\u001a\u00020\u00148BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006."}, d2 = {"Lcom/example/cleansmart/activities/CameraTaskActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "TAG", "", "binding", "Lcom/example/cleansmart/databinding/ActivityCameraTaskBinding;", "currentTasks", "", "Lcom/example/cleansmart/models/Task;", "isSelectAllChecked", "", "requestCameraPermissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "kotlin.jvm.PlatformType", "takePictureLauncher", "Landroid/content/Intent;", "taskAdapter", "Lcom/example/cleansmart/adapters/TaskAdapter;", "viewModel", "Lcom/example/cleansmart/viewmodels/TaskGenerationViewModel;", "getViewModel", "()Lcom/example/cleansmart/viewmodels/TaskGenerationViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "addSelectedTasksToDashboard", "", "checkCameraPermissionAndOpen", "displayCapturedImage", "bitmap", "Landroid/graphics/Bitmap;", "observeViewModel", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "openCamera", "setupClickListeners", "setupRecyclerView", "setupToolbar", "showTasks", "tasks", "updateAddButtonState", "Companion", "app_debug"})
public final class CameraTaskActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.cleansmart.databinding.ActivityCameraTaskBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private com.example.cleansmart.adapters.TaskAdapter taskAdapter;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "CameraTaskActivity";
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.example.cleansmart.models.Task> currentTasks;
    private boolean isSelectAllChecked = false;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String RESULT_TASK_DATA = "result_task_data";
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> requestCameraPermissionLauncher = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> takePictureLauncher = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.cleansmart.activities.CameraTaskActivity.Companion Companion = null;
    
    public CameraTaskActivity() {
        super();
    }
    
    private final com.example.cleansmart.viewmodels.TaskGenerationViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupToolbar() {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void updateAddButtonState() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void checkCameraPermissionAndOpen() {
    }
    
    private final void openCamera() {
    }
    
    private final void displayCapturedImage(android.graphics.Bitmap bitmap) {
    }
    
    private final void showTasks(java.util.List<com.example.cleansmart.models.Task> tasks) {
    }
    
    private final void addSelectedTasksToDashboard() {
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/cleansmart/activities/CameraTaskActivity$Companion;", "", "()V", "RESULT_TASK_DATA", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}