/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IScriptRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.webapp.IRepository;
import org.rcfaces.core.internal.webapp.IRepository.IFile;

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

    void computeRequires(IHtmlWriter writer,
            IJavaScriptComponentRenderer renderer);

    IRepository.IFile[] popRequiredFiles();

    boolean isRequiresPending();

    IJavaScriptRenderContext createChild();

    void pushChild(IJavaScriptRenderContext javaScriptRenderContext,
            IHtmlWriter htmlWriter) throws WriterException;

    void popChild(IJavaScriptRenderContext javaScriptRenderContext,
            IHtmlWriter htmlWriter) throws WriterException;

    String allocateVarName();

    String allocateString(String text, boolean mustDeclare[]);

    String allocateComponentVarId(String componentId, boolean mustDeclare[]);

    void initializeJavaScriptDocument(IJavaScriptWriter writer)
            throws WriterException;

    void appendRequiredClass(String className, String requiredId);

    boolean canLazyTagUsesBrother();

    IJavaScriptWriter removeJavaScriptWriter(IHtmlWriter writer);

    IJavaScriptWriter getJavaScriptWriter(IHtmlWriter writer,
            IJavaScriptComponentRenderer javaScriptComponent)
            throws WriterException;

    void initializePendingComponents(IJavaScriptWriter writer)
            throws WriterException;

    void initializeJavaScriptComponent(IJavaScriptWriter writer)
            throws WriterException;

    void releaseComponentJavaScript(IJavaScriptWriter writer,
            boolean sendComplete, AbstractHtmlRenderer htmlComponentRenderer)
            throws WriterException;

    void declareLazyJavaScriptRenderer(IComponentWriter writer);

    boolean isJavaScriptRendererDeclaredLazy(IComponentWriter writer);

    boolean isCollectorMode();

    void appendRequiredFiles(IFile[] files);
}
