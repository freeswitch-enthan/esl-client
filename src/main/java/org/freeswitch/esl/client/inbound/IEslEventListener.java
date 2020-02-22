package org.freeswitch.esl.client.inbound;

import org.freeswitch.esl.client.internal.Context;
import org.freeswitch.esl.client.transport.event.EslEvent;

/**
 * Interface for observers wanting to be notified of incoming FreeSWITCH Event Socket events.
 * <p/>
 * Events are guaranteed to be processed (and listeners notified) in the order in which the
 * events are received off the wire.
 * <p/>
 * This design ensures that incoming event processing is not blocked by any long-running listener process.
 * However multiple listeners will be notified sequentially, and so one slow listener can cause latency
 * to other listeners.
 */
public interface IEslEventListener {

	/**
	 * Signal of a server initiated event.
	 *
	 * @param event as an {@link EslEvent}
	 */
	void onEslEvent(Context ctx, EslEvent event);
}
