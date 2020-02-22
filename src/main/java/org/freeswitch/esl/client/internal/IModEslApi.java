package org.freeswitch.esl.client.internal;

import java.util.List;
import org.freeswitch.esl.client.transport.CommandResponse;
import org.freeswitch.esl.client.transport.SendMsg;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.freeswitch.esl.client.transport.message.EslMessage;

import java.util.concurrent.CompletableFuture;

public interface IModEslApi {

	enum EventFormat {

		PLAIN("plain"),
		XML("xml"),
		JSON("json");

		private final String text;

		EventFormat(String txt) {
			this.text = txt;
		}

		@Override
		public String toString() {
			return text;
		}

	}

	enum LoggingLevel {

		CONSOLE("console"),
		DEBUG("debug"),
		INFO("info"),
		NOTICE("notice"),
		WARNING("warning"),
		ERR("err"),
		CRIT("crit"),
		ALERT("alert");

		private final String text;

		LoggingLevel(String txt) {
			this.text = txt;
		}

		@Override
		public String toString() {
			return text;
		}

	}

	boolean canSend();

	EslMessage sendApiCommand(String command, String arg);

	CompletableFuture<EslEvent> sendBackgroundApiCommand(String command, String arg);

	CommandResponse setEventSubscriptions(EventFormat format, String events);

	List<CommandResponse> cancelEventSubscriptions(String... eventNames);

	/**
	 * Cancel any existing event subscription.
	 * @return a {@link CommandResponse} with the server's response.
	 */
	CommandResponse cancelEventSubscriptions();

	/**
	 * Add an event filter to the current set of event filters on this connection. Any of the event headers
	 * can be used as a filter.
	 * </p>
	 * Note that event filters follow 'filter-in' semantics. That is, when a filter is applied
	 * only the filtered values will be received. Multiple filters can be added to the current
	 * connection.
	 * </p>
	 * Example filters:
	 * <pre>
	 *    eventHeader        valueToFilter
	 *    ----------------------------------
	 *    Event-Name         CHANNEL_EXECUTE
	 *    Channel-State      CS_NEW
	 * </pre>
	 * @param eventHeader to filter on
	 * @param valueToFilter the value to match
	 * @return a {@link CommandResponse} with the server's response.
	 */
	CommandResponse addEventFilter(String eventHeader, String valueToFilter);

	CommandResponse deleteEventFilter(String eventHeader, String valueToFilter);

	CommandResponse sendMessage(SendMsg sendMsg);

	CommandResponse setLoggingLevel(LoggingLevel level);

	CommandResponse cancelLogging();
}
