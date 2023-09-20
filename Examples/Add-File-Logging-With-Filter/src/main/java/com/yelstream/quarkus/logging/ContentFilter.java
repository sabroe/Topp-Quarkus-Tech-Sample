package com.yelstream.quarkus.logging;

import io.quarkus.logging.LoggingFilter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@LoggingFilter(name="content-filter")
public final class ContentFilter implements Filter {

    private final String part;

    private final Pattern includePattern;

    public ContentFilter(@ConfigProperty(name="content-filter.part", defaultValue=".*") String part) {
//System.out.println("*** HELLO! ***");
        this.part=part==null?null:part.trim();
        this.includePattern=this.part==null||this.part.isEmpty()?null:Pattern.compile(this.part);
    }

    @Override
    public boolean isLoggable(LogRecord logRecord) {
        boolean res=false;
        if (includePattern!=null) {
            String message=logRecord.getMessage();
//System.out.println(":> "+message);
            Matcher matcher=includePattern.matcher(message);
            if (matcher.matches()) {
                res=true;
            }
        }
//System.out.println("*** "+res+"! ***");
        return res;
    }
}
