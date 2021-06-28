package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private SimpleDirectedWeightedGraph<Team,DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Integer,Team> idMap;
	
	public Model() {
		dao = new PremierLeagueDAO();
		idMap = new HashMap<Integer,Team>();
		dao.listAllTeams(idMap);
	}
	public void creaGrafo(){
		grafo = new SimpleDirectedWeightedGraph<Team,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		//vertici
		Graphs.addAllVertices(grafo, idMap.values());
		for(Team t : this.grafo.vertexSet()) {
			dao.setVittorie(t);
			dao.setPareggi(t);
		}
		//archi
		for(Team t1: this.grafo.vertexSet()) {
			for(Team t2 : this.grafo.vertexSet()) {
				if(t1.getTeamID() != t2.getTeamID()) {
				if(t1.getPunteggio() > t2.getPunteggio()) {
					Graphs.addEdgeWithVertices(grafo, t1, t2, t1.getPunteggio() - t2.getPunteggio());
				}else if(t2.getPunteggio() > t1.getPunteggio()) {
					Graphs.addEdgeWithVertices(grafo, t2, t1, t2.getPunteggio() - t1.getPunteggio());

				}
				
			}
			}
		}
		
		System.out.println("vertici " + grafo.vertexSet().size());
		System.out.println("archi " + grafo.edgeSet().size());

	}
	
}
