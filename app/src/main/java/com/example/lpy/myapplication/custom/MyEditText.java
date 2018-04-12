package com.example.lpy.myapplication.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.lpy.myapplication.R;

public class MyEditText extends RelativeLayout {
    private ImageView imageView;
    private EditText editText;
    private ImageView close;
    private View line;
    private int mResourceId;
    private boolean isCloseBtnVisible = true;//默认显示清除按钮

//    private String hint;
//    private int srcOfLift;

    public MyEditText(Context context) {
        super(context);
        initView(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyEditText);// 由attrs 获得 TypeArray
        String hint = ta.getString(R.styleable.MyEditText_my_hint);
        editText.setHint(hint);
        mResourceId = ta.getResourceId(R.styleable.MyEditText_my_bitmap, -1);
        if (mResourceId == -1) {
            throw new RuntimeException("资源没有被找到，请设置背景图");
        }
        imageView.setImageResource(mResourceId);

    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyEditText);// 由attrs 获得 TypeArray
        String hint = ta.getString(R.styleable.MyEditText_my_hint);
        editText.setHint(hint);
        mResourceId = ta.getResourceId(R.styleable.MyEditText_my_bitmap, -1);
        if (mResourceId == -1) {
            throw new RuntimeException("资源没有被找到，请设置背景图");
        }
        imageView.setImageResource(mResourceId);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.item_my_edittext, MyEditText.this);
        imageView = (ImageView) this.findViewById(R.id.imageView);
        close = (ImageView) this.findViewById(R.id.close);
        editText = (EditText) this.findViewById(R.id.editText);
        line = this.findViewById(R.id.line);
        editText.addTextChangedListener(textWatcher);
        close.setOnClickListener(onClickListener);
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    line.setBackgroundResource(R.color.color_ffa391);
                } else {
                    // 此处为失去焦点时的处理内容
                    line.setBackgroundResource(R.color.color_b4b4b4);
                }
            }
        });
    }

    public void setCloseBtnVisible(boolean isCloseBtnVisible) {
        this.isCloseBtnVisible = isCloseBtnVisible;
    }

    /**
     * 设置文本
     *
     * @param charSequence
     */
    public void setText(CharSequence charSequence) {
        editText.setText(charSequence);
    }

    /**
     * 设置imageView图片
     *
     * @param resourceId
     */
    public void setImageResource(int resourceId) {
        imageView.setImageResource(resourceId);
    }

    /**
     * @return editText的文本
     */
    public Editable getText() {
        return editText.getText();
    }

    /**
     * 设置editText的输入类型
     */
    public void setInputType(int type) {
        editText.setInputType(type);
    }

    /**
     * 设置editText是否可编辑
     */
    public void setEditable(boolean type) {
        editText.setFocusable(type);
        editText.setFocusableInTouchMode(type);
        close.setVisibility(GONE);
    }

    /**
     * 设置editText是否可编辑
     */
    public void setInputFilter(String filter) {
        editText.setKeyListener(DigitsKeyListener.getInstance(filter));
    }

    /**
     * 设置editText的输入最大长度
     */
    public void setMaxLength(int maxLength) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)}); //最大输入长度
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            editText.setTextColor(getResources().getColor(R.color.color_333333));
            imageView.setImageResource(mResourceId);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            setCloseBtn(editable);
        }
    };

    private void setCloseBtn(Editable editable) {
        if (isCloseBtnVisible && editable.length() > 0) {
            close.setVisibility(VISIBLE);
        } else {
            close.setVisibility(GONE);
        }
    }

    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.close:
                    editText.setText("");
                    break;
                default:
                    break;
            }
        }
    };
}
