package com.example.cleansmart.network;

/**
 * Retrofit API interface for CleanSmart app
 * This interface defines all network requests for the application
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000 >2\u00020\u0001:\u0001>J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\n\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00032\b\b\u0001\u0010\u0012\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u001e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00110\u00032\b\b\u0001\u0010\u0016\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u001e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0019H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u001e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u001dH\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0003H\u00a7@\u00a2\u0006\u0002\u0010!J\u001a\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0#0\u0003H\u00a7@\u00a2\u0006\u0002\u0010!J\u001e\u0010$\u001a\b\u0012\u0004\u0012\u00020 0\u00032\b\b\u0001\u0010%\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u001e\u0010&\u001a\b\u0012\u0004\u0012\u00020\'0\u00032\b\b\u0001\u0010\u0005\u001a\u00020(H\u00a7@\u00a2\u0006\u0002\u0010)J\u0014\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\u0003H\u00a7@\u00a2\u0006\u0002\u0010!J\u001e\u0010,\u001a\b\u0012\u0004\u0012\u00020-0\u00032\b\b\u0001\u0010\u0005\u001a\u00020.H\u00a7@\u00a2\u0006\u0002\u0010/J\u001e\u00100\u001a\b\u0012\u0004\u0012\u00020\'0\u00032\b\b\u0001\u0010\u0005\u001a\u000201H\u00a7@\u00a2\u0006\u0002\u00102J0\u00103\u001a\b\u0012\u0004\u0012\u00020-0\u00032\b\b\u0001\u00104\u001a\u00020\u00132\u0010\b\u0001\u00105\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010#H\u00a7@\u00a2\u0006\u0002\u00106J\u001e\u00107\u001a\b\u0012\u0004\u0012\u00020-0\u00032\b\b\u0001\u0010\u0005\u001a\u00020.H\u00a7@\u00a2\u0006\u0002\u0010/J(\u00108\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0012\u001a\u00020\u00132\b\b\u0001\u0010\n\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u00109J(\u0010:\u001a\b\u0012\u0004\u0012\u00020-0\u00032\b\b\u0001\u0010\u0016\u001a\u00020\u00132\b\b\u0001\u0010;\u001a\u00020<H\u00a7@\u00a2\u0006\u0002\u0010=\u00a8\u0006?"}, d2 = {"Lcom/example/cleansmart/network/ApiService;", "", "changePassword", "Lretrofit2/Response;", "Lcom/example/cleansmart/network/ChangePasswordResponse;", "request", "Lcom/example/cleansmart/network/ChangePasswordRequest;", "(Lcom/example/cleansmart/network/ChangePasswordRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createTask", "Lcom/example/cleansmart/network/Task;", "task", "(Lcom/example/cleansmart/network/Task;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAccount", "Lcom/example/cleansmart/network/DeleteAccountResponse;", "Lcom/example/cleansmart/network/DeleteAccountRequest;", "(Lcom/example/cleansmart/network/DeleteAccountRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteTask", "Lcom/example/cleansmart/network/DeleteResponse;", "taskId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteTaskGroup", "taskGroupId", "forgotPassword", "Lcom/example/cleansmart/network/ForgotPasswordResponse;", "Lcom/example/cleansmart/network/ForgotPasswordRequest;", "(Lcom/example/cleansmart/network/ForgotPasswordRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateTasks", "Lcom/example/cleansmart/network/GenerateTasksResponse;", "Lcom/example/cleansmart/network/GenerateTasksRequest;", "(Lcom/example/cleansmart/network/GenerateTasksRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTaskGroups", "Lcom/example/cleansmart/models/GetTaskGroupsResponse;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTasks", "", "getUserTaskGroups", "userId", "login", "Lcom/example/cleansmart/network/AuthResponse;", "Lcom/example/cleansmart/network/LoginRequest;", "(Lcom/example/cleansmart/network/LoginRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "Lcom/example/cleansmart/network/LogoutResponse;", "saveTaskGroup", "Lcom/example/cleansmart/models/SaveTaskGroupResponse;", "Lcom/example/cleansmart/models/SaveTaskGroupRequest;", "(Lcom/example/cleansmart/models/SaveTaskGroupRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signup", "Lcom/example/cleansmart/network/SignupRequest;", "(Lcom/example/cleansmart/network/SignupRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "simpleTestSaveTaskGroup", "areaName", "tasks", "(Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "testSaveTaskGroup", "updateTask", "(Ljava/lang/String;Lcom/example/cleansmart/network/Task;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateTaskGroupProgress", "updateRequest", "Lcom/example/cleansmart/network/UpdateProgressRequest;", "(Ljava/lang/String;Lcom/example/cleansmart/network/UpdateProgressRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public abstract interface ApiService {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BASE_URL = "http://192.168.1.9:5000/api/";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.cleansmart.network.ApiService.Companion Companion = null;
    
    @retrofit2.http.Headers(value = {"Content-Type: application/json"})
    @retrofit2.http.POST(value = "auth/signup")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object signup(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.example.cleansmart.network.SignupRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.cleansmart.network.AuthResponse>> $completion);
    
    @retrofit2.http.Headers(value = {"Content-Type: application/json"})
    @retrofit2.http.POST(value = "auth/login")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object login(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.example.cleansmart.network.LoginRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.cleansmart.network.AuthResponse>> $completion);
    
    @retrofit2.http.Headers(value = {"Content-Type: application/json"})
    @retrofit2.http.POST(value = "auth/logout")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object logout(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.cleansmart.network.LogoutResponse>> $completion);
    
    @retrofit2.http.Headers(value = {"Content-Type: application/json"})
    @retrofit2.http.POST(value = "auth/forgot-password")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object forgotPassword(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.example.cleansmart.network.ForgotPasswordRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.cleansmart.network.ForgotPasswordResponse>> $completion);
    
    @retrofit2.http.Headers(value = {"Content-Type: application/json"})
    @retrofit2.http.POST(value = "auth/change-password")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object changePassword(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.example.cleansmart.network.ChangePasswordRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.cleansmart.network.ChangePasswordResponse>> $completion);
    
    @retrofit2.http.Headers(value = {"Content-Type: application/json"})
    @retrofit2.http.HTTP(method = "DELETE", path = "auth/delete-account", hasBody = true)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAccount(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.example.cleansmart.network.DeleteAccountRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.cleansmart.network.DeleteAccountResponse>> $completion);
    
    @retrofit2.http.Headers(value = {"Content-Type: application/json"})
    @retrofit2.http.GET(value = "taskGroups/{userId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUserTaskGroups(@retrofit2.http.Path(value = "userId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.cleansmart.models.GetTaskGroupsResponse>> $completion);
    
    @retrofit2.http.Headers(value = {"Content-Type: application/json"})
    @retrofit2.http.GET(value = "taskGroups")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTaskGroups(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.cleansmart.models.GetTaskGroupsResponse>> $completion);
    
    @retrofit2.http.Headers(value = {"Content-Type: application/json"})
    @retrofit2.http.POST(value = "taskGroups")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveTaskGroup(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.example.cleansmart.models.SaveTaskGroupRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.cleansmart.models.SaveTaskGroupResponse>> $completion);
    
    @retrofit2.http.Headers(value = {"Content-Type: application/json"})
    @retrofit2.http.PUT(value = "taskGroups/{taskGroupId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateTaskGroupProgress(@retrofit2.http.Path(value = "taskGroupId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String taskGroupId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.example.cleansmart.network.UpdateProgressRequest updateRequest, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.cleansmart.models.SaveTaskGroupResponse>> $completion);
    
    @retrofit2.http.Headers(value = {"Content-Type: application/json"})
    @retrofit2.http.DELETE(value = "taskGroups/{taskGroupId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteTaskGroup(@retrofit2.http.Path(value = "taskGroupId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String taskGroupId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.cleansmart.network.DeleteResponse>> $completion);
    
    @retrofit2.http.Headers(value = {"Content-Type: application/json"})
    @retrofit2.http.GET(value = "tasks")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTasks(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.example.cleansmart.network.Task>>> $completion);
    
    @retrofit2.http.Headers(value = {"Content-Type: application/json"})
    @retrofit2.http.POST(value = "tasks")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createTask(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.example.cleansmart.network.Task task, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.cleansmart.network.Task>> $completion);
    
    @retrofit2.http.Headers(value = {"Content-Type: application/json"})
    @retrofit2.http.PUT(value = "tasks/{taskId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateTask(@retrofit2.http.Path(value = "taskId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String taskId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.example.cleansmart.network.Task task, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.cleansmart.network.Task>> $completion);
    
    @retrofit2.http.Headers(value = {"Content-Type: application/json"})
    @retrofit2.http.DELETE(value = "tasks/{taskId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteTask(@retrofit2.http.Path(value = "taskId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String taskId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.cleansmart.network.DeleteResponse>> $completion);
    
    @retrofit2.http.Headers(value = {"Content-Type: application/json"})
    @retrofit2.http.POST(value = "tasks/generate")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object generateTasks(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.example.cleansmart.network.GenerateTasksRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.cleansmart.network.GenerateTasksResponse>> $completion);
    
    @retrofit2.http.Headers(value = {"Content-Type: application/json"})
    @retrofit2.http.POST(value = "taskGroups/test")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object testSaveTaskGroup(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.example.cleansmart.models.SaveTaskGroupRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.cleansmart.models.SaveTaskGroupResponse>> $completion);
    
    @retrofit2.http.FormUrlEncoded()
    @retrofit2.http.POST(value = "taskGroups/simple-test")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object simpleTestSaveTaskGroup(@retrofit2.http.Field(value = "areaName")
    @org.jetbrains.annotations.NotNull()
    java.lang.String areaName, @retrofit2.http.Field(value = "tasks")
    @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.String> tasks, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.cleansmart.models.SaveTaskGroupResponse>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\n\u001a\u00020\u00042\b\u0010\u000b\u001a\u0004\u0018\u00010\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/example/cleansmart/network/ApiService$Companion;", "", "()V", "BASE_URL", "", "DEV_URL", "EMULATOR_URL", "PROD_URL", "create", "Lcom/example/cleansmart/network/ApiService;", "getBaseUrl", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        @org.jetbrains.annotations.NotNull()
        private static final java.lang.String DEV_URL = "http://192.168.1.9:5000/api/";
        @org.jetbrains.annotations.NotNull()
        private static final java.lang.String EMULATOR_URL = "http://10.0.2.2:5000/api/";
        @org.jetbrains.annotations.NotNull()
        private static final java.lang.String PROD_URL = "https://your-production-server.com/api/";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String BASE_URL = "http://192.168.1.9:5000/api/";
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getBaseUrl(@org.jetbrains.annotations.Nullable()
        android.content.Context context) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.cleansmart.network.ApiService create() {
            return null;
        }
    }
}