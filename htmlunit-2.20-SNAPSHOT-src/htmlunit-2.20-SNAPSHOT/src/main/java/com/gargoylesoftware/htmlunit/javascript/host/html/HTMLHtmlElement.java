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

import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.CHROME;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.EDGE;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.FF;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.IE;
import net.sourceforge.htmlunit.corejs.javascript.Context;

import com.gargoylesoftware.htmlunit.html.HtmlHtml;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxClass;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxClasses;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxConstructor;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxGetter;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxSetter;
import com.gargoylesoftware.htmlunit.javascript.configuration.WebBrowser;

/**
 * The JavaScript object {@code HTMLHtmlElement}.
 *
 * @author Ahmed Ashour
 * @author Marc Guillemot
 * @author Ronald Brill
 */
@JsxClasses({
        @JsxClass(domClass = HtmlHtml.class,
            browsers = { @WebBrowser(CHROME), @WebBrowser(FF), @WebBrowser(IE),
                    @WebBrowser(EDGE) })
    })
public class HTMLHtmlElement extends HTMLElement {

    /**
     * Creates an instance.
     */
    @JsxConstructor({ @WebBrowser(CHROME), @WebBrowser(FF), @WebBrowser(EDGE) })
    public HTMLHtmlElement() {
    }

    /** {@inheritDoc} */
    @Override
    public Object getParentNode() {
        return getWindow().getDocument_js();
    }

    /** {@inheritDoc} */
    @Override
    public int getClientWidth() {
        return getWindow().getInnerWidth();
    }

    /** {@inheritDoc} */
    @Override
    public int getClientHeight() {
        return getWindow().getInnerHeight();
    }

    /**
     * Overwritten to throw an exception.
     * @param value the new value for replacing this node
     */
    @JsxSetter
    @Override
    public void setOuterHTML(final Object value) {
        throw Context.reportRuntimeError("outerHTML is read-only for tag 'html'");
    }

    /**
     * Overwritten to throw an exception because this is readonly.
     * @param value the new value for the contents of this node
     */
    @Override
    protected void setInnerTextImpl(final String value) {
        throw Context.reportRuntimeError("innerText is read-only for tag 'html'");
    }

    /**
     * Returns {@code version} property.
     * @return the {@code version} property
     */
    @JsxGetter
    public String getVersion() {
        return getDomNodeOrDie().getAttribute("version");
    }

    /**
     * Sets {@code version} property.
     * @param version the {@code version} property
     */
    @JsxSetter
    public void setVersion(final String version) {
        getDomNodeOrDie().setAttribute("version", version);
    }

}
