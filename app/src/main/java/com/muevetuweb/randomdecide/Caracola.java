package com.muevetuweb.randomdecide;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;


public class Caracola extends AppWidgetProvider {
    final static String WIDGET_UPDATE_ACTION ="com.muevetuweb.intent.action.UPDATE_WIDGET";
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // initializing widget layout
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.caracola);


        remoteViews.setOnClickPendingIntent(R.id.btnWidget,
                buildButtonPendingIntent(context));

        remoteViews.setTextViewText(R.id.appwidget_text, getDefaultText(context));
        pushWidgetUpdate(context, remoteViews);
    }
    public static PendingIntent buildButtonPendingIntent(Context context) {
        ++MyWidgetIntentReceiver.clickCount;

        // initiate widget update request
        Intent intent = new Intent();
        intent.setAction(WIDGET_UPDATE_ACTION);
        return PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static CharSequence getDefaultText(Context context) {
        return context.getResources().getString(R.string.default_widget_text);
    }

    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
        ComponentName myWidget = new ComponentName(context,
                Caracola.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

