// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.extensions.algs4.AdjMatrixEdgeWeightedDigraph;
import graphtea.extensions.algs4.DirectedEdge;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.HashSet;
import java.util.Set;

/**
 * @author azin azadi

 */


@CommandAttitude(name = "eccentricitycomplexity_index", abbreviation = "_Ecomplexindex")
public class EccentricityComplexityIndex implements GraphReportExtension<Integer> {
    public String getName() {
        return "E-complex Index";
    }

    public String getDescription() {
        return "E-complex Index";
    }

    public Integer calculate(GraphModel g) {
        AdjMatrixEdgeWeightedDigraph G = new AdjMatrixEdgeWeightedDigraph(g.numOfVertices());
        for(Edge e : g.edges()) {
            G.addEdge(new DirectedEdge(e.source.getId(), e.target.getId(), 1d));
            G.addEdge(new DirectedEdge(e.target.getId(), e.source.getId(), 1d));
        }

        FloydWarshall fw = new FloydWarshall();
        Integer[][] spt = fw.getAllPairsShortestPathWithoutWeight(g);
    	Integer vEccentricity;
    	Set<Integer> uniqueEccentricities = new HashSet<>();

        for (int v = 0; v < G.V(); v++) {
        	vEccentricity = 0;
            for (int u = 0; u < G.V(); u++) {
            	if (v == u) {
            		continue;
            	}
                if(spt[v][u] < g.numOfVertices() + 1) {
                	Integer dist = spt[u][v];
                    if(dist > vEccentricity) {
                    	vEccentricity = dist;                    	
                    }
                }
            }
            
            if (vEccentricity > 0) {
            	uniqueEccentricities.add(vEccentricity);
            }
        }

        return uniqueEccentricities.size();
    }

	@Override
	public String getCategory() {
		return "Topological Indices";
	}
}