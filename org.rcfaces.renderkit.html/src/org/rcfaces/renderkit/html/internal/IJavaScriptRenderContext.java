/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Collection;

import org.rcfaces.core.internal.renderkit.IScriptRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.webapp.IRepository;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IJavaScriptRenderContext extends IScriptRenderContext {

    boolean isInitialized();

    void forceJavaScriptStub();

    boolean isJavaScriptStubForced();

    void clearJavaScriptStubForced();

    void computeRequires(IHtmlWriter writer, AbstractJavaScriptRenderer renderer);

    String[] popUnitializedComponentsClientId();

    IRepository.IFile[] popRequiredFiles();

    boolean isRequiresPending();

    IJavaScriptRenderContext pushInteractive();

    String allocateVarName();

    boolean isUnitializedComponentsPending();

    void pushUnitializedComponent(String componentId);

    String allocateString(String text, boolean mustDeclare[]);

    String allocateComponentVarId(String componentId, boolean mustDeclare[]);

    void restoreState(Object state);

    Object saveState();

    void initializeJavaScriptDocument(IJavaScriptWriter writer)
            throws WriterException;

    void appendRequiredClasses(Collection classNames, String className,
            String requiredId);

    boolean canLazyTagUsesBrother();
}
