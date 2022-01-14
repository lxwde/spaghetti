package com.app.zpmc.rabbitmq;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    private TextView showMessageTv;
    private EditText contentEt;
    private StringBuilder sb = new StringBuilder();

    private final String sendQueueOne = "zpmc";
    private final String sendRoutingKey = "two.rabbit.ok";
    private final String receiveQueueOne = "zpmc";
    private final String receiveQueueTwo = "testTwo";
    private final String receiveRoutingKey = "iacp.biz.job.alert.handler.#";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentEt = findViewById(R.id.et_content);
        showMessageTv = findViewById(R.id.tv_show_message);
        showMessageTv.setMovementMethod(ScrollingMovementMethod.getInstance());

        RabbitMQUtil.initService("10.0.2.2", 5672, "guest", "guest");
        RabbitMQUtil.initExchange("iacp.topic", "topic");
    }

    @Override
    protected void onDestroy() {
        RabbitMQUtil.getInstance().close();
        super.onDestroy();
    }

    public void sendQueue(View view) {
        final String message = contentEt.getText().toString().trim();

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(MainActivity.this, "消息不能为空！！！", Toast.LENGTH_SHORT).show();
            return;
        }

        RabbitMQUtil.getInstance().sendQueueMessage(message, sendQueueOne, new RabbitMQUtil.SendMessageListener() {
            @Override
            public void sendMessage(final boolean isSuccess) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess) {
                            sb.append("发送队列消息：").append(message).append("\n");
                            showMessageTv.setText(sb);
                            contentEt.setText("");

                        } else {
                            Toast.makeText(MainActivity.this, "发送消息失败，请检查网络后稍后再试！！！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }

    public void sendRouting(View view) {
        final String message = contentEt.getText().toString().trim();

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(MainActivity.this, "消息不能为空！！！", Toast.LENGTH_SHORT).show();
            return;
        }

        RabbitMQUtil.getInstance().sendRoutingKeyMessage(message, sendRoutingKey, new RabbitMQUtil.SendMessageListener() {
            @Override
            public void sendMessage(final boolean isSuccess) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess) {
                            sb.append("发送routing消息：").append(message).append("\n");
                            contentEt.setText("");
                            showMessageTv.setText(sb);

                        } else {
                            Toast.makeText(MainActivity.this, "发送消息失败，请检查网络后稍后再试！！！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void listenQueue(View view) {
        /*
        NotificationManager nm = (NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        NotificationManager notification_manager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String chanel_id = "3000";
            CharSequence name = "Channel Name";
            String description = "Chanel Description";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            notification_manager.createNotificationChannel(mChannel);
            builder = new NotificationCompat.Builder(this, chanel_id);
        } else {
            builder = new NotificationCompat.Builder(this);
        }

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("A new message received.")
                .setContentText("A new message received.")
                .setAutoCancel(false)
                .setContentIntent(contentIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Your Long Text here"));


        Notification notification = builder.build();
        nm.notify((int)System.currentTimeMillis(), notification);
        */
        final MainActivity mainActivity = this;
        RabbitMQUtil.getInstance().receiveQueueMessage(receiveQueueOne, new RabbitMQUtil.ReceiveMessageListener() {
            @Override
            public void receiveMessage(final String message) {

                sb.append("收到了queue消息：").append(message).append("\n");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMessageTv.setText(sb);

                        NotificationManager nm = (NotificationManager)mainActivity.getSystemService(NOTIFICATION_SERVICE);
                        NotificationCompat.Builder builder = null;
                        NotificationManager notification_manager = (NotificationManager) mainActivity
                                .getSystemService(Context.NOTIFICATION_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            String chanel_id = "3000";
                            CharSequence name = "Channel Name";
                            String description = "Chanel Description";
                            int importance = NotificationManager.IMPORTANCE_LOW;
                            NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
                            mChannel.setDescription(description);
                            mChannel.enableLights(true);
                            mChannel.setLightColor(Color.BLUE);
                            notification_manager.createNotificationChannel(mChannel);
                            builder = new NotificationCompat.Builder(mainActivity, chanel_id);
                        } else {
                            builder = new NotificationCompat.Builder(mainActivity);
                        }

                        Intent notificationIntent = new Intent(mainActivity, MainActivity.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(mainActivity,0,notificationIntent,0);

                        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setContentTitle("A new message received: " + message)
                                .setContentText("Message detail: " + message)
                                .setAutoCancel(false)
                                .setContentIntent(contentIntent)
                                .setStyle(new NotificationCompat.BigTextStyle().bigText("Message detail: " + message));


                        Notification notification = builder.build();
                        nm.notify((int)System.currentTimeMillis(), notification);
                    }
                });

            }
        });
    }

    public void listenRouting(View view) {
        RabbitMQUtil.getInstance().receiveRoutingKeyMessage(receiveRoutingKey, new RabbitMQUtil.ReceiveMessageListener() {
            @Override
            public void receiveMessage(String message) {
                sb.append("收到了routing消息：").append(message).append("\n");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMessageTv.setText(sb);
                    }
                });
            }
        });
    }
}
