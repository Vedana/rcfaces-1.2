/*
 * $Id$
 */

/**
 * f_dateFormat class
 *
 * @class public f_dateFormat extends Object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

function f_dateFormat() {
}

/**
 * @field hidden static final number
 */
f_dateFormat._DEFAULT_TWO_DIGIT_YEAR_START=1960;

/**
 * @method public static final 
 * @param Date date Date to format.
 * @param string format Expression of format.
 * @param Date twoDigitYearStart 
 * @param optional f_locale locale Locale used by format.
 * @return string Formatted date.
 */
f_dateFormat.FormatDate=function(date, format, twoDigitYearStart, locale) {
	if (!locale) {
		locale=f_locale.Get();
	}
	if (!format) {
		format=locale.f_getDateFormat();
		
		if (!format) {
			return String(date);
		}
	}
	
	var len=format.length;
	var lastChar;
	var nb=0;
	var ret="";
	
	for(var i=0;i<=len;i++) {
		var c=0;
		if (i<len) {
			c=format.charAt(i);
				
			if (c==lastChar) {
				nb++;
				continue;
			}
			if (!lastChar) {
				lastChar=c;
				nb=1;
				
				if (c!="\'") {
					continue;
				}
			}
		}
		
		// C'est le cas si la fin etait quotée !
		if (!nb) {
			break;
		}
		
		var f=f_locale.LONG;   // defaut: EEEE
		if (nb<3) { // EE
			f=f_locale.SHORT;
			
		} else if (nb<4) { // EEE
			f=f_locale.MEDIUM;
		}
					
		switch(lastChar) {
		case "y":
			var y=date.getFullYear(); // yyyy
			if (nb<4) {
				y%=100; // yy
				
				if (nb>1 && y<10) {
					y="0"+y;
				}
			}

			ret+=y;
			break;

		case "M":
			var m=date.getMonth();
			if (nb<2) { // M
				ret+=(m+1);
				break;
			}
			if (nb<3) { // MM
				ret+=(m>8)?(m+1):"0"+(m+1);
				break;
			}
				
			ret+=locale.f_getMonthName(m, f);
			break;

		case "d":
			var d=date.getDate();
			if (nb<2) { // d
				ret+=d;
				break;
			}
			
			ret+=(d>9)?d:"0"+d;
			break;

		case "E":
			var e=date.getDay();
			
			ret+=locale.f_getDayName(e, f);
			break;

		case "z": 
			ret+=date.getTimezoneOffset();
			break;

		case "\'":
			break;
			
		default:
			for(;nb;nb--) {
				ret+=lastChar;
			}
		}
		
		if (!c) {
			break;
		}
		
		if (c!="\'") {
			lastChar=c;
			nb=1;
			continue;
		}
		
		for(i++;i<len;i++) {
			var c2=format.charAt(i);
			
			if (c2!="\'") {
				ret+=c2;
				continue;
			}
			
			// double quote ???
			if (i+1<len && format.charAt(i+1)==c2) {
				ret+=c2;
				i++;
				continue;
			}
			break;
		}
		
		nb=1;
		lastChar=c;
	}
	
	return ret;
}

/**
 * @method public static final 
 * @param string text Text to parse.
 * @param string format Expression of parsing.
 * @param Date twoDigitYearStart 
 * @param optional f_locale locale Locale used by parsing.
 * @return Date Date parsed.
 */
f_dateFormat.ParseDate=function(text, format, twoDigitYearStart, locale) {
	if (!format) {
		if (!locale) {
			locale=f_locale.Get();
		}
		
		format=locale.f_getDateFormat();
		
		if (!format) {
			return new Date(text);
		}
	}	

	var len=format.length;
	var lastChar;
	var nb=0;
	var curPos=0;
	var year;
	var month;
	var date;
	
	for(var i=0;i<=len;i++) {
		var c=0;
		if (i<len) {
			c=format.charAt(i);
				
			if (c==lastChar) {
				nb++;
				continue;
			}
			if (!lastChar) {
				lastChar=c;
				nb=1;
				continue;
			}
		}
			
		switch(lastChar) {
		case "y":
		case "M":
		case "d":

			var nb=0;
			var cnt=0;
			for(;curPos<text.length;) {
				var cv=text.charAt(curPos);
				if (cv<'0' || cv>'9') {
					break;
				}
				
				nb=(nb*10)+(cv-'0');
				curPos++;
				cnt++;
			}
			
			if (cnt<1) {
				throw new Error("Invalid char at position '"+curPos+"' (expression='"+text+"') for token '"+lastChar+"' nb='"+nb+"'.");
			}
			
			switch(lastChar) {
			case "y":
				year=nb;
				break;
			case "M":
				month=nb;
				break;
			case "d":
				date=nb;
				break;
			}
			break;
		
		default:
			for(;nb;nb--) {
				if (curPos>=text.length) {
					throw new Error("Not enough character '"+lastChar+"' for expression '"+text+"'.");
				}
				
				var cv=text.charAt(curPos++);				
				if (cv!=lastChar) {
					throw new Error("Invalid char at position '"+curPos+"' (expression='"+text+"', character '"+cv+"') for token '"+lastChar+"' nb='"+nb+"'.");
				}
			}
			break;
		}

		lastChar=c;
		nb=1;
	}

	if (date===undefined || date<1) {
		date=1;
	}
	
	if (month===undefined) {
		month=0;

	} else if (month>0) {
		month--;
	}
	
	if (year===undefined) {
		throw new Error("Invalid parse date for expression '"+text+"' format='"+format+"' year="+year+" month="+month+" date="+date);
	}
	
	if (year<100) {
		year=f_dateFormat.ResolveYear(year, month, date, twoDigitYearStart, locale);
	}
	
	return new Date(year, month, date);
}
/**
 * @method public static final
 */
f_dateFormat.ResolveYear=function(year, month, date, twoDigitYearStart, locale) {
	if (year>=100) {
		return year;
	}
	
	if (!twoDigitYearStart) {
		if (!locale) {
			locale=f_locale.Get();
		}
		twoDigitYearStart=f_dateFormat.ParseStringDate(locale.f_getTwoDigitYearStart());

		if (!twoDigitYearStart) {
			twoDigitYearStart=new Date(f_dateFormat._DEFAULT_TWO_DIGIT_YEAR_START, 0, 1);
		}
	}
	
	var fy=twoDigitYearStart.getFullYear();
	year+=fy-(fy % 100);
	
	var dy=new Date(year, month, date);
	if (dy.getTime()<twoDigitYearStart.getTime()) {
		year+=100;
	}
	
	return year;
}

/**
 * @method hidden static final
 */
f_dateFormat.FormatStringDate=function(date, onlyDay) {
	var year=date.getFullYear();
	var month=date.getMonth();
	var day=date.getDate()-1;
	
	if (!day) {
		if (!month) {
			return "Y"+year.toString(32);
		}
		
		month+=year*12;
		
		return "M"+month.toString(32);
	}
	day+=(month+year*12)*31;
	day=day.toString(32);
	if (onlyDay) {
		return day;
	}
	return "D"+day;
}

/**
 * @method hidden static final
 */
f_dateFormat.ParseStringDate=function(value) {
	if (value.length<1) {
		return null;
	}
	
	var unit=value.charAt(0);
	if (unit>='A' && unit<='Z') {
		// Retire l'untité !
		value=value.substring(1);
	}
	value=parseInt(value, 32);
	
	if (unit=='Y') {
		return new Date(value, 0, 1);
	}
	if (unit=='M') {
		var m=value % 12;
		var y=Math.floor(value/12);
		return new Date(y, m, 1);
	}
/*	if (unit=='D') { */
			var d=(value % 31)+1
			var m=Math.floor(value/31);
			var y=Math.floor(m/12);
			return new Date(y, m % 12, d);
/*		}

	if (unit=='H') {
		var h=value;
		var d=Math.floor(h/24);
		var m=Math.floor(d/31);
		var y=Math.floor(m/12);
		return new Date(y, m % 12, (d % 31)+1, h % 24, 0, 0);
	}
	if (unit=='N') {
		var m=value;
		var h=Math.floor(m/60);
		var d=Math.floor(h/24);
		var m=Math.floor(d/31);
		var y=Math.floor(m/12);
		return new Date(y, m % 12, (d % 31)+1, h % 24, m % 60, 0);
	}
	
	var s=value;
	var m=Math.floor(s/60);
	var h=Math.floor(m/60);
	var d=Math.floor(h/24);
	var m=Math.floor(d/31);
	var y=Math.floor(m/12);
	
	return new Date(y, m % 12, (d % 31)+1, h % 24, m % 60, s % 60);
	*/
}


f_dateFormat.f_getName=function() {
	return "f_dateFormat";
}
