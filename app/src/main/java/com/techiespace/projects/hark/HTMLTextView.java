package com.techiespace.projects.hark;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.style.ImageSpan;
import android.util.AttributeSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLTextView extends android.support.v7.widget.AppCompatTextView {
    private static final Spannable.Factory spannableFactory = Spannable.Factory.getInstance();

    public HTMLTextView(Context context) {
        super(context);
        setText(Html.fromHtml(getText().toString()));
    }

    public HTMLTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setText(Html.fromHtml(getText().toString()));
    }

    public HTMLTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setText(Html.fromHtml(getText().toString()));
    }

    private static boolean addImages(Context context, Spannable spannable, float height) {
        Pattern refImg = Pattern.compile("\\Q[img src=\\E([a-zA-Z0-9_]+?)\\Q/]\\E");
        boolean hasChanges = false;

        Matcher matcher = refImg.matcher(spannable);
        while (matcher.find()) {
            boolean set = true;
            for (ImageSpan span : spannable.getSpans(matcher.start(), matcher.end(), ImageSpan.class)) {
                if (spannable.getSpanStart(span) >= matcher.start()
                        && spannable.getSpanEnd(span) <= matcher.end()
                        ) {
                    spannable.removeSpan(span);
                } else {
                    set = false;
                    break;
                }
            }
            String resName = spannable.subSequence(matcher.start(1), matcher.end(1)).toString().trim();
            int id = context.getResources().getIdentifier(resName, "drawable", context.getPackageName());
            Drawable mDrawable = context.getResources().getDrawable(id);
            mDrawable.setBounds(0, 0, (int) height, (int) height);
            if (set) {
                hasChanges = true;
                spannable.setSpan(new ImageSpan(mDrawable),
                        matcher.start(),
                        matcher.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        }

        return hasChanges;
    }

    private static Spannable getTextWithImages(Context context, CharSequence text, float height) {
        Spannable spannable = spannableFactory.newSpannable(text);
        addImages(context, spannable, height);
        return spannable;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        final Spannable spannable = getTextWithImages(getContext(), text, getLineHeight());
        super.setText(spannable, BufferType.SPANNABLE);
    }
}
