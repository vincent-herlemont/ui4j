package com.ui4j.webkit.spi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.webkit.dom.DocumentImpl;
import com.sun.webkit.dom.HTMLElementImpl;
import com.ui4j.api.browser.SelectorEngine;
import com.ui4j.api.dom.Document;
import com.ui4j.api.dom.Element;
import com.ui4j.spi.PageContext;
import com.ui4j.webkit.browser.WebKitPageContext;
import com.ui4j.webkit.dom.WebKitElement;

public class W3CSelectorEngine implements SelectorEngine {

    private PageContext context;

    private Document document;

    private WebKitJavaScriptEngine engine;

    public W3CSelectorEngine(PageContext context, Document document, WebKitJavaScriptEngine engine) {
        this.context = context;
        this.document = document;
        this.engine = engine;
    }

    @Override
    public Element query(String selector) {
        DocumentImpl documentImpl = (DocumentImpl) engine.getEngine().getDocument();
        String escapedSelector = selector.replace('\'', '"');
        if (documentImpl == null) {
            return null;
        }
        org.w3c.dom.Element element = documentImpl.querySelector(escapedSelector);
        if (element != null) {
            return ((WebKitPageContext) context).createElement(element, document, engine);
        } else {
            return null;
        }
    }

    @Override
    public List<Element> queryAll(String selector) {
        DocumentImpl documentImpl = (DocumentImpl) engine.getEngine().getDocument();
        String escapedSelector = selector.replace('\'', '"');
        if (documentImpl == null) {
            return Collections.emptyList();
        }
        NodeList nodes = documentImpl.querySelectorAll(escapedSelector);
        List<Element> elements = new ArrayList<>();
        for (int i = 0 ; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            elements.add(((WebKitPageContext) context).createElement(node, document, engine));
        }
        if (elements.isEmpty()) {
            return Collections.emptyList();
        }
        return elements;
    }

    @Override
    public Element query(Element element, String selector) {
        if (!(element instanceof WebKitElement)) {
            return null;
        }
        WebKitElement fxElementImpl = (WebKitElement) element;
        HTMLElementImpl elementImpl = fxElementImpl.getHtmlElement();
        String escapedSelector = selector.replace('\'', '"');
        org.w3c.dom.Element found = elementImpl.querySelector(escapedSelector);
        if (found == null) {
            return null;
        }
        return ((WebKitPageContext) context).createElement(found, document, engine);
    }

    @Override
    public List<Element> queryAll(Element element, String selector) {
        if (!(element instanceof WebKitElement)) {
            return Collections.emptyList();
        }
        WebKitElement fxElementImpl = (WebKitElement) element;
        HTMLElementImpl elementImpl = fxElementImpl.getHtmlElement();
        String escapedSelector = selector.replace('\'', '"');
        NodeList nodes = elementImpl.querySelectorAll(escapedSelector);
        List<Element> elements = new ArrayList<>();
        for (int i = 0 ; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            elements.add(((WebKitPageContext) context).createElement(node, document, engine));
        }
        if (elements.isEmpty()) {
            return Collections.emptyList();
        } else {
            return elements;
        }
    }
}
