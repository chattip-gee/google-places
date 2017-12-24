package com.fireoneone.android.placesapp.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.bases.BaseDialogFragment;
import com.fireoneone.android.placesapp.databinding.DialogConfirmationBinding;

public class ConfirmationDialog extends BaseDialogFragment<DialogConfirmationBinding> {

    public interface ConfirmationDialogListener {
        void onConfirm(ConfirmationDialog dialog, boolean answer);
    }

    protected int imageRes = 0;
    protected Object title;
    protected Object description;
    protected int noButtonRes = 0;
    protected int yesButtonRes = 0;
    private ConfirmationDialogListener confirmationDialogListener;

    public static ConfirmationDialog newInstance(Object title, Object description) {
        ConfirmationDialog fragment = new ConfirmationDialog();
        fragment.title = title;
        fragment.description = description;

        Bundle arg = new Bundle();
        fragment.setArguments(arg);
        return fragment;
    }

    public ConfirmationDialog() {
        cancellable = false;
        cancelOnTouchOutside = false;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof ConfirmationDialogListener) {
            confirmationDialogListener = (ConfirmationDialogListener) activity;
        }
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.dialog_confirmation;
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

        if (binding.buttonNo != null) {
            binding.buttonNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    no(v);
                }
            });

            if (noButtonRes != 0) {
                binding.buttonNo.setText(noButtonRes);
            }
        }

        if (binding.buttonYes != null) {
            binding.buttonYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    yes(v);
                }
            });

            if (yesButtonRes != 0) {
                binding.buttonYes.setText(yesButtonRes);
            }
        }
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public void yes(View v) {
        if (confirmationDialogListener != null) {
            confirmationDialogListener.onConfirm(this, true);
        }
    }

    public void no(View v) {
        if (confirmationDialogListener != null) {
            confirmationDialogListener.onConfirm(this, false);
        }
    }

    public void setNoButtonRes(int noButtonRes) {
        this.noButtonRes = noButtonRes;
    }

    public void setYesButtonRes(int yesButtonRes) {
        this.yesButtonRes = yesButtonRes;
    }

    public void setConfirmationDialogListener(ConfirmationDialogListener confirmationDialogListener) {
        this.confirmationDialogListener = confirmationDialogListener;
    }
}
