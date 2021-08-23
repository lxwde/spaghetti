package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.xb.ltgfmt.TestCase;
import org.aspectj.weaver.ast.Test;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.lang.management.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class TestUtils {
    private static final String TEST_CASES = "tests";
    private static final String ANT_PROPERTY = "${tests}";
    private static final String DELIMITER = ",";
    private static final Logger LOGGER = Logger.getLogger(TestUtils.class);
    private static final ThreadMXBean THREAD_MX_BEAN = ManagementFactory.getThreadMXBean();

    private TestUtils() {
    }

    public static boolean hasTestCases() {
        return System.getProperty(TEST_CASES) != null && !System.getProperty(TEST_CASES).equals(ANT_PROPERTY);
    }

//    public static TestSuite getSuite(Class inTestClass) {
//        if (!TestCase.class.isAssignableFrom(inTestClass)) {
//            throw new IllegalArgumentException("Must pass in a subclass of TestCase");
//        }
//        TestSuite suite = new TestSuite();
//        try {
//            Constructor constructor = inTestClass.getConstructor(String.class);
//            List testCaseNames = TestUtils.getTestCaseNames();
//            for (String testCaseName : testCaseNames) {
//                suite.addTest((Test)((TestCase)constructor.newInstance(testCaseName)));
//            }
//        }
//        catch (Exception e) {
//            throw new RuntimeException(inTestClass.getName() + " doesn't have the proper constructor");
//        }
//        return suite;
//    }

    private static List getTestCaseNames() {
        if (System.getProperty(TEST_CASES) == null) {
            throw new NullPointerException("Test case property is not set");
        }
        ArrayList<String> testCaseNames = new ArrayList<String>();
        String testCases = System.getProperty(TEST_CASES);
        StringTokenizer tokenizer = new StringTokenizer(testCases, DELIMITER);
        while (tokenizer.hasMoreTokens()) {
            testCaseNames.add(tokenizer.nextToken());
        }
        return testCaseNames;
    }

    public static String getClipboard() {
        boolean hasTransferableText;
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        boolean bl = hasTransferableText = contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText) {
            try {
                result = (String)contents.getTransferData(DataFlavor.stringFlavor);
            }
            catch (UnsupportedFlavorException ex) {
                LOGGER.error((Object)("getClipboard Exception: " + ex.getMessage()));
            }
            catch (IOException ex) {
                LOGGER.error((Object)("IOException during getClipboard: " + ex.getMessage()));
            }
        }
        return result;
    }

    public static void setClipboard(String inString) {
        StringSelection stringSelection = new StringSelection(inString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

//    public static TestSuite getOrderedSuite(Class inTestClass, String[] inTestMethods) {
//        if (!TestCase.class.isAssignableFrom(inTestClass)) {
//            throw new IllegalArgumentException("Must pass in a subclass of TestCase");
//        }
//        return new OrderedTestSuite(inTestClass, inTestMethods);
//    }

    public static void failOnException(String inComment, Exception inCaughtException, TestCase inTestCase) {
        BizFailure bf = BizFailure.create(IFrameworkPropertyKeys.FAILURE__NAVIS, inCaughtException);
        String errorLog = "\n";
        errorLog = errorLog + "\nFailure:     " + inComment;
        errorLog = errorLog + "\nTest:        " + (Object)inTestCase;
        errorLog = errorLog + "\nException:   " + inCaughtException;
        errorLog = errorLog + "\n\nStacktrace:\n" + bf.getStackTraceString();
//        Assert.fail((String)errorLog);
    }

    public static void assertTrueResponseSuccess(IMessageCollector inMessageCollector) {
        if (inMessageCollector.containsMessageLevel(MessageLevelEnum.SEVERE)) {
//            throw new AssertionFailedError("Request failed. SEVERE Errors in message collector: " + inMessageCollector.toString());
        }
    }

    public static void assertResponseFailed(IMessageCollector inResponse, IPropertyKey inExpectedErrorKey) {
        TestUtils.assertResponseFailed("", inResponse, inExpectedErrorKey);
    }

    public static void assertResponseFailed(String inPrintMsg, IMessageCollector inResponse, IPropertyKey inExpectedErrorKey) {
//        Assert.assertTrue((String)"Response unexpectedly succeeded.", (boolean)inResponse.hasError());
//        Assert.assertTrue((String)(inPrintMsg + " -- Response did not contain expected message " + inExpectedErrorKey + " in " + inResponse), (boolean)inResponse.containsMessage(inExpectedErrorKey));
    }

    public static void assertResponseFailed(IMessageCollector inResponse, IPropertyKey inExpectedErrorKey, String inExpectedErrorMessage) {
        TestUtils.assertResponseFailed("", inResponse, inExpectedErrorKey, inExpectedErrorMessage);
    }

    public static void assertResponseFailed(String inPrintMsg, IMessageCollector inResponse, IPropertyKey inExpectedErrorKey, String inExpectedErrorMessage) {
        TestUtils.assertResponseFailed(inPrintMsg, inResponse, inExpectedErrorKey);
        if (inExpectedErrorMessage != null) {
            List messages = inResponse.getMessages(MessageLevelEnum.SEVERE);
            boolean found = false;
            for (Object message : messages) {
                found |= message.toString().contains(inExpectedErrorMessage) && ((IUserMessage)message).getMessageKey().getKey().equals(inExpectedErrorKey.getKey());
            }
//            Assert.assertTrue((String)(inPrintMsg + " -- Response did not contain expected message " + inExpectedErrorMessage + " in " + inResponse), (boolean)found);
        }
    }

    public static String getMessage(IPropertyKey inKey, UserContext inUserContext) {
        String message = TranslationUtils.getTranslationContext(inUserContext).getMessageTranslator().getMessage(inKey);
        if (message.charAt(0) == '{' && message.charAt(message.length() - 1) == '}') {
            message = message.substring(1, message.length() - 1);
        }
        return message;
    }

    public static String getMessage(IPropertyKey inKey, Object[] inParams, UserContext inUserContext) {
        IMessageTranslator messageTranslator = TranslationUtils.getTranslationContext(inUserContext).getMessageTranslator();
        if (inParams == null || inParams.length == 0) {
            return messageTranslator.getMessage(inKey);
        }
        return messageTranslator.getMessage(inKey, inParams);
    }

    public static void executeAndAssertDeadlock(@NotNull Callable<?> inDeadlockingCallable, @NotNull Thread.State inExpectedThreadState) throws InterruptedException {
        TestUtils.executeAndAssertDeadlock(Arrays.asList(inDeadlockingCallable), Collections.emptyList(), Arrays.asList(new Thread.State[]{inExpectedThreadState}));
    }

    public static <T> void executeAndAssertDeadlock(@NotNull List<Callable<T>> inDeadlockingCallables, @NotNull List<CountDownLatch> inAllPartsReady, @NotNull List<Thread.State> inExpectedThreadStates) throws InterruptedException {
        TestUtils.executeAndAssertThreadsInStates(inDeadlockingCallables, inAllPartsReady, inExpectedThreadStates, true);
    }

    @NotNull
    public static <T> List<Future<T>> executeAndAssertThreadsInStates(@NotNull List<Callable<T>> inCallables, @NotNull List<CountDownLatch> inLatchesToWaitFor, @NotNull List<Thread.State> inExpectedThreadStates, boolean inDeadlockExpected) throws InterruptedException {
        int numberOfCallables = inCallables.size();
  //      Assert.assertEquals((long)numberOfCallables, (long)inExpectedThreadStates.size());
        ThreadRegistry threadRegistry = new ThreadRegistry();
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfCallables, threadRegistry);
        ArrayList<Future<Future>> results = new ArrayList<Future<Future>>(numberOfCallables);
        for (Callable<T> deadlockingCallable : inCallables) {
            results.add((Future<Future>) executorService.submit((Runnable) deadlockingCallable));
        }
        executorService.shutdown();
        for (CountDownLatch latchToWaitFor : inLatchesToWaitFor) {
            latchToWaitFor.await();
        }
        TestUtils.assertThreadsInState(inExpectedThreadStates, threadRegistry);
        if (inDeadlockExpected) {
            results.forEach(TestUtils::expectTimeoutFor);
        }
  //      Assert.assertEquals((long)numberOfCallables, (long)results.size());
  //      return results;
        return null;
    }

    private static void assertThreadsInState(@NotNull List<Thread.State> inExpectedThreadStates, @NotNull ThreadRegistry inThreadRegistry) {
        int i = 0;
        int maxSecondsToWait = 30;
        while (i++ < 30 && !inExpectedThreadStates.equals(inThreadRegistry.getActualThreadStates())) {
            try {
                TimeUnit.SECONDS.sleep(1L);
            }
            catch (InterruptedException e) {
                throw new AssertionError("Thread was interrupted. ", e);
            }
        }
        LogUtils.forceLogAtInfo(LOGGER, "Asserting thread states: " + TestUtils.getThreadDumpForIds(inThreadRegistry.getThreadIds()));
 //       Assert.assertEquals((String)"Threads are not in expected state:\n", inExpectedThreadStates, inThreadRegistry.getActualThreadStates());
    }

    private static void expectTimeoutFor(@Nullable Future<?> inFuture) {
        if (inFuture == null) {
            return;
        }
        try {
            inFuture.get(0L, TimeUnit.MILLISECONDS);
//            Assert.fail((String)"Deadlock expected.");
        }
        catch (TimeoutException timeoutException) {
        }
        catch (InterruptedException | ExecutionException e) {
//            Assert.fail((String)"Deadlock expected.");
        }
    }

    public static String getAllThreadDump() {
        ThreadInfo[] threads = THREAD_MX_BEAN.dumpAllThreads(true, true);
        return TestUtils.getThreadDump(threads);
    }

    public static String getCurrentThreadDump() {
        return TestUtils.getThreadDumpForIds(Arrays.asList(Thread.currentThread().getId()));
    }

    public static String getThreadDumpForIds(@NotNull List<Long> inThreadIds) {
        long[] longs = new long[inThreadIds.size()];
        for (int i = 0; i < longs.length; ++i) {
            longs[i] = inThreadIds.get(i);
        }
        ThreadInfo[] threadInfos = THREAD_MX_BEAN.getThreadInfo(longs, true, true);
        return TestUtils.getThreadDump(threadInfos);
    }

    public static String getThreadDump(@NotNull ThreadInfo[] inThreadInfos) {
        StringBuilder threadDumpBuilder = new StringBuilder("THREAD DUMP\n");
        for (ThreadInfo threadInfo : inThreadInfos) {
            threadDumpBuilder.append(TestUtils.getThreadInfoAsString(threadInfo)).append("\n\n\n");
        }
        return threadDumpBuilder.toString();
    }

    public static String getThreadInfoAsString(@Nullable ThreadInfo inThreadInfo) {
        String threadInfoAsString = inThreadInfo == null ? "Thread Info does not exist. Thread must already have terminated." : new ThreadInfoFormatter(inThreadInfo).toString();
        return threadInfoAsString;
    }

    private static final class ThreadRegistry
            implements ThreadFactory {
        private final List<Thread> _threadList = new CopyOnWriteArrayList<Thread>();

        private ThreadRegistry() {
        }

        @Override
        public Thread newThread(Runnable inRunnable) {
            Thread newThread = new Thread(inRunnable);
            this._threadList.add(newThread);
            return newThread;
        }

        public List<Thread.State> getActualThreadStates() {
            return this._threadList.stream().map(Thread::getState).collect(Collectors.toList());
        }

        public List<Long> getThreadIds() {
            return this._threadList.stream().map(Thread::getId).collect(Collectors.toList());
        }
    }

    private static class ThreadInfoFormatter {
        private final ThreadInfo _threadInfo;

        private ThreadInfoFormatter(ThreadInfo inThreadInfo) {
            this._threadInfo = inThreadInfo;
        }

        public String toString() {
            LockInfo[] locks;
            int i;
            StringBuilder sb = new StringBuilder("\"" + this._threadInfo.getThreadName() + "\" Id=" + this._threadInfo.getThreadId() + " " + (Object)((Object)this._threadInfo.getThreadState()));
            if (this._threadInfo.getLockName() != null) {
                sb.append(" on " + this._threadInfo.getLockName());
            }
            if (this._threadInfo.getLockOwnerName() != null) {
                sb.append(" owned by \"" + this._threadInfo.getLockOwnerName() + "\" Id=" + this._threadInfo.getLockOwnerId());
            }
            if (this._threadInfo.isSuspended()) {
                sb.append(" (suspended)");
            }
            if (this._threadInfo.isInNative()) {
                sb.append(" (in native)");
            }
            sb.append('\n');
            StackTraceElement[] stacktrace = this.getStacktrace();
            for (i = 0; i < stacktrace.length; ++i) {
                StackTraceElement ste = stacktrace[i];
                sb.append("\tat " + ste.toString());
                sb.append('\n');
                if (i == 0 && this._threadInfo.getLockInfo() != null) {
                    Thread.State ts = this._threadInfo.getThreadState();
                    switch (ts) {
                        case BLOCKED: {
                            sb.append("\t-  blocked on " + this._threadInfo.getLockInfo());
                            sb.append('\n');
                            break;
                        }
                        case WAITING: {
                            sb.append("\t-  waiting on " + this._threadInfo.getLockInfo());
                            sb.append('\n');
                            break;
                        }
                        case TIMED_WAITING: {
                            sb.append("\t-  waiting on " + this._threadInfo.getLockInfo());
                            sb.append('\n');
                            break;
                        }
                    }
                }
                for (LockInfo lockInfo : this.getLockedMonitors()) {
                    if (((MonitorInfo)lockInfo).getLockedStackDepth() != i) continue;
                    sb.append("\t-  locked " + lockInfo);
                    sb.append('\n');
                }
            }
            if (i < stacktrace.length) {
                sb.append("\t...");
                sb.append('\n');
            }
            if ((locks = this._threadInfo.getLockedSynchronizers()).length > 0) {
                sb.append("\n\tNumber of locked synchronizers = " + locks.length);
                sb.append('\n');
                for (LockInfo lockInfo : locks) {
                    sb.append("\t- " + lockInfo);
                    sb.append('\n');
                }
            }
            sb.append('\n');
            return sb.toString();
        }

        private StackTraceElement[] getStacktrace() {
            try {
                Field stackTrace = this._threadInfo.getClass().getDeclaredField("stackTrace");
                stackTrace.setAccessible(true);
                return (StackTraceElement[])stackTrace.get(this._threadInfo);
            }
            catch (Throwable t) {
                throw new IllegalStateException(t);
            }
        }

        private MonitorInfo[] getLockedMonitors() {
            try {
                Field lockedMonitors = this._threadInfo.getClass().getDeclaredField("lockedMonitors");
                lockedMonitors.setAccessible(true);
                return (MonitorInfo[])lockedMonitors.get(this._threadInfo);
            }
            catch (Throwable t) {
                throw new IllegalStateException(t);
            }
        }
    }

    private static class OrderedTestSuite
//            extends TestSuite
    {
        private OrderedTestSuite(Class inTestClass, String[] inTestMethods) {
//            super(inTestClass.getName());
            int i;
            HashSet<String> existingMethods = new HashSet<String>();
            Method[] methods = inTestClass.getMethods();
            for (i = 0; i < methods.length; ++i) {
                Method method = methods[i];
                existingMethods.add(method.getName());
            }
            for (i = 0; i < inTestMethods.length; ++i) {
                String testMethod = inTestMethods[i];
                if (existingMethods.remove(testMethod)) {
  //                  this.addTest(OrderedTestSuite.createTest((Class)inTestClass, (String)testMethod));
                    continue;
                }
//                this.addTest(OrderedTestSuite.error(inTestClass, testMethod, new IllegalArgumentException("Class '" + inTestClass.getName() + " misses test method '" + testMethod + "'.")));
            }
            for (String existingMethod : existingMethods) {
                if (!existingMethod.startsWith("test")) continue;
  //              this.addTest(OrderedTestSuite.error(inTestClass, existingMethod, new IllegalArgumentException("Test method '" + existingMethod + "' not listed in OrderedTestSuite of class '" + inTestClass.getName() + "'.")));
            }
        }

        private static Test error(Class inTestClass, String inTestMethod, Exception inException) {
            final Throwable e2 = inException.fillInStackTrace();
//            return new TestCase(inTestMethod + "(" + inTestClass.getName() + ")"){

//                protected void runTest() throws Throwable {
//                    throw e2;
//                }
//            };
            return null;
        }
    }
}
