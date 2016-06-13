package cz.tul.client;


public class ImageStatus {
    public enum ImageState {
        READY, PROCESSING, FAILED, UPLOADED,
    }

    private ImageState state;

    public ImageStatus(ImageState state) {
        super();
        this.state = state;
    }

    public ImageState getState() {
        return state;
    }
}

