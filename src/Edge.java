import java.awt.*;

public class Edge
{
    Vertex a;
    Vertex b;
    double length;
    Color color = Color.BLACK;
    public Edge (Vertex a, Vertex b, double length)
    {
        this.a = a;
        this.b = b;
        this.length = length;
    }

    public double getLength()
    {
        return length;
    }

    public void highlight(boolean doHighlight)
    {
        color = doHighlight ? Color.ORANGE:Color.BLACK;
    }

    public Vertex getA()
    {
        return a;
    }

    public Vertex getB()
    {
        return b;
    }

    public Color getColor()
    {
        return color;
    }

    public boolean hasVertNums(int n1, int n2)
    {
        return (a.getNum() == n1 || a.getNum() == n2) &&
                (b.getNum() == n1 || b.getNum() == n2);
    }

    public String toString()
    {
        return ("[" + a.getNum() + ", " + b.getNum() + "]");
    }
}
