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
package com.gargoylesoftware.htmlunit.javascript.host.performance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import com.gargoylesoftware.htmlunit.BrowserRunner;
import com.gargoylesoftware.htmlunit.WebDriverTestCase;
import com.gargoylesoftware.htmlunit.html.HtmlPageTest;

/**
 * Tests for {@link Performance}.
 *
 * @author Ronald Brill
 */
@RunWith(BrowserRunner.class)
public class PerformanceTest extends WebDriverTestCase {

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void now() throws Exception {
        final String html =
                HtmlPageTest.STANDARDS_MODE_PREFIX_
                + "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  if (!performance) { alert('performance not available'); retrun; };\n"
                + "  alert(performance.now());\n"
                + "  alert(performance.now());\n"
                + "  alert(typeof performance.now());\n"
                + "</script>\n"
                + "</head>\n"
                + "<body></body>\n"
                + "</html>";

        final WebDriver driver = loadPage2(html);
        final String now1 = getCollectedAlerts(driver).get(0);
        assertTrue(Double.parseDouble(now1) > 0);

        final String now2 = getCollectedAlerts(driver).get(1);
        assertTrue(Double.parseDouble(now2) > Double.parseDouble(now1));

        assertEquals("number", getCollectedAlerts(driver).get(2));
    }
}
