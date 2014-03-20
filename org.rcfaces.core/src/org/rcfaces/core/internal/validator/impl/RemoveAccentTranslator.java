/*
 * $Id$
 */
package org.rcfaces.core.internal.validator.impl;

import java.util.BitSet;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.validator.IClientValidatorContext;
import org.rcfaces.core.validator.ITranslatorTask;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class RemoveAccentTranslator extends AbstractClientValidatorTask
        implements ITranslatorTask {
    

    private static final Log LOG = LogFactory
            .getLog(RemoveAccentTranslator.class);

    private static final BitSet accentsHash = new BitSet(512);

    private static final AccentTranslator ACCENTS_MAPPERS[] = new AccentTranslator[] {
            new AccentTranslator("áãàâäåāăą", 'a'),
            new AccentTranslator("ćĉċčç", 'c'),
            new AccentTranslator("ďđ", 'd'),
            new AccentTranslator("éèêëēĕėęě", 'e'),
            new AccentTranslator("ĝğġģ", 'g'), new AccentTranslator("ĥħ", 'h'),
            new AccentTranslator("íìîïĩīĭįı", 'i'),
            new AccentTranslator("ñńņňŉŋ", 'n'),
            new AccentTranslator("óõòôöōŏő", 'o'),
            new AccentTranslator("úùûüµũūŭůűų", 'u'),
            new AccentTranslator("ýÿŷ", 'y'),
            new AccentTranslator("ÀÁÂÃÄÅĀĂĄ", 'A'),
            new AccentTranslator("ÇĆĈĊČ", 'C'),
            new AccentTranslator("ĎĐ", 'D'),
            new AccentTranslator("ÈÉÊËĒĔĖĘĚ", 'E'),
            new AccentTranslator("ĜĞĠĢ", 'G'), new AccentTranslator("ĤĦ", 'H'),
            new AccentTranslator("ÌÍÎÏĨĪĬĮİ", 'I'),
            new AccentTranslator("ÑŃŅŇŊ", 'N'),
            new AccentTranslator("ÓÔÕÖÒŌŎŐ", 'O'),
            new AccentTranslator("ÙÚÛÜŨŪŬŮŰŲ", 'U'),
            new AccentTranslator("ÝŶŸ", 'Y') };

    public char applyTranslator(IClientValidatorContext context, char keyChar) {

        if (accentsHash.get(computeHashCode(keyChar)) == false) {
            return keyChar;
        }

        String ch = String.valueOf(keyChar);

        for (int i = 0; i < ACCENTS_MAPPERS.length; i++) {
            AccentTranslator at = ACCENTS_MAPPERS[i];

            if (at.pattern.matcher(ch).find()) {
                return at.ch;
            }
        }

        return keyChar;
    }

    private static int computeHashCode(char keyChar) {
        return ((keyChar * 2777) >> 3) & 0x03ff;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author: jbmeslin $)
     * @version $Revision: 1.1.12.1 $ $Date: 2014/02/27 13:12:01 $
     */
    private static class AccentTranslator {
        

        private final Pattern pattern;

        private final char ch;

        public AccentTranslator(String regEx, char ch) {
            this.pattern = Pattern.compile("[" + regEx + "]");
            this.ch = ch;

            for (int i = 0; i < regEx.length(); i++) {
                accentsHash.set(computeHashCode(regEx.charAt(i)));
            }
        }
    }
}