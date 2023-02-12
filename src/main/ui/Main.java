package ui;

import model.StoryController;

public class Main {

    private static StoryController STORY;

    public static void main(String[] args) {
        STORY = new StoryController();
        STORY.start();
    }
}
