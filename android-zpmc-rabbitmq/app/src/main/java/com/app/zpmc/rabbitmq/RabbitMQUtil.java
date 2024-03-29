package com.app.zpmc.rabbitmq;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.TextUtils;


import com.blankj.utilcode.util.LogUtils;
import com.rabbitmq.client.AlreadyClosedException;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;


public class RabbitMQUtil {
    private final String TAG = "RabbitMQ";
    private static RabbitMQUtil singleton;
    private RabbitMQClient rabbitMQ;
    private ExecutorService executor;


    public static void initService(String hostIp, int port, String username, String password) {
        RabbitMQClient.SERVICE_HOST_IP = hostIp;
        RabbitMQClient.SERVICE_PORT = port;
        RabbitMQClient.SERVICE_USERNAME = username;
        RabbitMQClient.SERVICE_PASSWORD = password;
    }

    public static void initExchange(@NonNull String name, String type) {
        if (!TextUtils.isEmpty(name)) {
            RabbitMQClient.EXCHANGE_NAME = name;
        } else {
            throw new NullPointerException("转换器名称不能为空");
        }
        if ("fanout".equals(type) || "direct".equals(type) || "topic".equals(type) || "headers".equals(type)) {
            RabbitMQClient.EXCHANGE_TYPE = type;
        } else {
            throw new NullPointerException("转换器类型不正确");
        }
    }


    private RabbitMQUtil() {
        rabbitMQ = RabbitMQClient.getInstance();
        executor = Executors.newSingleThreadExecutor();
    }

    public static RabbitMQUtil getInstance() {
        if (singleton == null) {
            synchronized (RabbitMQClient.class) {
                if (singleton == null) {
                    singleton = new RabbitMQUtil();
                }
            }
        }
        return singleton;
    }

    public void sendQueueMessage(final String message, final String queueName, final SendMessageListener listener) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    rabbitMQ.sendQueueMessage(message, queueName);
                    if (listener != null) listener.sendMessage(true);
                } catch (IOException | TimeoutException | AlreadyClosedException e) {
                    e.printStackTrace();
                    if (listener != null) listener.sendMessage(false);
                }
            }
        });

    }

    public void sendRoutingKeyMessage(final String message, final String routingKey, final SendMessageListener listener) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    rabbitMQ.sendRoutingKeyMessage(message, routingKey);
                    if (listener != null) listener.sendMessage(true);
                } catch (IOException | TimeoutException | AlreadyClosedException e) {
                    e.printStackTrace();
                    if (listener != null) listener.sendMessage(false);
                }
            }
        });
    }

    public void sendQueueRoutingKeyMessage(final String message, final String routingKey, @NonNull final String exchangeName,
                                           final String exchangeType, final SendMessageListener listener) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    rabbitMQ.sendQueueRoutingKeyMessage(message, routingKey, exchangeName, exchangeType);
                    if (listener != null) listener.sendMessage(true);
                } catch (IOException | TimeoutException | AlreadyClosedException e) {
                    e.printStackTrace();
                    if (listener != null) listener.sendMessage(false);
                }
            }
        });
    }

    public void receiveQueueMessage(final String queueName, final ReceiveMessageListener listener) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (singleton != null) {
                    try {
                        rabbitMQ.receiveQueueMessage(queueName, new RabbitMQClient.ResponseListener() {

                            @Override
                            public void receive(String message) {
                                if (listener != null) listener.receiveMessage(message);
                            }

                        });
                        break;
                    } catch (IOException | TimeoutException | AlreadyClosedException e) {
                        e.printStackTrace();
                        LogUtils.d(TAG, "未连接到-" + queueName + "-----5秒后自动重连");
                        SystemClock.sleep(5000);
                    }
                }
            }
        });
    }

    public void receiveRoutingKeyMessage(final String routingKey, final ReceiveMessageListener listener) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (singleton != null) {
                    try {
                        rabbitMQ.receiveRoutingKeyMessage(routingKey, new RabbitMQClient.ResponseListener() {
                            @Override
                            public void receive(String message) {
                                if (listener != null) listener.receiveMessage(message);
                            }
                        });
                        break;
                    } catch (IOException | TimeoutException | AlreadyClosedException e) {
                        e.printStackTrace();
                        LogUtils.d(TAG, "未连接到-" + routingKey + "------5秒后自动重连");
                        SystemClock.sleep(5000);  //等待五秒
                    }
                }
            }
        });
    }

    public void receiveQueueRoutingKeyMessage(final String queueName, final String routingKey, final ReceiveMessageListener listener) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (singleton != null) {
                    try {
                        rabbitMQ.receiveQueueRoutingKeyMessage(queueName, routingKey, new RabbitMQClient.ResponseListener() {
                            @Override
                            public void receive(String message) {
                                if (listener != null) listener.receiveMessage(message);
                            }

                        });
                        break;
                    } catch (IOException | TimeoutException | AlreadyClosedException e) {
                        e.printStackTrace();
                        LogUtils.d(TAG, "未连接到-" + routingKey + "------5秒后自动重连");
                        SystemClock.sleep(5000);  //等待五秒
                    }
                }

            }
        });

    }

    public void receiveQueueRoutingKeyMessage(final String queueName, final String routingKey, final String exchangeName,
                                              final String exchangeType, final ReceiveMessageListener listener) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (singleton != null) {
                    try {
                        rabbitMQ.receiveQueueRoutingKeyMessage(queueName, routingKey, exchangeName, exchangeType, new RabbitMQClient.ResponseListener() {
                            @Override
                            public void receive(String message) {
                                if (listener != null) listener.receiveMessage(message);
                            }

                        });
                        break;
                    } catch (IOException | TimeoutException | AlreadyClosedException e) {
                        e.printStackTrace();
                        LogUtils.d(TAG, "未连接到-" + routingKey + "------5秒后自动重连");
                        SystemClock.sleep(5000);  //等待五秒
                    }
                }

            }
        });

    }

    public void close() {
        rabbitMQ.close();
        executor.shutdownNow();
        singleton = null;
        LogUtils.d(TAG, "关闭RabbitMQ");
    }

    public interface ReceiveMessageListener {
        void receiveMessage(String message);
    }

    public interface SendMessageListener {
        void sendMessage(boolean isSuccess);
    }
    
}
