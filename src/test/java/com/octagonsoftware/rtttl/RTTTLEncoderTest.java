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

/**
 * Unit tests for RTTTLEncoder
 */
public class RTTTLEncoderTest
{
    @Test
    public void testRTTTLEncoderLoopback()
        throws ParseException
    {
        String song = "name:o=8,d=2,b=10:c,d,e,f,g,a,b,c1,d1,e1,c.2,d.2,e.2,4c,16d,32e.3";
        RTTTLParser parser = new RTTTLParser();
        ToneSequence seq = parser.parse(song);
        RTTTLEncoder encoder = new RTTTLEncoder();
        String song2 = encoder.encode(seq);
        Assert.assertEquals(song, song2);
    }
}
