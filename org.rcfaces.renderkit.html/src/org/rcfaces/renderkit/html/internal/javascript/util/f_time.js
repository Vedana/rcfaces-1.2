/*
 * $Id$
 */

/**
 * f_time class
 *
 * @class public final f_time extends Object
 * @author Olivier Oeuillot (latest modification by $Author$) & Joel Merlin
 * @version $Revision$ $Date$
 */

var __statics = {
	/**
	 * @method hidden static
	 * @param f_time time
	 * @return String
	 */
	SerializeTime: function(time) {
		var millis=time.f_getMilliseconds();		
		if (millis) {
			return "S"+time.f_getTime().toString(32);
		}
		
		var hours=time.f_getHours();
		var minutes=time.f_getMinutes();
		var seconds=time.f_getSeconds();

		if (seconds) {
			seconds+=(hours*60+minutes)*60;
			return "s"+seconds.toString(32);
		}
		
		if (minutes) {
			minutes+=hours*60;
			return "m"+minutes.toString(32);		
		}
		
		return "H"+hours.toString(32);
	},

	/**
	 * @method hidden static
	 * @param String time
	 * @return f_time
	 */
	DeserializeTime: function(time) {
		if (!time.length) {
			return null;
		}
		
		var unit=time.charAt(0);
		var value=parseInt(time.substring(1), 32);

		switch(unit) {
		case 'H':		
			return new f_time(value, 0, 0);

		case 'm':
			var m=value;
			var h=Math.floor(value/60);
			return new f_time(h, m % 60 , 0);
		
		case 's':
			var s=value;
			var m=Math.floor(s/60);
			var h=Math.floor(m/60);
			return new Date(h, m % 60, s % 60);
	
		case 'S':
			return new f_time(value);
		}
		
		f_core.Error(f_time, "DeserializeTime: Invalid time format ! ("+time+")");
	}
}

/**
 * @method public
 */
var __members = {
 
	f_time: function(hours, minutes, seconds, millis) {
		if (arguments.length==1) {
			if (hours instanceof Date) {	
				var date=hours;
				
				this._hours=date.getHours();
				this._minutes=date.getMinutes();
				this._seconds=date.getSeconds();
				this._millis=date.getMilliseconds();
				return;
			}
	
			if (typeof(hours)=="number") {
				this._hours=Math.floor((hours/(60*60*1000)));
				this._minutes=Math.floor((hours/(60*1000)) % 60);
				this._seconds=Math.floor((hours/1000) % 60);
				this._millis=Math.floor(hours % 1000);
				return;
			}
		}
	
		f_core.Assert(typeof(hours)=="number", "Invalid hours parameters ("+hours+")");
		this._hours=hours;
	
		this._minutes=0;
		this._seconds=0;
		this._millis=0;
		
		if (minutes) {
			f_core.Assert(typeof(minutes)=="number" && minutes>=0 && minutes<=59, "Invalid minutes parameters ("+minutes+")");
			this._minutes=minutes;
		}
		if (seconds) {
			f_core.Assert(typeof(seconds)=="number" && seconds>=0 && seconds<=59, "Invalid seconds parameters ("+seconds+")");
			this._seconds=seconds;
		}
		if (millis) {
			f_core.Assert(typeof(millis)=="number" && millis>=0 && millis<=999, "Invalid millis parameters ("+millis+")");
			this._millis=millis;
		}
	},
	
	/**
	 * @method public
	 * @return number
	 */
	f_getHours: function() {
		return this._hours;
	},
	
	/**
	 * @method public
	 * @return number
	 */
	f_getMinutes: function() {
		return this._minutes;
	},
	
	/**
	 * @method public
	 * @return number
	 */
	f_getSeconds: function() {
		return this._seconds;
	},
	
	/**
	 * @method public
	 * @return number
	 */
	f_getMilliseconds: function() {
		return this._millis;
	},
	 
	/**
	 * @method public
	 * @return String
	 */
	toString: function() {
		return "[f_time "+this._hours+":"+this._minutes+":"+this._seconds+"."+this._millis+"]";
	},
	
	/**
	 * @method public
	 * @return Date
	 */
	f_toDate: function() {
		return new Date(1, 0, 2000, this._hour, this._minute, this._second, this._millis);
	},
	
	/**
	 * @method public
	 * @return number
	 */
	f_getTime: function() {
		return ((((this._hours*60)+ this._minutes)*60)+ this._seconds)*1000+ this._millis;
	}
}

new f_class("f_time", null, __statics, __members);
