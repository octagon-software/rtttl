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

import java.text.ParseException;

public class RTTTLParserTest {
    private static final double EPSILON = 1.0E-6;

    @Test
    public void testContainsSpaces()
        throws ParseException
    {
        RTTTLParser parser = new RTTTLParser();
        Assert.assertEquals(2, parser.parse("a:d=4:c6, d6").toneList.size());
    }

    @Test
    public void testTooFewColons() {
        RTTTLParser parser = new RTTTLParser();
        try {
            parser.parse("a:b");
            Assert.fail("Should have thrown an exception about too few colons");
        } catch (ParseException e) {
            // pass
        }
    }

    @Test
    public void testTooManyColons() {
        RTTTLParser parser = new RTTTLParser();
        try {
            parser.parse("a:b:c:d");
            Assert.fail("Should have thrown an exception about too many colons");
        } catch (ParseException e) {
            // pass
        }
    }

    @Test
    public void testBadControlSection() {
        RTTTLParser parser = new RTTTLParser();

        try {
            parser.parse("a:b:c");
            Assert.fail("Should have thrown an exception about missing '='");
        } catch (ParseException e) {
            // pass
        }

        try {
            parser.parse("a:oc=3:c");
            Assert.fail("Should have thrown an exception about bad control name");
        } catch (ParseException e) {
            // pass
        }

        try {
            parser.parse("a:z=3:c");
            Assert.fail("Should have thrown an exception about bad control name");
        } catch (ParseException e) {
            // pass
        }

        try {
            parser.parse("a:o=a:c");
            Assert.fail("Should have thrown an exception about bad control value");
        } catch (ParseException e) {
            // pass
        }
    }

    @Test
    public void testControlPairsInNotes()
        throws ParseException
    {
        RTTTLParser parser = new RTTTLParser();

        ToneSequence toneSequence = parser.parse("name:o=3:a,b,c,o=4,d,e,p,f");
        Assert.assertEquals(Note.A3, toneSequence.toneList.get(0).note);
        Assert.assertEquals(Note.B3, toneSequence.toneList.get(1).note);
        Assert.assertEquals(Note.C3, toneSequence.toneList.get(2).note);
        Assert.assertEquals(Note.D4, toneSequence.toneList.get(3).note);
        Assert.assertEquals(Note.E4, toneSequence.toneList.get(4).note);
        Assert.assertNull(toneSequence.toneList.get(5).note);
        Assert.assertEquals(Note.F4, toneSequence.toneList.get(6).note);
    }

    @Test
    public void testDuration()
        throws ParseException
    {
        RTTTLParser parser = new RTTTLParser();

        ToneSequence toneSequence = parser.parse("name:b=60:a,1a,2a,4a,8a,16a,32a,1a.,2a.,4a.");
        Assert.assertEquals(1.0, toneSequence.toneList.get(0).durationInSeconds, EPSILON);
        Assert.assertEquals(4.0, toneSequence.toneList.get(1).durationInSeconds, EPSILON);
        Assert.assertEquals(2.0, toneSequence.toneList.get(2).durationInSeconds, EPSILON);
        Assert.assertEquals(1.0, toneSequence.toneList.get(3).durationInSeconds, EPSILON);
        Assert.assertEquals(0.5, toneSequence.toneList.get(4).durationInSeconds, EPSILON);
        Assert.assertEquals(0.25, toneSequence.toneList.get(5).durationInSeconds, EPSILON);
        Assert.assertEquals(0.125, toneSequence.toneList.get(6).durationInSeconds, EPSILON);
        Assert.assertEquals(6.0, toneSequence.toneList.get(7).durationInSeconds, EPSILON);
        Assert.assertEquals(3.0, toneSequence.toneList.get(8).durationInSeconds, EPSILON);
        Assert.assertEquals(1.5, toneSequence.toneList.get(9).durationInSeconds, EPSILON);
    }

    @Test
    public void testOctave()
        throws ParseException
    {
        RTTTLParser parser = new RTTTLParser();

        ToneSequence toneSequence = parser.parse("name:b=60:a,a1,a2,a3,a4,a5,a6,a7");
        Assert.assertEquals(6, toneSequence.toneList.get(0).note.octave);
        Assert.assertEquals(1, toneSequence.toneList.get(1).note.octave);
        Assert.assertEquals(2, toneSequence.toneList.get(2).note.octave);
        Assert.assertEquals(3, toneSequence.toneList.get(3).note.octave);
        Assert.assertEquals(4, toneSequence.toneList.get(4).note.octave);
        Assert.assertEquals(5, toneSequence.toneList.get(5).note.octave);
        Assert.assertEquals(6, toneSequence.toneList.get(6).note.octave);
        Assert.assertEquals(7, toneSequence.toneList.get(7).note.octave);
    }

    @Test
    public void testInvalidDuration() {
        RTTTLParser parser = new RTTTLParser();

        try {
            parser.parse("name:b=60:34a");
            Assert.fail("Should have complained about invalid duration.");
        } catch (ParseException e) {
            // pass
        }
    }
}
