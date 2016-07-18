package br.com.mqsystems.jaPedi.application.util;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import br.com.mqsystems.jaPedi.domain.model.Ingrediente;

import br.com.mqsystems.jaPedi.R;

/**
 * Created by valter ramos on 16/01/20.
 */
public class NotificationUtil {

    private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();

    public static void createJokeNotification(Context context, Ingrediente joke) {
        String notificationTitle = "Piada do dia";
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        int smalIcon = R.drawable.ic_launcher;

		/*create intent for show notification details when user clicks notification*/
        /*Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra("joke", GSON.toJson(joke));
        long time = (new Date()).getTime();
		/*create unique this intent from  other intent using setData */
        //intent.setData(Uri.parse("content://" + time));
        /*create new task for each notification with pending intent so we set Intent.FLAG_ACTIVITY_NEW_TASK */
        //PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intent, 0);

		/*get the system service that manage notification NotificationManager*/
        NotificationManager notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

		/*build the notification*/
        /*NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                context)
                .setWhen(time)
                .setContentText(joke.piada)
                .setContentTitle(notificationTitle)
                .setSmallIcon(smalIcon)
                .setAutoCancel(true)
                .setTicker(notificationTitle)
                .setLargeIcon(largeIcon)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent);

		/*Create notification with builder*/
        //Notification notification = notificationBuilder.build();

		/*sending notification to system.Here we use unique id (when)for making different each notification
         * if we use same id,then first notification replace by the last notification*/
        //notificationManager.notify((int) time, notification);
    }
}
