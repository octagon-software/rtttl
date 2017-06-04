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
 * Encodes RTTTL Strings.
 *
 * @see RTTTLParser
 */
public class RTTTLEncoder
{
    /**
     * Encodes the provided tone sequence as an RTTTL String.
     * <p>
     * The encoder will attempt to utilize the default octave, duration and quarterNoteBeats per minute.
     *
     * @param toneSequence The sequence to encode
     * @return the RTTTL String that matches the tone sequence
     * @throws IllegalStateException If the tone sequence contains a duration that does not match a valid beat duration.
     */
    public String encode(ToneSequence toneSequence)
        throws IllegalStateException
    {
        StringBuilder result = new StringBuilder();
        encodeName(result, toneSequence);
        result.append(':');
        encodeControlSection(result, toneSequence);
        result.append(':');
        encodeToneList(result, toneSequence);
        return result.toString();
    }

    private void encodeName(StringBuilder result, ToneSequence toneSequence) {
        result.append(toneSequence.name);
    }

    private void encodeControlSection(StringBuilder result, ToneSequence toneSequence) {
        boolean needsSeparator = false;

        int octave = toneSequence.defaultOctave;
        if (octave != ToneSequence.DEFAULT_OCTAVE) {
            result.append(RTTTLParser.CONTROL_NAME_DEFAULT_OCTAVE).append('=').append(octave);
            needsSeparator = true;
        }

        Duration duration = toneSequence.defaultDuration;
        if (duration != ToneSequence.DEFAULT_DURATION) {
            if (needsSeparator) {
                result.append(',');
            }
            result.append(RTTTLParser.CONTROL_NAME_DEFAULT_DURATION).append('=').append(duration.beatDenominator);
            needsSeparator = true;
        }

        int beatsPerMinute = toneSequence.beatsPerMinute;
        if (beatsPerMinute != ToneSequence.DEFAULT_BEATS_PER_MINUTE) {
            if (needsSeparator) {
                result.append(',');
            }
            result.append(RTTTLParser.CONTROL_NAME_BEATS_PER_MINUTE).append('=').append(beatsPerMinute);
        }
    }

    /**
     * Encodes the list of tones as an RTTTL string, to the given StringBuilder
     *
     * @throws IllegalStateException If the tone list contains a duration does not match a valid beat duration
     */
    private void encodeToneList(StringBuilder result, ToneSequence toneSequence)
        throws IllegalStateException
    {
        int defaultOctave = toneSequence.defaultOctave;
        Duration defaultDuration = toneSequence.defaultDuration;

        List<Tone> toneList = toneSequence.toneList;
        for (int i = 0; i < toneList.size(); i++) {
            if (i > 0) {
                result.append(',');
            }

            Tone tone = toneList.get(i);

            // Duration
            if (tone.duration.beatDenominator != defaultDuration.beatDenominator) {
                result.append(tone.duration.beatDenominator);
            }

            // Note
            if (tone.isRest()) {
                result.append('p');
            } else {
                result.append(tone.note.note.toLowerCase());
            }

            // Dotted
            boolean dotted = tone.duration.isDotted();
            if (dotted) {
                result.append('.');
            }

            // Octave
            if (!tone.isRest()) {
                if (defaultOctave != tone.note.octave) {
                    result.append(tone.note.octave);
                }
            }
        }
    }
}
