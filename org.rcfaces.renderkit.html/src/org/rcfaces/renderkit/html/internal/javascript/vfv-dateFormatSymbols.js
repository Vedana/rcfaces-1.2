/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 */


/**
 * @class hidden F_DateFormatSymbols
 */
function F_DateFormatSymbols() {
	this._init(arguments);
}

F_DateFormatSymbols.prototype.f_eras = null;
F_DateFormatSymbols.prototype.f_months = null;
F_DateFormatSymbols.prototype.f_shortMonths = null;
F_DateFormatSymbols.prototype.f_weekdays = null;
F_DateFormatSymbols.prototype.f_shortWeekdays = null;
F_DateFormatSymbols.prototype.f_ampms = null;
F_DateFormatSymbols.prototype.f_zoneStrings = null;
F_DateFormatSymbols.prototype.f_dateSeparator = null;
F_DateFormatSymbols.prototype.f_timeSeparator = null;
// Unlocalized date time pattern characters
F_DateFormatSymbols.prototype.f_patternChars = "GyMdkHmsSEDFwWahKzZ";

/*-----------------------------------------------------------------------------
 * Instance private methods
 */
F_DateFormatSymbols.prototype._init = F_DateFormatSymbols__init;
F_DateFormatSymbols.prototype._initializeData = F_DateFormatSymbols__initializeData;

/*-----------------------------------------------------------------------------
 * Instance public methods
 */

/*-----------------------------------------------------------------------------
 * Class private methods
 */

/*-----------------------------------------------------------------------------
 * Class public methods
 */



/**
 * @class hidden F_DateFormatSymbols
 * @method F_DateFormatSymbols
 * @decl public
 *
 * DateFormatSymbols constructor
 *
 * @param
 * @return
 * @see
 */

function F_DateFormatSymbols__init(args) {
	this._initializeData();
}

function F_DateFormatSymbols__initializeData() {
	this.f_eras = new Array(
		"av. J.-C.", "ap. J.-C."
	);
	this.f_months = new Array(
		"Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
		"Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
	);
	this.f_shortMonths = new Array(
		"Jan", "Fév", "Mar", "Avr", "Mai", "Jun",
		"Jui", "Aoû", "Sep", "Oct", "Nov", "Déc"
	);
	this.f_weekdays = new Array(
		"Dimanche","Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"
	);
	this.f_shortWeekdays = new Array(
		"Dim","Lun", "Mar", "Mer", "Jeu", "Ven", "Sam"
	);
	this.f_ampms = new Array(
		"am", "pm"
	);
	this.f_zoneStrings = new Array(
		"GMT"
	);
	this.f_dateSeparator = "/";
	this.f_timeSeparator = ":";
}
