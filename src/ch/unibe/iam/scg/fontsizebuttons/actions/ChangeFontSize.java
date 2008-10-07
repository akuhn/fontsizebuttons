package ch.unibe.iam.scg.fontsizebuttons.actions;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.osgi.service.prefs.Preferences;

public abstract class ChangeFontSize implements IWorkbenchWindowActionDelegate {
	
	
	public ChangeFontSize() {
	}

	public void run(IAction action) {
		try { run(); }
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void run() throws Exception {
		IPreferencesService r = Platform.getPreferencesService();
		Preferences n = r.getRootNode().node("/instance/org.eclipse.ui.workbench");
		String value = n.get("org.eclipse.jface.textfont", null);
		FontData fontdata = PreferenceConverter.basicGetFontData(value)[0];
		fontdata.setHeight(changeFontSize(fontdata.getHeight()));
		n.put("org.eclipse.jface.textfont", fontdata.toString());
		n.flush();
	}
	
	protected abstract int changeFontSize(int height);

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
	}

}
