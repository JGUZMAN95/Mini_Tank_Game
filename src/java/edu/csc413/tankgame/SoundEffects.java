package edu.csc413.tankgame;

import java.io.File;
import javax.sound.sampled.*;

public class SoundEffects {

        /**
         * method to play music continuously
         * @param fileName is the file name
         */
        public static void play_continuously(String fileName) {



            File sound = new File(fileName);
            try {
                Clip c = AudioSystem.getClip();
                c.open(AudioSystem.getAudioInputStream(sound));
                c.loop(javax.sound.midi.Sequencer.LOOP_CONTINUOUSLY);
                c.start();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        /**
         * method to play sound just once
         * @param fileName is the file name
         */
        public static void play_once(String fileName) {


            File sound = new File(fileName);
            try {
                Clip c = AudioSystem.getClip();
                c.open(AudioSystem.getAudioInputStream(sound));
                c.start();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }
