/*
 * $Id$
 */

/**
 * Array class
 *
 * @class public Array
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

/**
 * Removes the first occurrence in this list of the specified element.
 *
 * @method hidden f_removeElement
 * @param Object element Object to be removed.
 * @return boolean <code>true</code> if success.
 */
Array.prototype.f_removeElement=function(element) {
	for(var i=0;i<this.length;i++) {
		if (this[i]!=element) {
			continue;
		}
		
		this.splice(i, 1);
		return true;
	}
	return false;
}
/**
 * Removes the first occurrence in this list of the specified elements.
 *
 * @method hidden f_removeElements
 * @return Number Number of removed element.
 */
Array.prototype.f_removeElements=function() {
	var cnt=0;
	for(var j=0;j<arguments.length && this.length;j++) {
		var element=arguments[j];
		
		for(var i=0;i<this.length;i++) {
			if (this[i]!=element) {
				continue;
			}
			
			this.splice(i, 1);
			cnt++;
			break;
		}
	}
	
	return cnt;
}
/**
 * Adds the specified element to the list if the list does not contain the element.
 *
 * @method hidden f_addElement
 * @param Object element element to be added.
 */
Array.prototype.f_addElement=function(element) {
	if (this.length && this.f_contains(element)) {
		return false;
	}

	this.push(element);
	return true;
}
/**
 * Adds the specified element to the list if the list does not contain the element.
 *
 * @method hidden f_addElements
 * @return Number Number of added elements.
 */
Array.prototype.f_addElements=function() {
	var cnt=0;
	for(var j=0;j<arguments.length;j++) {
		var element=arguments[j];
		
		if (!this.f_addElement(element)) {
			continue;
		}
		
		cnt++;
	}
	
	return cnt;		
}
/**
 * Returns <tt>true</tt> if this array contains the specified element.
 *
 * @method hidden f_contains
 * @param any element Element whose presence in this array is to be tested.
 * @return boolean <tt>true</tt> if this collection contains the specified element
 */
Array.prototype.f_contains=function(element) {
	for(var i=0;i<this.length;i++) {
		if (this[i]!=element) {
			continue;
		}
		
		return true;
	}
	
	return false;
}

/**
 * @method public static 
 * @return String 
 */
Array.f_getName=function() {
	return "Array";
}
