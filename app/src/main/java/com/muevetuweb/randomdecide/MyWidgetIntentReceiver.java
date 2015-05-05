package com.muevetuweb.randomdecide;

/**
 * Created by cesar on 05-05-2015.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Random;

public class MyWidgetIntentReceiver extends BroadcastReceiver {
    public static int clickCount = 0;
    private String msg[] = null;
    final static String WIDGET_UPDATE_ACTION ="com.muevetuweb.intent.action.UPDATE_WIDGET";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WIDGET_UPDATE_ACTION)) {
            updateWidgetPictureAndButtonListener(context);
        }
    }

    private void updateWidgetPictureAndButtonListener(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.caracola);

        // updating view
        remoteViews.setTextViewText(R.id.appwidget_text, getRndResp(context));

        // re-registering for click listener
        remoteViews.setOnClickPendingIntent(R.id.btnWidget,
                Caracola.buildButtonPendingIntent(context));

        Caracola.pushWidgetUpdate(context.getApplicationContext(),
                remoteViews);
    }

    private String getRndResp(Context context) {
        msg = context.getResources().getStringArray(R.array.default_list);
        int total   =   msg.length;

        Random r = new Random(System.currentTimeMillis());
        return msg[r.nextInt(total)];
    }
}