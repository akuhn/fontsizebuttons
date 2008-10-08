package ch.unibe.iam.scg.fontsizebuttons.actions;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

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
			if (ex instanceof RuntimeException) throw (RuntimeException) ex;
			throw new RuntimeException(ex);
		}
	}
	
	public void run() throws Exception {
        changeFont("org.eclipse.ui.workbench", "org.eclipse.jdt.ui.editors.textfont");
        // JFace text font must be last, since others might default to it 
	    changeFont("org.eclipse.ui.workbench", "org.eclipse.jface.textfont");
    }
	
    private void changeFont(String qualifier, String key) throws Exception {
        IPreferencesService r = Platform.getPreferencesService();
        //debugPreferencesService(r);
        String value = r.getString(qualifier,key,null,null);
        if (value == null) throw new AssertionError();
        FontData[] ary = PreferenceConverter.basicGetFontData(value);
        if (ary == null) throw new AssertionError();
        if (ary.length < 1) throw new AssertionError();
        FontData fontdata = ary[0];
        fontdata.setHeight(changeFontSize(fontdata.getHeight()));
        Preferences n = r.getRootNode().node("/instance/" + qualifier);
        n.put(key, fontdata.toString());
        n.flush();
    }	
	
	protected void debugPreferencesService(IPreferencesService r) throws Exception {
	    OutputStream baos = new ByteArrayOutputStream();
	    r.exportPreferences(r.getRootNode(), baos, null);
	    System.out.println(baos);
	}
	
	protected abstract int changeFontSize(int height);

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
	}

}
