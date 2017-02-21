/*
 * Copyright (c) 2002-2016 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.htmlunit.javascript.host.html;

import static com.gargoylesoftware.htmlunit.html.DomElement.ATTRIBUTE_NOT_DEFINED;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.CHROME;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.EDGE;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.FF;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.IE;

import java.net.MalformedURLException;
import java.net.URL;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxClass;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxClasses;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxConstructor;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxGetter;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxSetter;
import com.gargoylesoftware.htmlunit.javascript.configuration.WebBrowser;

/**
 * The JavaScript object that represents an {@code HTMLScriptElement}.
 *
 * @author Daniel Gredler
 * @author Marc Guillemot
 * @author Ahmed Ashour
 * @author Ronald Brill
 * @author Frank Danek
 */
@JsxClasses({
        @JsxClass(domClass = HtmlScript.class,
                browsers = { @WebBrowser(CHROME), @WebBrowser(FF), @WebBrowser(IE),
            @WebBrowser(EDGE) })
    })
public class HTMLScriptElement extends HTMLElement {

    /**
     * Creates an instance.
     */
    @JsxConstructor({ @WebBrowser(CHROME), @WebBrowser(FF), @WebBrowser(EDGE) })
    public HTMLScriptElement() {
    }

    /**
     * Returns the {@code src} attribute.
     * @return the {@code src} attribute
     */
    @JsxGetter
    public String getSrc() {
        final HtmlScript tmpScript = (HtmlScript) getDomNodeOrDie();
        String src = tmpScript.getSrcAttribute();
        if (ATTRIBUTE_NOT_DEFINED == src) {
            return src;
        }
        try {
            final URL expandedSrc = ((HtmlPage) tmpScript.getPage()).getFullyQualifiedUrl(src);
            src = expandedSrc.toString();
        }
        catch (final MalformedURLException e) {
            // ignore
        }
        return src;
    }

    /**
     * Sets the {@code src} attribute.
     * @param src the {@code src} attribute
     */
    @JsxSetter
    public void setSrc(final String src) {
        getDomNodeOrDie().setAttribute("src", src);
    }

    /**
     * Returns the {@code text} attribute.
     * @return the {@code text} attribute
     */
    @JsxGetter
    public String getText() {
        final StringBuilder scriptCode = new StringBuilder();
        for (final DomNode node : getDomNodeOrDie().getChildren()) {
            if (node instanceof DomText) {
                final DomText domText = (DomText) node;
                scriptCode.append(domText.getData());
            }
        }
        return scriptCode.toString();
    }

    /**
     * Sets the {@code text} attribute.
     * @param text the {@code text} attribute
     */
    @JsxSetter
    public void setText(final String text) {
        final HtmlElement htmlElement = getDomNodeOrDie();
        htmlElement.removeAllChildren();
        final DomNode textChild = new DomText(htmlElement.getPage(), text);
        htmlElement.appendChild(textChild);

        final HtmlScript tmpScript = (HtmlScript) htmlElement;
        tmpScript.executeScriptIfNeeded();
    }

    /**
     * Returns the {@code type} attribute.
     * @return the {@code type} attribute
     */
    @JsxGetter
    public String getType() {
        return getDomNodeOrDie().getAttribute("type");
    }

    /**
     * Sets the {@code type} attribute.
     * @param type the {@code type} attribute
     */
    @JsxSetter
    public void setType(final String type) {
        getDomNodeOrDie().setAttribute("type", type);
    }

    /**
     * Returns the event handler that fires on every state change.
     * @return the event handler that fires on every state change
     */
    @JsxGetter(@WebBrowser(IE))
    public Object getOnreadystatechange() {
        return getEventHandlerProp("onreadystatechange");
    }

    /**
     * Sets the event handler that fires on every state change.
     * @param handler the event handler that fires on every state change
     */
    @JsxSetter(@WebBrowser(IE))
    public void setOnreadystatechange(final Object handler) {
        setEventHandlerProp("onreadystatechange", handler);
    }

    /**
     * Returns the event handler that fires on load.
     * @return the event handler that fires on load
     */
    @JsxGetter({ @WebBrowser(CHROME), @WebBrowser(FF), @WebBrowser(IE) })
    public Object getOnload() {
        return getEventHandlerProp("onload");
    }

    /**
     * Sets the event handler that fires on load.
     * @param handler the event handler that fires on load
     */
    @JsxSetter({ @WebBrowser(CHROME), @WebBrowser(FF), @WebBrowser(IE) })
    public void setOnload(final Object handler) {
        setEventHandlerProp("onload", handler);
    }

    /**
     * Returns the ready state of the script. This is an IE-only property.
     * @return the ready state of the script
     * @see DomNode#READY_STATE_UNINITIALIZED
     * @see DomNode#READY_STATE_LOADING
     * @see DomNode#READY_STATE_LOADED
     * @see DomNode#READY_STATE_INTERACTIVE
     * @see DomNode#READY_STATE_COMPLETE
     */
    @JsxGetter(@WebBrowser(IE))
    public String getReadyState() {
        return getDomNodeOrDie().getReadyState();
    }

    /**
     * Overwritten for special IE handling.
     *
     * @param childObject the node to add to this node
     * @return the newly added child node
     */
    @Override
    public Object appendChild(final Object childObject) {
        final HtmlScript tmpScript = (HtmlScript) getDomNodeOrDie();
        final boolean wasEmpty = tmpScript.getFirstChild() == null;
        final Object result = super.appendChild(childObject);

        if (wasEmpty) {
            tmpScript.executeScriptIfNeeded();
        }
        return result;
    }

    /**
     * Returns the <tt>async</tt> attribute.
     * @return the <tt>async</tt> attribute
     */
    @JsxGetter({@WebBrowser(CHROME), @WebBrowser(FF), @WebBrowser(IE)})
    public String getAsync() {
        return getDomNodeOrDie().getAttribute("async");
    }

    /**
     * Sets the <tt>async</tt> attribute.
     * @param type the <tt>async</tt> attribute
     */
    @JsxSetter({@WebBrowser(CHROME), @WebBrowser(FF), @WebBrowser(IE)})
    public void setAsync(final String type) {
        getDomNodeOrDie().setAttribute("async", type);
    }
}
