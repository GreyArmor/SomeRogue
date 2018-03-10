package Engine.Components.AI.NonPlayerCharacter;

import Engine.Components.ChunksAndTiles.Tile;
import abstraction.IChunkProvider;
import com.jogamp.nativewindow.util.Point;

import java.util.ArrayList;
import java.util.Collections;

public class AStarPathfinderSimple {

    private class SearchNode
    {
        public SearchNode Parent;
        public Point NodePosition;
        public int DinstanceToStartingNode;
        public int DinstanceToDestinationNode;

        private SearchNode(SearchNode parent, Point nodePosition, Point destination) {
            Parent = parent;
            NodePosition = nodePosition;
            if(parent==null) {
                DinstanceToStartingNode = 0;
            }
            else
            {
                DinstanceToStartingNode = parent.DinstanceToStartingNode+1;
            }

            DinstanceToDestinationNode = CalculateManhattanDistance(nodePosition, destination);

        }

        private int CalculateManhattanDistance(Point a, Point b)
        {
            return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
        }

    }



    private SearchNode FindClosest(){
        SearchNode closest = openList.stream().findFirst().get();
        int distanceToClosest = closest.DinstanceToDestinationNode + closest.DinstanceToStartingNode;
        for (SearchNode node : openList){
            int distanceToCurrent = node.DinstanceToDestinationNode + node.DinstanceToStartingNode;
            if(distanceToClosest > distanceToCurrent)
            {
                closest = node;
                distanceToClosest = distanceToCurrent;
            }
        }
        return closest;
    }

    private void AddNeighborsToOpenList(SearchNode node, IChunkProvider world, Point destination)
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
                        openList.add(new SearchNode(node, currentposition, destination));
                    }
                    else
                    {
                        closedList.add(new SearchNode(node, currentposition, destination));
                    }
                }
            }
        }
    }

    ArrayList<SearchNode> openList;
    ArrayList<SearchNode> closedList;
    boolean seachNearPosiiton;
    public ArrayList<Point> FindPath(Point start, Point destination, IChunkProvider world, boolean positionNear){
        seachNearPosiiton = positionNear;
        openList = new ArrayList<>();
        closedList = new ArrayList<>();

        openList.add(new SearchNode(null, start,destination));

        while (openList.stream().count()>0)
        {
            SearchNode closestNode = FindClosest();
            openList.remove(closestNode);
            closedList.add(closestNode);
            if(closestNode.NodePosition.equals(destination))
            {
                return ConstructPath(closestNode);
            }

            if(seachNearPosiiton && closestNode.DinstanceToDestinationNode==1)
            {
                return ConstructPath(closestNode);
            }
            AddNeighborsToOpenList(closestNode, world, destination);
            if (closedList.stream().count()>500)
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
