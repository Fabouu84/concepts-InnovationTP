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
        case OBSERVE:
        	int x, y;
        	
        	for(Host cHost : hosts){
	        	if(cHost.getVmList().size() != 0){
					Boolean condition = true; //Permet de savoir si je peux créer ma VM sur ce serveur
					x = vm.getId()/100;
					for(Vm cVm : cHost.getVmList()){ //On boucle sur la liste des VMs présente sur le serveur
						y = cVm.getId()/100;
						if(x == y){
							condition = false;
							break;
						}
					}
					if(condition){
						if(cHost.vmCreate(vm)){
							System.out.println("Vm avec id : "+vm.getId()+" créé sur le host avec id : "+cHost.getId());
							return true;
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
