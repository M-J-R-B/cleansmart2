package com.example.cleansmart.network;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0004#$%&B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u000fJ\b\u0010!\u001a\u00020\"H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\t8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0019\u001a\n \u001b*\u0004\u0018\u00010\u001a0\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/example/cleansmart/network/NetworkClient;", "", "()V", "BASE_URL", "", "TAG", "TIMEOUT_SECONDS", "", "apiService", "Lcom/example/cleansmart/network/ApiService;", "getApiService", "()Lcom/example/cleansmart/network/ApiService;", "apiService$delegate", "Lkotlin/Lazy;", "appContext", "Landroid/content/Context;", "authInterceptor", "Lokhttp3/Interceptor;", "gson", "Lcom/google/gson/Gson;", "loggingInterceptor", "Lokhttp3/logging/HttpLoggingInterceptor;", "networkInterceptor", "okHttpClient", "Lokhttp3/OkHttpClient;", "retrofit", "Lretrofit2/Retrofit;", "kotlin.jvm.PlatformType", "secureStorageManager", "Lcom/example/cleansmart/utils/SecureStorageManager;", "initialize", "", "context", "isNetworkAvailable", "", "ConnectionFailureException", "NetworkTimeoutException", "NoConnectivityException", "ServerUnreachableException", "app_debug"})
public final class NetworkClient {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String BASE_URL = "http://192.168.1.9:5000/api/";
    private static final long TIMEOUT_SECONDS = 30L;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "NetworkClient";
    @org.jetbrains.annotations.NotNull()
    private static final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.Nullable()
    private static com.example.cleansmart.utils.SecureStorageManager secureStorageManager;
    @org.jetbrains.annotations.Nullable()
    private static android.content.Context appContext;
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.logging.HttpLoggingInterceptor loggingInterceptor = null;
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.Interceptor networkInterceptor = null;
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.Interceptor authInterceptor = null;
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.OkHttpClient okHttpClient = null;
    private static final retrofit2.Retrofit retrofit = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.Lazy apiService$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.cleansmart.network.NetworkClient INSTANCE = null;
    
    private NetworkClient() {
        super();
    }
    
    public final void initialize(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.cleansmart.network.ApiService getApiService() {
        return null;
    }
    
    private final boolean isNetworkAvailable() {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/example/cleansmart/network/NetworkClient$ConnectionFailureException;", "Ljava/io/IOException;", "message", "", "(Ljava/lang/String;)V", "app_debug"})
    public static final class ConnectionFailureException extends java.io.IOException {
        
        public ConnectionFailureException(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/example/cleansmart/network/NetworkClient$NetworkTimeoutException;", "Ljava/io/IOException;", "message", "", "(Ljava/lang/String;)V", "app_debug"})
    public static final class NetworkTimeoutException extends java.io.IOException {
        
        public NetworkTimeoutException(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/example/cleansmart/network/NetworkClient$NoConnectivityException;", "Ljava/io/IOException;", "message", "", "(Ljava/lang/String;)V", "app_debug"})
    public static final class NoConnectivityException extends java.io.IOException {
        
        public NoConnectivityException(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/example/cleansmart/network/NetworkClient$ServerUnreachableException;", "Ljava/io/IOException;", "message", "", "(Ljava/lang/String;)V", "app_debug"})
    public static final class ServerUnreachableException extends java.io.IOException {
        
        public ServerUnreachableException(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
            super();
        }
    }
}