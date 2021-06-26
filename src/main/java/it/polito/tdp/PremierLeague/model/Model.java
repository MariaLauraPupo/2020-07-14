package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private SimpleDirectedGraph<Team,DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Integer,Team> idMap;
	
	public Model() {
		dao = new PremierLeagueDAO();
		idMap = new HashMap<Integer,Team>();
		dao.listAllTeams(idMap);
	}
	public void creaGrafo(){
		grafo = new SimpleDirectedGraph<Team,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		//vertici
		Graphs.addAllVertices(grafo, idMap.values());
		//archi
		for(Arco a : dao.getArchi(idMap)) {
			if(grafo.containsVertex(a.getT1()) &&  grafo.containsVertex(a.getT2())) {
				DefaultWeightedEdge e = this.grafo.addEdge(a.getT1(), a.getT2());
				if(e==null) {
					if(a.getPeso()<0) {
						Graphs.addEdgeWithVertices(grafo, a.getT2(), a.getT1(), ((double) -1)*a.getPeso());
					}else if(a.getPeso()>0) {
						Graphs.addEdgeWithVertices(grafo, a.getT1(), a.getT2(), a.getPeso());
					}
				}
			}
		}
		System.out.println("vertici " + grafo.vertexSet().size());
		System.out.println("archi " + grafo.edgeSet().size());

	}
	
}
