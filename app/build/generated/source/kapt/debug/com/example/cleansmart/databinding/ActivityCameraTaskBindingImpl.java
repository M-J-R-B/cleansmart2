package com.example.cleansmart.databinding;
import com.example.cleansmart.R;
import com.example.cleansmart.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityCameraTaskBindingImpl extends ActivityCameraTaskBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.toolbar, 1);
        sViewsWithIds.put(R.id.imageContainer, 2);
        sViewsWithIds.put(R.id.capturedImageView, 3);
        sViewsWithIds.put(R.id.noImageText, 4);
        sViewsWithIds.put(R.id.captureButton, 5);
        sViewsWithIds.put(R.id.areaNameInput, 6);
        sViewsWithIds.put(R.id.generateTasksButton, 7);
        sViewsWithIds.put(R.id.taskSelectionLayout, 8);
        sViewsWithIds.put(R.id.tasksHeading, 9);
        sViewsWithIds.put(R.id.selectAllCheckbox, 10);
        sViewsWithIds.put(R.id.tasksRecyclerView, 11);
        sViewsWithIds.put(R.id.addToDashboardButton, 12);
        sViewsWithIds.put(R.id.progressBar, 13);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityCameraTaskBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }
    private ActivityCameraTaskBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[12]
            , (com.google.android.material.textfield.TextInputEditText) bindings[6]
            , (android.widget.Button) bindings[5]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.Button) bindings[7]
            , (android.widget.FrameLayout) bindings[2]
            , (android.widget.TextView) bindings[4]
            , (android.widget.ProgressBar) bindings[13]
            , (android.widget.CheckBox) bindings[10]
            , (androidx.cardview.widget.CardView) bindings[8]
            , (android.widget.TextView) bindings[9]
            , (androidx.recyclerview.widget.RecyclerView) bindings[11]
            , (androidx.appcompat.widget.Toolbar) bindings[1]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}