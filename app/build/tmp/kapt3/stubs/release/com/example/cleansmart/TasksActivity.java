package com.example.cleansmart;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0014H\u0002J\u0010\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\b\u0010\u001b\u001a\u00020\u0014H\u0002J\b\u0010\u001c\u001a\u00020\u0014H\u0002J\u0012\u0010\u001d\u001a\u00020\u00142\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0014J\u0012\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0016J\u0010\u0010$\u001a\u00020!2\u0006\u0010%\u001a\u00020&H\u0016J\u0018\u0010\'\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010(\u001a\u00020!H\u0002J\b\u0010)\u001a\u00020\u0014H\u0002J\b\u0010*\u001a\u00020\u0014H\u0002J\b\u0010+\u001a\u00020\u0014H\u0002J\u0010\u0010,\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010-\u001a\u00020\u0014H\u0002J\u0018\u0010.\u001a\u00020\u00142\u0006\u0010/\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0010\u00100\u001a\u00020\u00142\u0006\u00101\u001a\u00020\u001aH\u0002J\b\u00102\u001a\u00020\u0014H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010\u00a8\u00063"}, d2 = {"Lcom/example/cleansmart/TasksActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "allTaskGroups", "", "Lcom/example/cleansmart/models/TaskGroup;", "binding", "Lcom/example/cleansmart/databinding/ActivityTasksBinding;", "currentTaskGroup", "secureStorageManager", "Lcom/example/cleansmart/utils/SecureStorageManager;", "taskAdapter", "Lcom/example/cleansmart/adapters/TaskAdapter;", "viewModel", "Lcom/example/cleansmart/viewmodels/TaskViewModel;", "getViewModel", "()Lcom/example/cleansmart/viewmodels/TaskViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "deleteTask", "", "task", "Lcom/example/cleansmart/models/Task;", "loadTaskGroups", "loadTaskGroupsWithSelected", "selectedTaskGroupId", "", "navigateToLogin", "observeTaskGroups", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "", "menu", "Landroid/view/Menu;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onTaskClicked", "isCheckboxClick", "setupClickListeners", "setupObservers", "setupRecyclerView", "showDeleteTaskDialog", "showTaskGroupSelectionDialog", "tryFallbackLoad", "userId", "tryLocalFallback", "taskGroupId", "updateTaskGroupUI", "app_release"})
public final class TasksActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.cleansmart.databinding.ActivityTasksBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private com.example.cleansmart.adapters.TaskAdapter taskAdapter;
    private com.example.cleansmart.utils.SecureStorageManager secureStorageManager;
    @org.jetbrains.annotations.Nullable()
    private com.example.cleansmart.models.TaskGroup currentTaskGroup;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.example.cleansmart.models.TaskGroup> allTaskGroups;
    
    public TasksActivity() {
        super();
    }
    
    private final com.example.cleansmart.viewmodels.TaskViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public boolean onCreateOptionsMenu(@org.jetbrains.annotations.Nullable()
    android.view.Menu menu) {
        return false;
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void setupObservers() {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void loadTaskGroups() {
    }
    
    private final void loadTaskGroupsWithSelected(java.lang.String selectedTaskGroupId) {
    }
    
    private final void tryFallbackLoad(java.lang.String userId, java.lang.String selectedTaskGroupId) {
    }
    
    private final void tryLocalFallback(java.lang.String taskGroupId) {
    }
    
    private final void navigateToLogin() {
    }
    
    private final void observeTaskGroups() {
    }
    
    private final void onTaskClicked(com.example.cleansmart.models.Task task, boolean isCheckboxClick) {
    }
    
    private final void showTaskGroupSelectionDialog() {
    }
    
    private final void updateTaskGroupUI() {
    }
    
    private final void showDeleteTaskDialog(com.example.cleansmart.models.Task task) {
    }
    
    private final void deleteTask(com.example.cleansmart.models.Task task) {
    }
}