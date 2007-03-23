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

var __static = {
	/**
	 * @field private static final number
	 */
	_DEFAULT_TWO_DIGIT_YEAR_START: 1960,
	
	/**
	 * @method public static final 
	 * @param Date date Date to format.
	 * @param optional String format Expression of format.
	 * @param optional f_locale locale Locale used by format.
	 * @return String Formatted date.
	 */
	FormatDate: function(date, format, locale) {
		f_core.Assert(date instanceof Date, "f_dateFormat.FormatDate: Invalid date parameter '"+date+"'.");
		f_core.Assert(format===undefined || format===null || typeof(format)=="string", "f_dateFormat.FormatDate: Invalid format parameter '"+format+"'.");
		f_core.Assert(locale===undefined || (locale instanceof f_locale), "f_dateFormat.FormatDate: Invalid locale parameter '"+locale+"'.");
	
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
	},
	
	/**
	 * @method public static final 
	 * @param String text Text to parse.
	 * @param optional String format Expression of parsing.
	 * @param optional Date twoDigitYearStart 
	 * @param optional f_locale locale Locale used by parsing.
	 * @return Date Date parsed.
	 */
	ParseDate: function(text, format, twoDigitYearStart, locale) {
		f_core.Assert(typeof(text)=="string", "f_dateFormat.ParseDate: Invalid text parameter '"+text+"'.");
		f_core.Assert(format===undefined || format===null || typeof(format)=="string", "f_dateFormat.ParseDate: Invalid format parameter '"+format+"'.");
		f_core.Assert(twoDigitYearStart===undefined || (twoDigitYearStart instanceof Date), "f_dateFormat.ParseDate: Invalid twoDigitYearStart parameter '"+twoDigitYearStart+"'.");
		f_core.Assert(locale===undefined || (locale instanceof f_locale), "f_dateFormat.ParseDate: Invalid locale parameter '"+locale+"'.");
	
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
	},
	
	/**
	 * @method public static final
	 * @param number year
	 * @param number month
	 * @param number date
	 * @param optional Date twoDigitYearStart
	 * @param optional f_locale locale
	 * @return number
	 */
	ResolveYear: function(year, month, date, twoDigitYearStart, locale) {
		f_core.Assert(typeof(year)=="number", "f_dateFormat.ResolveYear: Invalid year parameter '"+year+"'.");
		f_core.Assert(typeof(month)=="number", "f_dateFormat.ResolveYear: Invalid month parameter '"+month+"'.");
		f_core.Assert(typeof(date)=="number", "f_dateFormat.ResolveYear: Invalid date parameter '"+date+"'.");
		f_core.Assert(twoDigitYearStart===undefined || (twoDigitYearStart instanceof Date), "f_dateFormat.ResolveYear: Invalid twoDigitYearStart parameter '"+twoDigitYearStart+"'.");
		f_core.Assert(locale===undefined || (locale instanceof f_locale), "f_dateFormat.ResolveYear: Invalid locale parameter '"+locale+"'.");
	
		if (year>=100) {
			return year;
		}
		
		if (!twoDigitYearStart) {
			if (!locale) {
				locale=f_locale.Get();
			}
			twoDigitYearStart=f_core.DeserializeDate(locale.f_getTwoDigitYearStart());
	
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
}


new f_class("f_dateFormat", null, __static);
