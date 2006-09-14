/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.37  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.36  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 * Revision 1.35  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 * Revision 1.34  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 * Revision 1.33  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.32  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.31  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.30  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.29  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.28  2005/12/27 16:08:16  oeuillot
 * Gestion imageButtonWriter
 * Ajout de fa_images
 * Preparation de f_imageCombo
 *
 * Revision 1.27  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.26  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.25  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.24  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.23  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.22  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.21  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.20  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.19  2004/09/13 08:34:26  oeuillot
 * *** empty log message ***
 *
 * Revision 1.18  2004/09/08 09:26:08  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public final class JavaScriptClasses {
    private static final String REVISION = "$Revision$";

    public static final String ACCELERATOR = "f_accelerator";

    public static final String BOX = "f_box";

    public static final String BUTTON = "f_button";

    public static final String CALENDAR = "f_calendar";

    public static final String CALENDAR_OBJECT = "f_calendarObject";

    public static final String CARD = "f_card";

    public static final String CARD_BOX = "f_cardBox";

    public static final String CHECK_BUTTON = "f_checkButton";

    public static final String CHECK_BUTTON_3_STATES = "f_checkButton3states";

    public static final String COMBO = "f_combo";

    public static final String COMBO_EX = "f_comboEx";

    public static final String CUSTOM_BUTTON = "f_customButton";

    public static final String DATA_GRID = "f_dataGrid";

    public static final String DATA_LIST = "f_dataList";

    public static final String DATA_PAGER = "f_dataPager";

    public static final String DATE_CHOOSER = "f_dateChooser";

    public static final String DATE_ENTRY = "f_dateEntry";

    public static final String FIELD_SET = "f_fieldSet";

    public static final String FOCUS_MANAGER = "f_focusManager";

    public static final String HELP_BUTTON = "f_helpButton";

    public static final String HELP_MESSAGE_ZONE = "f_helpMessageZone";

    public static final String HIDDEN_VALUE = "f_hiddenValue";

    public static final String HYPER_LINK = "f_hyperLink";

    public static final String IMAGE = "f_image";

    public static final String IMAGE_BUTTON = "f_imageButton";

    public static final String IMAGE_CHECK_BUTTON = "f_imageCheckButton";

    public static final String IMAGE_COMBO = "f_imageCombo";

    public static final String IMAGE_RADIO_BUTTON = "f_imageRadioButton";

    public static final String IMAGE_RESET_BUTTON = "f_imageResetButton";

    public static final String IMAGE_SUBMIT_BUTTON = "f_imageSubmitButton";

    public static final String INPUT = "f_input";

    public static final String LINE_BREAK = "f_lineBreak";

    public static final String LIST = "f_list";

    public static final String MENU = "f_menu";

    public static final String MENU_BAR = "f_menuBar";

    public static final String MENU_ITEM = "f_menuItem";

    public static final String MESSAGE = "f_message";

    public static final String MESSAGES = "f_messages";

    public static final String EXPAND_BAR = "f_expandBar";

    public static final String PASSWORD_ENTRY = "f_passwordEntry";

    public static final String PROGRESS_BAR = "f_progressBar";

    public static final String PROGRESS_INDICATOR = "f_progressIndicator";

    public static final String RADIO_BUTTON = "f_radioButton";

    public static final String RESET_BUTTON = "f_resetButton";

    public static final String RULER = "f_ruler";

    public static final String SERVICE = "f_service";

    public static final String SPINNER = "f_spinner";

    public static final String STYLED_TEXT = "f_styledText";

    public static final String SUGGEST_TEXT_ENTRY = "f_suggestTextEntry";

    public static final String SUBMIT_BUTTON = "f_submitButton";

    public static final String TAB = "f_tab";

    public static final String TABBED_PANE = "f_tabbedPane";

    public static final String TEXT = "f_text";

    public static final String TEXT_ENTRY = "f_textEntry";

    public static final String TEXT_AREA = "f_textArea";

    public static final String TOOL_BAR = "f_toolBar";

    public static final String TREE = "f_tree";

    public static final String EVENT_CHECK = "check";

    public static final String EVENT_CHECK_CST = "f_event.CHECK";

    public static final String EVENT_DBLCLICK = "dblclick";

    public static final String EVENT_DBLCLICK_CST = "f_event.DBLCLICK";

    public static final String EVENT_BLUR = "blur";

    public static final String EVENT_BLUR_CST = "f_event.BLUR";

    public static final String EVENT_VALUE_CHANGE = "change";

    public static final String EVENT_VALUE_CHANGE_CST = "f_event.CHANGE";

    public static final String EVENT_FOCUS = "focus";

    public static final String EVENT_FOCUS_CST = "f_event.FOCUS";

    public static final String EVENT_KEYDOWN = "keydown";

    public static final String EVENT_KEYDOWN_CST = "f_event.KEYDOWN";

    public static final String EVENT_KEYPRESS = "keypress";

    public static final String EVENT_KEYPRESS_CST = "f_event.KEYPRESS";

    public static final String EVENT_KEYUP = "keyup";

    public static final String EVENT_KEYUP_CST = "f_event.KEYUP";

    public static final String EVENT_MOUSEOUT = "mouseout";

    public static final String EVENT_MOUSEOUT_CST = "f_event.MOUSEOUT";

    public static final String EVENT_MOUSEOVER = "mouseover";

    public static final String EVENT_MOUSEOVER_CST = "f_event.MOUSEOVER";

    public static final String EVENT_RESET = "reset";

    public static final String EVENT_RESET_CST = "f_event.RESET";

    public static final String EVENT_SELECTION = "selection";

    public static final String EVENT_SELECTION_CST = "f_event.SELECTION";

    public static final String EVENT_SUGGESTION = "suggestion";

    public static final String EVENT_SUGGESTION_CST = "f_event.SUGGESTION";

    public static final String EVENT_PROPERTY_CHANGE = "propertyChange";

    public static final String EVENT_PROPERTY_CHANGE_CST = "f_event.PROPERTY_CHANGE";

    public static final String EVENT_CLOSE = "close";

    public static final String EVENT_CLOSE_CST = "f_event.CLOSE";

    public static final String EVENT_MENU = "menu";

    public static final String EVENT_MENU_CST = "f_event.MENU";

    public static final String EVENT_USER = "user";

    public static final String EVENT_USER_CST = "f_event.USER";

    public static final String EVENT_INIT = "init";

    public static final String EVENT_INIT_CST = "f_event.INIT";

    public static final String EVENT_LOAD = "load";

    public static final String EVENT_LOAD_CST = "f_event.LOAD";

    public static final String EVENT_BLUR_ATTRIBUTE = "blur";

    public static final String EVENT_CHECK_ATTRIBUTE = "check";

    public static final String EVENT_CLOSE_ATTRIBUTE = "close";

    public static final String EVENT_DBLCLICK_ATTRIBUTE = "dblClick";

    public static final String EVENT_FOCUS_ATTRIBUTE = "focus";

    public static final String EVENT_INIT_ATTRIBUTE = "init";

    public static final String EVENT_KEYDOWN_ATTRIBUTE = "keyDown";

    public static final String EVENT_KEYPRESS_ATTRIBUTE = "keyPress";

    public static final String EVENT_KEYUP_ATTRIBUTE = "keyUp";

    public static final String EVENT_MOUSEOUT_ATTRIBUTE = "mouseOut";

    public static final String EVENT_MOUSEOVER_ATTRIBUTE = "mouseOver";

    public static final String EVENT_SUGGESTION_ATTRIBUTE = "suggestion";

    public static final String EVENT_PROPERTY_CHANGE_ATTRIBUTE = "propertyChange";

    public static final String EVENT_RESET_ATTRIBUTE = "reset";

    public static final String EVENT_SELECTION_ATTRIBUTE = "selection";

    public static final String EVENT_MENU_ATTRIBUTE = "menu";

    public static final String EVENT_USER_ATTRIBUTE = "user";

    public static final String EVENT_VALUE_CHANGE_ATTRIBUTE = "change";

    public static final String EVENT_LOAD_ATTRIBUTE = "load";
}