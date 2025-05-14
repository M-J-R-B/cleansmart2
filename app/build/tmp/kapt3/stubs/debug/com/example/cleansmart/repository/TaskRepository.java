package com.example.cleansmart.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J$\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n2\u0006\u0010\u000b\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\f\u0010\rJ\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J$\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\n2\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017J\"\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000f0\nH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aJ,\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\b0\n2\u0006\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001d\u0010\u001eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001f"}, d2 = {"Lcom/example/cleansmart/repository/TaskRepository;", "", "apiService", "Lcom/example/cleansmart/network/ApiService;", "(Lcom/example/cleansmart/network/ApiService;)V", "convertNetworkTaskToModelTask", "Lcom/example/cleansmart/models/Task;", "networkTask", "Lcom/example/cleansmart/network/Task;", "createTask", "Lkotlin/Result;", "task", "createTask-gIAlu-s", "(Lcom/example/cleansmart/network/Task;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generatePredefinedTasks", "", "area", "", "generateTasks", "Lcom/example/cleansmart/models/TaskGenerationResponse;", "request", "Lcom/example/cleansmart/models/TaskGenerationRequest;", "generateTasks-gIAlu-s", "(Lcom/example/cleansmart/models/TaskGenerationRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTasks", "getTasks-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateTask", "taskId", "updateTask-0E7RQCE", "(Ljava/lang/String;Lcom/example/cleansmart/network/Task;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class TaskRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.cleansmart.network.ApiService apiService = null;
    
    public TaskRepository(@org.jetbrains.annotations.NotNull()
    com.example.cleansmart.network.ApiService apiService) {
        super();
    }
    
    private final com.example.cleansmart.models.Task convertNetworkTaskToModelTask(com.example.cleansmart.network.Task networkTask) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.cleansmart.models.Task> generatePredefinedTasks(@org.jetbrains.annotations.NotNull()
    java.lang.String area) {
        return null;
    }
}