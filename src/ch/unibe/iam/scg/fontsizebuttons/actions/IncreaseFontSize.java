package ch.unibe.iam.scg.fontsizebuttons.actions;

public class IncreaseFontSize extends ChangeFontSize {

	@Override
	protected int changeFontSize(int h) {
		if (h >= 40) return h + 8;
		if (h >= 28) return h + 4;
		if (h >= 14) return h + 2;
		return h + 1;
	}
	
}