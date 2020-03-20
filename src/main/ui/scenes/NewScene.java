package ui.scenes;

//basic interface for each scene of the appliation
public interface NewScene {

    // EFFECTS: initializes contents/elements of the scene
    void initializeSceneContent();

    // EFFECTS: add elements to the scene
    void addSceneContent();

    // EFFECTS: initializes the scene, setting the title and size
    void initializeScene();


}
