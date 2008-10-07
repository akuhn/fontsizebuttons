package ch.unibe.iam.scg.fontsizebuttons.actions;

public class DecreaseFontSize extends ChangeFontSize {

	@Override
	protected int changeFontSize(int h) {
		if (h > 40) return h - 8;
		if (h > 28) return h - 4;
		if (h > 14) return h - 2;
		if (h > 6) return h - 1;
		return h;
	}
	
}