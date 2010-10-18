package org.rcfaces.core.internal.capability;

/**
 * Listener Manager strategy constant
 * @author jbmeslin@vedana.com
 *
 */
public interface IListenerStrategy {
	/**
	 * DEFAULT : append listener without control
	 */
	int DEFAULT = 0x01;
	
	/**
	 * ClEAN_ALL : remove all faces listener during the initialize phase of lyfe cycle. Used in facelet
	 */
	int CLEAN_ALL = 0x02;
	
	/**
	 * CLEAN_BYCLASS : do not add faces listener if already one exist for this class  
	 */
	int CLEAN_BYCLASS = 0x04;
}
