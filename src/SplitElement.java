public class SplitElement {


    private int startIndex;
    private int size;

    public SplitElement(int startIndex, int size) {
        this.startIndex = startIndex;
        this.size = size;
    }

    public int index() {
        return startIndex;
    }

    public int size() {
        return size;
    }
    
    @Override
    public String toString() {
    	return startIndex + " " + size;
    }
	
}
