package com.example.cleansmart;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0016B\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\nH\u0016J\u0018\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nH\u0016J\u0010\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0002H\u0002J\u0018\u0010\u0014\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0007H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/example/cleansmart/TeamMemberAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/cleansmart/TeamMemberAdapter$TeamMemberViewHolder;", "context", "Landroid/content/Context;", "teamMembers", "", "Lcom/example/cleansmart/AboutDevsActivity$TeamMember;", "(Landroid/content/Context;Ljava/util/List;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setupExpansion", "setupSkillTags", "member", "TeamMemberViewHolder", "app_debug"})
public final class TeamMemberAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.cleansmart.TeamMemberAdapter.TeamMemberViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.example.cleansmart.AboutDevsActivity.TeamMember> teamMembers = null;
    
    public TeamMemberAdapter(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.cleansmart.AboutDevsActivity.TeamMember> teamMembers) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.example.cleansmart.TeamMemberAdapter.TeamMemberViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.example.cleansmart.TeamMemberAdapter.TeamMemberViewHolder holder, int position) {
    }
    
    private final void setupSkillTags(com.example.cleansmart.TeamMemberAdapter.TeamMemberViewHolder holder, com.example.cleansmart.AboutDevsActivity.TeamMember member) {
    }
    
    private final void setupExpansion(com.example.cleansmart.TeamMemberAdapter.TeamMemberViewHolder holder) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\fR\u0011\u0010\u0019\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\bR\u0011\u0010\u001b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\fR\u0011\u0010\u001d\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0012\u00a8\u0006\u001f"}, d2 = {"Lcom/example/cleansmart/TeamMemberAdapter$TeamMemberViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "expandIcon", "Landroid/widget/ImageView;", "getExpandIcon", "()Landroid/widget/ImageView;", "funFactText", "Landroid/widget/TextView;", "getFunFactText", "()Landroid/widget/TextView;", "memberBio", "getMemberBio", "memberContent", "Landroid/widget/LinearLayout;", "getMemberContent", "()Landroid/widget/LinearLayout;", "memberHeader", "Landroidx/constraintlayout/widget/ConstraintLayout;", "getMemberHeader", "()Landroidx/constraintlayout/widget/ConstraintLayout;", "memberName", "getMemberName", "memberPhoto", "getMemberPhoto", "memberTitle", "getMemberTitle", "skillsContainer", "getSkillsContainer", "app_debug"})
    public static final class TeamMemberViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final android.widget.ImageView memberPhoto = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView memberName = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView memberTitle = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView memberBio = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView funFactText = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.LinearLayout skillsContainer = null;
        @org.jetbrains.annotations.NotNull()
        private final androidx.constraintlayout.widget.ConstraintLayout memberHeader = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.LinearLayout memberContent = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.ImageView expandIcon = null;
        
        public TeamMemberViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.ImageView getMemberPhoto() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getMemberName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getMemberTitle() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getMemberBio() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getFunFactText() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.LinearLayout getSkillsContainer() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.constraintlayout.widget.ConstraintLayout getMemberHeader() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.LinearLayout getMemberContent() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.ImageView getExpandIcon() {
            return null;
        }
    }
}