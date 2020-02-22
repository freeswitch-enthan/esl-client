package org.freeswitch.esl.client.transport;

import static com.google.common.base.MoreObjects.toStringHelper;

import org.freeswitch.esl.client.transport.message.EslHeaders.Name;
import org.freeswitch.esl.client.transport.message.EslMessage;

/**
 * Result object to carry the results of a command sent to the FreeSWITCH Event Socket.
 */
public class CommandResponse {
	private final String command;
	private final String replyText;
	private final EslMessage response;
	private final boolean success;

	public CommandResponse(String command, EslMessage response) {
		this.command = command;
		this.response = response;
		this.replyText = response.getHeaderValue(Name.REPLY_TEXT);
		this.success = replyText.startsWith("+OK");
	}

	/**
	 * @return the original command sent to the server
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @return true if and only if the response Reply-Text line starts with "+OK"
	 */
	public boolean isOk() {
		return success;
	}

	/**
	 * @return the full response Reply-Text line.
	 */
	public String getReplyText() {
		return replyText;
	}

	/**
	 * @return {@link EslMessage} the full response from the server
	 */
	public EslMessage getResponse() {
		return response;
	}

	@Override
	public String toString() {
		return toStringHelper(this)
				.add("command", getCommand())
				.add("replyText", getReplyText())
				.add("success", isOk())
				.toString();
	}
}
