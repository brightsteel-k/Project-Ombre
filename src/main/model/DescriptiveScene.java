package model;

// A Scene that presents the user with multiple blocks of text
public class DescriptiveScene extends Scene {

    protected String nextScene;

    public DescriptiveScene(String textIn, String nextIn) {
        super(new String[] { textIn });
        nextScene = nextIn;
    }

    public DescriptiveScene(String[] textsIn, String nextIn) {
        super(textsIn);
        nextScene = nextIn;
    }

    @Override
    protected boolean endLine() {
        if (!super.endLine()) {
            StoryController.beginScene(nextScene);
            return false;
        }
        return true;
    }
}
