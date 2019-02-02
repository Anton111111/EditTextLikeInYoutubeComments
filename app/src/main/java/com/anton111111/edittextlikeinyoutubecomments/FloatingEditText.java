package com.anton111111.edittextlikeinyoutubecomments;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.anton111111.edittextlikeinyoutubecomments.databinding.FloatingEditTextBinding;
import com.anton111111.edittextlikeinyoutubecomments.databinding.FloatingEditTextDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FloatingEditText extends FrameLayout {
    private FloatingEditTextBinding mBinding;
    private FloatingEditTextDialogBinding mBindingDialog;
    private BottomSheetDialog editDialog;

    public FloatingEditText(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public FloatingEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FloatingEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        mBinding = FloatingEditTextBinding.inflate(layoutInflater, this, true);
        mBinding.setMView(this);
        setupDialog(context, layoutInflater);
    }

    private void setupDialog(Context context, LayoutInflater layoutInflater) {
        mBindingDialog = FloatingEditTextDialogBinding.inflate(layoutInflater, null);
        mBindingDialog.setMView(this);
        mBindingDialog.editText.addTextChangedListener(textWatcher);
        editDialog = new BottomSheetDialog(context, R.style.FloatingEditTextDialog);
        editDialog.setContentView(mBindingDialog.getRoot());
        editDialog.setOnShowListener(dialog -> {
            //Hack to show correct height
            mBindingDialog.getRoot().getLayoutParams().height =
                    getContext().getResources().getDimensionPixelSize(R.dimen.edit_text_height);
            mBindingDialog.getRoot().requestLayout();
        });
    }

    public void openEditDialog() {
        mBindingDialog.editText.getText().clear();
        mBindingDialog.editText.append(mBinding.dummyEditText.getText().toString());
        editDialog.show();
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mBinding.dummyEditText.setText(s.toString().trim());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
