package com.example.newsapplication;

import android.widget.Button;
import android.widget.TextView;

public class GetterSetterGrid {
    public class GetterSetter {

        TextView textView;
        Button button;
        int Img;

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }

        public Button getButton() {
            return button;
        }

        public void setButton(Button button) {
            this.button = button;
        }

        public int getImg() {
            return Img;
        }

        public void setImg(int img) {
            Img = img;
        }


    }
}
