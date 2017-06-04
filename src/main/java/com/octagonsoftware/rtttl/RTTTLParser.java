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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for RTTTL (Ring Tone Text Transfer Language) strings, which
 * represent simple musical tunes.
 * <p>
 * Example RTTTL String:
 * <code>
 * Auld L S:d=4,o=6,b=101:g5,c,8c,c,e,d,8c,d,8e,8d,c,8c,e,g,2a,a,g,8e,e,c,d,8c,d,8e,8d,c,8a5,a5,g5,2c
 * </code>
 * <p>
 * This parser is a bit looser than the original specification, in that:
 * <ol>
 * <li>It allows longer names than the original 10 character limit.</li>
 * <li>It allows octaves 0-8 instead of the original limit of 4-7.</li>
 * <li>The special duration '.' can appear anywhere in the note string.</li>
 * </ol>
 *
 * @see <a href="http://www.panuworld.net/nuukiaworld/download/nokix/rtttl.htm">RTTTL Specification</a>
 */
public class RTTTLParser
{

    /**
     * Name used in a control pair to indicate quarterNoteBeats per minute
     */
    static final char CONTROL_NAME_BEATS_PER_MINUTE = 'b';

    /**
     * Name used in a control pair to indicate default duration
     */
    static final char CONTROL_NAME_DEFAULT_DURATION = 'd';

    /**
     * Name used in a control pair to indicate default defaultOctave
     */
    static final char CONTROL_NAME_DEFAULT_OCTAVE = 'o';

    private static final Pattern PATTERN_NOTE = Pattern.compile("(\\d{1,2})?([pcdefgab]#?)(\\d)?");


    /**
     * Parses an RTTTL string and returns a {@link ToneSequence}.
     *
     * @param str The RTTTL string to parse
     * @return The {@link ToneSequence}
     * @throws ParseException If the RTTTL string is invalid.
     */
    public ToneSequence parse(String str)
        throws ParseException
    {
        // RTTTLString := <name> ":" <control-section> ":" <tone-section>
        String[] parts = str.split(":");
        assertSyntax(parts.length == 3, "Expected 2 ':'s but got " + (parts.length - 1));

        ParseContext context = new ParseContext();
        String name = parts[0];
        parseControlSection(context, parts[1].replaceAll(" ", ""));
        parseToneSection(context, parts[2].replaceAll(" ", ""));

        return new ToneSequence(name, context.toneList, context.defaultOctave, context.defaultDuration, context.beatsPerMinute);
    }

    /**
     * control-section := &lt;control-pair&gt; ["," &lt;control-section&gt;]
     */
    private void parseControlSection(ParseContext context, String controlStr)
        throws ParseException
    {
        String[] controlPairStrs = controlStr.split(",");
        for (String controlPairStr : controlPairStrs) {
            processControlPair(context, controlPairStr);
        }
    }

    /**
     * &lt;control-pair&gt; := &lt;control-name&gt; ["="] &lt;control-value&gt;
     */
    private void processControlPair(ParseContext context, String controlPairStr)
        throws ParseException
    {
        if ("".equals(controlPairStr)) {
            return;
        }

        String[] parts = controlPairStr.split("=");
        assertSyntax(parts.length == 2, "Expected 'name'='value' in control section");

        String controlNameStr = parts[0];
        assertSyntax(controlNameStr.length() == 1, "Control name must be 1 character");
        char controlName = controlNameStr.charAt(0);

        String controlValueStr = parts[1];
        int value;
        try {
            value = Integer.parseInt(controlValueStr);
        } catch (NumberFormatException e) {
            throw new ParseException("Could not convert value to number for control pair " +
                controlNameStr + "=" + controlValueStr, 0);
        }

        switch (controlName) {
            case CONTROL_NAME_DEFAULT_OCTAVE:
                context.defaultOctave = value;
                break;
            case CONTROL_NAME_DEFAULT_DURATION:
                context.defaultDuration = durationIntToDuration(value);
                break;
            case CONTROL_NAME_BEATS_PER_MINUTE:
                context.beatsPerMinute = value;
                break;
            default:
                throw new ParseException("Unrecognized control name: " + controlName, 0);
        }
    }

    /**
     * &lt;tone-commands&gt; := &lt;tone-command&gt; ["," &lt;tone-commands&gt;]
     */
    private void parseToneSection(ParseContext context, String toneSectionStr)
        throws ParseException
    {
        String[] toneCommandStrs = toneSectionStr.split(",");

        for (String toneCommandStr : toneCommandStrs) {
            processToneCommand(context, toneCommandStr);
        }
    }

    /**
     * &lt;tone-command&gt; :=&lt;note&gt; | &lt;control-pair&gt;
     */
    private void processToneCommand(ParseContext context, String toneCommandStr)
        throws ParseException
    {
        if (toneCommandStr.contains("=")) {
            processControlPair(context, toneCommandStr);
        } else {
            processNote(context, toneCommandStr);
        }
    }

    /**
     * &lt;note&gt; := [&lt;duration&gt;] &lt;note-name&gt; [&lt;scale&gt;] [&lt;special-duration&gt;]
     * <p>
     * &lt;duration&gt; := "1" | "2" | "4" | "8" | "16" | "32" <br>
     * ; duration is divider of full note duration, eg. 4 represents a quarter note
     * <p>
     * &lt;note-name&gt; := "P" | "C" | "C#" | "D" | "D#" | "E" | "F" | "F#" | "G" | "G#" | "A" | "A#" | "B"
     * <p>
     * &lt;octave&gt; := "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" <br>
     * ; Note that octave 4: A=440Hz, 5: A=880Hz, 6: A=1.76 kHz, 7: A=3.52 kHz <br>
     * ; The lowest note on the Nokia 61xx is A4, the highest is B7> <br>
     * ; The original spec allowed 4-7, but this parser allows octaves 0 through 8, inclusive.
     * <p>
     * &lt;special-duration&gt; := "." ; Dotted note <br>
     * ; Note: The original specification stated this must appear at the end but this parser can handle it anywhere.
     */
    private void processNote(ParseContext context, String toneCommandStr)
        throws ParseException
    {
        // First, parse special duration, since it may appear anywhere
        boolean specialDuration = false;
        int dotIndex = toneCommandStr.indexOf('.');
        if (dotIndex != -1) {
            specialDuration = true;
            // Remove the dot
            toneCommandStr = toneCommandStr.substring(0, dotIndex) + toneCommandStr.substring(dotIndex + 1);
        }

        Matcher matcher = PATTERN_NOTE.matcher(toneCommandStr);
        assertSyntax(matcher.matches(), "Note pattern does not match [duration]note[special-duration][octave]");
        String durationStr = matcher.group(1);
        String noteStr = matcher.group(2);
        String octaveStr = matcher.group(3);

        Duration duration;
        if (durationStr == null) {
            duration = context.defaultDuration;
        } else {
            try {
                duration = durationIntToDuration(Integer.parseInt(durationStr));
            } catch (NumberFormatException e) {
                throw new ParseException("Invalid duration: " + durationStr, 0);
            }
        }
        if (specialDuration) {
            duration = duration.asDotted();
        }

        int octave;
        if (octaveStr == null) {
            octave = context.defaultOctave;
        } else {
            try {
                octave = Integer.parseInt(octaveStr);
            } catch (NumberFormatException e) {
                throw new ParseException("Invalid octave: " + octaveStr, 0);
            }
        }

        Note note;
        if (noteStr.equals("p")) {
            note = null;
        } else {
            String noteName = noteStr + octave;
            note = Note.findNoteByName(noteName.toUpperCase());
            if (note == null) {
                throw new ParseException("Note not found: " + noteStr, 0);
            }
        }

        Tone tone = new Tone(note, duration);

        context.toneList.add(tone);
    }

    /**
     * If condition is false, throw a ParseException with the given message and error offset, else pass through.
     */
    private void assertSyntax(boolean condition, String message)
        throws ParseException
    {
        if (!condition) {
            throw new ParseException(message, 0);
        }
    }

    /**
     * State for the RTTTL parser.
     */
    private static class ParseContext
    {
        Duration defaultDuration = ToneSequence.DEFAULT_DURATION;
        int defaultOctave = ToneSequence.DEFAULT_OCTAVE;
        int beatsPerMinute = ToneSequence.DEFAULT_BEATS_PER_MINUTE;
        public List<Tone> toneList = new ArrayList<Tone>();
    }

    /**
     * Convert an int to a duration (4 = quarter note).
     */
    private Duration durationIntToDuration(int value)
        throws ParseException
    {
        switch (value) {
            case 1:
                return Duration.WHOLE;
            case 2:
                return Duration.HALF;
            case 4:
                return Duration.QUARTER;
            case 8:
                return Duration.EIGHTH;
            case 16:
                return Duration.SIXTEENTH;
            case 32:
                return Duration.THIRTY_SECOND;
            default:
                throw new ParseException("Duration must be one of 1, 2, 4, 8, 16, or 32.", 0);
        }
    }

}
