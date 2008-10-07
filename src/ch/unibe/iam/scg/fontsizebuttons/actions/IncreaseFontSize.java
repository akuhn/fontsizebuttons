package ch.unibe.iam.scg.fontsizebuttons.actions;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.ITheme;
import org.eclipse.jface.dialogs.MessageDialog;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class IncreaseFontSize implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public IncreaseFontSize() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		try { run(); }
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void run() throws Exception {
		IPreferenceNode f = findFontPreferenceNode(PlatformUI.getWorkbench().getPreferenceManager().getRootSubNodes());
		System.out.println(f);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		IPreferencesService r = Platform.getPreferencesService();
		Preferences n;
		System.out.println(n = r.getRootNode().node("/instance/org.eclipse.ui.workbench"));
		r.exportPreferences(r.getRootNode(), baos, null);
		System.out.println(baos.toString());
		String value = n.get("org.eclipse.jface.textfont", "KJFJKHFJKHFK");
		System.out.println(value);
		FontData fontdata = PreferenceConverter.basicGetFontData(value)[0];
		fontdata.setHeight(fontdata.getHeight() + 4);
		n.put("org.eclipse.jface.textfont", fontdata.toString());
		n.flush();
	}

	private IPreferenceNode findFontPreferenceNode(IPreferenceNode[] rootSubNodes) {
		final String ID = "org.eclipse.ui.preferencePages.ColorsAndFonts";
		for (IPreferenceNode each : rootSubNodes) {
			if (ID.equals(each.getId())) return each;
			IPreferenceNode maybe = findFontPreferenceNode(each.getSubNodes());
			if (maybe != null) return maybe;
		}
		return null;
	}

	private void printEclipsePreferences(IEclipsePreferences r, String tab) throws BackingStoreException {
		System.out.println(tab + r.absolutePath() + " = " + r.getClass());
		for (String s : r.childrenNames()) {
			System.out.println(s);
		}
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}