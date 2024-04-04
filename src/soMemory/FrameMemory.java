package soMemory;

public class FrameMemory {

    private int pageNumber;
    private int displacement;
    private int offset;
    private int frameNumber;

    public FrameMemory(int pageNumber, int displacement) {
        this.pageNumber = pageNumber;
        this.displacement = displacement;
    }
    
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getDisplacement() {
        return displacement;
    }

    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    
}
