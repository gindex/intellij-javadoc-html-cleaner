package com.github.gindex.cleaner

import com.github.gindex.cleaner.TagStyle.mapTagToStyle
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.javadoc.PsiDocComment

class HtmlTagAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        val javadoc = element as? PsiDocComment ?: return
        val text = javadoc.text ?: return

        Tag.tags.forEach {
            val styleKey = mapTagToStyle(it) ?: return@forEach

            it.tagStartAndEndPattern.extractMatchingRanges(text)
                .map { (_, start, end) -> TextRange(javadoc.textOffset + start, javadoc.textOffset + end) }
                .forEach { range ->
                    holder.createInfoAnnotation(range, null)
                        .textAttributes = styleKey
                }
        }
    }
}