package com.example.cleansmart.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ$\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ*\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00110\n2\u0006\u0010\u0012\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0013\u0010\u000fJ,\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\n2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0006H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017J,\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001aH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001b\u0010\u001cR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001d"}, d2 = {"Lcom/example/cleansmart/repository/TaskGroupRepository;", "", "apiService", "Lcom/example/cleansmart/network/ApiService;", "(Lcom/example/cleansmart/network/ApiService;)V", "convertToTaskDataTransfer", "Lcom/example/cleansmart/models/TaskDataTransfer;", "taskGroup", "Lcom/example/cleansmart/models/TaskGroup;", "deleteTaskGroup", "Lkotlin/Result;", "", "taskGroupId", "", "deleteTaskGroup-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserTaskGroups", "", "userId", "getUserTaskGroups-gIAlu-s", "saveTaskGroup", "taskData", "saveTaskGroup-0E7RQCE", "(Ljava/lang/String;Lcom/example/cleansmart/models/TaskDataTransfer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateTaskGroupProgress", "progress", "", "updateTaskGroupProgress-0E7RQCE", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
public final class TaskGroupRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.cleansmart.network.ApiService apiService = null;
    
    public TaskGroupRepository(@org.jetbrains.annotations.NotNull()
    com.example.cleansmart.network.ApiService apiService) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.cleansmart.models.TaskDataTransfer convertToTaskDataTransfer(@org.jetbrains.annotations.NotNull()
    com.example.cleansmart.models.TaskGroup taskGroup) {
        return null;
    }
}