package myprojects.automation.assignment4.utils.logging;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult iTestResult) {
        CustomReporter.logAction("Test " + iTestResult.getName() +  " started");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        CustomReporter.logAction("PASSED: " + iTestResult.getName());
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        CustomReporter.logAction("FAILED: " + iTestResult.getName());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        CustomReporter.logAction("SKIPPED: " + iTestResult.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {
        CustomReporter.logAction("Start of execution " + iTestContext.getName());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        CustomReporter.logAction("End of execution " + iTestContext.getName());
    }
}
