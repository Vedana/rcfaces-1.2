/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2004/08/06 14:03:56  jmerlin
 * Vedana Faces
 *
 * Revision 1.2  2004/08/06 09:35:14  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/04 16:28:11  oeuillot
 * Premier jet !
 *
 */
package org.rcfaces.core.component.capability;

/**
 * Possibilit� de l'�lement � modifier ces parametres d'affichage du texte.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFontCapability {

	/**
	 * Retourne si la fonte est en mode gras ou non.
	 * 
	 * @return <pre>
	 * Boolean.TRUE
	 * </pre>,
	 * 
	 * <pre>
	 * Boolean.FALSE
	 * </pre>, ou
	 * 
	 * <pre>
	 * null
	 * </pre>
	 * 
	 * si le status de la fonte ne peut etre connu.
	 */
	Boolean getFontBold();

	/**
	 * Retourne si la fonte est en mode italic ou non.
	 * 
	 * @return <pre>
	 * Boolean.TRUE
	 * </pre>,
	 * 
	 * <pre>
	 * Boolean.FALSE
	 * </pre>, ou
	 * 
	 * <pre>
	 * null
	 * </pre>
	 * 
	 * si le status de la fonte ne peut etre connu.
	 */
	Boolean getFontItalic();

	/**
	 * Retourne si la fonte est en mode soulign� ou non.
	 * 
	 * @return <pre>
	 * Boolean.TRUE
	 * </pre>,
	 * 
	 * <pre>
	 * Boolean.FALSE
	 * </pre>, ou
	 * 
	 * <pre>
	 * null
	 * </pre>
	 * 
	 * si le status de la fonte ne peut etre connu.
	 */
	Boolean getFontUnderline();

	/**
	 * Retourne le nom de la fonte.
	 * 
	 * @return Le nom de la fonte, ou
	 * 
	 * <pre>
	 * null
	 * </pre>
	 * 
	 * si le nom de la fonte ne peut etre connu.
	 */
	String getFontName();

	/**
	 * Retourne le nom de la fonte.
	 * 
	 * @return La taille de la fonte, ou
	 * 
	 * <pre>
	 * null
	 * </pre>
	 * 
	 * si la taille de la fonte ne peut etre connue.
	 */
	String getFontSize();

	/**
	 * Sp�cifie le status "gras" de la fonte.
	 * 
	 * @param bold
	 * 
	 * <pre>
	 * Boolean.TRUE
	 * </pre>,
	 * 
	 * <pre>
	 * Boolean.FALSE
	 * </pre>, ou
	 * 
	 * <pre>
	 * null
	 * </pre>
	 * 
	 * si le status de la fonte doit etre celui par d�faut.
	 */
	void setFontBold(Boolean bold);

	/**
	 * Sp�cifie le status "italic" de la fonte.
	 * 
	 * @param italic
	 * 
	 * <pre>
	 * Boolean.TRUE
	 * </pre>,
	 * 
	 * <pre>
	 * Boolean.FALSE
	 * </pre>, ou
	 * 
	 * <pre>
	 * null
	 * </pre>
	 * 
	 * si le status de la fonte doit etre celui par d�faut.
	 */
	void setFontItalic(Boolean italic);

	/**
	 * Sp�cifie le status "soulign�" de la fonte.
	 * 
	 * @param underline
	 * 
	 * <pre>
	 * Boolean.TRUE
	 * </pre>,
	 * 
	 * <pre>
	 * Boolean.FALSE
	 * </pre>, ou
	 * 
	 * <pre>
	 * null
	 * </pre>
	 * 
	 * si le status de la fonte doit etre celui par d�faut.
	 */
	void setFontUnderline(Boolean underline);

	/**
	 * Sp�cifie le nom de la fonte.
	 */
	void setFontName(String name);

	/**
	 * Sp�cifie la taille de la fonte.
	 */
	void setFontSize(String size);
}