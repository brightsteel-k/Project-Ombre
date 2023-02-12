package model;

import ui.Printer;

// Framework for a scene: a distinct section of the story text with which the user will interact
public abstract class Scene {

    protected String[] texts;
    protected int index;

    public Scene(String[] textsIn) {
        texts = textsIn;
        index = 0;
    }

    // REQUIRES: index < texts.length
    public void printNextLine() {
        Printer.printText(texts[index]);
        endLine();
    }

    // EFFECTS: increments index, returns true iff the end of the scene has not been reached yet.
    protected boolean endLine() {
        index++;
        if (index == texts.length) {
            return false;
        }

        Printer.continueText();
        return true;
    }
}
