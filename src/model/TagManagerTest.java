package model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


class TagManagerTest {

    @Test
    void TestSaveToTagManagerFile() throws IOException, ClassNotFoundException {
        String tag = "helloJava";
        String imageFilePath = "imageFile.ser";
        String tagFilePath = "tagFile.ser";
        GoGoPhotoSystem system = new GoGoPhotoSystem(tagFilePath, imageFilePath);
        system.getTagManager().addNewTag("helloJava");
        system.saveToImageManagerFile(imageFilePath);
        system.saveToTagManagerFile(tagFilePath);
        GoGoPhotoSystem system1 = new GoGoPhotoSystem(tagFilePath, imageFilePath);
        assertEquals(true, system1.getTagManager().getTags().containsKey("helloJava"));

    }

    // case that tags has been added
    @Test
    void TestGetExistingTags() {
        Tag tag = new Tag("helloJava");
        TagManager tm = new TagManager();
        tm.addNewTag("helloJava");
        ArrayList<Tag> actualTags = new ArrayList<>();
        actualTags.add(tag);
        assertEquals(actualTags.get(0).getName(), tm.getExistingTags().get(0).getName());
    }

    // CASE THAT NO TAGS HAS BEEN added
    @Test
    void Test1GetExistingTags() {
        TagManager tm = new TagManager();
        ArrayList<Tag> tags;
        tags = tm.getExistingTags();
        ArrayList<Tag> actualTags = new ArrayList<>();
        assertEquals(actualTags, tags);


    }

    // indirect test
    @Test
    void TestUpdate() {
        Image image = new Image("IMG_454", "jpg", "./UnittestPictures/IMG_454.jpg");
        String tag = "helloJava";
        String[] tagAndType = {tag, "U"};
        ImageManager imagemanager = new ImageManager();
        TagManager tagmanager = new TagManager();
        imagemanager.addObserver(tagmanager);
        tagmanager.update(imagemanager, tagAndType);
        assertTrue(tagmanager.getTags().containsKey(tag));
        assertEquals(1, (int) tagmanager.getTags().get(tag));
    }

    // direct test
    @Test
    void Test1uUdate() {
        Image image = new Image("IMG_454", "jpg", "./UnittestPictures/IMG_454.jpg");
        String tag = "helloJava";
        String[] tagAndType = {tag, "U"};
        ImageManager imagemanager = new ImageManager();
        TagManager tagmanager = new TagManager();
        imagemanager.addObserver(tagmanager);
        imagemanager.addTagToName(image, tag);
        assertTrue(tagmanager.getTags().containsKey(tag));
        assertEquals(1, (int) tagmanager.getTags().get(tag));
        imagemanager.deleteTagFromName(image, tag);
    }

    // direct test value>2
    @Test
    void Test2Update() {
        Image image = new Image("IMG_454", "jpg", "./UnittestPictures/IMG_454.jpg");
        String tag = "helloJava";
        String[] tagAndType = {tag, "U"};
        ImageManager imagemanager = new ImageManager();
        TagManager tagmanager = new TagManager();
        imagemanager.addObserver(tagmanager);
        tagmanager.update(imagemanager, tagAndType);
        tagmanager.update(imagemanager, tagAndType);
        assertTrue(tagmanager.getTags().containsKey(tag));
        assertEquals(2, (int) tagmanager.getTags().get(tag));
        imagemanager.deleteTagFromName(image, tag);
    }


    @Test
    void TestAddNewTag() {
        TagManager tm = new TagManager();
        tm.addNewTag("helloJava");
        assertTrue(tm.getTags().containsKey("helloJava"));
        assertEquals(0, (int) tm.getTags().get("helloJava"));
    }

    // not tags
    @Test
    void TestGetTags() {
        TagManager tm = new TagManager();
        HashMap<String, Integer> actualTags = new HashMap<>();
        assertEquals(actualTags, tm.getTags());
    }

    //tags
    @Test
    void Test1GetTags() {
        TagManager tm = new TagManager();
        tm.addNewTag("helloJava");
        tm.addNewTag("helloWorld");
        tm.addNewTag("hi");
        assertTrue(tm.getTags().containsKey("helloJava"));
        assertTrue(tm.getTags().containsKey("helloWorld"));
        assertTrue(tm.getTags().containsKey("hi"));

    }

    //delete tags
    @Test
    void Test2GetTags() {
        TagManager tm = new TagManager();
        tm.addNewTag("helloJava");
        tm.addNewTag("helloWorld");
        tm.addNewTag("hi");
        tm.deleteTag("hi");
        assertTrue(tm.getTags().containsKey("helloJava"));
        assertTrue(tm.getTags().containsKey("helloWorld"));


    }

    @Test
    void TestSetTags() {
        TagManager tm = new TagManager();
        HashMap<String, Integer> newTags = new HashMap<String, Integer>();
        newTags.put("helloJava", 0);
        newTags.put("helloWorld", 0);
        newTags.put("hi", 0);
        tm.setTags(newTags);
        assertEquals(newTags, tm.getTags());
    }

    // empty sets
    @Test
     void Test1SetTags() {
        TagManager tm = new TagManager();
        HashMap<String, Integer> newTags = new HashMap<String, Integer>();
        tm.setTags(newTags);
        assertEquals(newTags, tm.getTags());
    }

    @Test
    void TestDeleteTag() {
        TagManager tm = new TagManager();
        tm.addNewTag("helloJava");
        tm.addNewTag("helloWorld");
        tm.addNewTag("hi");
        tm.deleteTag("hi");
        assertTrue(tm.getTags().containsKey("helloJava"));
        assertTrue(tm.getTags().containsKey("helloWorld"));

    }

    @Test
    void Test1DeleteTag() {
        TagManager tm = new TagManager();
        tm.addNewTag("helloJava");
        tm.addNewTag("helloWorld");
        tm.addNewTag("hi");
        tm.deleteTag("hi");
        tm.deleteTag("helloWorld");
        tm.deleteTag("helloJava");
        assertEquals(false, tm.getTags().containsKey("helloJava"));
        assertTrue(tm.getExistingTags().isEmpty());
    }

}