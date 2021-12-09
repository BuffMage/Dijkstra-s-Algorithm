import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class DijkstraWindow extends JComponent
{
    static int x = 100;
    static Vertex [] vertSet = new Vertex[8];
    static Edge [] edgeSet = new Edge[11];
    public static void main(String [] args) throws InterruptedException
    {
        vertSet[0] = new Vertex(400, 300, 0);
        vertSet[1] = new Vertex(400, 200, 1);
        vertSet[2] = new Vertex(300, 200, 2);
        vertSet[3] = new Vertex(300, 300, 3);
        vertSet[4] = new Vertex(200, 300, 4);
        vertSet[5] = new Vertex(200, 200, 5);
        vertSet[6] = new Vertex(100, 200, 6);
        vertSet[7] = new Vertex(100, 300, 7);

        edgeSet[0] = new Edge(vertSet[0], vertSet[1], 1);
        edgeSet[1] = new Edge(vertSet[1], vertSet[2], 1);
        edgeSet[2] = new Edge(vertSet[2], vertSet[3], 1);
        edgeSet[3] = new Edge(vertSet[3], vertSet[4], 1);
        edgeSet[4] = new Edge(vertSet[4], vertSet[5], 1);
        edgeSet[5] = new Edge(vertSet[5], vertSet[6], 1);
        edgeSet[6] = new Edge(vertSet[6], vertSet[7], 1);
        edgeSet[7] = new Edge(vertSet[0], vertSet[2], Math.sqrt(2));
        edgeSet[8] = new Edge(vertSet[1], vertSet[3], Math.sqrt(2));
        edgeSet[9] = new Edge(vertSet[2], vertSet[5], 1);
        edgeSet[10] = new Edge(vertSet[0], vertSet[3], 1);

        JFrame frame = new JFrame("Dijkstra's Algorithm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new DijkstraWindow());
        frame.pack();
        frame.setSize(new Dimension(500, 440));
        frame.setVisible(true);
        double [] vertDists = new double[vertSet.length];
        int [] prevVerts = new int[vertSet.length];
        for (int i = 0; i < vertDists.length; i++)
        {
            //Arbitrarily large number
            vertDists[i] = 1000;
        }
        vertDists[0] = 0;

        double currDist = 0;
        Vertex currentVert = vertSet[0];
        while (currentVert.getNum() != 7)
        {
            ArrayList<Edge> incEdges = findIncidentEdges(currentVert);
            ArrayList<Edge> notVisited = removeVisited(incEdges, currentVert);
            Edge closestEdge = findLeastDistance(notVisited);
            //closestEdge.highlight(true);
            currentVert.visited(true);

            //Calculate new distances and check to see if we need to update
            for (Edge e : notVisited)
            {
                Vertex otherVert = getOtherVert(e, currentVert);
                if (vertDists[otherVert.getNum()] > currDist + e.length)
                {
                    vertDists[otherVert.getNum()] = currDist + e.length;
                    prevVerts[otherVert.getNum()] = currentVert.getNum();
                }
            }


            for (int i = 0; i < vertDists.length - 1; i++)
            {
                System.out.print(vertDists[i] + ", ");
            }
            System.out.println(vertDists[vertDists.length - 1]);

            for (int i = 0; i < vertDists.length - 1; i++)
            {
                System.out.print(prevVerts[i] + ", ");
            }
            System.out.println(prevVerts[vertDists.length - 1]);

            highlightEdges(notVisited);
            currentVert.highlight(true);
            frame.repaint();

            if (closestEdge.getA().getNum() == currentVert.getNum())
            {
                currentVert = closestEdge.getB();
            }
            else
            {
                currentVert = closestEdge.getA();
            }
            currDist = vertDists[currentVert.getNum()];

            Thread.sleep(1000);
            resetEdgeHighlight();
            frame.repaint();
        }
        resetAllHighlight();
        frame.repaint();
        ArrayList<Edge> path = getPath(prevVerts, edgeSet, vertSet[0], vertSet[vertSet.length - 1]);
        System.out.println(path);
        Thread.sleep(2000);
        vertSet[0].highlight(true);
        frame.repaint();
        Thread.sleep(2000);
        for (int i = path.size() - 1; i >= 0; i--)
        {
            path.get(i).highlight(true);
            path.get(i).getA().highlight(true);
            path.get(i).getB().highlight(true);
            frame.repaint();
            Thread.sleep(2000);
        }
    }

    public static Vertex getOtherVert(Edge e, Vertex vert)
    {
        if (e.getA().getNum() == vert.getNum())
        {
            return e.getB();
        }
        return e.getA();
    }

    public static ArrayList<Edge> findIncidentEdges(Vertex vert)
    {
        ArrayList<Edge> incidentEdges = new ArrayList<Edge>();
        for (int i = 0; i < edgeSet.length; i++)
        {
            if (edgeSet[i].getA().getNum() == vert.getNum() || edgeSet[i].getB().getNum() == vert.getNum())
            {
                incidentEdges.add(edgeSet[i]);
            }
            /*
            else if (edgeSet[i].getB().getNum() == vert.getNum())
            {
                incidentEdges.add(new Edge(edgeSet[i].getB(), edgeSet[i].getA(), edgeSet[i].getLength()));
            }*/
        }
        return incidentEdges;
    }

    public static Edge findLeastDistance(ArrayList<Edge> edgeList)
    {
        double min = Double.MAX_VALUE;
        Edge minEdge = null;
        for (Edge e : edgeList)
        {
            if (e.getLength() < min)
            {
                min = e.getLength();
                minEdge = e;
            }
        }
        return minEdge;
    }

    public static ArrayList<Edge> removeVisited(ArrayList<Edge> edgeList, Vertex currentVert)
    {
        ArrayList<Edge> notVisited = new ArrayList<>();
        for (Edge e : edgeList)
        {
            if (!e.getA().isVisited() && e.getA().getNum() != currentVert.getNum())
            {
                notVisited.add(e);
            }
            else if (!e.getB().isVisited() && e.getB().getNum() != currentVert.getNum())
            {
                notVisited.add(e);
            }
        }
        return notVisited;
    }

    public static ArrayList<Edge> getPath(int[] prevVerts, Edge[] edgeList, Vertex startVert, Vertex endVert)
    {
        ArrayList<Edge> path = new ArrayList<>();
        Edge currEdge;
        int currVert = endVert.getNum();
        int nextVert = prevVerts[currVert];
        do
        {
            for (int i = 0; i < edgeList.length; i++)
            {
                if (edgeList[i].hasVertNums(currVert, nextVert))
                {
                    path.add(edgeList[i]);

                    break;
                }
            }
            currVert = nextVert;
            nextVert = prevVerts[currVert];
        } while(currVert != startVert.getNum());
        return path;
    }

    public static void resetAllHighlight()
    {
        for (int i = 0; i < vertSet.length; i++)
        {
            vertSet[i].highlight(false);
        }

        for (int i = 0; i < edgeSet.length; i++)
        {
            edgeSet[i].highlight(false);
        }
    }

    public static void resetEdgeHighlight()
    {
        for (int i = 0; i < edgeSet.length; i++)
        {
            edgeSet[i].highlight(false);
        }
    }

    public static void highlightEdges(ArrayList<Edge> edges)
    {
        for (Edge e : edges)
        {
            e.highlight(true);
        }
    }

    @Override
    public void paint(Graphics g) {
        // Draw a simple line using the Graphics2D draw() method.
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2f));

        for (int i = 0; i < edgeSet.length; i++)
        {
            g2.setColor(edgeSet[i].getColor());
            g2.draw(new Line2D.Double(edgeSet[i].a.getX() + 10, edgeSet[i].a.getY() + 10, edgeSet[i].b.getX() + 10, edgeSet[i].b.getY() + 10));
        }

        for (int i = 0; i < vertSet.length; i++)
        {
            g2.setColor(vertSet[i].getColor());
            Ellipse2D.Double circle = new Ellipse2D.Double(vertSet[i].getX(), vertSet[i].getY(), 20, 20);
            g2.draw(circle);
            g2.fill(circle);
            g2.setColor(Color.WHITE);
            g2.drawString(String.valueOf(vertSet[i].getNum()), vertSet[i].getX() + 6, vertSet[i].getY() + 13);
        }

    }
}
