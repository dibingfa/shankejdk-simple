/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.doclets.formats.html.markup;

import java.io.IOException;
import java.io.Writer;

import com.sun.tools.doclets.internal.toolkit.Content;
import com.sun.tools.doclets.internal.toolkit.util.*;

/**
 * Class for generating raw HTML content to be added to HTML pages of javadoc output.
 *
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 *
 * @author Bhavesh Patel
 */
public class RawHtml extends Content {

    private String rawHtmlContent;

    public static final Content nbsp = new RawHtml("&nbsp;");

    /**
     * Constructor to construct a RawHtml object.
     *
     * @param rawHtml raw HTML text to be added
     */
    public RawHtml(String rawHtml) {
        rawHtmlContent = nullCheck(rawHtml);
    }

    /**
     * This method is not supported by the class.
     *
     * @param content content that needs to be added
     * @throws DocletAbortException this method will always throw a
     *                              DocletAbortException because it
     *                              is not supported.
     */
    public void addContent(Content content) {
        throw new DocletAbortException("not supported");
    }

    /**
     * This method is not supported by the class.
     *
     * @param stringContent string content that needs to be added
     * @throws DocletAbortException this method will always throw a
     *                              DocletAbortException because it
     *                              is not supported.
     */
    public void addContent(String stringContent) {
        throw new DocletAbortException("not supported");
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEmpty() {
        return rawHtmlContent.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return rawHtmlContent;
    }

    private enum State { TEXT, ENTITY, TAG, STRING };

    @Override
    public int charCount() {
        return charCount(rawHtmlContent);
    }

    static int charCount(String htmlText) {
        State state = State.TEXT;
        int count = 0;
        for (int i = 0; i < htmlText.length(); i++) {
            char c = htmlText.charAt(i);
            switch (state) {
                case TEXT:
                    switch (c) {
                        case '<':
                            state = State.TAG;
                            break;
                        case '&':
                            state = State.ENTITY;
                            count++;
                            break;
                        default:
                            count++;
                    }
                    break;

                case ENTITY:
                    if (!Character.isLetterOrDigit(c))
                        state = State.TEXT;
                    break;

                case TAG:
                    switch (c) {
                        case '"':
                            state = State.STRING;
                            break;
                        case '>':
                            state = State.TEXT;
                            break;
                    }
                    break;

                case STRING:
                    switch (c) {
                        case '"':
                            state = State.TAG;
                            break;
                    }
            }
        }
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean write(Writer out, boolean atNewline) throws IOException {
        out.write(rawHtmlContent);
        return rawHtmlContent.endsWith(DocletConstants.NL);
    }
}
