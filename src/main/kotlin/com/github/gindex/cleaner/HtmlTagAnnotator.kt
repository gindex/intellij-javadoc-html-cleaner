package com.github.gindex.cleaner

import com.github.gindex.cleaner.TagStyle.mapTagToStyle
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.javadoc.PsiDocComment
import java.util.regex.Pattern

class HtmlTagAnnotator : Annotator {
    companion object {
        private val leadingAsterisksPattern = Pattern.compile("(\\R\\s+\\*)")
    }

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        val javadoc = element as? PsiDocComment ?: return
        val javadocText = javadoc.text ?: return

        Tag.tags.forEach {
            val styleKey = mapTagToStyle(it) ?: return@forEach

            it.tagStartAndEndPattern.extractMatchingRanges(javadocText)
                    .flatMap { (text, start, end) ->
                        extractRangesWithSplit(text, javadoc.textOffset + start, javadoc.textOffset + end)
                    }
                    .forEach { range ->
                        holder.createInfoAnnotation(range, null).textAttributes = styleKey
                    }
        }
    }

    private fun extractRangesWithSplit(text: String, startInParent: Int, endInParent: Int): List<TextRange> {
        return if (text.contains("*")) {
            leadingAsterisksPattern.extractMatchingRanges(text)
                    .fold(Pair(startInParent, listOf<TextRange>())) { (currentStart, collector), asterisksRange ->
                        val extendedRange = collector.plus(TextRange(currentStart, startInParent + asterisksRange.start))
                        Pair(startInParent + asterisksRange.end, extendedRange)
                    }.run {
                        second.plus(TextRange(first, endInParent))
                    }
        } else {
            listOf(TextRange(startInParent, endInParent))
        }
    }
}