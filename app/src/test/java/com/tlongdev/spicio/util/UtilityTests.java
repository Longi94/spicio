package com.tlongdev.spicio.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
@RunWith(MockitoJUnitRunner.class)
public class UtilityTests {

    @Test
    public void testStringJoin() {
        String[] strings = new String[5];

        for (int i = 0; i < 5; i++) {
            strings[i] = String.valueOf(i);
        }

        assertEquals("0|1|2|3|4", Utility.join(strings, "|"));
    }
}
