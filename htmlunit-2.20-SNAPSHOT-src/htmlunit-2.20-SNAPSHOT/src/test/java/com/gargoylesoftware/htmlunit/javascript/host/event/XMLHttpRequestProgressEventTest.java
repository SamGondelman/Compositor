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
package com.gargoylesoftware.htmlunit.javascript.host.event;

import static com.gargoylesoftware.htmlunit.BrowserRunner.Browser.CHROME;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.BrowserRunner;
import com.gargoylesoftware.htmlunit.BrowserRunner.Alerts;
import com.gargoylesoftware.htmlunit.BrowserRunner.NotYetImplemented;
import com.gargoylesoftware.htmlunit.WebDriverTestCase;
import com.gargoylesoftware.htmlunit.html.HtmlPageTest;

/**
 * Tests for {@link XMLHttpRequestProgressEvent}.
 *
 * @author Ronald Brill
 */
@RunWith(BrowserRunner.class)
public class XMLHttpRequestProgressEventTest extends WebDriverTestCase {

    private static final String DUMP_EVENT_FUNCTION = "  function dump(event) {\n"
        + "    if (event) {\n"
        + "      alert(event);\n"
        + "      alert(event.type);\n"
        + "      alert(event.bubbles);\n"
        + "      alert(event.cancelable);\n"
        + "      alert(event.lengthComputable);\n"
        + "      alert(event.loaded);\n"
        + "      alert(event.total);\n"
        + "    } else {\n"
        + "      alert('no event');\n"
        + "    }\n"
        + "  }\n";

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("exception")
    @NotYetImplemented(CHROME)
    public void create_ctor() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    try {\n"
            + "      var event = new XMLHttpRequestProgressEvent('test');\n"
            + "      dump(event);\n"
            + "    } catch (e) { alert('exception') }\n"
            + "  }\n"
            + DUMP_EVENT_FUNCTION
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("exception")
    @NotYetImplemented(CHROME)
    public void create_ctorWithDetails() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    try {\n"
            + "      var event = new XMLHttpRequestProgressEvent('test', {\n"
            + "        'bubbles': true,\n"
            + "        'lengthComputable': true,\n"
            + "        'loaded': 234,\n"
            + "        'total': 666\n"
            + "      });\n"
            + "      dump(event);\n"
            + "    } catch (e) { alert('exception') }\n"
            + "  }\n"
            + DUMP_EVENT_FUNCTION
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "exception",
            CHROME = { "[object XMLHttpRequestProgressEvent]", "", "false", "false", "false", "0", "0" })
    public void create_createEvent() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    try {\n"
            + "      var event = document.createEvent('XMLHttpRequestProgressEvent');\n"
            + "      dump(event);\n"
            + "    } catch (e) { alert('exception') }\n"
            + "  }\n"
            + DUMP_EVENT_FUNCTION
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }
}
