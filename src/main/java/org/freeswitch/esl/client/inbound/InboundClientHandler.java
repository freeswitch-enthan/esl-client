package org.freeswitch.esl.client.inbound;

import io.netty.channel.ChannelHandlerContext;
import org.freeswitch.esl.client.internal.AbstractEslClientHandler;
import org.freeswitch.esl.client.internal.Context;
import org.freeswitch.esl.client.transport.CommandResponse;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.freeswitch.esl.client.transport.message.EslHeaders;

/**
 * End users of the inbound {@link Client} should not need to use this class.
 * <p/>
 * Specialised {@link AbstractEslClientHandler} that implements the connection logic for an
 * 'Inbound' FreeSWITCH Event Socket connection.  The responsibilities for this class are:
 * <ul><li>
 * To handle the auth request that the FreeSWITCH server will send immediately following a new
 * connection when mode is Inbound.
 * <li>
 * To signal the observing {@link IEslProtocolListener} (expected to be the Inbound client
 * implementation) when ESL events are received.
 * </ul>
 * Note: implementation requirement is that an {@link ExecutionHandler} is placed in the processing
 * pipeline prior to this handler. This will ensure that each incoming message is processed in its
 * own thread (although still guaranteed to be processed in the order of receipt).
 */
public class InboundClientHandler extends AbstractEslClientHandler {

	private final String password;
	private final IEslProtocolListener listener;

	public InboundClientHandler(String password, IEslProtocolListener listener) {
		this.password = password;
		this.listener = listener;
	}

	@Override
	protected void handleEslEvent(ChannelHandlerContext ctx, EslEvent event) {
		log.info("Received event: [{}]", event);
		listener.eventReceived(new Context(ctx.channel(), this), event);
	}

	@Override
	protected void handleAuthRequest(ChannelHandlerContext ctx) {
		log.info("Auth requested, sending [auth {}]", this.password);

		sendApiSingleLineCommand(ctx.channel(), "auth " + this.password)
			.thenAccept(response -> {
				log.info("Auth response [{}]", response);
				if (response.getContentType().equals(EslHeaders.Value.COMMAND_REPLY)) {
					final CommandResponse commandResponse = new CommandResponse("auth " + this.password, response);
					listener.authResponseReceived(commandResponse);
				} else {
					log.error("Bad auth response message [{}]", response);
					throw new IllegalStateException("Incorrect auth response");
				}
			});
	}

	@Override
	protected void handleDisconnectionNotice() {
		log.info("Received disconnection notice");
		listener.disconnected();
	}

}
