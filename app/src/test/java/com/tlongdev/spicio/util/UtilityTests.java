package com.tlongdev.spicio.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
@RunWith(MockitoJUnitRunner.class)
public class UtilityTests {

    @Test
    public void testStringJoin() {
        List<String> strings = new LinkedList<>();

        for (int i = 0; i < 5; i++) {
            strings.add(String.valueOf(i));
        }

        assertEquals("0|1|2|3|4", Utility.join(strings, "|"));
    }
}
