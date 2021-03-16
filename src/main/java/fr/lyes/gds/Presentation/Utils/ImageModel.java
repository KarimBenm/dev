package fr.lyes.gds.Presentation.Utils;

public class ImageModel {
    public ImageModel() {
        super();
    }
    public ImageModel(String name, byte[] picByte) {
        this.name = name;
        this.picByte = picByte;
    }

    private String name;
    //image bytes can have large lengths so we specify a value
    //which is more than the default length for picByte column
    private byte[] picByte;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicByte() {
        return picByte;
    }
    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }
}
