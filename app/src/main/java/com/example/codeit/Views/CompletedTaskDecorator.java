package com.example.codeit.Views;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.codeit.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.HashSet;

public class CompletedTaskDecorator implements DayViewDecorator {
    private final HashSet<CalendarDay> dates;
    private final Drawable drawable;
    public CompletedTaskDecorator(Context context, HashSet<CalendarDay> dates) {
        this.dates = dates;
        this.drawable = ContextCompat.getDrawable(context, R.drawable.correct);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(drawable);
    }
}
