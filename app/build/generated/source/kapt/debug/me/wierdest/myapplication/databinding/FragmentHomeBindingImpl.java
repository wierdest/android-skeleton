package me.wierdest.myapplication.databinding;
import me.wierdest.myapplication.R;
import me.wierdest.myapplication.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentHomeBindingImpl extends FragmentHomeBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.homeTextView, 2);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentHomeBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }
    private FragmentHomeBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (com.google.android.material.textview.MaterialTextView) bindings[1]
            , (com.google.android.material.textview.MaterialTextView) bindings[2]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            );
        this.homeSessionTextView.setTag(null);
        this.titleLayout.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
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
        if (BR.myViewModel == variableId) {
            setMyViewModel((me.wierdest.myapplication.database.MyViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setMyViewModel(@Nullable me.wierdest.myapplication.database.MyViewModel MyViewModel) {
        this.mMyViewModel = MyViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.myViewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeMyViewModelLastSessionId((androidx.lifecycle.LiveData<java.lang.Object>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeMyViewModelLastSessionId(androidx.lifecycle.LiveData<java.lang.Object> MyViewModelLastSessionId, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
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
        androidx.lifecycle.LiveData<java.lang.Object> myViewModelLastSessionId = null;
        java.lang.Object myViewModelLastSessionIdGetValue = null;
        me.wierdest.myapplication.database.MyViewModel myViewModel = mMyViewModel;
        java.lang.String stringValueOfMyViewModelLastSessionId = null;

        if ((dirtyFlags & 0x7L) != 0) {



                if (myViewModel != null) {
                    // read myViewModel.lastSessionId
                    myViewModelLastSessionId = myViewModel.getLastSessionId();
                }
                updateLiveDataRegistration(0, myViewModelLastSessionId);


                if (myViewModelLastSessionId != null) {
                    // read myViewModel.lastSessionId.getValue()
                    myViewModelLastSessionIdGetValue = myViewModelLastSessionId.getValue();
                }


                // read String.valueOf(myViewModel.lastSessionId.getValue())
                stringValueOfMyViewModelLastSessionId = java.lang.String.valueOf(myViewModelLastSessionIdGetValue);
        }
        // batch finished
        if ((dirtyFlags & 0x7L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.homeSessionTextView, stringValueOfMyViewModelLastSessionId);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): myViewModel.lastSessionId
        flag 1 (0x2L): myViewModel
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}