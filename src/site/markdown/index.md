# About
RTTTL is a Java library containing a parser for the Ring Tone Text Transfer
Language (RTTTL) and various utilities for working with notes and frequencies.

RTTTL was developed as a way to represent simple single-voice musical tunes as
a string of characters that could be easily entered into a phone via T9 input.
Though cell phones have evolved to be able to play much more sophisticated
ring tones, today RTTTL is still occasionally used as a standard way to
represent a melody to be played by a simple sound system.

In addition to the parser, this package also contains a useful Note enum type
which includes MIDI semitones and frequencies.

To keep things lightweight, this library does not contain any code to actually
play tone sequences. However, this documentation does contain some example
code to do so (see last section, below).

# RTTTL Specification
The following specification comes from [this website](http://www.panuworld.net/nuukiaworld/download/nokix/rtttl.htm)
but other copies can be found floating around the Internet.

```
RTTTL Format Specifications

RTTTL (RingTone Text Transfer Language) is the primary format used to distribute 
ringtones for Nokia phones. An RTTTL file is a text file, containing the 
ringtone name, a control section and a section containing a comma separated 
sequence of ring tone commands. White space must be ignored by any reader 
application. 

Example: 
Simpsons:d=4,o=5,b=160:32p,c.6,e6,f#6,8a6,g.6,e6,c6,8a,8f#,8f#,8f#,2g

This file describes a ringtone whose name is 'Simpsons'. The control section 
sets the quarterNoteBeats per minute at 160, the default note length as 4, and the default 
scale as Octave 5. 
<RTX file> := <name> ":" [<control section>] ":" <tone-commands>

	<name> := <char> ; maximum name length 10 characters

	<control-section> := <control-pair> ["," <control-section>]

		<control-pair> := <control-name> ["="] <control-value>

		<control-name> := "o" | "d" | "b"
		; Valid in control section: o=default scale, d=default duration, b=default quarterNoteBeats per minute. 
		; if not specified, defaults are 4=duration, 6=scale, 63=quarterNoteBeats-per-minute
		; any unknown control-names must be ignored

		<tone-commands> := <tone-command> ["," <tone-commands>]

		<tone-command> :=<note> | <control-pair>

		<note> := [<duration>] <note> [<scale>] [<special-duration>] <delimiter>

			<duration> := "1" | "2" | "4" | "8" | "16" | "32" 
			; duration is divider of full note duration, eg. 4 represents a quarter note

			<note> := "P" | "C" | "C#" | "D" | "D#" | "E" | "F" | "F#" | "G" | "G#" | "A" | "A#" | "B" 

			<scale> :="4" | "5" | "6" | "7"
			; Note that octave 4: A=440Hz, 5: A=880Hz, 6: A=1.76 kHz, 7: A=3.52 kHz
			; The lowest note on the Nokia 61xx is A4, the highest is B7

			<special-duration> := "." ; Dotted note

; End of specification
```

The specification has some notable differences from RTTTL strings found in the
wild. In particular:

* The name sometimes exceeds the 10 character limit.
* RTTTL strings usually place the special duration '.' after the note instead
  of after the scale (a.k.a. octave).
* The octave may sometimes be outside the 4-7 range.
* RTTTL strings often contain spaces.
* RTTTL strings are often lowercase.

This library is a big more forgiving than the strict specification to be able
to accommodate more RTTTL strings without modification.

# Example RTTTL Ring Tones
Here are a couple of example RTTTL ring tones:

* `Auld L S:d=4,o=5,b=100:g,c.6,8c6,c6,e6,d.6,8c6,d6,8e6,8d6,c.6,8c6,e6,g6,2a.6,a6,g.6,8e6,e6,c6,d.6,8c6,d6,8e6,8d6,c.6,8a,a,g,2c.6`
* `Ba Ba:d=4,o=5,b=100:c,c,g,g,8a,8b,8c6,8a,g,p,f,f,e,e,d,d,c`
* `Beethoven:d=4,o=5,b=160:c,e,c,g,c,c6,8b,8a,8g,8a,8g,8f,8e,8f,8e,8d,c,e,g,e,c6,g`

You can find many example ring tones on the Internet. One popular source is
http://www.picaxe.com/RTTTL-Ringtones-for-Tune-Command/

# Library Usage
Check the unit tests for examples of how to use the API. In addition, here is
a simple Java application that plays an RTTTL string provided as a command line
argument:

```java
import com.octagonsoftware.rtttl.RTTTLParser;
import com.octagonsoftware.rtttl.Tone;
import com.octagonsoftware.rtttl.ToneSequence;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PlayRTTTL {
    private static final int SAMPLE_RATE = 8000;

    public static void main(String[] args) throws Exception {
        RTTTLParser parser = new RTTTLParser();
        ToneSequence toneSequence = parser.parse(args[0]);
        System.out.println("Playing song " + toneSequence.name);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (Tone tone : toneSequence.toneList) {
            float duration = tone.duration.secondsAtBeatsPerMinute(toneSequence.beatsPerMinute);
            if (tone.isRest()) {
                rest(out, duration);
            } else {
                beep(out, tone.note.hz, duration);
            }
        }
        out.close();

        Clip clip = AudioSystem.getClip();
        clip.open(new AudioFormat(SAMPLE_RATE, 32, 1, true, true),
            out.toByteArray(), 0, out.size());
        clip.start();
        while (clip.getFramePosition() < clip.getFrameLength()) {
            Thread.yield();
        }
        clip.close();
    }

    private static void rest(ByteArrayOutputStream out, float duration) {
        int sampleCount = (int) (SAMPLE_RATE * duration);
        byte[] buffer = new byte[sampleCount * 4];
        out.write(buffer, 0, sampleCount * 4);
    }

    private static void beep(ByteArrayOutputStream out, float freq, float duration) {
        int sampleCount = (int) (SAMPLE_RATE * duration);
        byte[] buffer = new byte[sampleCount * 4];
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer).order(ByteOrder.BIG_ENDIAN);
        for (int i = 0; i < sampleCount; i++) {
            byteBuffer.putInt((int) (Math.sin(freq * i * Math.PI * 2 / SAMPLE_RATE) * Integer.MAX_VALUE));
        }
        out.write(buffer, 0, sampleCount * 4);
    }
}
```

# Encoding RTTTL Strings
RTTTL Strings can also be encoded from a `ToneSequence`, as follows:

```java
import com.octagonsoftware.rtttl.RTTTLEncoder;
import com.octagonsoftware.rtttl.ToneSequence;

ToneSequence seq = ...;
RTTTLEncoder encoder = new RTTTLEncoder();
String rtttl = encoder.encode(seq);
```
