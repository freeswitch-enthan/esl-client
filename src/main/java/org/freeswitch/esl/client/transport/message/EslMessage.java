package org.freeswitch.esl.client.transport.message;

import org.freeswitch.esl.client.transport.message.EslHeaders.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Basic FreeSWITCH Event Socket messages from the server are decoded into this data object.
 * <p/>
 * An ESL message is modelled as text lines.  A message always has one or more header lines, and
 * optionally may have some body lines.
 * <p/>
 * Header lines are parsed and cached in a map keyed by the {@link EslHeaders.Name} enum.  A message
 * is always expected to have a "Content-Type" header
 * <p/>
 * Any Body lines are cached in a list.
 *
 * @see EslHeaders.Name
 */
public class EslMessage {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final Map<Name, String> headers = new HashMap<>();
	private final List<String> body = new ArrayList<>();
	private Integer contentLength = null;

	/**
	 * All the received message headers in a map keyed by {@link EslHeaders.Name}. The string mapped value
	 * is the parsed content of the header line (ie, it does not include the header name).
	 *
	 * @return map of header values
	 */
	public Map<Name, String> getHeaders() {
		return headers;
	}

	/**
	 * Convenience method
	 *
	 * @param headerName as a {@link EslHeaders.Name}
	 * @return true if an only if there is a header entry with the supplied header name
	 */
	public boolean hasHeader(Name headerName) {
		return headers.containsKey(headerName);
	}

	/**
	 * Convenience method
	 *
	 * @param headerName as a {@link EslHeaders.Name}
	 * @return same as getHeaders().get( headerName )
	 */
	public String getHeaderValue(Name headerName) {
		return headers.get(headerName);
	}

	/**
	 * Convenience method
	 *
	 * @return true if and only if a header exists with name "Content-Length"
	 */
	public boolean hasContentLength() {
		return headers.containsKey(Name.CONTENT_LENGTH);
	}

	/**
	 * Convenience method
	 *
	 * @return integer value of header with name "Content-Length"
	 */
	public Integer getContentLength() {
		if (contentLength != null) {
			return contentLength;
		}
		if (hasContentLength()) {
			contentLength = Integer.valueOf(headers.get(Name.CONTENT_LENGTH));
		}
		return contentLength;
	}

	/**
	 * Convenience method
	 *
	 * @return header value of header with name "Content-Type"
	 */
	public String getContentType() {
		return headers.get(Name.CONTENT_TYPE);
	}

	/**
	 * Any received message body lines
	 *
	 * @return list with a string for each line received, may be an empty list
	 */
	public List<String> getBodyLines() {
		return body;
	}

	/**
	 * Used by the {@link EslFrameDecoder}.
	 */
	void addHeader(Name name, String value) {
		log.debug("adding header [{}] [{}]", name, value);
		headers.put(name, value);
	}

	/**
	 * Used by the {@link EslFrameDecoder}
	 */
	void addBodyLine(String line) {
		if (line == null) {
			return;
		}
		body.add(line);
	}

	/**
	 * Did this message return Reply-Text: +OK
	 *
	 * @return true if reply equals +OK, false if not.
	 */
	public boolean isReplyOk() {
		String reply_text = getHeaderValue(Name.REPLY_TEXT);
		return reply_text != null && reply_text.trim().equals("+OK");
	}

	@Override
	public String toString() {
		return toStringHelper(this)
				.add("contentType", getContentType())
				.add("headers", headers.size())
				.add("body", body.size() + " lines")
				.toString();
	}

}
