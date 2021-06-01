package com.github.gindex.cleaner

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.javadoc.PsiDocComment
import com.intellij.psi.util.PsiTreeUtil

class TagFoldingBuilder : FoldingBuilderEx() {

    private val group: FoldingGroup = FoldingGroup.newGroup("html")

    override fun isCollapsedByDefault(node: ASTNode): Boolean = true

    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> =
        PsiTreeUtil.findChildrenOfType(root, PsiDocComment::class.java)
            .flatMap { javadoc ->
                getHtmlTagFoldingDecs(javadoc, group) +
                        getEntityFoldingDesc(javadoc, group) +
                        getJavadocTagFoldingDesc(javadoc, group)
            }.toTypedArray()


    private fun getJavadocTagFoldingDesc(javadoc: PsiDocComment, group: FoldingGroup): List<FoldingDescriptor> =
        JavadocTag.anyTagPattern
            .extractMatchingRanges(javadoc.text, listOf(1, 3))
            .map { (_, start, end) ->
                FoldingDescriptor(
                    javadoc.node,
                    TextRange(javadoc.textOffset + start, javadoc.textOffset + end),
                    group,
                    ""
                )
            }


    private fun getHtmlTagFoldingDecs(javadoc: PsiDocComment, group: FoldingGroup): List<FoldingDescriptor> =
        HtmlTag.anyTagPattern
            .extractMatchingRanges(javadoc.text)
            .map { (text, start, end) ->
                val placeholderText = if (text.contains("<li>")) "-" else ""

                FoldingDescriptor(
                    javadoc.node,
                    TextRange(javadoc.textOffset + start, javadoc.textOffset + end),
                    group,
                    placeholderText
                )
            }

    private fun getEntityFoldingDesc(javadoc: PsiDocComment, group: FoldingGroup): List<FoldingDescriptor> =
        CharacterEntityMapping.anyEntityPattern
            .extractMatchingRanges(javadoc.text)
            .mapNotNull { (text, start, end) ->
                CharacterEntityMapping.mapToChar(text)?.let { char ->
                    FoldingDescriptor(
                        javadoc.node,
                        TextRange(javadoc.textOffset + start, javadoc.textOffset + end),
                        group,
                        char
                    )
                }
            }

    override fun getPlaceholderText(node: ASTNode): String? = null
}
