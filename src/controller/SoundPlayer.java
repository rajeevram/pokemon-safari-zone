package controller;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundPlayer {

	// These are the paths for the background songs
	private static String WildBattle = "media/sounds/WildBattle.mp3";
	private static String TrainerBattle = "media/sounds/TrainerBattle.mp3";
	private static String SafariZone = "media/sounds/SafariZone.mp3";
	private static String ChooseTrainer = "media/sounds/ChooseTrainer.mp3";
	private static String BicycleRide = "media/sounds/BicycleRide.mp3";
	private static String TownMusic = "media/sounds/TownMusic.mp3";
	private static String CaveMusic = "media/sounds/CaveMusic.mp3";
	private static String BuildingMusic = "media/sounds/BuildingMusic.mp3";

	// These are the paths for the sound effects
	private static String Capture = "media/sounds/Capture.mp3";
	private static String HitBarrier = "media/sounds/HitBarrier.mp3";
	private static String BreakOut = "media/sounds/BreakOut.mp3";
	private static String ClickButton = "media/sounds/ClickButton.mp3";
	private static String MoveAttack = "media/sounds/MoveAttack.mp3";
	private static String PickupItem = "media/sounds/PickupItem.mp3";
	private static String RunAway = "media/sounds/RunAway.mp3";
	private static String ThrowBall = "media/sounds/ThrowBall.mp3";
	private static String WasCaught = "media/sounds/WasCaught.mp3";
	private static String EatingBait = "media/sounds/EatingBait.mp3";

	// This is the media player for songs on repeat
	private static MediaPlayer mediaPlayerOne;
	private static MediaPlayer mediaPlayerTwo;

	/**
	 * 
	 * @param name
	 * @return
	 */
	private static String getMusicString(String name) {

		switch (name) {
		case "SafariZone":
			return SafariZone;
		case "WildBattle":
			return WildBattle;
		case "TrainerBattle":
			return TrainerBattle;
		case "ChooseTrainer":
			return ChooseTrainer;
		case "BicycleRide":
			return BicycleRide;
		case "TownMusic":
			return TownMusic;
		case "CaveMusic":
			return CaveMusic;
		case "BuildingMusic":
			return BuildingMusic;
		default:
			return null;
		}

	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	private static String getEffectString(String name) {

		switch (name) {
		case "Capture":
			return Capture;
		case "HitBarrier":
			return HitBarrier;
		case "BreakOut":
			return BreakOut;
		case "ClickButton":
			return ClickButton;
		case "MoveAttack":
			return MoveAttack;
		case "PickupItem":
			return PickupItem;
		case "RunAway":
			return RunAway;
		case "ThrowBall":
			return ThrowBall;
		case "WasCaught":
			return WasCaught;
		case "EatingBait":
			return EatingBait;
		default:
			return null;
		}

	}

	/**
	 * Plays a specific sound effect just once
	 * 
	 * @param path
	 */
	public static void playEffectOnce(String path) {
		path = SoundPlayer.getEffectString(path);
		Media media = new Media(new File(path).toURI().toString());
		mediaPlayerTwo = new MediaPlayer(media);
		mediaPlayerTwo.setStartTime(Duration.millis(0));
		mediaPlayerTwo.play();
		mediaPlayerTwo.setOnEndOfMedia(new EndOfEffectHandler());
	}

	/**
	 * Plays the song for a specific view on repeat
	 * 
	 * @param path
	 */
	public static void playSongRepeat(String path, int startTime, int stopTime) {
		path = SoundPlayer.getMusicString(path);
		Media media = new Media(new File(path).toURI().toString());
		mediaPlayerOne = new MediaPlayer(media);
		mediaPlayerOne.setStartTime(Duration.millis(0));
		// mediaPlayerOne.setStartTime(Duration.millis(stopTime-2000));
		mediaPlayerOne.setStopTime(Duration.millis(stopTime));
		mediaPlayerOne.setVolume(0.6);
		mediaPlayerOne.play();
		mediaPlayerOne.setOnEndOfMedia(new EndOfSongHandler(startTime));
	}

	/**
	 * Checks whether the media player is currently playing or not
	 */
	public static boolean isPlayingEffect() {
		if (mediaPlayerTwo != null) {
			return mediaPlayerTwo.getStatus().equals(MediaPlayer.Status.PLAYING);
		}
		return false;
	}

	/**
	 * Temporarily pause the music while an effect plays
	 */
	public static void stopBackgroundMusic() {
		if (mediaPlayerOne != null) {
			mediaPlayerOne.pause();
		}
	}

	public static void resumeBackgroundMusic() {
		if (mediaPlayerOne != null) {
			mediaPlayerOne.play();
		}
	}

	/**
	 * Stops the current song or sound effect playing
	 */
	public static void stopSound() {
		if (mediaPlayerOne != null) {
			mediaPlayerOne.stop();
		}
		if (mediaPlayerTwo != null) {
			mediaPlayerTwo.stop();
		}
	}

	/**
	 * Private inner class to handle songs toe be played on repeat
	 */
	private static class EndOfSongHandler implements Runnable {

		int startTime;

		EndOfSongHandler(int startTime) {
			this.startTime = startTime;
		}

		@Override
		public void run() {
			mediaPlayerOne.setStartTime(Duration.millis(startTime));
			mediaPlayerOne.seek(Duration.ZERO);
		}

	}

	/**
	 * Private inner class to handle sound effects not on repeat
	 */
	private static class EndOfEffectHandler implements Runnable {

		@Override
		public void run() {
			mediaPlayerTwo.stop();
		}

	}

}
