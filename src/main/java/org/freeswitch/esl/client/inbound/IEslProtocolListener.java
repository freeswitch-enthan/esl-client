package org.freeswitch.esl.client.inbound;

import org.freeswitch.esl.client.internal.Context;
import org.freeswitch.esl.client.transport.CommandResponse;
import org.freeswitch.esl.client.transport.event.EslEvent;

/**
 * End users of the {@link Client} should not need to use this class.
 *
 * Allow client implementations to observe events arriving from the server.
 */
interface IEslProtocolListener {
	void authResponseReceived(CommandResponse response);

	void eventReceived(Context ctx, EslEvent event);

	void disconnected();
}
