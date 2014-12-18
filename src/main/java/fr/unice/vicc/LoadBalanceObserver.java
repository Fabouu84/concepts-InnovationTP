package fr.unice.vicc;

import java.util.List;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.core.SimEvent;
import org.cloudbus.cloudsim.power.PowerHost;

public class LoadBalanceObserver extends SimEntity {
    public static final int OBSERVE = 728070;
    private List<PowerHost> hosts;
    private float delay;
    public static final float DEFAULT_DELAY = 1;

    public LoadBalanceObserver(List<PowerHost> hosts){
    	this(hosts, DEFAULT_DELAY);
    }
    
	public LoadBalanceObserver(List<PowerHost> hosts, float delay) {
        super("LoadBalanceObserver");
        this.hosts = hosts;
        this.delay = delay;
	}
	
	@Override
	public void startEntity() {
        Log.printLine(getName() + " démaré...");
        send(this.getId(), delay, OBSERVE, null);	
	}

	@Override
	public void processEvent(SimEvent ev) {
		switch(ev.getTag()) {
        case OBSERVE:
        	double maxMIPS = 0, minMIPS = 0, ecartMIPS = 0;
        	for(Host cHost : hosts){
        		double cMIPS = cHost.getAvailableMips();
        		if(maxMIPS == 0 && minMIPS == 0){
        			maxMIPS = minMIPS = cMIPS;
        		}
        		else{
        			if(cMIPS > maxMIPS){
        				maxMIPS = cMIPS;
        			}
        			if(cMIPS < minMIPS){
        				minMIPS = cMIPS;
        			}
        		}
        	}
        	ecartMIPS = maxMIPS - minMIPS;
        	Log.printLine("MIPS actuelle : "+ecartMIPS);
            //Observation loop, re-observe in `delay` seconds
            send(this.getId(), delay, OBSERVE, null);
		}	
	}

	@Override
	public void shutdownEntity() {
		Log.printLine(getName() + " arrêté...");
	}
}