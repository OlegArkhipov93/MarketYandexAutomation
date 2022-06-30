package helpers;

import org.apache.commons.lang3.StringUtils;
import org.testng.Reporter;
import org.testng.log4testng.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static Logger Log = Logger.getLogger(Log.class);
    private static final int LOGLEVEL_ERROR = 1;
    private static final int LOGLEVEL_STEP = 10;
    private static final int LOGLEVEL_ACTION = 20;
    private static final int LOGLEVEL_META = 22;
    private static final int LOGLEVEL_WARNING = 25;
    private static final int LOGLEVEL_DEBUG = 30;
    private static final int LOGLEVEL_TRACE = 100;
    private static final int LOGLEVEL_ASSERT_FAIL = 11;
    private static int logLevel = 30;
    private static boolean printActionInterval = false;
    private static boolean hideInfoMessages = false;
    private static long previousTs = 0L;
    public static SimpleDateFormat dateFormat;

    public Log() {
    }

    public static void startTestCase(String sTestCaseName) {
        Log.info("************************              Started                *************************");
        Log.info(sTestCaseName);
        Reporter.log("************************              Started                *************************", true);
        Reporter.log(sTestCaseName, true);
    }

    public static void endTestCase(String sTestCaseName) {
        Reporter.log(sTestCaseName, true);
        Reporter.log("***********************             -E---N---D-             **********************", true);
        Log.info("Completed testing: " + sTestCaseName);
        Log.info("***********************             -E---N---D-             **********************");
    }

    public static void step(String message) {
        if (logLevel >= 10) {
            printInterval();
            Reporter.log(dateFormat.format(new Date()) + " [step]  " + message, true);
        }

    }

    public static void action(String message) {
        if (logLevel >= 20) {
            printInterval();
            Reporter.log(dateFormat.format(new Date()) + "\t [action]  " + message, true);
        }

    }

    public static void warn(String message) {
        if (logLevel >= 25) {
            printInterval();
            Reporter.log(dateFormat.format(new Date()) + " [warn]  " + message, true);
        }

    }

    public static void error(String message) {
        if (logLevel >= 1) {
            printInterval();
            Reporter.log(dateFormat.format(new Date()) + " [error]  " + message, true);
        }

    }

    public static void debug(String message) {
        if (logLevel >= 30) {
            printInterval();
            Reporter.log(dateFormat.format(new Date()) + "\t\t [debug]  " + message, true);
        }

    }

    public static void trace(String message) {
        if (logLevel >= 100) {
            printInterval();
            Reporter.log(dateFormat.format(new Date()) + "\t\t\t [trace]  " + message, true);
        }

    }

    public static void meta(String message) {
        if (logLevel >= 22) {
            printInterval();
            Reporter.log(dateFormat.format(new Date()) + "\t\t [meta]  " + message, true);
        }

    }

    public static void assertFail(String message) {
        if (logLevel >= 11) {
            printInterval();
            Reporter.log(dateFormat.format(new Date()) + " [assertFail]  " + message, true);
        }

    }

    /** @deprecated */
    @Deprecated
    public static void fatal(String message) {
        Reporter.log(dateFormat.format(new Date()) + " [fatal]  " + message, true);
    }

    public static void info(String message) {
        if (!hideInfoMessages) {
            Reporter.log(dateFormat.format(new Date()) + " [info]  " + message, true);
        }

    }

    public static void always(String message, boolean addDate) {
        if (addDate) {
            message = dateFormat.format(new Date()) + "  " + message;
        }

        Reporter.log(message, true);
    }

    public static void always(String message) {
        always(message, false);
    }

    private static void printInterval() {
        if (printActionInterval) {
            long curInt = System.currentTimeMillis();
            if (previousTs > 0L) {
                Reporter.log(String.format("\n+%s ms", curInt - previousTs), true);
            }

            previousTs = curInt;
        }

    }

    static {
        String level = System.getProperty("loglevel");
        if (!StringUtils.isEmpty(level)) {
            logLevel = Integer.parseInt(level);
        }

        String interval = System.getProperty("printInterval");
        if (!StringUtils.isEmpty(interval)) {
            printActionInterval = Boolean.parseBoolean(interval);
        }

        String hideInfo = System.getProperty("hideInfoMessages");
        if (!StringUtils.isEmpty(hideInfo)) {
            hideInfoMessages = Boolean.parseBoolean(hideInfo);
        }

        dateFormat = new SimpleDateFormat("(z) HH.mm.ss:SSS");
    }
}