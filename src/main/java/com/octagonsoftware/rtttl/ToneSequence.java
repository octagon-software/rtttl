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

import java.util.List;

/**
 * Sequence of tones, each of which has a note and duration.
 */
public class ToneSequence {
    /** Name of this ring tone */
    public final String name;

    /** Sequential list of tones that make up this ring tone */
    public final List<Tone> toneList;

    /**
     * Creates a new ring tone.
     *
     * @param name The name of the ring tone
     * @param toneList The list of tones in this ring tone.
     * @throws IllegalArgumentException If either the name or toneList is null.
     */
    public ToneSequence(String name, List<Tone> toneList) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null.");
        }
        if (toneList == null) {
            throw new IllegalArgumentException("tone cannot be null.");
        }
        this.name = name;
        this.toneList = toneList;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ToneSequence toneSequence = (ToneSequence) o;

        if (!name.equals(toneSequence.name)) {
            return false;
        }
        return toneList.equals(toneSequence.toneList);
    }

    @Override public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + toneList.hashCode();
        return result;
    }

    @Override public String toString() {
        return "ToneSequence{" +
            "name='" + name + '\'' +
            ", toneList=" + toneList +
            '}';
    }

}
