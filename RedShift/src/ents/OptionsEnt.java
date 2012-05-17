package ents;
/**
 * persistent options class
 * @author Roohr
 * @version 1.0
 */
public class OptionsEnt {

	//vars
	private int smallWin, medWin, largeWin;
	private int musevol, fxvol, voicevol, totvol;
	private boolean fullscreen;
	
	//const
	public OptionsEnt(){}
	//methods
	public boolean getFullscreen() {
		return fullscreen;
	}

	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

	public int getSmallWin() {
		return smallWin;
	}

	public void setSmallWin(int smallWin) {
		this.smallWin = smallWin;
	}

	public int getMedWin() {
		return medWin;
	}

	public void setMedWin(int medWin) {
		this.medWin = medWin;
	}

	public int getLargeWin() {
		return largeWin;
	}

	public void setLargeWin(int largeWin) {
		this.largeWin = largeWin;
	}

	public int getMusevol() {
		return musevol;
	}

	public void setMusevol(int musevol) {
		this.musevol = musevol;
	}

	public int getFxvol() {
		return fxvol;
	}

	public void setFxvol(int fxvol) {
		this.fxvol = fxvol;
	}

	public void setTotvol(int totvol) {
		this.totvol = totvol;
	}

	public int getTotvol() {
		return totvol;
	}

	public void setVoicevol(int voicevol) {
		this.voicevol = voicevol;
	}

	public int getVoicevol() {
		return voicevol;
	}
}
