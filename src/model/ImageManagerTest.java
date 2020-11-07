package model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ImageManagerTest {

    @Test
    void testImageManagerAddImage() {
        Image image = new Image("IMG_454", "jpg", "./UnittestPictures/IMG_454.jpg");
        ImageManager im = new ImageManager();
        im.addTagToName(image, "helloJava");
        im.addImage(image);
        assertEquals(image, im.foundImage(image.getPath()));
        im.deleteTagFromName(image,"helloJava");
    }

    @Test
    //not found the image
    void test1ImageManagerFoundImage() {
        Image image = new Image("IMG_454", "jpg", "./UnittestPictures/IMG_454.jpg");
        ImageManager im = new ImageManager();
        assertEquals(null, im.foundImage(image.getPath()));
    }

    @Test
    void testImageManagerRenameFile(){
        Image image = new Image("IMG_454", "jpg", "./UnittestPictures/IMG_454.jpg");
        ImageManager im = new ImageManager();
        im.renameFile(image, "helloJava");
        File newFile = new File("./UnittestPictures/helloJava.jpg");
        String newFileName = newFile.getName().substring(0,newFile.getName().lastIndexOf("."));
        assertEquals(newFileName, image.getName());
    }


    @Test
    void test1ImageManagerRenameFile(){
        Image image = new Image("helloJava", "jpg", "./UnittestPictures/helloJava.jpg");
        ImageManager im = new ImageManager();
        im.renameFile(image, "IMG_454");
        File newFile = new File("./UnittestPictures/IMG_454.jpg");
        String newFileName = newFile.getName().substring(0,newFile.getName().lastIndexOf("."));
        assertEquals(newFileName, image.getName());
    }

    @Test
    void test2ImageManagerRenameFile(){
        Image image = new Image("IMG_454", "jpg", "./UnittestPictures/IMG_454.jpg");
        ImageManager im = new ImageManager();
        im.renameFile(image, " ");
        File newFile = new File("./UnittestPictures/ .jpg");
        String newFileName = newFile.getName().substring(0,newFile.getName().lastIndexOf("."));
        assertEquals(newFileName, image.getName());
        im.backToOldName(image, "IMG_454.jpg");
    }



    @Test
    void testImageManagerMoveImageToDirectory() throws IOException {
        Image image = new Image("IMG_4922", "jpg", "./UnittestPictures/IMG_4922.jpg");
        ImageManager im = new ImageManager();
        im.moveImageToDirectory(image, "./TestPictures");
        File newFile = new File("./TestPictures/IMG_4922.jpg");
        assertEquals(true, newFile.exists());
    }

    @Test
     void test1ImageManagerMoveImageToDirectory() throws IOException {
        Image image = new Image("IMG_4922", "jpg", "./TestPictures/IMG_4922.jpg");
        ImageManager im = new ImageManager();
        im.moveImageToDirectory(image, "./UnittestPictures");
        File newFile = new File("./UnittestPictures/IMG_4922.jpg");
        assertEquals(newFile.getPath(), image.getPath());

    }

    @Test
    void testImageManagerBackToOldName() throws IOException {
        Image image = new Image("IMG_4922", "jpg", "./UnittestPictures/IMG_4922.jpg");
        ImageManager im = new ImageManager();
        im.addTagToName(image, "helloJava");
        im.addTagToName(image, "helloPaul");
        im.addTagToName(image, "helloWorld");
        File newFile = new File("./UnittestPictures/IMG_4922 @helloJava @helloPaul @helloWorld.jpg");
        String newFileName = newFile.getName().substring(0,newFile.getName().lastIndexOf("."));
        assertEquals(image.getName(), newFileName);
        assertEquals(image.getRenamingHistory().get(0).getName(), "IMG_4922.jpg");
        assertEquals(image.getRenamingHistory().get(1).getName(), "IMG_4922 @helloJava.jpg");
        im.backToOldName(image, "IMG_4922.jpg");
        assertEquals("IMG_4922", image.getName());
    }

    @Test
    void testImageManagerDeleteTagFromName() throws IOException {
        Image image = new Image("IMG_4922", "jpg", "./UnittestPictures/IMG_4922.jpg");
        ImageManager im = new ImageManager();
        im.addTagToName(image, "helloPaul");
        File newFile = new File("./UnittestPictures/IMG_4922 @helloPaul.jpg");
        String newFileName = newFile.getName().substring(0, newFile.getName().lastIndexOf("."));
        im.deleteTagFromName(image, "helloPaul");
        File newFile1 = new File("./UnittestPictures/IMG_4922.jpg");
        String newFileName1 = newFile1.getName().substring(0, newFile1.getName().lastIndexOf("."));
        assertEquals(image.getName(), newFileName1);
        assertEquals(image.getRenamingHistory().get(0).getName(), "IMG_4922.jpg");
        assertEquals(image.getRenamingHistory().get(1).getName(), "IMG_4922 @helloPaul.jpg");
        im.backToOldName(image, "IMG_4922.jpg");
        assertEquals("IMG_4922", image.getName());
    }

    @Test
    void testImageManagerAddTagToName() throws IOException {
        Image image = new Image("IMG_4922", "jpg", "./UnittestPictures/IMG_4922.jpg");
        ImageManager im = new ImageManager();
        im.addTagToName(image, "helloJava");
        File newFile = new File("./UnittestPictures/IMG_4922 @helloJava.jpg");
        String newFileName = newFile.getName().substring(0, newFile.getName().lastIndexOf("."));
        assertEquals(image.getName(), newFileName);
        assertEquals(image.getRenamingHistory().get(0).getName(), "IMG_4922.jpg");
        assertEquals(image.getRenamingHistory().get(1).getName(), "IMG_4922 @helloJava.jpg");
        im.backToOldName(image, "IMG_4922.jpg");
        assertEquals("IMG_4922", image.getName());
    }


    @Test
    void testImageManagerSaveToImageManagerFile() throws IOException, ClassNotFoundException {
        Image image = new Image("IMG_454", "jpg", "./UnittestPictures/IMG_454.jpg");
        String imageFilePath = new String();
        imageFilePath = "imageFile1.ser";
        String tagFilePath = new String();
        tagFilePath = "tagFile1.ser";
        GoGoPhotoSystem system = new GoGoPhotoSystem(tagFilePath, imageFilePath);
        system.getImageManager().addTagToName(image, "helloJava");
        system.getImageManager().addImage(image);
        system.saveToImageManagerFile(imageFilePath);
        system.saveToTagManagerFile(tagFilePath);
        GoGoPhotoSystem system1 = new GoGoPhotoSystem(tagFilePath, imageFilePath);
        assertEquals("IMG_454 @helloJava", system1.getImageManager().getImages().get(0).getName());
        system.getImageManager().deleteTagFromName(image, "helloJava");
    }

    @Test
    void testImageManagerFoundTag() throws IOException {
        Image image = new Image("IMG_4922", "jpg", "./UnittestPictures/IMG_4922.jpg");
        ImageManager im = new ImageManager();
        im.addTagToName(image, "cindy");
        assertTrue(im.foundTag(image, "cindy"));
        im.deleteTagFromName(image, "cindy");
    }



}