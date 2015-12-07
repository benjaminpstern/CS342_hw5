package mocknet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import mocknet.Control.QuitException;
/**
 * Implementation of Dijkstra's algorithm
 * @author you
 *
 */
public class Bellman implements Algorithm {
    final public static int INFINITY = Integer.MAX_VALUE - 5000;	
    private Collection<Router> routers;
    private Control control;
    private Iterator<Router> sit;
    private Vector<Router> targets;
    // HashSet open hold all routers yet to be processed
    private HashSet<Router> open = null;
    private Router target = null;	

    public Bellman(Control control, Collection<Router> routers) {
        this.control = control;
        this.routers = routers;	
    }
    @Override
        // Called when running non-single-step	
        public void go() {
            try {
                for (Router source : routers) {
                    shortestPath(source, true);
                }
            } catch (QuitException ex) {}
        }
    @Override
        // Initializes single step mode	
        public void initStep() {
            target = null;
            targets = new Vector<>();
            targets.addAll(routers);
            open = null;
            sit = targets.iterator();
        }

    @Override
        // Called when single stepping	
        public void step() {
            if (target == null || open.size() == 0) {
                control.stopAlgorithm();
                if (!sit.hasNext()) return;
                target = sit.next();
                open = initLoop(target, true);
                sit.remove();
                control.startAlgorithm(target);
            }
            // select random router
            Router r = open.toArray(new Router[0])[0];
            open.remove(r);
            try {
                shortestPath(r, target, open);
            } catch (QuitException e) {}
            control.getParent().repaint();
        }

    // ---------------------- You should not have to touch anything above this line ------------------------

    private void shortestPath(Router target, boolean hard) throws QuitException {
        control.startAlgorithm(target);
        // hard is true when this is computing entire algorithm; false when called by redo after link change	    
        // Your code here
        control.stopAlgorithm();
    }

    private void shortestPath(Router router, Router target, HashSet<Router> open) throws QuitException {
        // Your code here
        control.showAlgorithm(router);
    }

    private HashSet<Router> initLoop(Router target, boolean hard) {
        // hard is true when this is computing entire algorithm; false when called by redo after link change
        // Your code here	   
        return null;
    }

    private void initRouters(Router target, boolean hard) {
        // hard is true when this is computing entire algorithm; false when called by redo after link change	    
        if (hard) {
            target.costFrom = new HashMap<Router, Integer>;
            for (Router source : routers) {
                if (source == target) target.costFrom.put(source, 0);
                else target.costFrom.put(source, INFINITY);
                // ??? open.add(source);
            }
        }
        else {
        }
    }

    // ---------------------- You should not have to touch anything below this line ------------------------
    @Override
        public void redo(Collection<Router> routers) {
            // Called with affected routers when a link changes.
            // Note:  calls shortestPath with "hard" value of false;
            try {
                for (Router router : routers) {
                    shortestPath(router, false);
                }
            } catch (QuitException ex) {}
        }
    @Override
        public Collection<Router> getOpen() {return open;}
}
