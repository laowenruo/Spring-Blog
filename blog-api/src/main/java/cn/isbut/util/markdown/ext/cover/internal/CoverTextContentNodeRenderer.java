package cn.isbut.util.markdown.ext.cover.internal;

import org.commonmark.node.Node;
import org.commonmark.renderer.text.TextContentNodeRendererContext;
import org.commonmark.renderer.text.TextContentWriter;

/**
 * @author Ryan
 * @Description: 文本节点渲染
 *
 */
public class CoverTextContentNodeRenderer extends AbstractCoverNodeRenderer {
    private final TextContentNodeRendererContext context;
    private final TextContentWriter textContent;

    public CoverTextContentNodeRenderer(TextContentNodeRendererContext context) {
        this.context = context;
        this.textContent = context.getWriter();
    }

    @Override
    public void render(Node node) {
        textContent.write('/');
        renderChildren(node);
        textContent.write('/');
    }

    private void renderChildren(Node parent) {
        Node node = parent.getFirstChild();
        while (node != null) {
            Node next = node.getNext();
            context.render(node);
            node = next;
        }
    }
}
