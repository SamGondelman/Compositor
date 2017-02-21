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
package com.gargoylesoftware.htmlunit.javascript.host.dom;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.BrowserRunner;
import com.gargoylesoftware.htmlunit.BrowserRunner.Alerts;
import com.gargoylesoftware.htmlunit.WebDriverTestCase;

/**
 * Tests for {@link DOMTokenList}.
 *
 * @author Ahmed Ashour
 * @author Ronald Brill
 * @author Frank Danek
 */
@RunWith(BrowserRunner.class)
public class DOMTokenListTest extends WebDriverTestCase {

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({ "3", "b", "true", "false", "c d" })
    public void various() throws Exception {
        final String html
            = "<html><head><title>First</title><script>\n"
            + "function test() {\n"
            + "    var list = document.body.classList;\n"
            + "    if (list) {\n"
            + "      alert(list.length);\n"
            + "      alert(list.item(1));\n"
            + "      alert(list.contains('c'));\n"
            + "      list.add('d');\n"
            + "      list.remove('a');\n"
            + "      alert(list.toggle('b'));\n"
            + "      alert(list);\n"
            + "    }\n"
            + "}\n"
            + "</script></head><body onload='test()' class='a b c'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({ "0", "null", "false", "# removed", "" })
    public void noAttribute() throws Exception {
        final String html
            = "<html><head><title>First</title><script>\n"
            + "function test() {\n"
            + "    var list = document.body.classList;\n"
            + "    if (list) {\n"
            + "      alert(list.length);\n"
            + "      alert(list.item(0));\n"
            + "      alert(list.contains('#'));\n"
            + "      list.remove('#'); alert('# removed');\n"
            + "      alert(document.body.className);\n"
            + "    }\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({ "0", "undefined", "1", "#" })
    public void noAttributeAdd() throws Exception {
        final String html
            = "<html><head><title>First</title><script>\n"
            + "function test() {\n"
            + "    var list = document.body.classList;\n"
            + "    if (list) {\n"
            + "      alert(list.length);\n"
            + "      alert(list.add('#'));\n"
            + "      alert(list.length);\n"
            + "      alert(document.body.className);\n"
            + "    }\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({ "0", "true", "1", "#" })
    public void noAttributeToggle() throws Exception {
        final String html
            = "<html><head><title>First</title><script>\n"
            + "function test() {\n"
            + "    var list = document.body.classList;\n"
            + "    if (list) {\n"
            + "      alert(list.length);\n"
            + "      alert(list.toggle('#'));\n"
            + "      alert(list.length);\n"
            + "      alert(document.body.className);\n"
            + "    }\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = { "3", "0", "3", "8" },
            IE = { "3", "0", "3", "7" })
    public void length() throws Exception {
        final String html
            = "<html><head><title>First</title><script>\n"
            + "function test() {\n"
            + "    var list = document.getElementById('d1').classList;\n"
            + "    if (list) {\n"
            + "      alert(list.length);\n"
            + "      list = document.getElementById('d2').classList;\n"
            + "      alert(list.length);\n"
            + "      list = document.getElementById('d3').classList;\n"
            + "      alert(list.length);\n"
            + "      list = document.getElementById('d4').classList;\n"
            + "      alert(list.length);\n"
            + "    }\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='d1' class=' a b c '></div>\n"
            + "  <div id='d2' class=''></div>\n"
            + "  <div id='d3' class=' a b a'></div>\n"
            + "  <div id='d4' class=' a b \t c \n d \u000B e \u000C f \r g'></div>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = { "a", "b", "c", "d", "\u000B", "e", "f", "g", "null", "null", "null" },
            IE = { "a", "b", "c", "d", "e", "f", "g", "null", "null", "null" })
    public void item() throws Exception {
        final String html
            = "<html><head><title>First</title><script>\n"
            + "function test() {\n"
            + "    var list = document.getElementById('d1').classList;\n"
            + "    if (list) {\n"
            + "      for (var i = 0; i < list.length; i++) {\n"
            + "        alert(list.item(i));\n"
            + "      }\n"
            + "      alert(list.item(-1));\n"
            + "      alert(list.item(list.length));\n"
            + "      alert(list.item(100));\n"
            + "    }\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='d1' class=' a b \t c \n d \u000B e \u000C f \r g'></div>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "null" })
    public void itemNegative() throws Exception {
        item("a b", -1);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "null" })
    public void itemNegative2() throws Exception {
        item("a b", -123);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "a" })
    public void itemFirst() throws Exception {
        item("a b", 0);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "b" })
    public void itemLast() throws Exception {
        item("a b", 1);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "null" })
    public void itemOutside() throws Exception {
        item("a b", 13);
    }

    private void item(final String in, final int pos) throws Exception {
        final String html
            = "<html><head><title>Test</title>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var elem = document.getElementById('d1');\n"
            + "    var list = elem.classList;\n"
            + "    if (!list) { alert('no list'); return; }\n"

            + "    alert(elem.className);\n"
            + "    alert(list.length);\n"
            + "    try {\n"
            + "      alert(list.item(" + pos + "));\n"
            + "    } catch(e) { alert('exception');}\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='d1' class='" + in + "'></div>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "exception" })
    public void containsEmpty() throws Exception {
        contains("a b", "");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "exception" })
    public void containsBlank() throws Exception {
        contains("a b", " ");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "exception" })
    public void containsTab() throws Exception {
        contains("a b", "\t");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "exception" })
    public void containsCr() throws Exception {
        contains("a b", "\\r");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "exception" })
    public void containsNl() throws Exception {
        contains("a b", "\\n");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"a b", "2", "false" },
            IE = {"a b", "2", "exception" })
    public void containsVt() throws Exception {
        contains("a b", "\u000B");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"", "0", "false" })
    public void containsInsideEmpty() throws Exception {
        contains("", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {" \t \n  ", "0", "false" },
            CHROME = {"", "0", "false" })
    public void containsInsideWhitespace() throws Exception {
        contains(" \t \r  ", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "true" })
    public void containsInsideAtStart() throws Exception {
        contains("a b", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "true" })
    public void containsInsideAtEnd() throws Exception {
        contains("a b", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"abc def", "2", "false" })
    public void containsInsideSubstringAtStart() throws Exception {
        contains("abc def", "ab");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"abc def", "2", "false" })
    public void containsInsideSubstringAtEnd() throws Exception {
        contains("abc def", "bc");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"abcd ef", "2", "false" })
    public void containsInsideSubstringInside() throws Exception {
        contains("abcd ef", "bc");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a  ", "1", "true" })
    public void containsInsideWhitespaceAtEnd() throws Exception {
        contains("a  ", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"  a", "1", "true" })
    public void containsInsideWhitespaceInFront() throws Exception {
        contains("  a", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({ "a \t c \n d  e", "4", "true" })
    public void containsWhitespaceExisting() throws Exception {
        contains("a \t c \n d  e", "c");
    }

    private void contains(final String in, final String toAdd) throws Exception {
        final String html
            = "<html><head><title>Test</title>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var elem = document.getElementById('d1');\n"
            + "    var list = elem.classList;\n"
            + "    if (!list) { alert('no list'); return; }\n"

            + "    alert(elem.className);\n"
            + "    alert(list.length);\n"
            + "    try {\n"
            + "      alert(list.contains('" + toAdd + "'));\n"
            + "    } catch(e) { alert('exception');}\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='d1' class='" + in + "'></div>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "exception", "2", "a b" })
    public void addEmpty() throws Exception {
        add("a b", "");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "exception", "2", "a b" })
    public void addBlank() throws Exception {
        add("a b", " ");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "exception", "2", "a b" })
    public void addTab() throws Exception {
        add("a b", "\t");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "exception", "2", "a b" })
    public void addCr() throws Exception {
        add("a b", "\\r");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "exception", "2", "a b" })
    public void addNl() throws Exception {
        add("a b", "\\n");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"a b", "2", "3", "a b \u000B" },
            IE = {"a b", "2", "exception", "2", "a b" })
    public void addVt() throws Exception {
        add("a b", "\u000B");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"", "0", "1", "a" })
    public void addToEmpty() throws Exception {
        add("", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {" \t \n  ", "0", "1", " \t \n  a" },
            IE = {" \t \n  ", "0", "1", "a" },
            CHROME = {"", "0", "1", " \t \n  a" })
    public void addToWhitespace() throws Exception {
        add(" \t \r  ", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"a  ", "1", "2", "a  b" },
            IE = {"a  ", "1", "2", "a b" })
    public void addToWhitespaceAtEnd() throws Exception {
        add("a  ", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "3", "a b c" })
    public void addNotExisting() throws Exception {
        add("a b", "c");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "2", "a b" })
    public void addExisting() throws Exception {
        add("a b", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"b a", "2", "2", "b a" })
    public void addExisting2() throws Exception {
        add("b a", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b a", "3", "exception", "3", "a b a" })
    public void addElementWithBlank() throws Exception {
        add("a b a", "a b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b a\tb", "4", "exception", "4", "a b a\tb" })
    public void addElementWithTab() throws Exception {
        add("a b a\tb", "a\tb");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = { "a \t c \n d  e", "4", "4", "a \t c \n d  e" },
            IE = { "a \t c \n d  e", "4", "4", "a \t c \n d  e" })
    public void addToWhitespaceExisting() throws Exception {
        add("a \t c \n d  e", "c");
    }

    private void add(final String in, final String toAdd) throws Exception {
        final String html
            = "<html><head><title>Test</title>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var elem = document.getElementById('d1');\n"
            + "    var list = elem.classList;\n"
            + "    if (!list) { alert('no list'); return; }\n"

            + "    alert(elem.className);\n"
            + "    alert(list.length);\n"
            + "    try {\n"
            + "      list.add('" + toAdd + "');\n"
            + "    } catch(e) { alert('exception');}\n"
            + "    alert(list.length);\n"
            + "    alert(elem.className);\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='d1' class='" + in + "'></div>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "exception", "2", "a b" })
    public void removeEmpty() throws Exception {
        remove("a b", "");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "exception", "2", "a b" })
    public void removeBlank() throws Exception {
        remove("a b", " ");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "exception", "2", "a b" })
    public void removeTab() throws Exception {
        remove("a b", "\t");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "exception", "2", "a b" })
    public void removeCr() throws Exception {
        remove("a b", "\\r");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "exception", "2", "a b" })
    public void removeNl() throws Exception {
        remove("a b", "\\n");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"a b", "2", "2", "a b" },
            IE = {"a b", "2", "exception", "2", "a b" })
    public void removeVt() throws Exception {
        remove("a b", "\u000B");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"", "0", "0", "" })
    public void removeFromEmpty() throws Exception {
        remove("", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {" \t \n  ", "0", "0", " \t \n  " },
            CHROME = {"", "0", "0", "" })
    public void removeFromWhitespace() throws Exception {
        remove(" \t \r  ", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b", "2", "2", "a b" })
    public void removeNotExisting() throws Exception {
        remove("a b", "c");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b a", "3", "1", "b" })
    public void removeDuplicated() throws Exception {
        remove("a b a", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b a", "3", "exception", "3", "a b a" })
    public void removeElementWithBlank() throws Exception {
        remove("a b a", "a b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a b a\tb", "4", "exception", "4", "a b a\tb" })
    public void removeElementWithTab() throws Exception {
        remove("a b a\tb", "a\tb");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"a", "1", "0", "" })
    public void removeLast() throws Exception {
        remove("a", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = { "a \t c \n d  e", "4", "3", "a d  e" },
            IE = { "a \t c \n d  e", "4", "3", "a d e" })
    public void removeWhitespace() throws Exception {
        remove("a \t c \n d  e", "c");
    }

    private void remove(final String in, final String toRemove) throws Exception {
        final String html
            = "<html><head><title>Test</title>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var elem = document.getElementById('d1');\n"
            + "    var list = elem.classList;\n"
            + "    if (!list) { alert('no list'); return; }\n"

            + "    alert(elem.className);\n"
            + "    alert(list.length);\n"
            + "    try {\n"
            + "      list.remove('" + toRemove + "');\n"
            + "    } catch(e) { alert('exception');}\n"
            + "    alert(list.length);\n"
            + "    alert(elem.className);\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='d1' class='" + in + "'></div>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({ "exception", "exception", "2", "true", "false", "1", "false", "true", "2", "true" })
    public void toggle() throws Exception {
        final String html
            = "<html><head><title>First</title><script>\n"
            + "function test() {\n"
            + "    var list = document.getElementById('d1').classList;\n"
            + "    if (list) {\n"
            + "      try {\n"
            + "        list.toggle('ab e');\n"
            + "      } catch(e) { alert('exception');}\n"
            + "      try {\n"
            + "        list.toggle('');\n"
            + "      } catch(e) { alert('exception');}\n"
            + "      alert(list.length);\n"
            + "      alert(list.contains('e'));\n"
            + "      alert(list.toggle('e'));\n"
            + "      alert(list.length);\n"
            + "      alert(list.contains('e'));\n"
            + "      alert(list.toggle('e'));\n"
            + "      alert(list.length);\n"
            + "      alert(list.contains('e'));\n"
            + "    }\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='d1' class='a e'></div>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }
}