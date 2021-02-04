//AFrameBB
//class that represents one node/frame for Black Belt

import java.io.Serializable;

public class AFrameBB implements Serializable {

    private AFrameBB prevFrame;//previous frame
    private String content;//content in this frame
    private AFrameBB nextFrame;//next frame

    public AFrameBB(){//every node/frame is initialized with everything set to null
        this.prevFrame = null;
        this.content=null;
        this.nextFrame = null;
    }

    public AFrameBB(String content){//fancy constructor that initializes node with content
        this.prevFrame = null;
        this.content = content;
        this.nextFrame = null;
    }
    //getters and setters
    public AFrameBB getPrevFrame() {
        return prevFrame;
    }

    public void setPrevFrame(AFrameBB prevFrame) {
        this.prevFrame = prevFrame;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AFrameBB getNextFrame() {
        return nextFrame;
    }

    public void setNextFrame(AFrameBB nextFrame) {
        this.nextFrame = nextFrame;
    }
}
