package cn.isbut.util.markdown.ext.heimu;


import cn.isbut.util.markdown.ext.heimu.internal.HeimuHtmlNodeRenderer;
import cn.isbut.util.markdown.ext.heimu.internal.HeimuTextContentNodeRenderer;
import cn.isbut.util.markdown.ext.heimu.internal.HeimuDelimiterProcessor;
import org.commonmark.Extension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlNodeRendererFactory;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.text.TextContentNodeRendererContext;
import org.commonmark.renderer.text.TextContentNodeRendererFactory;
import org.commonmark.renderer.text.TextContentRenderer;


public class HeimuExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension, TextContentRenderer.TextContentRendererExtension {
	private HeimuExtension() {
	}

	public static Extension create() {
		return new HeimuExtension();
	}

	@Override
	public void extend(Parser.Builder parserBuilder) {
		parserBuilder.customDelimiterProcessor(new HeimuDelimiterProcessor());
	}

	@Override
	public void extend(HtmlRenderer.Builder rendererBuilder) {
		rendererBuilder.nodeRendererFactory(new HtmlNodeRendererFactory() {
			@Override
			public NodeRenderer create(HtmlNodeRendererContext context) {
				return new HeimuHtmlNodeRenderer(context);
			}
		});
	}

	@Override
	public void extend(TextContentRenderer.Builder rendererBuilder) {
		rendererBuilder.nodeRendererFactory(new TextContentNodeRendererFactory() {
			@Override
			public NodeRenderer create(TextContentNodeRendererContext context) {
				return new HeimuTextContentNodeRenderer(context);
			}
		});
	}
}
