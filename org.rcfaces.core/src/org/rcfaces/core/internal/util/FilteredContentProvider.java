/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.1  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 */
package org.rcfaces.core.internal.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Locale;

import org.rcfaces.core.internal.codec.StringAppender;
import org.rcfaces.core.webapp.IRepository.IContent;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class FilteredContentProvider extends BasicContentProvider {
    private static final String REVISION = "$Revision$";

    private static final String CHARSET = "UTF-8";

    public IContent getContent(URL url, Locale locale) {
        return new FilteredContent(url, locale);
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    protected class FilteredContent extends BasicContent {
        private static final String REVISION = "$Revision$";

        public FilteredContent(URL url, Locale locale) {
            super(url, locale);
        }

        protected InputStream openInputStream(boolean toClose)
                throws IOException {
            InputStream ins = super.openInputStream(toClose);
            if (ins == null || toClose) {
                return ins;
            }

            StringWriter writer = new StringWriter(8000);
            try {
                InputStreamReader reader = new InputStreamReader(ins,
                        getCharset());

                char buffer[] = new char[4096];
                for (;;) {
                    int ret = reader.read(buffer);
                    if (ret < 1) {
                        break;
                    }

                    writer.write(buffer, 0, ret);
                }
            } finally {
                ins.close();
            }

            String file = writer.toString();

            file = updateBuffer(file, url, locale);

            return new ByteArrayInputStream(file.getBytes(getCharset()));
        }
    }

    protected String getCharset() {
        return CHARSET;
    }

    protected String updateBuffer(String buffer, URL url, Locale locale) {
        return buffer;
    }

    public static String replace(String source, String oldString,
            String newString) {
        int index = source.indexOf(oldString);
        if (index < 0) {
            return source;
        }

        int lf = oldString.length();
        char[] dest = source.toCharArray();

        StringAppender sb = new StringAppender(source.length());

        for (int last = 0;;) {
            sb.append(dest, last, index - last);
            sb.append(newString);
            last = index + lf;

            index = source.indexOf(oldString, last);
            if (index >= 0) {
                continue;
            }

            if (last < dest.length) {
                sb.append(dest, last, dest.length - last);
            }
            return sb.toString();
        }
    }
}
