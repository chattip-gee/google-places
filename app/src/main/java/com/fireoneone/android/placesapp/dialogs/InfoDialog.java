package com.fireoneone.android.placesapp.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.bases.BaseDialogFragment;
import com.fireoneone.android.placesapp.databinding.DialogInfoBinding;

public class InfoDialog extends BaseDialogFragment<DialogInfoBinding> {

    public interface InfoDialogListener {
        void onClose(InfoDialog dialog);
    }

    protected int imageRes = 0;
    protected int closeButtonRes = 0;
    protected Object title;
    protected Object description;
    private InfoDialogListener mInfoDialogListener;

    public InfoDialog() {
        cancellable = false;
        cancelOnTouchOutside = false;
    }

    public static InfoDialog newInstance(Object title, Object description) {
        InfoDialog fragment = new InfoDialog();
        fragment.title = title;
        fragment.description = description;

        Bundle arg = new Bundle();
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof InfoDialogListener) {
            mInfoDialogListener = (InfoDialogListener) activity;
        }
    }

    @Override
    protected void afterLoadContentView(View view, Bundle savedInstanceState) {
        super.afterLoadContentView(view, savedInstanceState);
        initWidget();
    }

    public void initWidget() {
        if (binding.imageviewInfo != null) {
            if (imageRes != 0) {
                binding.imageviewInfo.setVisibility(View.VISIBLE);
                binding.imageviewInfo.setImageResource(imageRes);
            } else {
                binding.imageviewInfo.setVisibility(View.GONE);
            }
        }

        if (binding.buttonClose != null) {
            if (closeButtonRes != 0) {
                binding.buttonClose.setText(closeButtonRes);
            }
        }

        if (binding.textviewTitle != null) {
            if (title instanceof Integer) {
                binding.textviewTitle.setText((Integer) title);
            } else if (title instanceof String) {
                binding.textviewTitle.setText((String) title);
            } else {
                binding.textviewTitle.setVisibility(View.GONE);
            }
        }

        if (binding.textviewDescription != null) {
            if (description instanceof Integer) {
                binding.textviewDescription.setText((Integer) description);
            } else if (description instanceof String) {
                binding.textviewDescription.setText((String) description);
            } else {
                binding.textviewDescription.setVisibility(View.GONE);
            }
        }

        if (binding.buttonClose != null) {
            binding.buttonClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    close(v);
                }
            });
        }
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public void setCloseButtonRes(int closeButtonRes) {
        this.closeButtonRes = closeButtonRes;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.dialog_info;
    }

    public void close(View v) {
        if (mInfoDialogListener != null) {
            mInfoDialogListener.onClose(this);
        } else {
            dismiss();
        }
    }

    public InfoDialog setInfoDialogListener(InfoDialogListener infoDialogListener) {
        mInfoDialogListener = infoDialogListener;
        return this;
    }
}
