package model;

// A Scene that ends by presenting the user with a choice and calling the next scene based on their decision
public class ChoiceScene extends Scene {

    public ChoiceScene(String[] textsIn) {
        super(textsIn);
    }

    @Override
    protected boolean endLine() {
        if (!super.endLine()) {
            System.out.println("Here's your choice.");
            return false;
        }
        return true;
    }
}
