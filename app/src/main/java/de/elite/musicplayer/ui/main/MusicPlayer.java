package de.elite.musicplayer.ui.main;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

import de.elite.musicplayer.Song;
import io.reactivex.subjects.PublishSubject;

public class MusicPlayer implements MediaPlayer.OnPreparedListener {

    private static MusicPlayer instance;

    private PlayerState playerState = PlayerState.PAUSE;
    private PublishSubject<PlayerState> playerStateSubject = PublishSubject.create();

    private Song currentSong;
    private PublishSubject<Song> currentSongSubject = PublishSubject.create();

    private MediaPlayer mediaPlayer = new MediaPlayer();

    private MusicPlayer() {
        playerStateSubject.onNext(playerState);
    }

    public static MusicPlayer getInstance() {
        if (MusicPlayer.instance == null) {
            MusicPlayer.instance = new MusicPlayer();
        }
        return MusicPlayer.instance;
    }

    public void playPause() {
        if (this.playerState == PlayerState.PAUSE) {
            mediaPlayer.start();
            this.playerState = PlayerState.PLAY;
        } else if (this.playerState == PlayerState.PLAY) {
            mediaPlayer.pause();
            this.playerState = PlayerState.PAUSE;
        }
        playerStateSubject.onNext(this.playerState);
    }

    public void playSong(Context context, Song song) {
        try {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(context, song.getUri());
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepareAsync(); // prepare async to not block main thread
            this.currentSong = song;
            this.currentSongSubject.onNext(song);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PublishSubject<PlayerState> getPlayerState() {
        return playerStateSubject;
    }

    public PublishSubject<Song> getCurrentSong() {
        return currentSongSubject;
    }

    public void seekToPosiontInSong(int millis) {
        mediaPlayer.seekTo(millis);
    }

    public int getCurrentPositionInSong() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        this.playerState = PlayerState.PLAY;
        playerStateSubject.onNext(this.playerState);
    }

    enum PlayerState {
        PLAY, PAUSE
    }
}
