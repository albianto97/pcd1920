package pcd.ass02.sol.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ViewModelNode {

	private Concept concept;
	private double weight;
	private P2d pos;
	private List<ViewModelNode> linked;
	
	public ViewModelNode(Concept concept, P2d pos, double weight) {
		this.pos = pos;
		this.weight = weight;
		this.concept = concept;
		linked = new LinkedList<ViewModelNode>();
	}
	
	public void addLinked(ViewModelNode node) {
		synchronized (linked) {
			linked.add(node);
		}
	}

	public void addLinkedIfNotPresent(ViewModelNode node) {
		synchronized (linked) {
			if (linked.indexOf(node) == -1) {
				linked.add(node);
			}
		}
	}
	
	public boolean equals(Object obj) {
		return ((ViewModelNode) obj).concept == concept;
	}

	public Concept getConcept() {
		return concept;
	}

	public double getWeight() {
		return weight;
	}

	public P2d getPos() {
		return pos;
	}

	public void setPos(P2d pos) {
		this.pos = pos;
	}

	public List<ViewModelNode> getLinked() {
		synchronized (linked) {
			List<ViewModelNode> copy = new ArrayList<>();
			copy.addAll(linked);
			return copy;
		}
	}
	
	
}
