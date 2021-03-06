//AsciiCanvasBB.java

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class AsciiCanvasBB extends JTextArea{

    //current and head nodes/frames
    private AFrameBB head=new AFrameBB();
    private AFrameBB current;
    private AFrameBB temp;//helps on some processes

    //getters and setters
    public AFrameBB getTemp() {
        return temp;
    }

    public void setTemp(AFrameBB temp) {
        this.temp = temp;
    }

    public AFrameBB getHead() {
        return head;
    }

    public void setHead(AFrameBB head) {
        this.head = head;
    }

    public AFrameBB getCurrent() {
        return current;
    }

    public void setCurrent(AFrameBB current) {
        this.current = current;
    }

    //constructor
    public AsciiCanvasBB(){
        this.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));//all the letters occupy the same horizontal space
        current = head;
    }
    public void anim(){//animation method
        if(current.getContent()==null){//if current node/frame has not been edited
            current = head;
        }else if(current.getNextFrame()==null){//if last node/frame is reached
            current = head;
            this.setText(current.getContent());
        }else if(current.getNextFrame()!=null){//if current node/frame is the head or any middle node/frame
            current = current.getNextFrame();
            this.setText(current.getContent());
        }
    }
    public void nextFrame(){//to next node/frame
        if(head.getContent()==null){//if head has not been edited yet
            head.setContent(this.getText());
            temp = new AFrameBB();
            temp.setPrevFrame(current);
            current=temp;
            this.setText(current.getContent());
        }else if(current.getNextFrame()==null){//if last node/frame is reached
            if(current.getPrevFrame()!=null) {
                current.setContent(this.getText());
                current.getPrevFrame().setNextFrame(current);
                temp = new AFrameBB();
                temp.setPrevFrame(current);
                current = temp;
                this.setText(current.getContent());
            }else{
                current.setContent(this.getText());
                temp = new AFrameBB();
                temp.setPrevFrame(current);
                current = temp;
                this.setText(current.getContent());
            }
        }else if(current.getNextFrame()!=null){//if current node/frame is a middle node/frame
            current.setContent(this.getText());
            current=current.getNextFrame();
            this.setText(current.getContent());
        }
    }
    public void prevFrame(){//to previous node/frame
        if(current==head){//if current node/frame is the head
            current.setContent(this.getText());
            this.setText(current.getContent());
        }
        else {
            if (current.getNextFrame()==null) {//if last node/frame is reached
                current.setContent(this.getText());
                current.getPrevFrame().setNextFrame(current);
                current=current.getPrevFrame();
                this.setText(current.getContent());
            } else {//if current node/frame is a middle node/frame
                current.setContent(this.getText());
                current = current.getPrevFrame();
                this.setText(current.getContent());
            }

        }
    }
    public void insert() {//insert node/frame behind the current node
        if(head.getContent()==null) {//if head has not been edited does nothing
        }else {//if head has been edited

            if (current == head) {//if current is at the head node/frame
                AFrameBB newFrame = new AFrameBB();
                newFrame.setNextFrame(head);
                head.setPrevFrame(newFrame);
                newFrame.setContent("");
                current.setContent(this.getText());
                this.setText(newFrame.getContent());
                current = newFrame;
                head = newFrame;
            } else {//if current is at any other node/frame
                AFrameBB newFrame = new AFrameBB();
                current.getPrevFrame().setNextFrame(newFrame);
                newFrame.setPrevFrame(current.getPrevFrame());
                newFrame.setNextFrame(current);
                current.setPrevFrame(newFrame);
                newFrame.setContent("");
                current.setContent(this.getText());
                this.setText(newFrame.getContent());
                current = newFrame;
            }
        }
    }
    public void delete(){//delete current node/frame and goes to the previous node/frame

        if(head.getNextFrame()==null&&current==head){//if there only exists one node/frame does nothing

        }else if(head.getContent()==null){//if head node/frame has not been edited does nothing

        }else{
            if(current==head){//if current is at the head node/frame
                head=head.getNextFrame();
                head.setPrevFrame(null);
                current=head;
                this.setText(current.getContent());
            }else if(current.getNextFrame()==null){//if current is at the last node/frame
                current.getPrevFrame().setNextFrame(null);
                current=current.getPrevFrame();
                this.setText(current.getContent());
            }
            else{//if current is at any middle node/frame
                current.getNextFrame().setPrevFrame(current.getPrevFrame());
                current.getPrevFrame().setNextFrame(current.getNextFrame());
                current=current.getPrevFrame();
                this.setText(current.getContent());
            }
        }
    }
}



