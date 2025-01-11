package com.nextone.output;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.nextone.datandroid.R;
import com.nextone.model.MyContextManagement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoundPlayer {
    private static volatile SoundPlayer instance;
    private final ExecutorService threadPool;
    private final ConcurrentAudioPlayer concurrentPlayer;
    private final SequentialAudioPlayer sequentialPlayer;

    private SoundPlayer() {
        Context context = MyContextManagement.getInstance().getAplicationContext();
        this.threadPool = Executors.newCachedThreadPool();
        this.concurrentPlayer = new ConcurrentAudioPlayer(context);
        this.sequentialPlayer = new SequentialAudioPlayer(context);
    }

    public static SoundPlayer getInstance() {
        SoundPlayer ins = instance;
        if (ins == null) {
            synchronized (SoundPlayer.class) {
                ins = instance;
                if (ins == null) {
                    ins = instance = new SoundPlayer();
                }
            }
        }
        return ins;
    }

    public void sayResultTest(int score, boolean isPass) {
        if (isPass) {
            playSequential(R.raw.congratulations);
        } else {
            playSequential(R.raw.congratulations1);
        }
        playSequential(R.raw.the_score);
        sayNumber(score);
        playSequential(R.raw.diem);
        if (isPass) {
            playSequential(R.raw.success);
        } else {
            playSequential(R.raw.failure);
        }
    }

    public void sayNumber(int num) {
        ////453
        if (num > 0) {
            Stack<Integer> nElems = new Stack<>();
            while (num > 0) {
                nElems.add(num % 10);
                num /= 10;
            }
            int size = nElems.size();
            for (int i = 0; i < size; i++) {
                playSequential(getNumberSoundId(nElems.pop()));
            }
        }
    }

    private int getNumberSoundId(int num) {
        return switch (num) {
            case 0 -> R.raw.number_0;
            case 1 -> R.raw.number_1;
            case 2 -> R.raw.number_2;
            case 3 -> R.raw.number_3;
            case 4 -> R.raw.number_4;
            case 5 -> R.raw.number_5;
            case 6 -> R.raw.number_6;
            case 7 -> R.raw.number_7;
            case 8 -> R.raw.number_8;
            case 9 -> R.raw.number_9;
            default -> -1;
        };
    }

    private void play(int resId) {
        threadPool.execute(() -> {
            this.concurrentPlayer.play(resId);
        });
    }

    private synchronized void playSequential(int resId) {
            this.sequentialPlayer.addToQueue(resId);
    }

    public void contestName(int soundId, boolean contestation) {
        if (!contestation) {
            this.playSequential(R.raw.contest);
        }
        this.playSequential(soundId);
    }

    public void welcomeCarId(String numString) {
        if (numString == null || numString.isBlank()) {
            return;
        }
        int num = Integer.parseInt(numString.trim());
        playSequential(R.raw.carid);
        sayNumber(num);
    }

    public void inputCarId() {
        play(R.raw.input_car);
    }

    public void inputId() {
        play(R.raw.input_id);
    }

    public void welcomeId(String numString) {
        if (numString == null || numString.isBlank()) {
            return;
        }
        int num = Integer.parseInt(numString.trim());
        playSequential(R.raw.welcomeid);
        sayNumber(num);
    }

    public void startContest() {
        play(R.raw.contest_start);
    }

    public void endContest() {
        playSequential(R.raw.contest_finish);
    }

    public void begin() {
        play(R.raw.begin);
    }

    public void nextId() {
        playSequential(R.raw.next_id);
    }

    public void sayErrorCode(int soundId) {
        play(soundId);
    }

    public void successSound() {
        playSequential(R.raw.ding_dong);
    }

    public void alarm() {
        play(R.raw.warning);
    }

    public void sayWelcome() {
        play(R.raw.welcome);
    }

    public void changeSuccess() {
        play(R.raw.change_sucess);
    }
    public void changeFault() {
        play(R.raw.error);
    }

    private static class SequentialAudioPlayer {
        private final Context context;
        private final Queue<Integer> audioQueue;
        private boolean isPlaying;
        private Thread thread;

        private SequentialAudioPlayer(Context context) {
            this.context = context;
            this.audioQueue = new LinkedList<>();
            this.isPlaying = false;
        }


        public synchronized void addToQueue(int resId) {
            if (resId < 0) return;
            audioQueue.add(resId);
            if (thread == null) {
                thread = new Thread(this::playNext);
                thread.start();
            }
        }

        private void playNext() {
            MediaPlayer mediaPlayer = null;
            try {
                while (!audioQueue.isEmpty()) {
                    Integer resId = audioQueue.poll();
                    if (resId != null) {
                        this.isPlaying = true;
                        mediaPlayer = MediaPlayer.create(context, resId);
                        if (mediaPlayer != null) {
                            mediaPlayer.setOnCompletionListener(mp -> {
                                mp.release();
                                this.isPlaying = false;
                            });
                            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                                mp.release();
                                this.isPlaying = false;
                                return false;
                            });
                            mediaPlayer.start();
                            while (this.isPlaying) {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException ignored) {

                                }
                            }
                        }
                    }
                }
                this.thread = null;
            } catch (Exception e) {
                Log.e("SoundPlayer.SequentialAudioPlayer", "Error playing sound:", e);
            }
        }

        public void clearQueue() {
            audioQueue.clear();
        }
    }

    private static class ConcurrentAudioPlayer {
        private final Context context;
        private final List<MediaPlayer> activePlayers;

        private ConcurrentAudioPlayer(Context context) {
            this.context = context;
            this.activePlayers = new ArrayList<>();
        }

        public void play(int resId) {
            if (resId < 0) return;
            MediaPlayer mediaPlayer = null;
            try {
                mediaPlayer = MediaPlayer.create(context, resId);
                if (mediaPlayer != null) {
                    activePlayers.add(mediaPlayer);
                    mediaPlayer.setOnCompletionListener(mp -> {
                        mp.release();
                        activePlayers.remove(mp);
                    });
                    mediaPlayer.start();
                }
            } catch (Exception e) {
                Log.e("SoundPlayer.ConcurrentAudioPlayer", "Error playing sound:", e);
            } finally {
                if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    mediaPlayer.release();
                }
            }
        }

        public void stopAll() {
            for (MediaPlayer player : activePlayers) {
                if (player.isPlaying()) {
                    player.stop();
                }
                player.release();
            }
            activePlayers.clear();
        }
    }

}
