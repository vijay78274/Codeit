package com.example.codeit;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;

import java.util.Calendar;

public class TodayDecorator implements DayViewDecorator {
    private final CalendarDay today;
    private final Drawable backgroundDrawable;

    public TodayDecorator(Context context) {
        // Get current date using ThreeTenABP
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());

        // Create CalendarDay instance
        this.today = CalendarDay.from(localDate);

        // Load the background drawable
        this.backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.today_background);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(today); // Decorate today's date
    }

    @Override
    public void decorate(DayViewFacade view) {
        if (backgroundDrawable != null) {
            view.setBackgroundDrawable(backgroundDrawable);
        }
    }
}
