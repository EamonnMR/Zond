package ents;
/**
 * persistent options class
 * @author Roohr
 * @version 1.0
 */
public class OptionsEnt {

	//vars
	private float musVol, fxVol, voiceVol, totalVol;
	private boolean fullscrn_bool, partcle_bool;
	
	
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

	public float getMusevol() {
		return musVol;
	}

	public void setMusevol(float musevol) {
		this.musVol = musevol;
	}

	public float getFxvol() {
		return fxVol;
	}

	public void setFxvol(float fxvol) {
		this.fxVol = fxvol;
	}

	public void setTotvol(float totvol) {
		this.totalVol = totvol;
	}

	public float getTotvol() {
		return totalVol;
	}

	public void setVoicevol(float voicevol) {
		this.voiceVol = voicevol;
	}

	public float getVoicevol() {
		return voiceVol;
	}
	public void setPartcle_bool(boolean partcle_bool) {
		this.partcle_bool = partcle_bool;
	}
	public boolean isPartcle_bool() {
		return partcle_bool;
	}
}
