/*
 * Copyright (c) 2017 Octagon Software, LLC
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.octagonsoftware.rtttl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for Duration enumeration
 */
public class DurationTest
{
    private static final float EPSILON = 1.0E-6f;

    @Test
    public void testDurationIsDotted() {
        Assert.assertTrue(Duration.DOTTED_HALF.isDotted());
    }

    @Test
    public void testQuarterNoteBeats() {
        Assert.assertEquals(3.0f, Duration.DOTTED_HALF.quarterNoteBeats, EPSILON);
    }

    @Test
    public void testAsDotted() {
        Assert.assertEquals(Duration.DOTTED_HALF, Duration.HALF.asDotted());
        try {
            Duration.DOTTED_HALF.asDotted();
            Assert.fail("Should have thrown an exception, since already dotted.");
        } catch (IllegalStateException e) {
            // pass
        }
    }

    @Test
    public void testSecondsAtBeatsPerMinute() {
        Assert.assertEquals(3.0f, Duration.DOTTED_HALF.secondsAtBeatsPerMinute(60.0f), EPSILON);
        Assert.assertEquals(6.0f, Duration.DOTTED_HALF.secondsAtBeatsPerMinute(30.0f), EPSILON);
    }
}