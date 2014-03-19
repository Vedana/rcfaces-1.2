/*
 * $Id$
 */
package org.rcfaces.core.internal.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.FacesException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MessageDigestSelector {

    private static final Log LOG = LogFactory
            .getLog(MessageDigestSelector.class);

    private static final Set<String> notSupported = new HashSet<String>(32);

    private static final Set<String> supported = new HashSet<String>(32);

    public static MessageDigest getInstance(String[] algorithmNames) {
        String algorithmName = null;

        synchronized (notSupported) {
            for (int i = 0; i < algorithmNames.length; i++) {
                algorithmName = algorithmNames[i];

                if (algorithmName == null || algorithmName.length() == 0) {
                    continue;
                }

                algorithmName = algorithmName.trim();

                if (notSupported.contains(algorithmName)) {
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("Algorithm '" + algorithmName
                                + "' is marked disabled.");

                    }
                    continue;
                }

                if (supported.contains(algorithmName)) {
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("Algorithm '" + algorithmName
                                + "' is marked enabled.");

                    }
                    break;
                }

                MessageDigest messageDigest;
                try {
                    messageDigest = MessageDigest.getInstance(algorithmName);

                } catch (NoSuchAlgorithmException e) {
                    LOG.debug("Message digest '" + algorithmName
                            + "' throws exception", e);

                    messageDigest = null;
                }

                if (messageDigest != null) {
                    supported.add(algorithmName);

                    LOG.debug("Mark algorithm '" + algorithmName + "' enabled.");

                    return messageDigest;
                }

                notSupported.add(algorithmName);

                LOG.debug("Mark algorithm '" + algorithmName + "' disabled.");
            }
        }

        if (algorithmName != null) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Try algorithm '" + algorithmName + "' ...");
            }

            try {
                MessageDigest messageDigest = MessageDigest
                        .getInstance(algorithmName);

                if (messageDigest != null) {
                    return messageDigest;
                }

            } catch (NoSuchAlgorithmException e) {
                LOG.error("Message digest '" + algorithmName
                        + "' throws exception", e);
            }
        }

        throw new FacesException("Can not find valid algorithm in '"
                + Arrays.asList(algorithmNames) + "'");
    }
}
