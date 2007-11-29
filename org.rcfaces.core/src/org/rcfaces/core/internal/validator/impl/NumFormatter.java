/*
 * $Id$
 */
package org.rcfaces.core.internal.validator.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.validator.IClientValidatorContext;
import org.rcfaces.core.validator.IFormatterTask;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class NumFormatter extends AbstractClientValidatorTask implements
        IFormatterTask {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(NumFormatter.class);

    private static final Pattern LEADING_SPACES_PATTERN = Pattern
            .compile("^(\\s)");

    public String applyFormatter(IClientValidatorContext context, String value) {
        int decimal = getIntParameter(context, "num.decimal", -1);
        String dec = getParameter(context, "num.decSign");
        String neg = getParameter(context, "num.negSign", "-");
        String sep = getParameter(context, "num.sepSign");

        if (sep != null && sep.length() > 0) {
            Pattern pattern = getPattern("[" + buildEscaped(sep) + "]");

            value = pattern.matcher(value).replaceAll("");
        }

        Pattern pattern = getPattern("^(" + buildEscaped(neg) + "?)(\\d*)(["
                + buildEscaped(dec) + "]?)(\\d*)$");

        // Check expression
        Matcher matcher = pattern.matcher(value);

        // No match
        if (matcher.matches() == false) {
            return null;
        }

        String ip = null;
        String d = null;
        String dp = null;

        int groupCount = matcher.groupCount();
        String n = matcher.group(1);
        if (groupCount > 1) {
            ip = matcher.group(2);
            if (groupCount > 2) {
                d = dec.substring(0, 1);
                if (groupCount > 3) {
                    dp = matcher.group(4);
                }
            }
        }

        for (; ip.length() > 1 && ip.charAt(0) == '0'; ip = ip.substring(1)) {
            // Nothing
        }

        if (decimal == 0) { // Attention au false ou -1
            d = "";
            dp = "";

        } else if (decimal > 0) {
            d = dec.substring(0, 1);

            if (dp.length() > decimal) {
                dp = dp.substring(0, decimal);

            } else {
                for (; dp.length() < decimal;) {
                    dp += "0";
                }
            }
        }

        // Check if no need
        if (sep == null || sep.length() < 1 || (ip.length() < 4)) {
            return n + ip + d + dp;
        }

        // Traitement des milliers ...

        // Otherwise format integer part

        if (ip.length() > 3) {
            StringAppender sa = new StringAppender(ip);

            char c = sep.charAt(0);

            for (int l = ip.length() - 3; l > 0; l -= 3) {
                sa.insert(l, c);
            }
            
            ip=sa.toString();
        }

        // Rebuild string
        value = n + ip + d + dp;

        return value;
    }
}
