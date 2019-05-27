package com.ougong.shop.base_mvp.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.baigui.commonview.BaseFrgment;

public  abstract class mvpDialogFragment extends BaseFrgment
        implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {

    public static final int STYLE_NORMAL = 0;


    public static final int STYLE_NO_TITLE = 1;


    public static final int STYLE_NO_FRAME = 2;

    public static final int STYLE_NO_INPUT = 3;

    private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
    private static final String SAVED_STYLE = "android:style";
    private static final String SAVED_THEME = "android:theme";
    private static final String SAVED_CANCELABLE = "android:cancelable";
    private static final String SAVED_SHOWS_DIALOG = "android:showsDialog";
    private static final String SAVED_BACK_STACK_ID = "android:backStackId";

    int mStyle = STYLE_NORMAL;
    int mTheme = 0;
    boolean mCancelable = true;
    boolean mShowsDialog = true;
    int mBackStackId = -1;

    Dialog mDialog;
    boolean mViewDestroyed;
    boolean mDismissed;
    boolean mShownByMe;

    public mvpDialogFragment() {
    }


    /**
     * Display the dialog, adding the fragment to the given FragmentManager.  This
     * is a convenience for explicitly creating a transaction, adding the
     * fragment to it with the given tag, and committing it.  This does
     * <em>not</em> add the transaction to the back stack.  When the fragment
     * is dismissed, a new transaction will be executed to remove it from
     * the activity.
     * @param manager The FragmentManager this fragment will be added to.
     * @param tag The tag for this fragment, as per
     * {@link FragmentTransaction#add(Fragment, String) FragmentTransaction.add}.
     */
    public void show(FragmentManager manager, String tag) {
        mDismissed = false;
        mShownByMe = true;
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commit();
    }

    /**
     * Display the dialog, adding the fragment using an existing transaction
     * and then committing the transaction.
     * @param transaction An existing transaction in which to add the fragment.
     * @param tag The tag for this fragment, as per
     * {@link FragmentTransaction#add(Fragment, String) FragmentTransaction.add}.
     * @return Returns the identifier of the committed transaction, as per
     * {@link FragmentTransaction#commit() FragmentTransaction.commit()}.
     */
    public int show(FragmentTransaction transaction, String tag) {
        mDismissed = false;
        mShownByMe = true;
        transaction.add(this, tag);
        mViewDestroyed = false;
        mBackStackId = transaction.commit();
        return mBackStackId;
    }

    /**
     * Dismiss the fragment and its dialog.  If the fragment was added to the
     * back stack, all back stack state up to and including this entry will
     * be popped.  Otherwise, a new transaction will be committed to remove
     * the fragment.
     */
    public void dismiss() {
        dismissInternal(false);
    }

    /**
     * Version of {@link #dismiss()} that uses
     * {@link FragmentTransaction#commitAllowingStateLoss()
     * FragmentTransaction.commitAllowingStateLoss()}.  See linked
     * documentation for further details.
     */
    public void dismissAllowingStateLoss() {
        dismissInternal(true);
    }

    void dismissInternal(boolean allowStateLoss) {
        if (mDismissed) {
            return;
        }
        mDismissed = true;
        mShownByMe = false;
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
        mViewDestroyed = true;
        if (mBackStackId >= 0) {
            getFragmentManager().popBackStack(mBackStackId,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
            mBackStackId = -1;
        } else {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.remove(this);
            if (allowStateLoss) {
                ft.commitAllowingStateLoss();
            } else {
                ft.commit();
            }
        }
    }

    public Dialog getDialog() {
        return mDialog;
    }

    public int getTheme() {
        return mTheme;
    }

    /**
     * Control whether the shown Dialog is cancelable.  Use this instead of
     * directly calling {@link Dialog#setCancelable(boolean)
     * Dialog.setCancelable(boolean)}, because DialogFragment needs to change
     * its behavior based on this.
     *
     * @param cancelable If true, the dialog is cancelable.  The default
     * is true.
     */
    public void setCancelable(boolean cancelable) {
        mCancelable = cancelable;
        if (mDialog != null) mDialog.setCancelable(cancelable);
    }

    /**
     * Return the current value of {@link #setCancelable(boolean)}.
     */
    public boolean isCancelable() {
        return mCancelable;
    }

    /**
     * Controls whether this fragment should be shown in a dialog.  If not
     * set, no Dialog will be created in {@link #onActivityCreated(Bundle)},
     * and the fragment's view hierarchy will thus not be added to it.  This
     * allows you to instead use it as a normal fragment (embedded inside of
     * its activity).
     *
     * <p>This is normally set for you based on whether the fragment is
     * associated with a container view ID passed to
     * {@link FragmentTransaction#add(int, Fragment) FragmentTransaction.add(int, Fragment)}.
     * If the fragment was added with a container, setShowsDialog will be
     * initialized to false; otherwise, it will be true.
     *
     * @param showsDialog If true, the fragment will be displayed in a Dialog.
     * If false, no Dialog will be created and the fragment's view hierarchly
     * left undisturbed.
     */
    public void setShowsDialog(boolean showsDialog) {
        mShowsDialog = showsDialog;
    }

    /**
     * Return the current value of {@link #setShowsDialog(boolean)}.
     */
    public boolean getShowsDialog() {
        return mShowsDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!mShownByMe) {
            // If not explicitly shown through our API, take this as an
            // indication that the dialog is no longer dismissed.
            mDismissed = false;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (!mShownByMe && !mDismissed) {
            // The fragment was not shown by a direct call here, it is not
            // dismissed, and now it is being detached...  well, okay, thou
            // art now dismissed.  Have fun.
            mDismissed = true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDialog = onCreateDialog(savedInstanceState);
    }


    /**
     * Override to build your own custom Dialog container.  This is typically
     * used to show an AlertDialog instead of a generic Dialog; when doing so,
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} does not need
     * to be implemented since the AlertDialog takes care of its own content.
     *
     * <p>This method will be called after {@link #onCreate(Bundle)} and
     * before {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.  The
     * default implementation simply instantiates and returns a {@link Dialog}
     * class.
     *
     * <p><em>Note: DialogFragment own the {@link Dialog#setOnCancelListener
     * Dialog.setOnCancelListener} and {@link Dialog#setOnDismissListener
     * Dialog.setOnDismissListener} callbacks.  You must not set them yourself.</em>
     * To find out about these events, override {@link #onCancel(DialogInterface)}
     * and {@link #onDismiss(DialogInterface)}.</p>
     *
     * @param savedInstanceState The last saved instance state of the Fragment,
     * or null if this is a freshly created Fragment.
     *
     * @return Return a new Dialog instance to be displayed by the Fragment.
     */
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme());
    }

    public void onCancel(DialogInterface dialog) {
    }

    public void onDismiss(DialogInterface dialog) {
        if (!mViewDestroyed) {
            // Note: we need to use allowStateLoss, because the dialog
            // dispatches this asynchronously so we can receive the call
            // after the activity is paused.  Worst case, when the user comes
            // back to the activity they see the dialog again.
            dismissInternal(true);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!mShowsDialog) {
            return;
        }

        View view = getView();
        if (view != null) {
            if (view.getParent() != null) {
                throw new IllegalStateException(
                        "DialogFragment can not be attached to a container view");
            }
            mDialog.setContentView(view);
        }
        final Activity activity = getActivity();
        if (activity != null) {
            mDialog.setOwnerActivity(activity);
        }
        mDialog.setCancelable(mCancelable);
//        if (!mDialog.takeCancelAndDismissListeners("DialogFragment", this, this)) {
//            throw new IllegalStateException(
//                    "You can not set Dialog's OnCancelListener or OnDismissListener");
//        }
        if (savedInstanceState != null) {
            Bundle dialogState = savedInstanceState.getBundle(SAVED_DIALOG_STATE_TAG);
            if (dialogState != null) {
                mDialog.onRestoreInstanceState(dialogState);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mDialog != null) {
            mViewDestroyed = false;
            mDialog.show();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mDialog != null) {
            mDialog.hide();
        }
    }

    /**
     * Remove dialog.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDialog != null) {
            // Set removed here because this dismissal is just to hide
            // the dialog -- we don't want this to cause the fragment to
            // actually be removed.
            mViewDestroyed = true;
            mDialog.dismiss();
            mDialog = null;
        }
    }

}
