package fr.unice.vicc;

import java.util.List;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.core.SimEvent;
import org.cloudbus.cloudsim.power.PowerHost;

public class AntiAffinityObserver extends SimEntity {
    public static final int OBSERVE = 728080;
    private List<PowerHost> hosts;
    private float delay;
    public static final float DEFAULT_DELAY = 1;

    public AntiAffinityObserver(List<PowerHost> hosts){
    	this(hosts, DEFAULT_DELAY);
    }
    
	public AntiAffinityObserver(List<PowerHost> hosts, float delay) {
        super("AntiAffinityObserver");
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
		//TODO Apporter amélioration pour vitesse d'exécution
        case OBSERVE: 
        	int x, y;
        	for(Host cHost : hosts){
	        	if(cHost.getVmList().size() > 1){ // Si il y a au moins 2 Vms sur le serveur, on compare toutes les Vms entre elles
	        		for(int i=0;i<cHost.getVmList().size()-1;i++){
	        			Vm cVm1 = cHost.getVmList().get(i);
	        			x = cVm1.getId()/100;
	        			for(int j=i+1;j<cHost.getVmList().size();j++){
	        				Vm cVm2 = cHost.getVmList().get(j);
	        				y = cVm2.getId()/100;
	        				if(x == y){
	        					//System.out.println("Erreur sur le host avec l'Id : "+cHost.getId());
	        					//System.out.println("     Vms avec id : "+cVm1.getId()+" en conflit avec "+cVm2.getId());
	        					Log.printLine("Erreur sur le host avec l'Id : "+cHost.getId());
	        					Log.printLine("     Vms avec id : "+cVm1.getId()+" en conflit avec "+cVm2.getId());
	        				}
	        			}
	        		}
				}
        	}
            //Observation loop, re-observe in `delay` seconds
            send(this.getId(), delay, OBSERVE, null);
		}	
	}

	@Override
	public void shutdownEntity() {
		Log.printLine(getName() + " arrêté...");
	}

}
