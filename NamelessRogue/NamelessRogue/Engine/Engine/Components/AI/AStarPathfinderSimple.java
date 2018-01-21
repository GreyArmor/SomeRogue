package Engine.Components.AI;

import Engine.Components.Physical.Position;
import Engine.Tile;
import abstraction.IWorldProvider;
import com.jogamp.nativewindow.util.Point;

import java.util.ArrayList;
import java.util.Collections;

public class AStarPathfinderSimple {

    private class SearchNode
    {
        public SearchNode Parent;
        public Point NodePosition;

        private SearchNode(SearchNode parent, Point nodePosition) {
            Parent = parent;
            NodePosition = nodePosition;
        }
    }

    private int CalculateManhattanDistance(Point a, Point b)
    {
            return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - a.getY());
    }

    private SearchNode FindClosest(Point destination){
        SearchNode closest = openList.stream().findFirst().get();
        int distanceToClosest = CalculateManhattanDistance(closest.NodePosition, destination);
        for (SearchNode node : openList){
            int distanceToCurrent = CalculateManhattanDistance(node.NodePosition, destination);
            if(distanceToClosest > distanceToCurrent)
            {
                closest = node;
                distanceToClosest = distanceToCurrent;
            }
        }
        return closest;
    }

    private void AddNeighborsToOpenList(SearchNode node, IWorldProvider world)
    {
        for (int x = node.NodePosition.getX() - 1; x<=node.NodePosition.getX()+1; x++)
        {
            for (int y = node.NodePosition.getY() - 1; y<=node.NodePosition.getY()+1; y++) {
                Point currentposition = new Point(x,y);
                boolean isInClosed = closedList.stream().anyMatch(n -> n.NodePosition.equals(currentposition));
                boolean isInOpen = openList.stream().anyMatch(n -> n.NodePosition.equals(currentposition));
                if (!isInClosed && !isInOpen) {
                    Tile t = world.getTile(x,y);
                    if(t.getPassable()) {
                        openList.add(new SearchNode(node, currentposition));
                    }
                    else
                    {
                        closedList.add(new SearchNode(node, currentposition));
                    }
                }
            }
        }
    }

    ArrayList<SearchNode> openList;
    ArrayList<SearchNode> closedList;
    public ArrayList<Point> FindPath(Point start, Point destination, IWorldProvider world){
        openList = new ArrayList<>();
        closedList = new ArrayList<>();

        openList.add(new SearchNode(null, start));

        while (openList.stream().count()>0)
        {
            SearchNode closestNode = FindClosest(destination);
            openList.remove(closestNode);
            closedList.add(closestNode);
            if(closestNode.NodePosition.equals(destination))
            {
                return ConstructPath(closestNode);
            }
            AddNeighborsToOpenList(closestNode, world);
            if (closedList.stream().count()>300)
            {
                return null;
            }
        }
        return null;

    }

    //excluding the start point
    private ArrayList<Point> ConstructPath(SearchNode closestNode) {
        ArrayList<Point> path = new ArrayList<>();
        SearchNode node = closestNode;
        while (node.Parent!=null){
            path.add(node.NodePosition);
            node = node.Parent;
        }
        Collections.reverse(path);
        return path;
    }
}
