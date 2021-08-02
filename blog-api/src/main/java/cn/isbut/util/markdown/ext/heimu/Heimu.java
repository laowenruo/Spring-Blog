package cn.isbut.util.markdown.ext.heimu;

import org.commonmark.node.CustomNode;
import org.commonmark.node.Delimited;


public class Heimu extends CustomNode implements Delimited {
	private static final String DELIMITER = "@@";

	@Override
	public String getOpeningDelimiter() {
		return DELIMITER;
	}

	@Override
	public String getClosingDelimiter() {
		return DELIMITER;
	}
}
