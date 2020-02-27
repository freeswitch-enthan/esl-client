package org.freeswitch.esl.client.conference;

import java.net.InetSocketAddress;
import org.freeswitch.esl.client.ESLEventListener;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.freeswitch.esl.client.internal.IModEslApi.EventFormat;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Client client = new Client();

    @Before
    public void setupTest() throws InboundConnectionFailure {
        client.addEventListener(new ESLEventListener());

        client.connect(new InetSocketAddress("192.168.10.100", 8021), "ClueCon", 10);
    }

}
