package it.polito.tdp.extflightdelays.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	//Da aggiungere i pesi al grafo
	public Graph<Airport,DefaultWeightedEdge> grafo;

	public void creaGrafo(int distance) {
		grafo=new SimpleWeightedGraph<Airport,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		ExtFlightDelaysDAO dao=new ExtFlightDelaysDAO();
		
		List<Airport> areoporti=dao.loadAllAirports();
		
		Graphs.addAllVertices(grafo, areoporti);
		
		for(Airport andata: areoporti) {
			for(Airport ritorno: areoporti) {
				Integer avgDistance=0;
				if(dao.isAirportConnected(distance,andata,ritorno,avgDistance)) {
					//grafo.setEdgeWeight(grafo.addEdge(andata, ritorno), avgDistance);
					DefaultWeightedEdge edge=this.grafo.getEdge(ritorno, andata);
					if(edge==null) {
						Graphs.addEdge(this.grafo, ritorno, andata, avgDistance);
					}else {
						double peso=this.grafo.getEdgeWeight(edge);
						this.grafo.setEdgeWeight(edge, avgDistance);
					}
					
				}
			}
		}
	}
	public String info() {
		return "Grafo creato:\n#Vertici: "+this.grafo.vertexSet().size()+"\n#Archi: "
	+this.grafo.edgeSet().size()+"\n";
	}
	
}
