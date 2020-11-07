package model;

/**
 * This class is created to assist DirectoryManager class to display the
 * name information and path information in the table view created in the ListAllScene and LocalOnlyScene.
 *
 * @see DirectoryManager
 */
public class ExistingImage {
    /**
     * The name including both file name and file name extension of this ExistingImage
     */
    private String name;

    /**
     * the path of this ExistingImage
     */
    private String path;

    /**
     * Creates a new ExistingImage with given
     * name and path of this ExistingImage.
     *
     * @param name the name of this ExistingImage
     * @param path the path of this ExistingImage
     */
    ExistingImage(String name, String path) {
        this.name = name;
        this.path = path;
    }

    /**
     * Gets the name of the file without name extension of this ExistingImage.
     *
     * @return the name of the image file(without name extension) of this ExistingImage
     */
    public String getImageName() {
        int i = this.name.lastIndexOf(".");
        String imageName = this.name.substring(0, i);
        return imageName;
    }


    /**
     * Gets the name extension of this ExistingImage.
     *
     * @return the name extension of this ExistingImage
     */
    public String getImageType() {
        int i = this.name.lastIndexOf(".");
        String imageType = this.name.substring(i + 1);
        return imageType;
    }

    /**
     * Gets the full name(both image name and name extension) of this ExistingImage.
     *
     * @return the full name of this ExistingImage
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the path of this ExistingImage.
     *
     * @return the path of this ExistingImage
     */
    public String getPath() {
        return this.path;
    }
}
