package logger;

import org.junit.jupiter.api.extension.*;

public class ResultLoggerExtension implements TestWatcher {

    @Override
    public void testSuccessful(ExtensionContext context) {
        Logger.logIndented("✅ PASSED: " + context.getDisplayName());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Logger.logIndented("❌ FAILED: " + context.getDisplayName());
        Logger.logIndented("   Reason: " + cause.getMessage());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        Logger.logIndented("⚠️ SKIPPED: " + context.getDisplayName());
    }
}

