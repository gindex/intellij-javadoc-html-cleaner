package com.github.gindex.cleaner

import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase


class AnnotatorTest : LightJavaCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String? {
        return "testData"
    }

    fun doHighlightingTest() {
        myFixture.configureByFile( getTestName(false) + ".java")
        myFixture.testHighlighting(false, true, false)
    }

    fun testOnelineTagHighlighting() {
        doHighlightingTest()
    }

    fun testNewlineTagHighlighting() {
        doHighlightingTest()
    }

    fun testMultilineTagHighlighting() {
        doHighlightingTest()
    }

}