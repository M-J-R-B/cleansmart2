package com.example.cleansmart.network;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u001e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\r0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0014H\u00a7@\u00a2\u0006\u0002\u0010\u0015\u00a8\u0006\u0017"}, d2 = {"Lcom/example/cleansmart/network/ApiService;", "", "changePassword", "Lretrofit2/Response;", "Lcom/example/cleansmart/network/ChangePasswordResponse;", "request", "Lcom/example/cleansmart/network/ChangePasswordRequest;", "(Lcom/example/cleansmart/network/ChangePasswordRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "forgotPassword", "Lcom/example/cleansmart/network/ForgotPasswordResponse;", "Lcom/example/cleansmart/network/ForgotPasswordRequest;", "(Lcom/example/cleansmart/network/ForgotPasswordRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "login", "Lcom/example/cleansmart/network/AuthResponse;", "Lcom/example/cleansmart/network/LoginRequest;", "(Lcom/example/cleansmart/network/LoginRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "Lcom/example/cleansmart/network/LogoutResponse;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signup", "Lcom/example/cleansmart/network/SignupRequest;", "(Lcom/example/cleansmart/network/SignupRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public abstract interface ApiService {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/example/cleansmart/network/ApiService$Companion;", "", "()V", "create", "Lcom/example/cleansmart/network/ApiService;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.cleansmart.network.ApiService create() {
            return null;
        }
    }
}