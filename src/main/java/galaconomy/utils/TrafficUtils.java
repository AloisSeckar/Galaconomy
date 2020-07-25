package galaconomy.utils;

import galaconomy.universe.UniverseManager;
import galaconomy.universe.map.*;
import java.util.*;
import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;

public class TrafficUtils {
    
    private static Graph<Star, Connector> universeMap;
    
    private static void buildUniverseMap() {
        Collection<Star> stars = UniverseManager.getInstance().getStars().values();
        List<Connector> gates = UniverseManager.getInstance().getGates();
        universeMap = new SimpleWeightedGraph<>(Connector.class);
        
        stars.forEach((star) -> {
            universeMap.addVertex(star);
        });
        gates.forEach((route) -> {
            universeMap.addEdge(route.getPoint1(), route.getPoint2(), route);
            universeMap.setEdgeWeight(route, route.getDistance());
        });
    }
    
    public static List<Star> getPath(Star from, Star to) {
        if (universeMap == null) {
            buildUniverseMap();
        }
        
        GraphPath<Star, Connector> path = DijkstraShortestPath.findPathBetween(universeMap, from, to);
        return path.getVertexList();
    }
    
}
