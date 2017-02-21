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
package com.gargoylesoftware.htmlunit.javascript.host;

import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.CHROME;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.EDGE;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.FF;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.IE;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;

import net.sourceforge.htmlunit.corejs.javascript.Context;

import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxClass;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxClasses;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxConstructor;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxFunction;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxGetter;
import com.gargoylesoftware.htmlunit.javascript.configuration.WebBrowser;

/**
 * A JavaScript object for the client's browsing history.
 *
 * @author <a href="mailto:mbowler@GargoyleSoftware.com">Mike Bowler</a>
 * @author Chris Erskine
 * @author Daniel Gredler
 * @author Ahmed Ashour
 * @author Ronald Brill
 * @author Adam Afeltowicz
 */
@JsxClasses({
        @JsxClass(browsers = { @WebBrowser(CHROME), @WebBrowser(FF), @WebBrowser(IE),
                @WebBrowser(EDGE) })
    })
public class History extends SimpleScriptable {

    /**
     * Creates an instance.
     */
    @JsxConstructor({ @WebBrowser(CHROME), @WebBrowser(FF), @WebBrowser(EDGE) })
    public History() {
    }

    /**
     * Returns the {@code length} property.
     * @return the {@code length} property
     */
    @JsxGetter
    public int getLength() {
        final WebWindow w = getWindow().getWebWindow();
        return w.getHistory().getLength();
    }

    /**
     * Returns the {@code state} property.
     * @return the {@code state} property
     */
    @JsxGetter({ @WebBrowser(CHROME), @WebBrowser(FF), @WebBrowser(IE) })
    public Object getState() {
        final WebWindow w = getWindow().getWebWindow();
        return w.getHistory().getCurrentState();
    }

    /**
     * JavaScript function "back".
     */
    @JsxFunction
    public void back() {
        final WebWindow w = getWindow().getWebWindow();
        try {
            w.getHistory().back();
        }
        catch (final IOException e) {
            Context.throwAsScriptRuntimeEx(e);
        }
    }

    /**
     * JavaScript function "forward".
     */
    @JsxFunction
    public void forward() {
        final WebWindow w = getWindow().getWebWindow();
        try {
            w.getHistory().forward();
        }
        catch (final IOException e) {
            Context.throwAsScriptRuntimeEx(e);
        }
    }

    /**
     * JavaScript function "go".
     * @param relativeIndex the relative index
     */
    @JsxFunction
    public void go(final int relativeIndex) {
        final WebWindow w = getWindow().getWebWindow();
        try {
            w.getHistory().go(relativeIndex);
        }
        catch (final IOException e) {
            Context.throwAsScriptRuntimeEx(e);
        }
    }

    /**
     * Replaces a state.
     * @param object the state object
     * @param title the title
     * @param url an optional URL
     */
    @JsxFunction({ @WebBrowser(CHROME), @WebBrowser(FF), @WebBrowser(IE) })
    public void replaceState(final Object object, final String title, final String url) {
        final WebWindow w = getWindow().getWebWindow();
        final HtmlPage page = (HtmlPage) w.getEnclosedPage();
        try {
            URL newStateUrl = null;
            if (StringUtils.isNotBlank(url)) {
                newStateUrl = page.getFullyQualifiedUrl(url);
            }
            w.getHistory().replaceState(object, newStateUrl);
            if (newStateUrl != null) {
                page.getWebResponse().getWebRequest().setUrl(newStateUrl);
            }
        }
        catch (final MalformedURLException e) {
            Context.throwAsScriptRuntimeEx(e);
        }
    }

    /**
     * Pushes a state.
     * @param object the state object
     * @param title the title
     * @param url an optional URL
     */
    @JsxFunction({ @WebBrowser(CHROME), @WebBrowser(FF), @WebBrowser(IE) })
    public void pushState(final Object object, final String title, final String url) {
        try {
            getWindow().getLocation().setHref(url, true, object);
        }
        catch (final IOException e) {
            Context.throwAsScriptRuntimeEx(e);
        }
    }
}
