/*
 * Copyright (C) 2021 Scott Shaffer - All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tipplerow.jam.lang;

import java.util.List;
import java.util.regex.Pattern;

import com.tipplerow.jam.regex.RegexUtil;
import com.tipplerow.jam.testng.JamTestBase;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class StringUtilTest extends JamTestBase {
    @Test public void testBackslash() {
        assertEquals(1, StringUtil.BACK_SLASH.length());
        assertEquals(2, StringUtil.DOUBLE_BACK_SLASH.length());
    }

    @Test public void testChop() {
        assertEquals("cho", StringUtil.chop("chop"));
        assertEquals("", StringUtil.chop("c"));
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testChopEmpty() {
        StringUtil.chop("");
    }

    @Test public void testMultiLine() {
        assertEquals(List.of(""), StringUtil.multiLine("", 5));
        assertEquals(List.of("abc"), StringUtil.multiLine("abc", 5));
        assertEquals(List.of("abcde"), StringUtil.multiLine("abcde", 5));
        assertEquals(List.of("abcde", "f"), StringUtil.multiLine("abcdef", 5));
        assertEquals(List.of("abcde", "fghij", "klm"), StringUtil.multiLine("abcdefghijklm", 5));
    }

    @Test public void testRemoveCommentText() {
        Pattern delim1 = RegexUtil.PYTHON_COMMENT;
        Pattern delim2 = RegexUtil.CPP_COMMENT;

        assertEquals("",  StringUtil.removeCommentText("#", delim1));
        assertEquals("#", StringUtil.removeCommentText("#", delim2));

        assertEquals("//", StringUtil.removeCommentText("//", delim1));
        assertEquals("",   StringUtil.removeCommentText("//", delim2));

        assertEquals("abc ",      StringUtil.removeCommentText("abc # def", delim1));
        assertEquals("abc # def", StringUtil.removeCommentText("abc # def", delim2));

        assertEquals("abc // def", StringUtil.removeCommentText("abc // def", delim1));
        assertEquals("abc ",       StringUtil.removeCommentText("abc // def", delim2));

        assertEquals("abc / def", StringUtil.removeCommentText("abc / def", delim1));
        assertEquals("abc / def", StringUtil.removeCommentText("abc / def", delim2));

        assertEquals("",          StringUtil.removeCommentText("# abc def", delim1));
        assertEquals("# abc def", StringUtil.removeCommentText("# abc def", delim2));

        assertEquals("// abc def", StringUtil.removeCommentText("// abc def", delim1));
        assertEquals("",           StringUtil.removeCommentText("// abc def", delim2));
    }

    @Test public void testRemovePrefix() {
        assertEquals("abc", StringUtil.removePrefix("abc", "def"));
        assertEquals("abc", StringUtil.removePrefix("abc", "abcdef"));
        assertEquals("def", StringUtil.removePrefix("abcdef", "abc"));
    }

    @Test public void testRemoveSuffix() {
        assertEquals("abc", StringUtil.removeSuffix("abc", "def"));
        assertEquals("abc", StringUtil.removeSuffix("abc", "defabc"));
        assertEquals("def", StringUtil.removeSuffix("defabc", "abc"));
    }

    @Test public void testRemoveWhiteSpace() {
        assertEquals("", StringUtil.removeWhiteSpace(""));
        assertEquals("abc", StringUtil.removeWhiteSpace("abc")); 
        assertEquals("abc", StringUtil.removeWhiteSpace("   a b  c  "));
    }
}
