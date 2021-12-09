import java.awt.*;

public class Vertex
{
    private int x;
    private int y;
    private int vertNum;
    private Color color = Color.BLUE;
    private boolean isVisited = false;

    public Vertex(int x, int y, int vertNum)
    {
        this.x = x;
        this.y = y;
        this.vertNum = vertNum;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getNum()
    {
        return vertNum;
    }

    public void highlight(boolean doHighlight)
    {
        color = doHighlight ? Color.ORANGE:Color.BLUE;
    }

    public Color getColor()
    {
        return color;
    }

    public boolean isVisited()
    {
        return isVisited;
    }

    public void visited(boolean visit)
    {
        isVisited = visit;
    }
}
