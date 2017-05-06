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
 * Unit tests for Note enum.
 */
public class NoteTest
{
    private static final double EPSILON = 1.0E-6;

    @Test
    public void testMinMaxSemitone() {
        Assert.assertEquals(Note.C0.semitone, Note.MIN_SEMITONE);
        Assert.assertEquals(Note.B8.semitone, Note.MAX_SEMITONE);
    }

    @Test
    public void testFindNoteForSemitone() {
        Assert.assertEquals(Note.GS4, Note.findNoteFromSemitone(68));
        Assert.assertEquals(Note.A4, Note.findNoteFromSemitone(69));
        Assert.assertEquals(Note.AS4, Note.findNoteFromSemitone(70));

        try {
            Note.findNoteFromSemitone(Note.MIN_SEMITONE - 1);
        } catch (IndexOutOfBoundsException e) {
            // pass
        }

        try {
            Note.findNoteFromSemitone(Note.MAX_SEMITONE + 1);
        } catch (IndexOutOfBoundsException e) {
            // pass
        }
    }

    @Test
    public void testGetHz() {
        Assert.assertEquals(1760.0f, Note.A6.hz, EPSILON);
        Assert.assertEquals(880.0f, Note.A5.hz, EPSILON);
        Assert.assertEquals(440.0f, Note.A4.hz, EPSILON);
        Assert.assertEquals(220.0f, Note.A3.hz, EPSILON);
        Assert.assertEquals(110.0f, Note.A2.hz, EPSILON);
    }

    @Test
    public void testfindClosestNote() {
        Assert.assertEquals(Note.C0, Note.findClosestNote(0.0f));
        Assert.assertEquals(Note.A2, Note.findClosestNote(110.0f));
        Assert.assertEquals(Note.A4, Note.findClosestNote(439.0f));
        Assert.assertEquals(Note.A4, Note.findClosestNote(440.0f));
        Assert.assertEquals(Note.A4, Note.findClosestNote(441.0f));
        Assert.assertEquals(Note.A5, Note.findClosestNote(879.0f));
        Assert.assertEquals(Note.A5, Note.findClosestNote(880.0f));
        Assert.assertEquals(Note.A5, Note.findClosestNote(881.0f));
        Assert.assertEquals(Note.A6, Note.findClosestNote(1761.0f));
        Assert.assertEquals(Note.B8, Note.findClosestNote(9999.0f));
    }

    @Test
    public void testNoteProperties() {
        Assert.assertEquals("C#", Note.CS4.note);
        Assert.assertEquals(4, Note.CS4.octave);
        Assert.assertEquals(61, Note.CS4.semitone);
        Assert.assertEquals(true, Note.CS4.sharp);
        Assert.assertEquals("A", Note.A2.note);
        Assert.assertEquals(false, Note.A2.sharp);
    }

    @Test
    public void testFindNoteByName() {
        Assert.assertEquals(Note.CS4, Note.findNoteByName("C#4"));
        Assert.assertEquals(Note.G2, Note.findNoteByName("G2"));
        Assert.assertNull(Note.findNoteByName("foo"));
    }

    @Test
    public void testSemitoneFromHz() {
        Assert.assertEquals(69, Note.semitoneFromHz(440.0f), EPSILON);
        Assert.assertEquals(81, Note.semitoneFromHz(880.0f), EPSILON);
    }

    @Test
    public void testHzFromSemitone() {
        Assert.assertEquals(440.0f, Note.hzFromSemitone(69), EPSILON);
        Assert.assertEquals(880.0f, Note.hzFromSemitone(81), EPSILON);
    }

}
