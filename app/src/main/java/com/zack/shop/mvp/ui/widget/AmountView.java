package com.zack.shop.mvp.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zack.shop.R;

import timber.log.Timber;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/9 下午6:39
 * @Package com.zack.shop.mvp.ui.widget
 **/
public class AmountView extends LinearLayout implements View.OnClickListener, TextWatcher {

    private static final String TAG = "AmountView";
    private int currentAmount = 1;
    private int minValue = 1;
    private int maxValue = 1; //商品库存

    private OnAmountChangeListener mListener;

    private EditText etAmount;
    private Button btnDecrease;
    private Button btnIncrease;
    private Context mContext;

    public AmountView(Context context) {
        this(context, null);
    }

    public AmountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_amount, this);
        etAmount = findViewById(R.id.etAmount);
        btnDecrease = findViewById(R.id.btnDecrease);
        btnIncrease = findViewById(R.id.btnIncrease);
        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);

        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView);
        int btnWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnWidth, LayoutParams.WRAP_CONTENT);
        int tvWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvWidth, 80);
        int tvTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvTextSize, 0);
        int btnTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnTextSize, 0);
        obtainStyledAttributes.recycle();

        etAmount.setOnFocusChangeListener(null);
        etAmount.removeTextChangedListener(this);
        etAmount.setOnEditorActionListener(null);

        etAmount.setOnFocusChangeListener((v, hasFocus) -> {
            Timber.e("onFocusChange %s", hasFocus);
            if (!hasFocus) {
                etAmount.setText(String.valueOf(currentAmount));
            }
        });

        etAmount.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (mListener != null) {
                    int currentAmount = Integer.parseInt(etAmount.getText().toString());
                    this.currentAmount = currentAmount;
                    mListener.onAmountChange(AmountView.this, currentAmount);
                    etAmount.clearFocus();
                    hideSoft(v);
                }

                return false;
            }
            return false;
        });
    }

    private void hideSoft(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener) {
        this.mListener = onAmountChangeListener;
    }

    public void recoverAmount(){
        etAmount.removeTextChangedListener(this);
        etAmount.setText(String.valueOf(currentAmount));
        etAmount.addTextChangedListener(this);
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
        etAmount.removeTextChangedListener(this);
        etAmount.setText(String.valueOf(currentAmount));
        etAmount.addTextChangedListener(this);
        if (currentAmount >= maxValue) {
            btnIncrease.setEnabled(false);
        } else {
            btnIncrease.setEnabled(true);
        }

        if (currentAmount <= minValue) {
            btnDecrease.setEnabled(false);
        } else {
            btnDecrease.setEnabled(true);
        }
    }


    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnDecrease) {
            etAmount.setText(String.valueOf(Integer.parseInt(etAmount.getText().toString()) - 1));
            currentAmount = currentAmount - 1;
        } else if (i == R.id.btnIncrease) {
            etAmount.setText(String.valueOf(Integer.parseInt(etAmount.getText().toString()) + 1));
            currentAmount = currentAmount + 1;
        }
        etAmount.clearFocus();
        hideSoft(etAmount);
        Timber.e("etAmount : %s", etAmount.isFocused());
        if (!etAmount.isFocused()) {
            if (mListener != null) mListener.onAmountChange(AmountView.this, currentAmount);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s.toString())) {
            etAmount.setText(String.valueOf(minValue));
        } else {
            int amount = Integer.valueOf(s.toString());
            if (s.toString().startsWith("0")) {
                etAmount.setText(String.valueOf(amount));
            } else if (amount < minValue) {
                etAmount.setText(String.valueOf(minValue));
            } else if (amount > maxValue) {
                etAmount.setText(String.valueOf(maxValue));
            } else {
                Timber.e("afterTextChanged %s", amount);
                if (amount == maxValue) {
                    btnIncrease.setEnabled(false);
                } else {
                    btnIncrease.setEnabled(true);
                }

                if (amount == minValue) {
                    btnDecrease.setEnabled(false);
                } else {
                    btnDecrease.setEnabled(true);
                }
            }
        }
    }


    public interface OnAmountChangeListener {
        void onAmountChange(View view, int amount);
    }

}
