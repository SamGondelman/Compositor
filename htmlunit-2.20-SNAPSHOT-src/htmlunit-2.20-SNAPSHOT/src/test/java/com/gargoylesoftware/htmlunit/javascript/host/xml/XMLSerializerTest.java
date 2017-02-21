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
package com.gargoylesoftware.htmlunit.javascript.host.xml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.gargoylesoftware.htmlunit.BrowserRunner;
import com.gargoylesoftware.htmlunit.BrowserRunner.Alerts;
import com.gargoylesoftware.htmlunit.BrowserRunner.NotYetImplemented;
import com.gargoylesoftware.htmlunit.WebDriverTestCase;

/**
 * Tests for {@link XMLSerializer}.
 *
 * @author Ahmed Ashour
 * @author Darrell DeBoer
 * @author Frank Danek
 */
@RunWith(BrowserRunner.class)
public class XMLSerializerTest extends WebDriverTestCase {

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("<note>32<to>Tove</to>3210<from>Jani</from>321032<body>Do32not32forget32me32this32weekend!</body>"
                    + "32<outer>10323232<inner>Some32Value</inner></outer>32</note>")
    public void test() throws Exception {
        final String expectedString = getExpectedAlerts()[0];
        setExpectedAlerts();
        final String serializationText =
                "<note> "
                + "<to>Tove</to> \\n"
                + "<from>Jani</from> \\n "
                + "<body>Do not forget me this weekend!</body> "
                + "<outer>\\n "
                + "  <inner>Some Value</inner>"
                + "</outer> "
                + "</note>";

        final WebDriver driver = loadPageWithAlerts2(constructPageContent(serializationText));
        final WebElement textArea = driver.findElement(By.id("myTextArea"));
        assertEquals(expectedString, textArea.getAttribute("value"));
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("<a><!--32abc32--></a>")
    public void comment() throws Exception {
        final String expectedString = getExpectedAlerts()[0];
        setExpectedAlerts();
        final String serializationText = "<a><!-- abc --></a>";
        final WebDriver driver = loadPageWithAlerts2(constructPageContent(serializationText));
        final WebElement textArea = driver.findElement(By.id("myTextArea"));
        assertEquals(expectedString, textArea.getAttribute("value"));
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("<a>&lt;&gt;&amp;</a>")
    public void xmlEntities() throws Exception {
        final String expectedString = getExpectedAlerts()[0];
        setExpectedAlerts();
        final String serializationText = "<a>&lt;&gt;&amp;</a>";
        final WebDriver driver = loadPageWithAlerts2(constructPageContent(serializationText));
        final WebElement textArea = driver.findElement(By.id("myTextArea"));
        assertEquals(expectedString, textArea.getAttribute("value"));
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "<?xml32version=\"1.0\"32encoding=\"UTF-8\"?>1310<xsl:stylesheet32version=\"1.0\"32"
                    + "xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">103232<xsl:template32match=\"/\">103232<html>"
                    + "1032323232<body>1032323232</body>103232</html>103232</xsl:template>10</xsl:stylesheet>",
            IE = "<xsl:stylesheet32xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"32version=\"1.0\">103232"
                    + "<xsl:template32match=\"/\">103232<html>1032323232<body>1032323232</body>103232</html>103232"
                    + "</xsl:template>10</xsl:stylesheet>")
    @NotYetImplemented
    public void nameSpaces() throws Exception {
        final String expectedString = getExpectedAlerts()[0];
        setExpectedAlerts();
        final String serializationText =
                "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\\n"
                + "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\\n"
                + "  <xsl:template match=\"/\">\\n"
                + "  <html>\\n"
                + "    <body>\\n"
                + "    </body>\\n"
                + "  </html>\\n"
                + "  </xsl:template>\\n"
                + "</xsl:stylesheet>";

        final WebDriver driver = loadPageWithAlerts2(constructPageContent(serializationText));
        final WebElement textArea = driver.findElement(By.id("myTextArea"));
        assertEquals(expectedString, textArea.getAttribute("value"));
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "<document32attrib=\"attribValue\"><outer32attrib=\"attribValue\">"
                    + "<inner32attrib=\"attribValue\"/><meta32attrib=\"attribValue\"/></outer></document>",
            IE = "<document32attrib=\"attribValue\"><outer32attrib=\"attribValue\">"
                    + "<inner32attrib=\"attribValue\"32/><meta32attrib=\"attribValue\"32/></outer></document>")
    public void attributes() throws Exception {
        final String expectedString = getExpectedAlerts()[0];
        setExpectedAlerts();
        final String serializationText = "<document attrib=\"attribValue\">"
                                            + "<outer attrib=\"attribValue\">"
                                            + "<inner attrib=\"attribValue\"/>"
                                            + "<meta attrib=\"attribValue\"/>"
                                            + "</outer></document>";

        final WebDriver driver = loadPageWithAlerts2(constructPageContent(serializationText));
        final WebElement textArea = driver.findElement(By.id("myTextArea"));
        assertEquals(expectedString, textArea.getAttribute("value"));
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "<?xml32version=\"1.0\"32encoding=\"UTF-8\"?>1310<html32xmlns=\"http://www.w3.org/1999/xhtml\">"
                    + "<head><title>html</title></head>"
                    + "<body32id=\"bodyId\">"
                    + "<span32class=\"spanClass\">foo</span>"
                    + "</body>"
                    + "</html>",
            IE = "<html32xmlns=\"http://www.w3.org/1999/xhtml\">"
                    + "<head><title>html</title></head>"
                    + "<body>"
                    + "<span32class=\"spanClass\">foo</span>"
                    + "</body>"
                    + "</html>")
    @NotYetImplemented
    // IE11 omits the body's ID attribute
    public void htmlAttributes() throws Exception {
        final String expectedString = getExpectedAlerts()[0];
        setExpectedAlerts();
        final String serializationText = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"
                                          + "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
                                          + "<head><title>html</title></head>"
                                          + "<body id=\"bodyId\">"
                                          + "<span class=\"spanClass\">foo</span>"
                                          + "</body>"
                                          + "</html>";

        final WebDriver driver = loadPageWithAlerts2(constructPageContent(serializationText));
        final WebElement textArea = driver.findElement(By.id("myTextArea"));
        assertEquals(expectedString, textArea.getAttribute("value"));
    }

    /**
     * Constructs an HTML page that when loaded will parse and serialize the provided text.
     * First the provided text is parsed into a Document. Then the Document is serialized (browser-specific).
     * Finally the result is placed into the text area "myTextArea".
     */
    private String constructPageContent(final String serializationText) {
        final String escapedText = serializationText.replace("\n", "\\n");

        final StringBuilder buffer = new StringBuilder();
        buffer.append(
              "<html><head><title>foo</title><script>\n"
            + "  function test() {\n");

        buffer.append("    var text = '").append(escapedText).append("';\n").append(
              "    var doc = " + XMLDocumentTest.callLoadXMLDocumentFromString("text") + ";\n"
            + "    var xml = " + XMLDocumentTest.callSerializeXMLDocumentToString("doc") + ";\n"
            + "    var ta = document.getElementById('myTextArea');\n"
            + "    for (var i = 0; i < xml.length; i++) {\n"
            + "      if (xml.charCodeAt(i) < 33)\n"
            + "        ta.value += xml.charCodeAt(i);\n"
            + "      else\n"
            + "        ta.value += xml.charAt(i);\n"
            + "    }\n"
            + "  }\n"
            + XMLDocumentTest.LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION
            + XMLDocumentTest.SERIALIZE_XML_DOCUMENT_TO_STRING_FUNCTION
            + "</script></head><body onload='test()'>\n"
            + "  <textarea id='myTextArea' cols='80' rows='30'></textarea>\n"
            + "</body></html>");
        return buffer.toString();
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = { "<foo/>", "<foo/>" },
            IE = { "<foo />", "<foo />" })
    public void document() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    if (document.implementation.createDocument) {\n"
            + "      var doc = document.implementation.createDocument('', 'foo', null);\n"
            + "      alert(new XMLSerializer().serializeToString(doc));\n"
            + "      alert(new XMLSerializer().serializeToString(doc.documentElement));\n"
            + "    } else { alert('createDocument not available'); }\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = { "<img/>", "<img xmlns=\"http://www.w3.org/1999/xhtml\" />", "<?myTarget myData?>" },
            IE = { "<img />", "", "<?myTarget myData?>" })
    public void xml() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var doc = " + XMLDocumentTest.callCreateXMLDocument() + ";\n"
            + "    if (window.XMLSerializer) {\n"
            + "      testFragment(doc);\n"
            + "      testFragment(document);\n"
            + "      var pi = doc.createProcessingInstruction('myTarget', 'myData');\n"
            + "      alert(new XMLSerializer().serializeToString(pi));\n"
            + "    } else { alert('XMLSerializer not defined') };\n"
            + "  }\n"
            + "  function testFragment(doc) {\n"
            + "    var fragment = doc.createDocumentFragment();\n"
            + "    var img = doc.createElement('img');\n"
            + "    fragment.appendChild(img);\n"
            + "    alert(new XMLSerializer().serializeToString(fragment));\n"
            + "  }\n"
            + XMLDocumentTest.CREATE_XML_DOCUMENT_FUNCTION
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "<root><my:parent xmlns:my=\"myUri\"><my:child/><another_child/></my:parent></root>",
            IE = "<root><my:parent xmlns:my=\"myUri\"><my:child /><another_child /></my:parent></root>")
    public void namespace() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var doc = " + XMLDocumentTest.callCreateXMLDocument() + ";\n"
            + "    var root = doc.createElement('root');\n"
            + "    doc.appendChild(root);\n"
            + "    var parent = createNS(doc, 'my:parent', 'myUri');\n"
            + "    root.appendChild(parent);\n"
            + "    parent.appendChild(createNS(doc, 'my:child', 'myUri'));\n"
            + "    parent.appendChild(doc.createElement('another_child'));\n"
            + "    alert(" + XMLDocumentTest.callSerializeXMLDocumentToString("doc") + ");\n"
            + "  }\n"
            + "  function createNS(doc, name, uri) {\n"
            + "    return typeof doc.createNode == 'function' || typeof doc.createNode == 'unknown' ? "
            + "doc.createNode(1, name, uri) : doc.createElementNS(uri, name);\n"
            + "  }\n"
            + XMLDocumentTest.CREATE_XML_DOCUMENT_FUNCTION
            + XMLDocumentTest.SERIALIZE_XML_DOCUMENT_TO_STRING_FUNCTION
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "<textarea xmlns=\"http://www.w3.org/1999/xhtml\"></textarea>",
            IE = "<textarea xmlns=\"http://www.w3.org/1999/xhtml\" />")
    public void mixedCase() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    if (window.XMLSerializer) {\n"
            + "      var t = document.createElement('teXtaREa');\n"
            + "      alert(new XMLSerializer().serializeToString(t));\n"
            + "    } else { alert('XMLSerializer not defined') };\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({ "<area xmlns=\"http://www.w3.org/1999/xhtml\" />",
                    "<base xmlns=\"http://www.w3.org/1999/xhtml\" />",
                    "<basefont xmlns=\"http://www.w3.org/1999/xhtml\" />",
                    "<br xmlns=\"http://www.w3.org/1999/xhtml\" />",
                    "<hr xmlns=\"http://www.w3.org/1999/xhtml\" />",
                    "<input xmlns=\"http://www.w3.org/1999/xhtml\" />",
                    "<link xmlns=\"http://www.w3.org/1999/xhtml\" />",
                    "<meta xmlns=\"http://www.w3.org/1999/xhtml\" />" })
    public void noClosingTag() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    if (window.XMLSerializer) {\n"
            + "      var t = document.createElement('area');\n"
            + "      alert(new XMLSerializer().serializeToString(t));\n"
            + "      var t = document.createElement('base');\n"
            + "      alert(new XMLSerializer().serializeToString(t));\n"
            + "      var t = document.createElement('basefont');\n"
            + "      alert(new XMLSerializer().serializeToString(t));\n"
            + "      var t = document.createElement('br');\n"
            + "      alert(new XMLSerializer().serializeToString(t));\n"
            + "      var t = document.createElement('hr');\n"
            + "      alert(new XMLSerializer().serializeToString(t));\n"
            + "      var t = document.createElement('input');\n"
            + "      alert(new XMLSerializer().serializeToString(t));\n"
            + "      var t = document.createElement('link');\n"
            + "      alert(new XMLSerializer().serializeToString(t));\n"
            + "      var t = document.createElement('meta');\n"
            + "      alert(new XMLSerializer().serializeToString(t));\n"
            + "    } else { alert('XMLSerializer not defined') };\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * There is a small difference between HtmlUnit and FF.
     * HtmlUnit adds type=text per default to the input
     * FF handles input without type as type=text
     * the fix for this is too big, so i made this workaround
     * and add another (not yet implemented) test
     *
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("<input xmlns=\"http://www.w3.org/1999/xhtml\" />")
    public void inputTagWithoutType() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var t = document.createElement('input');\n"
            + "    if (window.XMLSerializer) {\n"
            + "      alert(new XMLSerializer().serializeToString(t));\n"
            + "    } else { alert('XMLSerializer not defined') };\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Test for some not self closing tags.
     *
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = { "<div xmlns=\"http://www.w3.org/1999/xhtml\"></div>",
                    "<h1 xmlns=\"http://www.w3.org/1999/xhtml\"></h1>",
                    "<p xmlns=\"http://www.w3.org/1999/xhtml\"></p>",
                    "<li xmlns=\"http://www.w3.org/1999/xhtml\"></li>",
                    "<textarea xmlns=\"http://www.w3.org/1999/xhtml\"></textarea>" },
            IE = { "<div xmlns=\"http://www.w3.org/1999/xhtml\" />",
                    "<h1 xmlns=\"http://www.w3.org/1999/xhtml\" />",
                    "<p xmlns=\"http://www.w3.org/1999/xhtml\" />",
                    "<li xmlns=\"http://www.w3.org/1999/xhtml\" />",
                    "<textarea xmlns=\"http://www.w3.org/1999/xhtml\" />" })
    public void otherTags() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    if (window.XMLSerializer) {\n"
            + "      var t = document.createElement('div');\n"
            + "      alert(new XMLSerializer().serializeToString(t));\n"
            + "      var t = document.createElement('h1');\n"
            + "      alert(new XMLSerializer().serializeToString(t));\n"
            + "      var t = document.createElement('p');\n"
            + "      alert(new XMLSerializer().serializeToString(t));\n"
            + "      var t = document.createElement('li');\n"
            + "      alert(new XMLSerializer().serializeToString(t));\n"
            + "      var t = document.createElement('textarea');\n"
            + "      alert(new XMLSerializer().serializeToString(t));\n"
            + "    } else { alert('XMLSerializer not defined') };\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("<img xmlns=\"http://www.w3.org/1999/xhtml\" href=\"mypage.htm\" />")
    public void noClosingTagWithAttribute() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    if (window.XMLSerializer) {\n"
            + "      var t = document.createElement('img');\n"
            + "      t.setAttribute('href', 'mypage.htm');\n"
            + "      alert(new XMLSerializer().serializeToString(t));\n"
            + "    } else { alert('XMLSerializer not defined') };\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }
}
