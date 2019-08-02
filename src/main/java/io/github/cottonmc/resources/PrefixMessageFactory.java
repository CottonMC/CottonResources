package io.github.cottonmc.resources;

import org.apache.logging.log4j.message.AbstractMessageFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;

public class PrefixMessageFactory extends AbstractMessageFactory {
	private static final long serialVersionUID = -5551389178212469285L;
	private final String prefix;

	public PrefixMessageFactory(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public Message newMessage(String message, Object... params) {
		return new ParameterizedMessage("[" + prefix + "] " + message, params);
	}
}