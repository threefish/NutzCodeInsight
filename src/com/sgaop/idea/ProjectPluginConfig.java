package com.sgaop.idea;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;

/**
 * Created with IntelliJ IDEA.
 * 创建人: 黄川
 * 创建时间: 2017/12/1  19:28
 * 描述此类：
 */
public class ProjectPluginConfig {

    Application applicationManager;

    CommandProcessor processor;
    /**
     * 当前所在文档
     **/
    Document document;

    Project project;

    Editor editor;

    PsiFile psiFile;

    public ProjectPluginConfig(Application applicationManager, CommandProcessor processor, Document document, Project project, Editor editor, PsiFile psiFile) {
        this.applicationManager = applicationManager;
        this.processor = processor;
        this.document = document;
        this.project = project;
        this.editor = editor;
        this.psiFile = psiFile;
    }

    public PsiFile getPsiFile() {
        return psiFile;
    }

    public void setPsiFile(PsiFile psiFile) {
        this.psiFile = psiFile;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Application getApplicationManager() {
        return applicationManager;
    }

    public void setApplicationManager(Application applicationManager) {
        this.applicationManager = applicationManager;
    }

    public CommandProcessor getProcessor() {
        return processor;
    }

    public void setProcessor(CommandProcessor processor) {
        this.processor = processor;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
