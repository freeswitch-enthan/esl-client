package org.freeswitch.esl.client;

import com.google.common.base.Throwables;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.internal.IModEslApi.EventFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class ClientExample {
    private static final Logger L = LoggerFactory.getLogger(ClientExample.class);

    public static void main(String[] args) {
        try {
            Client client = new Client();

            client.addEventListener(new ESLEventListener());

            client.connect(new InetSocketAddress("192.168.10.100", 8021), "ClueCon", 10);
            client.setEventSubscriptions(EventFormat.PLAIN, "all");

            // 取消不需要的订阅事件
            client.cancelEventSubscriptions("RE_SCHEDULE", "MESSAGE_WAITING", "MESSAGE_QUERY")
                    .forEach(System.out::println);

        } catch (Throwable t) {
            Throwables.propagate(t);
        }
    }
}
