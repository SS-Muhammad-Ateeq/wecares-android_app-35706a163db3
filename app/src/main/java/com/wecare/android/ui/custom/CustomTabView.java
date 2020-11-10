package com.wecare.android.ui.custom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.wecare.android.R;
import com.wecare.android.WeCareApplication;
import com.wecare.android.data.model.others.CustomTabItem;
import com.wecare.android.utils.AppConstants;

import java.util.List;

public class CustomTabView extends LinearLayout {
    /**/
    List<CustomTabItem> items;

    /**/
    private Context context;
    onTabClickListener clickListener;
    /**/
    private int selectedTabID = 1;

    private int selectedColor;
    private int unselectedColor;
    private int strokeColor;


    public CustomTabView(Context context) {
        super(context);
    }

    public CustomTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * initialize and draw tags on screen this method should be called after finish setup view with all items and and properties
     *
     * @param context
     * @param itemList
     */
    public void initializeTabs(Context context, List<CustomTabItem> itemList, int strokeColor, int selectedColor, int unselectedColor, onTabClickListener clickListener) {
        this.context = context;
        this.items = itemList;
        this.clickListener = clickListener;
        this.strokeColor = strokeColor;
        this.selectedColor = selectedColor;
        this.unselectedColor = unselectedColor;
        drawTabs();
    }

    private void drawTabs() {
        int position = 0;
        removeAllViews();
        int radius = (int) (25 * Resources.getSystem().getDisplayMetrics().density);
        for (final CustomTabItem item :
                items) {
            final TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.custom_tab_view, null);
//            textView.setTypeface(WeCareApplication.getInstance());
            GradientDrawable shape = null;
            try {
                shape = new GradientDrawable();
                shape.setShape(GradientDrawable.RECTANGLE);
                shape.setStroke(2, ContextCompat.getColor(context, strokeColor));

                textView.setText(item.getName());
                if (position == 0) {
                    //top-left, top-right, bottom-right, bottom-left.
//                    textView.setBackground(getResources().getDrawable(R.drawable.rounded_corners_button_coloraccent));
                    if (AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ARABIC)) {
                        shape.setCornerRadii(new float[]{0, 0, radius, radius, radius, radius, 0, 0});
                    } else {
                        shape.setCornerRadii(new float[]{radius, radius, 0, 0, 0, 0, radius, radius});
                    }
                } else if (position == items.size() - 1) {
//                    textView.setBackground(getResources().getDrawable(R.drawable.rounded_corners_button_colorprimary));
                    if (AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ARABIC)) {
                        shape.setCornerRadii(new float[]{radius, radius, 0, 0, 0, 0, radius, radius});
                    } else {
                        shape.setCornerRadii(new float[]{0, 0, radius, radius, radius, radius, 0, 0});
                    }
                }

                if (item.isSelected()) {
                    shape.setColor(ContextCompat.getColor(context, selectedColor));
                    textView.setTextColor(ContextCompat.getColor(context, R.color.text_white));
                } else {
                    shape.setColor(ContextCompat.getColor(context, unselectedColor));
                    textView.setTextColor(ContextCompat.getColor(context, R.color.text_white));
                }

                if (Build.VERSION.SDK_INT >= 16) {
                    textView.setBackground(shape);
                } else {
                    textView.setBackgroundDrawable(shape);
                }

                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                buttonLayoutParams.weight = 1;
                textView.setLayoutParams(buttonLayoutParams);
                //convert do to pixels
                int horizontalPaddingInPx = (int) (5 * Resources.getSystem().getDisplayMetrics().density);
                int verticalPaddingInPx = (int) (10 * Resources.getSystem().getDisplayMetrics().density);
                textView.setPadding(horizontalPaddingInPx, verticalPaddingInPx, horizontalPaddingInPx, verticalPaddingInPx);
            } catch (Exception e) {
                e.getMessage();
            }
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!item.isSelected()) {
                        item.setSelected(true);
                        selectedTabID = item.getId();
                    }
                    for (CustomTabItem tabItem : items) {
                        if (tabItem.getId() != item.getId())
                            tabItem.setSelected(false);
                    }
                    clickListener.onTabClick(item.getId());
                    drawTabs();
                }
            });

            addView(textView);
            position++;
        }
    }

    public int getSelectedTabID() {
        return selectedTabID;
    }

    public void setSelectedTabID(int selectedTabID) {
        this.selectedTabID = selectedTabID;
        for (CustomTabItem item :
                items) {
            if (item.getId() == selectedTabID) {
                item.setSelected(true);
            } else {
                item.setSelected(false);
            }
        }
        drawTabs();
    }

    public interface onTabClickListener {
        void onTabClick(int tabID);
    }
}
