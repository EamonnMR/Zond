package ents;
/**
 * persistent options class
 * @author Roohr
 * @version 1.0
 */
public class OptionsEnt {

	//vars
	private int musVol, fxVol, voiceVol, totalVol;
	private boolean fullscrn_bool, sndOn_bool, musOn_bool;
	
	//const
	public OptionsEnt(){
		
	}
	//methods
	public boolean getFullscreen() {
		return fullscrn_bool;
	}

	public void setFullscreen(boolean fullscreen) {
		this.fullscrn_bool = fullscreen;
	}

	public int getMusevol() {
		return musVol;
	}

	public void setMusevol(int musevol) {
		this.musVol = musevol;
	}

	public int getFxvol() {
		return fxVol;
	}

	public void setFxvol(int fxvol) {
		this.fxVol = fxvol;
	}

	public void setTotvol(int totvol) {
		this.totalVol = totvol;
	}

	public int getTotvol() {
		return totalVol;
	}

	public void setVoicevol(int voicevol) {
		this.voiceVol = voicevol;
	}

	public int getVoicevol() {
		return voiceVol;
	}
	public boolean isSndOn_bool() {
		return sndOn_bool;
	}
	public void setSndOn_bool(boolean sndOn_bool) {
		this.sndOn_bool = sndOn_bool;
	}
	public boolean isMusOn_bool() {
		return musOn_bool;
	}
	public void setMusOn_bool(boolean musOn_bool) {
		this.musOn_bool = musOn_bool;
	}
}
