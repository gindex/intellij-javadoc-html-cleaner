package com.github.gindex.cleaner

import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase

class FoldingTest : LightJavaCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String? {
        return "testData"
    }

    fun doFoldingTest() {
        myFixture.testFolding(testDataPath + "/" + getTestName(false) + ".java")
    }

    fun testOnelineTagsFolding() {
        doFoldingTest()
    }

    fun testMultilineTagsFolding() {
        doFoldingTest()
    }

    fun testNewlineTagsFolding() {
        doFoldingTest()
    }

}