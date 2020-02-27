package org.freeswitch.esl.client;

import java.util.Iterator;
import java.util.Map;
import org.freeswitch.esl.client.inbound.IEslEventListener;
import org.freeswitch.esl.client.internal.Context;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ESLEventListener implements IEslEventListener {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onEslEvent(Context ctx, EslEvent event) {
        log.info("===================================================================\n");
        Map<String, String> headers = event.getEventHeaders();

        if (event.getEventName().equals("API")) {
            log.info("API-->Event-Calling-Function[{}] API-Command[{}]",
                    headers.get("Event-Calling-Function"),
                    headers.get("API-Command") + " " + headers.get("API-Command-Argument")
            );
            if(headers.get("API-Command").equals("list_users")) showHeaderDetail(event);

        } else if (event.getEventName().equals("CHANNEL_STATE")) {
            log.info("CHN STATE-->[{}] Channel-Name[{}]", headers.get("Channel-State"),
                    headers.get("Channel-Name") + ":" + headers.get("Call-Direction"));
            showHeaderDetail(event);

        } else if (event.getEventName().equals("PRESENCE_IN")) {
            log.info("PRESENCE_IN-->user-agent[{}] from[{}]", headers.get("user-agent"),
                    headers.get("from") + ":" + headers.get("Call-Direction"));

        } else if (event.getEventName().equals("MESSAGE_QUERY")) {
            showHeaderDetail(event);

        } else if (event.getEventName().equals("MESSAGE_WAITING")) {
            showHeaderDetail(event);

        } else if (event.getEventName().equals("CHANNEL_EXECUTE")) {
            log.info("CHANNEL_EXCUTE-->app[{}] data[{}]", headers.get("Application"), headers.get("Application-Data"));

        } else if (event.getEventName().equals("CHANNEL_EXECUTE_COMPLETE")) {
            showHeaderDetail(event);

        } else if (event.getEventName().equals("PLAYBACK_START")) {
            log.info("        PLAY -->File[{}]", headers.get("Playback-File-Path"));
            showHeaderDetail(event);

        } else if (event.getEventName().equals("PLAYBACK_STOP")) {
            showHeaderDetail(event);

        } else if (event.getEventName().equals("CHANNEL_HANGUP")) {
            showHeaderDetail(event);

        } else if (event.getEventName().equals("CHANNEL_HANGUP_COMPLETE")) {
            log.info("CHANNEL_HANGUP_COMPLETE-->Channel-Name[{}]", headers.get("Channel-Name"));

        } else if (event.getEventName().equals("CHANNEL_DESTROY")) {
            showHeaderDetail(event);

        } else if (event.getEventName().equals("DTMF")) {
            log.info("        DTMF-->DTMF-Digit[{}] Channel-Name[{}]", headers.get("DTMF-Digit"),
                    headers.get("Channel-Name"));

        } else if (event.getEventName().equals("CHANNEL_CALLSTATE")) {
            log.info("CHANNEL_CALLSTATE-->Channel-State[{}] Channel-Name[{}]", headers.get("Channel-State"),
                    headers.get("Channel-Name"));

        } else if (event.getEventName().equals("CHANNEL_ANSWER")) {
            log.info("CHANNEL_ANSWER-->ANI[" + headers.get("Caller-Caller-ID-Name") + "] DNIS[{}] Codec[{}]",
                    headers.get("Caller-Destination-Number"),
                    headers.get("Channel-Read-Codec-Name") + "/" + headers.get("Channel-Write-Codec-Name"));

        } else if (event.getEventName().equals("CHANNEL_DESTROY")) {
            showHeaderDetail(event);

        } else if (event.getEventName().equals("CHANNEL_DESTROY")) {
            showHeaderDetail(event);

        } else if (event.getEventName().equals("CUSTOM")) {
            if (headers.get("Action").contains("talking") || headers.get("Action").contains("play-file")) {
                return;
            }

            log.info("CUSTOME_CONFERENCE --> Action[{}]", headers.get("Action"));
            showHeaderDetail(event);

        } else {
            log.info("Other ESL Event Name: {}", event.getEventName());
            showHeaderDetail(event);
        }
    }

    private void showHeaderDetail(EslEvent event) {
        Map<String, String> headers = event.getEventHeaders();
        String eventName = event.getEventName();
        Iterator<String> it = headers.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            log.info(eventName + "-->{}={} ", key, headers.get(key));
        }
    }

}
